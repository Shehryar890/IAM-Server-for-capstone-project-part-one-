package com.example.IAM_SERVER.Keys.domain.model;

import com.example.IAM_SERVER.Keys.domain.exceptions.KidvalueException;
import com.example.IAM_SERVER.Keys.domain.exceptions.keystatusException;
import com.example.IAM_SERVER.Keys.domain.exceptions.publicKeyvalueException;
import com.example.IAM_SERVER.Keys.domain.model.Algoritham;

import java.time.Instant;
import java.util.Objects;

public class SigningKeys {

    private Long key_id;
    private Keystatus status;
    private Kid kid;
    private publickey publickey;

    // Base64-encoded AES-GCM payloads
    private String encrypted_private_key;
    private String wrapped_dek;

    private Instant created_at;
    private Instant updated_at;
    private Instant expired_at;
    private Algoritham algoritham;
    private Instant retire_at;
    private Instant activated_at;

    public SigningKeys() {
    }

    public SigningKeys(
            Kid kid,
            publickey publickey,
            String encrypted_private_key,
            String wrapped_dek,
            Algoritham algoritham,
            Keystatus status,
            Instant created_at,
            Instant updated_at,
            Instant activated_at,
            Instant expired_at,
            Instant retire_at
    ) {
        this.kid = Objects.requireNonNull(kid, "Kid must not be null.");
        this.publickey = Objects.requireNonNull(
                publickey, "Public key must not be null."
        );

        setEncrypted_private_key(encrypted_private_key);
        setWrapped_dek(wrapped_dek);

        this.algoritham = Objects.requireNonNull(
                algoritham, "Algorithm must not be null."
        );
        this.status = Objects.requireNonNull(
                status, "Status must not be null."
        );
        this.created_at = Objects.requireNonNull(
                created_at, "Creation time must not be null."
        );
        this.updated_at = Objects.requireNonNull(
                updated_at, "Update time must not be null."
        );

        if (this.updated_at.isBefore(this.created_at)) {
            throw new IllegalArgumentException(
                    "Update time cannot be before creation time."
            );
        }

        if (status == Keystatus.ACTIVE&& activated_at == null) {
            throw new IllegalArgumentException(
                    "An active key must have an activation time."
            );
        }

        this.activated_at = activated_at;
        this.expired_at = expired_at;
        this.retire_at = retire_at;
    }

    public String getEncrypted_private_key() {
        return encrypted_private_key;
    }

    public void setEncrypted_private_key(String encrypted_private_key) {
        if (encrypted_private_key == null || encrypted_private_key.isBlank()) {
            throw new IllegalArgumentException(
                    "Encrypted private key must not be blank."
            );
        }

        this.encrypted_private_key = encrypted_private_key;
    }

    public String getWrapped_dek() {
        return wrapped_dek;
    }

    public void setWrapped_dek(String wrapped_dek) {
        if (wrapped_dek == null || wrapped_dek.isBlank()) {
            throw new IllegalArgumentException(
                    "Wrapped DEK must not be blank."
            );
        }

        this.wrapped_dek = wrapped_dek;
    }

    public void setexpiredat(Instant expired_at) {
        if (this.status == Keystatus.RETIRED){
            throw new keystatusException("A retired key cannot be expired.");
        }

        this.expired_at = Objects.requireNonNull(
                expired_at,
                "Expiration time must not be null."
        );

        this.updated_at = Instant.now();
    }

    public void activatekey(Keystatus status) {
        if (this.status == Keystatus.ACTIVE){
            throw new keystatusException("Key is already active.");
        }

        if (status != Keystatus.ACTIVE){
            throw new keystatusException(
                    "A signing key can only be activated with ACTIVE status."
            );
        }

        this.status = Keystatus.ACTIVE;
        this.activated_at = Instant.now();
        this.updated_at = this.activated_at;
    }

    public void retirekey() {
        if (this.status == Keystatus.RETIRED){
            throw new keystatusException("Key is already retired.");
        }

        if (this.status != Keystatus.ACTIVE
                && this.status != Keystatus.RETIRED ){
            throw new keystatusException(
                    "Only an active or retiring key can be retired."
            );
        }

        this.status = Keystatus.RETIRED;
        this.retire_at = Instant.now();
        this.updated_at = this.retire_at;
    }

    public void setAlgoritham(Algoritham algo) {
        this.algoritham = Objects.requireNonNull(
                algo,
                "Algorithm must not be null."
        );
    }

    public Long getKey_id() {
        return key_id;
    }

    public Keystatus getstatus() {
        return status;
    }

    public String getKid() {
        return kid == null ? null : kid.getValue();
    }

    public String getPublickey() {
        return publickey == null ? null : publickey.getvalue();
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public Instant getExpired_at() {
        return expired_at;
    }

    public Instant getActivated_at() {
        return activated_at;
    }

    public Instant getRetire_at() {
        return retire_at;
    }

    public Algoritham getalgoritham() {
        return algoritham;
    }
}