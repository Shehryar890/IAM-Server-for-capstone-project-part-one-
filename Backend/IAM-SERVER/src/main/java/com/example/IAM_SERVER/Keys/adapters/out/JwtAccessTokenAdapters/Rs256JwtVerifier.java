package com.example.IAM_SERVER.Keys.adapters.out.JwtAccessTokenAdapters;


import com.example.IAM_SERVER.Keys.Utility.RsaPublicKeyConverterUtility;
import com.example.IAM_SERVER.Keys.domain.exceptions.InvalidJwtTokenException;
import com.example.IAM_SERVER.Keys.domain.ports.out.NimbusJwtVerifier;

import com.example.IAM_SERVER.Keys.domain.ports.out.database.KeyRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

@Component
public final class Rs256JwtVerifier
        implements NimbusJwtVerifier<String> {

    private final KeyRepository keyRepository;

    public Rs256JwtVerifier(
            KeyRepository keyRepository
    ) {
        this.keyRepository = keyRepository;
    }

    @Override
    public void verify(String token) {

        // 1. Validate token input
        if (token == null || token.isBlank()) {
            throw new InvalidJwtTokenException(
                    "JWT token cannot be null or blank"
            );
        }

        // 2. Parse JWT
        SignedJWT signedJWT;

        try {
            signedJWT = SignedJWT.parse(token);

        } catch (ParseException exception) {
            throw new InvalidJwtTokenException(
                    "Invalid JWT compact format",
                    exception
            );
        }

        // 3. Validate signing algorithm
        JWSAlgorithm algorithm =
                signedJWT.getHeader().getAlgorithm();

        if (!JWSAlgorithm.RS256.equals(algorithm)) {
            throw new InvalidJwtTokenException(
                    "Unsupported JWT signing algorithm: " + algorithm
            );
        }

        // 4. Extract key ID (kid)
        String kid = signedJWT.getHeader().getKeyID();

        if (kid == null || kid.isBlank()) {
            throw new InvalidJwtTokenException(
                    "JWT header does not contain a valid key ID"
            );
        }

        // 5. Query database adapter using kid
        String publicKeyValue;

        try {
            publicKeyValue = keyRepository
                    .findPublicKeyByKid(kid)
                    .orElseThrow(() ->
                            new InvalidJwtTokenException(
                                    "No public key found for kid: " + kid
                            )
                    );

        } catch (InvalidJwtTokenException exception) {
            throw exception;

        } catch (RuntimeException exception) {
            throw new InvalidJwtTokenException(
                    "Unable to retrieve public key from database",
                    exception
            );
        }

        // 6. Convert stored public key into RSA PublicKey
        RSAPublicKey publicKey;

        try {
            publicKey =
                    RsaPublicKeyConverterUtility.fromString(
                            publicKeyValue
                    );

        } catch (IllegalArgumentException | IllegalStateException exception) {
            throw new InvalidJwtTokenException(
                    "Unable to reconstruct RSA public key",
                    exception
            );
        }

        // 7. Verify JWT signature
        verifySignature(signedJWT, publicKey);
    }

    private void verifySignature(
            SignedJWT signedJWT,
            RSAPublicKey publicKey
    ) {

        try {
            RSASSAVerifier verifier =
                    new RSASSAVerifier(publicKey);

            boolean valid = signedJWT.verify(verifier);

            if (!valid) {
                throw new InvalidJwtTokenException(
                        "JWT signature verification failed"
                );
            }

        } catch (JOSEException exception) {
            throw new InvalidJwtTokenException(
                    "An error occurred while verifying JWT signature",
                    exception
            );
        }
    }
}