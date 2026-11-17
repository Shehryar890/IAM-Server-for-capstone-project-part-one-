
package com.example.IAM_SERVER.Keys.adapters.out.Jpa.JpaRepository;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.PlatformAdminRefreshTokenEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlatformAdminRefreshTokenJpaRepository
        extends JpaRepository<PlatformAdminRefreshTokenEntity, UUID> {

    /*
     * INSERT a new refresh-token record.
     */
    @Modifying
    @Query(
            value = """
                    INSERT INTO platform_admin_refresh_token (
                        refresh_token_id,
                        platform_admin_id,
                        token_hash,
                        family_id,
                        created_at,
                        expires_at,
                        is_revoked,
                        replaced_by
                    )
                    VALUES (
                        :refreshTokenId,
                        :platformAdminId,
                        :tokenHash,
                        :familyId,
                        :createdAt,
                        :expiresAt,
                        :revoked,
                        :replacedBy
                    )
                    """,
            nativeQuery = true
    )
    int insert(
            @Param("refreshTokenId") UUID refreshTokenId,
            @Param("platformAdminId") UUID platformAdminId,
            @Param("tokenHash") String tokenHash,
            @Param("familyId") UUID familyId,
            @Param("createdAt") Instant createdAt,
            @Param("expiresAt") Instant expiresAt,
            @Param("revoked") boolean revoked,
            @Param("replacedBy") UUID replacedBy
    );


    /*
     * FIND a refresh token using its token hash.
     */
    @Query(
            value = """
                    SELECT *
                    FROM platform_admin_refresh_token
                    WHERE token_hash = :tokenHash
                    """,
            nativeQuery = true
    )
    Optional<PlatformAdminRefreshTokenEntity> findByTokenHash(
            @Param("tokenHash") String tokenHash
    );


    /*
     * FIND all refresh tokens belonging to a session family.
     */
    @Query(
            value = """
                    SELECT *
                    FROM platform_admin_refresh_token
                    WHERE family_id = :familyId
                    ORDER BY created_at ASC
                    """,
            nativeQuery = true
    )
    List<PlatformAdminRefreshTokenEntity> findAllByFamilyId(
            @Param("familyId") UUID familyId
    );


    /*
     * REVOKE all refresh tokens belonging to a platform administrator.
     */
    @Modifying
    @Query(
            value = """
                    UPDATE platform_admin_refresh_token
                    SET is_revoked = TRUE
                    WHERE platform_admin_id = :platformAdminId
                      AND is_revoked = FALSE
                    """,
            nativeQuery = true
    )
    int revokeAllByPlatformAdminId(
            @Param("platformAdminId") UUID platformAdminId
    );


    /*
     * REVOKE all refresh tokens belonging to a session family.
     */
    @Modifying
    @Query(
            value = """
                    UPDATE platform_admin_refresh_token
                    SET is_revoked = TRUE
                    WHERE family_id = :familyId
                      AND is_revoked = FALSE
                    """,
            nativeQuery = true
    )
    int revokeAllByFamilyId(
            @Param("familyId") UUID familyId
    );


    /*
     * FIND and LOCK a valid refresh-token row for rotation.
     */
    @Query(
            value = """
                    SELECT *
                    FROM platform_admin_refresh_token
                    WHERE token_hash = :tokenHash
                      AND is_revoked = FALSE
                      AND expires_at > CURRENT_TIMESTAMP
                    FOR UPDATE
                    """,
            nativeQuery = true
    )
    Optional<PlatformAdminRefreshTokenEntity> findUsableByTokenHashForUpdate(
            @Param("tokenHash") String tokenHash
    );


    /*
     * MARK the existing refresh token as replaced.
     */
    @Modifying
    @Query(
            value = """
                    UPDATE platform_admin_refresh_token
                    SET is_revoked = TRUE,
                        replaced_by = :replacementTokenId
                    WHERE refresh_token_id = :refreshTokenId
                      AND platform_admin_id = :platformAdminId
                      AND family_id = :familyId
                      AND is_revoked = FALSE
                      AND replaced_by IS NULL
                    """,
            nativeQuery = true
    )
    int markAsReplaced(
            @Param("refreshTokenId") UUID refreshTokenId,
            @Param("replacementTokenId") UUID replacementTokenId,
            @Param("platformAdminId") UUID platformAdminId,
            @Param("familyId") UUID familyId
    );

}
