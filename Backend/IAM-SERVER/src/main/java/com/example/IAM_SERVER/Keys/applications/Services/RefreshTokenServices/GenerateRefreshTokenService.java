
package com.example.IAM_SERVER.Keys.applications.Services.RefreshTokenServices;



import com.example.IAM_SERVER.Keys.Dtos.RefreshTokenResultDto;
import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;
import com.example.IAM_SERVER.Keys.domain.model.TokenHash;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenGeneratorPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenHasherPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.database.RefreshTokenRepositoryPort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class GenerateRefreshTokenService {

    private static final Duration REFRESH_TOKEN_LIFETIME =
            Duration.ofDays(30);

    private final RefreshTokenGeneratorPort tokenGenerator;
    private final RefreshTokenHasherPort tokenHasher;
    private final RefreshTokenRepositoryPort refreshTokenRepository;
    private final Clock clock;

    public GenerateRefreshTokenService(
            RefreshTokenGeneratorPort tokenGenerator,
            RefreshTokenHasherPort tokenHasher,
            RefreshTokenRepositoryPort refreshTokenRepository,
            Clock clock
    ) {
        this.tokenGenerator = tokenGenerator;
        this.tokenHasher = tokenHasher;
        this.refreshTokenRepository = refreshTokenRepository;
        this.clock = clock;
    }

    @Transactional
    public RefreshTokenResultDto generateFirstTimeRefreshToken(UUID platformAdminId) {

        // 1. Create a new session family.
        UUID familyId = UUID.randomUUID();

        // 2. Generate the raw refresh token.
        String rawToken = tokenGenerator.generateRefreshToken();

        // 3. Hash the raw refresh token.
        TokenHash tokenHash = tokenHasher.hash(rawToken);

        // 4. Generate token identity and timestamps.
        UUID refreshTokenId = UUID.randomUUID();

        Instant createdAt = Instant.now(clock);
        Instant expiresAt = createdAt.plus(REFRESH_TOKEN_LIFETIME);

        // 5. Create the refresh-token aggregate.
        RefreshToken refreshToken = new RefreshToken(
                refreshTokenId,
                platformAdminId,
                tokenHash,
                familyId,
                createdAt,
                expiresAt,
                false,
                null
        );

        // 6. Persist the refresh-token aggregate.
        refreshTokenRepository.save(refreshToken);

        // 7. Return the raw refresh token and its expiration time.
        return new RefreshTokenResultDto(
                rawToken,
                expiresAt
        );
    }
}
