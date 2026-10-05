package com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.jPaRepository;

import com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.entity.PlatformAdminJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
@Repository
 public interface  platfromAdminRepository extends  JpaRepository<PlatformAdminJpaEntity, Long> {

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
     void insertPlatformAdmin(

             @Param("username") String username,
             @Param("password") String password,
             @Param("createdAt") Instant createdAt,
             @Param("updatedAt") Instant updatedAt
     );
 }
