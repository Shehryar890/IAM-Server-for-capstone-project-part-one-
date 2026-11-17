package com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.jPaRepository;

import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminData;
import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.entity.PlatformAdminJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface platfromAdminRepository
        extends JpaRepository<PlatformAdminJpaEntity, UUID> {

    // Check whether a platform admin already exists
    @Query(
            value = """
                SELECT EXISTS (
                    SELECT 1
                    FROM platform_admin
                )
                """,
            nativeQuery = true
    )
    boolean existsPlatformAdmin();





    // Insert platform admin
    @Modifying
    @Query(
            value = """
                INSERT INTO platform_admin
                    (username, password, created_at, updated_at)
                VALUES
                    (:username, :password, :createdAt, :updatedAt)
                """,
            nativeQuery = true
    )
    int insertPlatformAdmin(
            @Param("username") String username,
            @Param("password") String password,
            @Param("createdAt") Instant createdAt,
            @Param("updatedAt") Instant updatedAt
    );


    // Find platform admin by ID
    @Query(
            value = """
                SELECT platform_admin_id AS id,
                       username
                FROM platform_admin
                WHERE platform_admin_id = :id
                """,
            nativeQuery = true
    )
    Optional<PlatformAdminData> findPlatformAdminById(
            @Param("id") UUID id
    );

    @Query(
            value = """
            SELECT
                platform_admin_id AS id,
                username AS username,
                password AS passwordHash
            FROM platform_admin
            WHERE username = :username
            LIMIT 1
            """,
            nativeQuery = true
    )
    Optional<PlatformAdminData> findPlatformAdminForLogin(
            @Param("username") String username
    );
    @Query(
            value = """
            SELECT platform_admin_id AS id,
                   username AS username
            FROM platform_admin
            WHERE platform_admin_id = :id
            """,
            nativeQuery = true
    )
    Optional<PlatformAdminData> findadminbyid(
            @Param("id") UUID id
    );
}