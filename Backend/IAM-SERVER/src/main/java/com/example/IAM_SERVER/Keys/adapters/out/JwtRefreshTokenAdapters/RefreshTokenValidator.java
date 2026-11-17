package com.example.IAM_SERVER.Keys.adapters.out.JwtRefreshTokenAdapters;

import com.example.IAM_SERVER.Keys.domain.exceptions.InvalidRefreshTokenException;
import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;
import com.example.IAM_SERVER.Keys.domain.model.TokenHash;

import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenHasherPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenValidatorPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.database.RefreshTokenRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenValidator
        implements RefreshTokenValidatorPort {

    private final RefreshTokenHasherPort tokenHasher;

    private final RefreshTokenRepositoryPort refreshTokenRepository;


    @Override
    @Transactional(readOnly = true)
    public RefreshToken validateRefreshToken(String rawToken) {

        // 1. Check incoming raw token.
        if (rawToken == null || rawToken.isBlank()) {
            throw new InvalidRefreshTokenException(
                    "Refresh token is missing or invalid"
            );
        }

        // 2. Hash the incoming token.
        TokenHash tokenHash = tokenHasher.hash(rawToken);

        // 3. Find the corresponding token in the database.
        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenHash(tokenHash)
                .orElseThrow(() -> new InvalidRefreshTokenException(
                        "Refresh token does not exist"
                ));

        // 4. Validate revocation and expiration.
        if (!refreshToken.isUsableAt(Instant.now())) {
            throw new InvalidRefreshTokenException(
                    "Refresh token is expired or revoked"
            );
        }

        // 5. Return the verified domain aggregate.
        return refreshToken;
    }
}
