
package com.example.IAM_SERVER.Keys.adapters.out.JwtAccessTokenAdapters;

import com.example.IAM_SERVER.Keys.Dtos.AccessTokenResultDto;
import com.example.IAM_SERVER.Keys.domain.exceptions.InvalidJwtTokenException;
import com.example.IAM_SERVER.Keys.domain.model.ActiveKey;
import com.example.IAM_SERVER.Keys.domain.model.AdminJwtHeader;

import com.example.IAM_SERVER.Keys.domain.model.JwtClaims;
import com.example.IAM_SERVER.Keys.domain.ports.out.JwtSigner;
import com.example.IAM_SERVER.Keys.domain.ports.out.PlatformAdminJwtService;
import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminData;
import com.example.IAM_SERVER.platformAdmin.domain.port.out.Repository;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class PlatformAdminJwtServiceImpl
        implements PlatformAdminJwtService {

    private final JwtSigner<SignedJWT> jwtSigner;
    private final ActiveKey activeKey;
    private final Rs256JwtVerifier verifier;
    private final Repository adminrepository;

    public PlatformAdminJwtServiceImpl(
            JwtSigner<SignedJWT> jwtSigner,
            ActiveKey activeKey,
            Rs256JwtVerifier verifier,
            Repository adminrepository
    ) {
        this.jwtSigner = jwtSigner;
        this.activeKey = activeKey;
        this.verifier = verifier;
        this.adminrepository = adminrepository;
    }

    @Override
    public AccessTokenResultDto generateToken(UUID subjectId) {

        // 1. Create JWT header
        AdminJwtHeader AdminJwtheader = new AdminJwtHeader(
                "RS256",
                activeKey.key(),
                "JWT"
        );

        // 2. Create standard JWT claims
        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiresAt = now.plusSeconds(900);

        JwtClaims claims = new JwtClaims(
                "iam-server",
                subjectId.toString(),
                "iam-platform",
                now,
                expiresAt,
                null,
                UUID.randomUUID().toString()
        );

        // 3. Convert application header to Nimbus header
        JWSHeader nimbusHeader = new JWSHeader.Builder(
                JWSAlgorithm.parse(AdminJwtheader.algorithm())
        )
                .keyID(AdminJwtheader.keyId())
                .type(new JOSEObjectType(AdminJwtheader.type()))
                .build();

        // 4. Construct Nimbus claims
        JWTClaimsSet.Builder claimsBuilder =
                new JWTClaimsSet.Builder()
                        .issuer(claims.issuer())
                        .subject(claims.subject())
                        .audience(claims.audience())
                        .issueTime(Date.from(claims.issuedAt()))
                        .expirationTime(Date.from(claims.expirationTime()))
                        .jwtID(claims.jwtId());

        // 5. Create SignedJWT
        SignedJWT signedJWT = new SignedJWT(
                nimbusHeader,
                claimsBuilder.build()
        );

        // 6. Delegate signing
        String signedToken = jwtSigner.sign(signedJWT);

        return new AccessTokenResultDto(
                signedToken,
                expiresAt
        );
    }

    @Override
    public PlatformAdminData validateToken(String token) {

        // 1. Verify the cryptographic signature first.
        // This must throw an exception if verification fails.
        verifier.verify(token);

        try {

            // 2. Parse the JWT after signature verification.
            SignedJWT signedJWT = SignedJWT.parse(token);

            // 3. Validate the signing algorithm.
            if (!JWSAlgorithm.RS256.equals(
                    signedJWT.getHeader().getAlgorithm())) {

                throw new InvalidJwtTokenException(
                        "Invalid JWT algorithm"
                );
            }

            // 4. Validate the token type.
            if (!JOSEObjectType.JWT.equals(
                    signedJWT.getHeader().getType())) {

                throw new InvalidJwtTokenException(
                        "Invalid JWT type"
                );
            }

            // 5. Extract claims.
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            Instant now = Instant.now();

            // 6. Validate issuer.
            if (!"iam-server".equals(claims.getIssuer())) {

                throw new InvalidJwtTokenException(
                        "Invalid token issuer"
                );
            }

            // 7. Validate audience.
            if (claims.getAudience() == null ||
                    !claims.getAudience().contains("iam-platform")) {

                throw new InvalidJwtTokenException(
                        "Invalid token audience"
                );
            }

            // 8. Validate issued-at.
            Date issuedAt = claims.getIssueTime();

            if (issuedAt == null ||
                    issuedAt.toInstant().isAfter(now.plusSeconds(60))) {

                throw new InvalidJwtTokenException(
                        "Invalid token issued-at time"
                );
            }

            Instant issuedAtInstant = issuedAt.toInstant();

            // 9. Validate expiration.
            Date expiration = claims.getExpirationTime();

            if (expiration == null ||
                    !now.isBefore(expiration.toInstant())) {

                throw new InvalidJwtTokenException(
                        "Token expired"
                );
            }

            Instant expirationInstant = expiration.toInstant();

            // 10. Validate token lifetime.
            if (!expirationInstant.isAfter(issuedAtInstant)) {

                throw new InvalidJwtTokenException(
                        "Invalid token lifetime"
                );
            }

            if (expirationInstant.isAfter(
                    issuedAtInstant.plusSeconds(900))) {

                throw new InvalidJwtTokenException(
                        "Token lifetime exceeded"
                );
            }

            // 11. Validate JWT ID.
            String jwtId = claims.getJWTID();

            if (jwtId == null || jwtId.isBlank()) {

                throw new InvalidJwtTokenException(
                        "Missing JWT ID"
                );
            }

            // 12. Validate subject.
            String subject = claims.getSubject();

            if (subject == null || subject.isBlank()) {

                throw new InvalidJwtTokenException(
                        "Missing token subject"
                );
            }

            UUID subjectId;

            try {
                subjectId = UUID.fromString(subject);

            } catch (IllegalArgumentException exception) {

                throw new InvalidJwtTokenException(
                        "Invalid token subject"
                );
            }

            // 13. Retrieve the platform admin.
            return adminrepository.findPlatformAdminById(subjectId)
                    .orElseThrow(() ->
                            new InvalidJwtTokenException(
                                    "Platform admin not found"
                            )
                    );

        } catch (ParseException exception) {

            throw new InvalidJwtTokenException(
                    "Unable to parse JWT"
            );
        }
    }
}
