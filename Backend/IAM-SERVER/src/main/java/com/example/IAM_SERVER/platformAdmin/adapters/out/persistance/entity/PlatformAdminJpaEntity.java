package com.example.IAM_SERVER.platformAdmin.adapters.out.persistance.entity;



import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "platform_admin")
public class PlatformAdminJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iam_AdminId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;


    // JPA requires a no-argument constructor
    protected PlatformAdminJpaEntity() {
    }


    public PlatformAdminJpaEntity(
            Long iamAdminId,
            String username,
            String password,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.iam_AdminId = iamAdminId;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getIamAdminId() {
        return iam_AdminId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setupdatedAt(){
        this.updatedAt = updatedAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
