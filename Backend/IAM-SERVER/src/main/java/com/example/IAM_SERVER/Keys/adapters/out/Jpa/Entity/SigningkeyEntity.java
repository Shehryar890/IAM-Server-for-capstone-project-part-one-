package com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity;

import java.time.Instant;

import com.example.IAM_SERVER.Keys.domain.model.Keystatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "signing_key")
public class SigningkeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kid", nullable = false, unique = true, length = 255)
    private String kid;

    @Column(name = "algorithm", nullable = false, length = 50)
    private String algorithm;

    @Column(name = "public_key", nullable = false, columnDefinition = "TEXT")
    private String publicKey;

    @Column(name = "encrypted_private_key", nullable = false, columnDefinition = "TEXT")
    private String encryptedPrivateKey;

    @Column(name = "wrapped_dek", nullable = false, columnDefinition = "TEXT")
    private String wrappedDek;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Keystatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "activated_at")
    private Instant activatedAt;

    @Column(name = "retired_at")
    private Instant retiredAt;

    @Column(name = "expired_at")
    private Instant expiredAt;

    // Required by JPA
    protected SigningkeyEntity() {
    }

    public SigningkeyEntity(
            String kid,
            String algorithm,
            String publicKey,
            String encryptedPrivateKey,
            String wrappedDek,
            Keystatus status,
            Instant createdAt,
            Instant updatedAt,
            Instant activatedAt,
            Instant retiredAt,
            Instant expiredAt
    ) {
        this.kid = kid;
        this.algorithm = algorithm;
        this.publicKey = publicKey;
        this.encryptedPrivateKey = encryptedPrivateKey;
        this.wrappedDek = wrappedDek;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.activatedAt = activatedAt;
        this.retiredAt = retiredAt;
        this.expiredAt = expiredAt;
    }

    public Long getId() {
        return id;
    }

    public String getKid() {
        return kid;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }

    public String getWrappedDek() {
        return wrappedDek;
    }

    public Keystatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getActivatedAt() {
        return activatedAt;
    }

    public Instant getRetiredAt() {
        return retiredAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setEncryptedPrivateKey(String encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }

    public void setWrappedDek(String wrappedDek) {
        this.wrappedDek = wrappedDek;
    }

    public void setStatus(Keystatus status) {
        this.status = status;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setActivatedAt(Instant activatedAt) {
        this.activatedAt = activatedAt;
    }

    public void setRetiredAt(Instant retiredAt) {
        this.retiredAt = retiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }
}