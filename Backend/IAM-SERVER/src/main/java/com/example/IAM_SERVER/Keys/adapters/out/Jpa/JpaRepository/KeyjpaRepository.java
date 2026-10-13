package com.example.IAM_SERVER.Keys.adapters.out.Jpa.JpaRepository;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.SigningkeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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
            status,
            created_at, 
             updated_at, 
             activated_at , 
            retired_at,
            expires_at,
           
        )
        VALUES (
            :kid,
            :algorithm,
            :publicKey,
            :status,
            :created_at ,
            :updated_at,
            :activated_at,
            :retired_at,
            :expires_at
        )
        """, nativeQuery = true)
            void insertSigningKey(
                    @Param("kid") String kid,
                    @Param("algorithm") String algorithm,
                    @Param("publicKey") String publicKey,
                    @Param("status") String status,
                    @Param("created_at") Instant created_at,
                    @Param("updated_at") Instant updated_at,

                    @Param("activated_at") Instant activated_at,
                    @Param("retired_at") Instant retiredAt,
                    @Param("expires_at") Instant expiresAt
            );



    }


