package com.example.IAM_SERVER.Keys.adapters.out.Jpa.mapper;


import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.PlatformAdminRefreshTokenEntity;

import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;
import com.example.IAM_SERVER.Keys.domain.model.TokenHash;

import java.util.Objects;

public final class PlatformAdminRefreshTokenMapper {

    private PlatformAdminRefreshTokenMapper() {
        // Prevent instantiation.
    }

    /*
     * DOMAIN -> ENTITY
     */
    public static PlatformAdminRefreshTokenEntity toEntity(
            RefreshToken domain
    ) {

        Objects.requireNonNull(
                domain,
                "Platform admin refresh token domain cannot be null"
        );

        return PlatformAdminRefreshTokenEntity.builder()
                .refreshTokenId(domain.getRefreshTokenId())
                .platformAdminId(domain.getPlatformAdminId())
                .tokenHash(domain.getTokenHash().value())
                .familyId(domain.getFamilyId())
                .createdAt(domain.getCreatedAt())
                .expiresAt(domain.getExpiresAt())
                .revoked(domain.isRevoked())
                .replacedBy(domain.getReplacedBy())
                .build();
    }


    /*
     * ENTITY -> DOMAIN
     */
    public static RefreshToken toDomain(
            PlatformAdminRefreshTokenEntity entity
    ) {

        Objects.requireNonNull(
                entity,
                "Platform admin refresh token entity cannot be null"
        );

        return new RefreshToken(
                entity.getRefreshTokenId(),
                entity.getPlatformAdminId(),
                new TokenHash(entity.getTokenHash()),
                entity.getFamilyId(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isRevoked(),
                entity.getReplacedBy()
        );
    }
}
