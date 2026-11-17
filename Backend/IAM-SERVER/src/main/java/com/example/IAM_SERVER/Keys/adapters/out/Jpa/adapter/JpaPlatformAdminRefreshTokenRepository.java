
package com.example.IAM_SERVER.Keys.adapters.out.Jpa.adapter;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.PlatformAdminRefreshTokenEntity;
import com.example.IAM_SERVER.Keys.adapters.out.Jpa.JpaRepository.PlatformAdminRefreshTokenJpaRepository;
import com.example.IAM_SERVER.Keys.adapters.out.Jpa.mapper.PlatformAdminRefreshTokenMapper;

import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;
import com.example.IAM_SERVER.Keys.domain.model.TokenHash;
import com.example.IAM_SERVER.Keys.domain.ports.out.database.RefreshTokenRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaPlatformAdminRefreshTokenRepository
        implements RefreshTokenRepositoryPort {

    private final PlatformAdminRefreshTokenJpaRepository jpaRepository;


    /*
     * SAVE
     *
     * Domain Aggregate -> JPA Entity -> PostgreSQL
     */
    @Override
    @Transactional
    public RefreshToken save(RefreshToken refreshToken) {

        PlatformAdminRefreshTokenEntity entity =
                PlatformAdminRefreshTokenMapper.toEntity(refreshToken);

        int affectedRows = jpaRepository.insert(
                entity.getRefreshTokenId(),
                entity.getPlatformAdminId(),
                entity.getTokenHash(),
                entity.getFamilyId(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isRevoked(),
                entity.getReplacedBy()
        );

        if (affectedRows != 1) {
            throw new IllegalStateException(
                    "Failed to save platform admin refresh token"
            );
        }

        return refreshToken;
    }


    /*
     * FIND BY TOKEN HASH
     *
     * PostgreSQL -> JPA Entity -> Domain Aggregate
     */
    @Override
    public Optional<RefreshToken> findByTokenHash(TokenHash tokenHash) {

        return jpaRepository.findByTokenHash(tokenHash.value())
                .map(PlatformAdminRefreshTokenMapper::toDomain);
    }


    /*
     * FIND ALL BY FAMILY ID
     */
    @Override
    public List<RefreshToken> findAllByFamilyId(UUID familyId) {

        return jpaRepository.findAllByFamilyId(familyId)
                .stream()
                .map(PlatformAdminRefreshTokenMapper::toDomain)
                .toList();
    }


    /*
     * REVOKE ALL BY PLATFORM ADMIN ID
     */
    @Override
    @Transactional
    public void revokeAllByPlatformAdminId(UUID platformAdminId) {

        jpaRepository.revokeAllByPlatformAdminId(platformAdminId);
    }


    /*
     * REVOKE ALL BY FAMILY ID
     */
    @Override
    @Transactional
    public void revokeAllByFamilyId(UUID familyId) {

        jpaRepository.revokeAllByFamilyId(familyId);
    }
    @Override
    @Transactional(readOnly = false)
    public Optional<RefreshToken> findUsableByTokenHashForUpdate(
            TokenHash tokenHash
    ) {

        return jpaRepository
                .findUsableByTokenHashForUpdate(tokenHash.value())
                .map(PlatformAdminRefreshTokenMapper::toDomain);
    }


    /*
     * MARK EXISTING TOKEN AS REPLACED
     *
     * Sets:
     * is_revoked = TRUE
     * replaced_by = replacementTokenId
     */
    @Override
    @Transactional
    public int markAsReplaced(
            UUID refreshTokenId,
            UUID replacementTokenId,
            UUID platformAdminId,
            UUID familyId
    ) {

        return jpaRepository.markAsReplaced(
                refreshTokenId,
                replacementTokenId,
                platformAdminId,
                familyId
        );
    }
}
