package com.example.IAM_SERVER.Keys.adapters.out.Jpa.JpaRepository;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.SigningkeyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface KeyjpaRepository
        extends JpaRepository<SigningkeyEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO signing_key (
            kid,
            algorithm,
            public_key,
            encrypted_private_key,
            wrapped_dek,
            status,
            created_at,
            updated_at,
            activated_at,
            retired_at,
            expired_at
        )
        VALUES (
            :kid,
            :algorithm,
            :publicKey,
            :encryptedPrivateKey,
            :wrappedDek,
            :status,
            :createdAt,
            :updatedAt,
            :activatedAt,
            :retiredAt,
            :expiredAt
        )
        """, nativeQuery = true)
    void insertSigningKey(
            @Param("kid") String kid,
            @Param("algorithm") String algorithm,
            @Param("publicKey") String publicKey,
            @Param("encryptedPrivateKey") String encryptedPrivateKey,
            @Param("wrappedDek") String wrappedDek,
            @Param("status") String status,
            @Param("createdAt") Instant createdAt,
            @Param("updatedAt") Instant updatedAt,
            @Param("activatedAt") Instant activatedAt,
            @Param("retiredAt") Instant retiredAt,
            @Param("expiredAt") Instant expiredAt
    );
    @Query(value = """
        SELECT public_key
        FROM signing_key
        WHERE kid = :kid
        LIMIT 1
        """, nativeQuery = true)
    Optional<String> findPublicKeyByKid(
            @Param("kid") String kid
    );

    @Query(value = """
        SELECT *
        FROM signing_key
        WHERE status = 'ACTIVE'
        """, nativeQuery = true)
    Optional<SigningkeyEntity> findActiveSigningKey();

    @Query(value = """
        SELECT EXISTS (
            SELECT 1
            FROM signing_key
        )
        """, nativeQuery = true)
    boolean existsAnyKey();
}

