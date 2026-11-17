package com.example.IAM_SERVER.Keys.adapters.out.Jpa.mapper;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.SigningkeyEntity;
import com.example.IAM_SERVER.Keys.domain.model.Algoritham;
import com.example.IAM_SERVER.Keys.domain.model.Kid;
import com.example.IAM_SERVER.Keys.domain.model.SigningKeys;
import com.example.IAM_SERVER.Keys.domain.model.publickey;

public class Signingkeysmapper {

    public static SigningkeyEntity getkeyentity(SigningKeys object) {

        return new SigningkeyEntity(
                object.getKid(),
                object.getalgoritham().name(),
                object.getPublickey(),
                object.getEncrypted_private_key(),
                object.getWrapped_dek(),
                object.getstatus(),
                object.getCreated_at(),
                object.getUpdated_at(),
                object.getActivated_at(),
                object.getRetire_at(),
                object.getExpired_at()
        );
    }
    public static SigningKeys todomain(SigningkeyEntity entity) {

        return new SigningKeys(
                new Kid(entity.getKid()),
                new publickey(entity.getPublicKey()),
                entity.getEncryptedPrivateKey(),
                entity.getWrappedDek(),
                Algoritham.valueOf(entity.getAlgorithm()),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getActivatedAt(),
                entity.getExpiredAt(),
                entity.getRetiredAt()
        );
    }
}