package com.example.IAM_SERVER.Keys.adapters.out.Jpa.adapter;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.SigningkeyEntity;
import com.example.IAM_SERVER.Keys.adapters.out.Jpa.JpaRepository.KeyjpaRepository;
import com.example.IAM_SERVER.Keys.adapters.out.Jpa.mapper.Signingkeysmapper;
import com.example.IAM_SERVER.Keys.domain.model.SigningKeys;
import com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.SaveKeys.SavePublicKeydata;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class SavePublicKeyAdapter implements SavePublicKeydata {



    final private KeyjpaRepository repository;
    public SavePublicKeyAdapter( KeyjpaRepository repo ){

        this.repository = repo;


    }







    @Override
    public Optional<SigningKeys> findactivekey() {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getactivekey_Id() {
        return Optional.empty();
    }

    @Override
    public Boolean savekey(SigningKeys signkeyobject) {
try {
    SigningkeyEntity entity = Signingkeysmapper.getkeyentity(signkeyobject);
    Instant instant = Instant.now();
    entity.setCreatedAt(instant);
    entity.setUpdatedAt(instant);
    entity.setUpdatedAt(instant);

    repository.insertSigningKey(
            entity.getKid(),
            entity.getAlgorithm(),
            entity.getPublicKey(),
            entity.getStatus().name(),
            entity.getCreatedAt(),
            entity.getupdatedAt(),
            entity.getActivated_at(),
            entity.getRetiredAt(),
            entity.getExpiresAt()


    );


    return true;
} catch (Exception e) {
    throw new RuntimeException(e);

}


    }


}
