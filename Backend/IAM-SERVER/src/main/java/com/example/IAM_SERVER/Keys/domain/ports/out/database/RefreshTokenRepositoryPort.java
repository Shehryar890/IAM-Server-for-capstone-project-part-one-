package com.example.IAM_SERVER.Keys.domain.ports.out.database;


import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;
import com.example.IAM_SERVER.Keys.domain.model.TokenHash;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepositoryPort {

    // Create or persist a refresh-token aggregate.
    RefreshToken save(
            RefreshToken refreshToken
    );

    // Find a refresh token using the hash of its raw secret.
    Optional<RefreshToken> findByTokenHash(
            TokenHash tokenHash
    );

    // Retrieve all refresh tokens belonging to a particular session family.
    List<RefreshToken> findAllByFamilyId(
            UUID familyId
    );

    // Revoke every refresh token belonging to an admin.
    void revokeAllByPlatformAdminId(
            UUID platformAdminId
    );

    // Revoke every refresh token belonging to a session family.
    void revokeAllByFamilyId(
            UUID familyId
    );
    Optional<RefreshToken> findUsableByTokenHashForUpdate(
            TokenHash tokenHash
    );

    int markAsReplaced(
            UUID refreshTokenId,
            UUID replacementTokenId,
            UUID platformAdminId,
            UUID familyId
    );
}
