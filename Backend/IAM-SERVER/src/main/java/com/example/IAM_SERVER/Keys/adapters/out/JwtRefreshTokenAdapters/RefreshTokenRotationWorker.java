package com.example.IAM_SERVER.Keys.adapters.out.JwtRefreshTokenAdapters;


import com.example.IAM_SERVER.Keys.Dtos.RefreshTokenResultDto;
import com.example.IAM_SERVER.Keys.domain.exceptions.InvalidRefreshTokenException;
import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;
import com.example.IAM_SERVER.Keys.domain.model.TokenHash;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenGeneratorPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenHasherPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenRotationWorkerPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.database.RefreshTokenRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefreshTokenRotationWorker
        implements RefreshTokenRotationWorkerPort {

    private final RefreshTokenRepositoryPort refreshTokenRepository;
    private final RefreshTokenGeneratorPort refreshTokenGenerator;
    private final RefreshTokenHasherPort refreshTokenHasher;
    private final Clock clock;

    private static final Duration REFRESH_TOKEN_LIFETIME =
            Duration.ofDays(30);

    @Override
    @Transactional
    public RefreshTokenResultDto rotate(RefreshToken existingToken) {

        // 1. Lock and recheck the old token.
        RefreshToken lockedToken = refreshTokenRepository
                .findUsableByTokenHashForUpdate(existingToken.getTokenHash())
                .orElseThrow(() -> new InvalidRefreshTokenException(
                        "Refresh token is invalid, expired, or already revoked"
                ));

        // 2. Generate replacement raw token.
        String newRawToken = refreshTokenGenerator.generateRefreshToken();

        // 3. Hash the replacement.
        TokenHash newTokenHash = refreshTokenHasher.hash(newRawToken);

        // 4. Create replacement aggregate.
        Instant now = Instant.now(clock);

        Instant expiresAt = now.plus(REFRESH_TOKEN_LIFETIME);

        RefreshToken replacementToken = new RefreshToken(
                UUID.randomUUID(),
                lockedToken.getPlatformAdminId(),
                newTokenHash,
                lockedToken.getFamilyId(),
                now,
                expiresAt,
                false,
                null
        );

        // 5. Insert replacement first because of the replaced_by FK.
        refreshTokenRepository.save(replacementToken);

        // 6. Mark old token as replaced.
        int updatedRows = refreshTokenRepository.markAsReplaced(
                lockedToken.getRefreshTokenId(),
                replacementToken.getRefreshTokenId(),
                lockedToken.getPlatformAdminId(),
                lockedToken.getFamilyId()
        );

        if (updatedRows != 1) {
            throw new IllegalStateException(
                    "Refresh token rotation failed"
            );
        }

        // 7. Return raw token and its expiry.
        return new RefreshTokenResultDto(
                newRawToken,
                expiresAt
        );
    }
}