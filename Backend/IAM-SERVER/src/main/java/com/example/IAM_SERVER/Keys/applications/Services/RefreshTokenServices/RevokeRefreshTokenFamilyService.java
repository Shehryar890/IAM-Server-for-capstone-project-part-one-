package com.example.IAM_SERVER.Keys.applications.Services.RefreshTokenServices;

import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;
import com.example.IAM_SERVER.Keys.domain.model.TokenHash;
import com.example.IAM_SERVER.Keys.domain.ports.in.RevokeRefreshTokenFamilyPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenHasherPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.database.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RevokeRefreshTokenFamilyService
        implements RevokeRefreshTokenFamilyPort {

    private final RefreshTokenHasherPort tokenHasher;
    private final RefreshTokenRepositoryPort refreshTokenRepository;

    @Override
    @Transactional
    public void revokeFamily(String rawRefreshToken) {

        // No cookie/token: nothing to revoke.
        if (rawRefreshToken == null || rawRefreshToken.isBlank()) {
            return;
        }

        // 1. Hash the incoming raw token.
        TokenHash tokenHash = tokenHasher.hash(rawRefreshToken);

        // 2. Find the token record to identify its family.
        Optional<RefreshToken> existingToken =
                refreshTokenRepository.findByTokenHash(tokenHash);

        // 3. Unknown token: logout remains successful and idempotent.
        if (existingToken.isEmpty()) {
            return;
        }

        // 4. Get the family ID.
        UUID familyId = existingToken.get().getFamilyId();

        // 5. Revoke every token belonging to this family.
        refreshTokenRepository.revokeAllByFamilyId(familyId);
    }
}