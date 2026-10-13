
package com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity;

import java.time.Instant;

import com.example.IAM_SERVER.Keys.domain.model.Algoritham;
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
@Table(name = "Keys")
public class SigningkeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    private Long key_id;

    @Column(name = "kid", nullable = false, unique = true, length = 255)
    private String kid;

    @Column(name = "algorithm", nullable = false, length = 50)
    private String  algorithm;

    @Column(name = "public_key", nullable = false, columnDefinition = "TEXT")
    private String publicKey;



    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Keystatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    @Column(name = "activated_at")
    private Instant activated_at;
    @Column(name = "retired_at")
    private Instant retiredAt;

    @Column(name = "expires_at")
    private Instant expiresAt;
    protected SigningkeyEntity() {
    }
    public SigningkeyEntity(
            String kid,
        String  algorithm,
            String publicKey,
            Keystatus status
            ) {
        this.kid = kid;
        this.algorithm = algorithm;
        this.publicKey = publicKey;
        this.status = status;

    }
    public Long getId() {
        return key_id;
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
    public Instant getActivated_at(){
        return activated_at;
    }
    public Keystatus getStatus() {
        return status;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getupdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    

    public Instant getRetiredAt() {
        return retiredAt;
    }
    public Instant getExpiresAt() {
        return expiresAt;
    }
    public void setKid(String kid) {
        this.kid = kid;
    }
    public void setAlgorithm(String  algorithm) {
        this.algorithm = algorithm;
    }
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    public void setStatus(Keystatus status) {
        this.status = status;
    }
    public void setRetiredAt(Instant retiredAt) {
        this.retiredAt = retiredAt;
    }
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
    public void setUpdatedAt(Instant time){
        this.activated_at = time;
}


}
