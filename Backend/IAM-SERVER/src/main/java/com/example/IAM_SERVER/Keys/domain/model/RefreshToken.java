
package com.example.IAM_SERVER.Keys.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class RefreshToken {

    private final UUID refreshTokenId;
    private final UUID platformAdminId;
    private final TokenHash tokenHash;
    private final UUID familyId;
    private final Instant createdAt;

    private Instant expiresAt;
    private boolean revoked;
    private UUID replacedBy;


    public RefreshToken(
            UUID refreshTokenId,
            UUID platformAdminId,
            TokenHash tokenHash,
            UUID familyId,
            Instant createdAt,
            Instant expiresAt,
            boolean revoked,
            UUID replacedBy
    ) {
        this.refreshTokenId = Objects.requireNonNull(
                refreshTokenId, "Refresh token ID cannot be null"
        );

        this.platformAdminId = Objects.requireNonNull(
                platformAdminId, "Platform admin ID cannot be null"
        );

        this.tokenHash = Objects.requireNonNull(
                tokenHash, "Token hash cannot be null"
        );

        this.familyId = Objects.requireNonNull(
                familyId, "Family ID cannot be null"
        );

        this.createdAt = Objects.requireNonNull(
                createdAt, "Creation time cannot be null"
        );

        this.expiresAt = Objects.requireNonNull(
                expiresAt, "Expiry time cannot be null"
        );

        if (!expiresAt.isAfter(createdAt)) {
            throw new IllegalArgumentException(
                    "Expiry time must be after creation time"
            );
        }

        if (replacedBy != null && !revoked) {
            throw new IllegalArgumentException(
                    "A replaced token must be revoked"
            );
        }

        if (refreshTokenId.equals(replacedBy)) {
            throw new IllegalArgumentException(
                    "A refresh token cannot replace itself"
            );
        }

        this.revoked = revoked;
        this.replacedBy = replacedBy;
    }


    public boolean isExpired(Instant now) {
        Objects.requireNonNull(now, "Current time cannot be null");
        return !expiresAt.isAfter(now);
    }


    public boolean isUsableAt(Instant now) {
        return !revoked && !isExpired(now);
    }


    public void revoke() {
        this.revoked = true;
    }


    public void markAsReplacedBy(UUID replacementTokenId) {

        Objects.requireNonNull(
                replacementTokenId,
                "Replacement token ID cannot be null"
        );

        if (refreshTokenId.equals(replacementTokenId)) {
            throw new IllegalArgumentException(
                    "A refresh token cannot replace itself"
            );
        }

        if (replacedBy != null) {
            throw new IllegalStateException(
                    "This refresh token has already been replaced"
            );
        }

        this.revoked = true;
        this.replacedBy = replacementTokenId;
    }


    public UUID getRefreshTokenId() {
        return refreshTokenId;
    }

    public UUID getPlatformAdminId() {
        return platformAdminId;
    }

    public TokenHash getTokenHash() {
        return tokenHash;
    }

    public UUID getFamilyId() {
        return familyId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public UUID getReplacedBy() {
        return replacedBy;
    }
}
