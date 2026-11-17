
package com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "platform_admin_refresh_token",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_platform_admin_refresh_token_hash",
                        columnNames = {"token_hash"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_refresh_token_platform_admin",
                        columnList = "platform_admin_id"
                ),
                @Index(
                        name = "idx_refresh_token_family",
                        columnList = "family_id"
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformAdminRefreshTokenEntity {

    @Id
    @Column(
            name = "refresh_token_id",
            nullable = false,
            updatable = false
    )
    private UUID refreshTokenId;

    @Column(
            name = "platform_admin_id",
            nullable = false,
            updatable = false
    )
    private UUID platformAdminId;

    @Column(
            name = "token_hash",
            nullable = false,
            length = 64,
            updatable = false
    )
    private String tokenHash;

    @Column(
            name = "family_id",
            nullable = false,
            updatable = false
    )
    private UUID familyId;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    @Column(
            name = "expires_at",
            nullable = false
    )
    private Instant expiresAt;

    @Column(
            name = "is_revoked",
            nullable = false
    )
    private boolean revoked;

    @Column(
            name = "replaced_by"
    )
    private UUID replacedBy;


    @Builder
    public PlatformAdminRefreshTokenEntity(
            UUID refreshTokenId,
            UUID platformAdminId,
            String tokenHash,
            UUID familyId,
            Instant createdAt,
            Instant expiresAt,
            boolean revoked,
            UUID replacedBy
    ) {
        this.refreshTokenId = refreshTokenId;
        this.platformAdminId = platformAdminId;
        this.tokenHash = tokenHash;
        this.familyId = familyId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.replacedBy = replacedBy;
    }
}
