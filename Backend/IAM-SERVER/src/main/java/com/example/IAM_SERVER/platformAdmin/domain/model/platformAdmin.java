package com.example.IAM_SERVER.platformAdmin.domain.model;

import java.time.Instant;
import java.util.UUID;

public class platformAdmin {

    private UUID iamadmin_id;
    private String username;
    private PasswordHash password;
    private Instant createdAT;
    private Instant updatedAt;

    public UUID getIamadmin_id() {
        return iamadmin_id;
    }

    public void setIamadmin_id(UUID iamadmin_id) {
        this.iamadmin_id = iamadmin_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getpassword() {
        return password.value();
    }

    public void setPassword(PasswordHash password) {
        this.password = password;
    }

    public Instant getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(Instant createdAT) {
        this.createdAT = createdAT;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}