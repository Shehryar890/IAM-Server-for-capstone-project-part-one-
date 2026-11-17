package com.example.IAM_SERVER.Keys.adapters.out.Jpa.adapter;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.Entity.SigningkeyEntity;
import com.example.IAM_SERVER.Keys.adapters.out.Jpa.JpaRepository.KeyjpaRepository;
import com.example.IAM_SERVER.Keys.adapters.out.Jpa.mapper.Signingkeysmapper;
import com.example.IAM_SERVER.Keys.domain.model.SigningKeys;
import com.example.IAM_SERVER.Keys.domain.ports.out.database.KeyRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Component
public class SaveJwtKeyAdapter implements KeyRepository {

    private final KeyjpaRepository repository;

    public SaveJwtKeyAdapter(KeyjpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void save(SigningKeys signingKeyObject) {

        SigningkeyEntity entity =
                Signingkeysmapper.getkeyentity(signingKeyObject);
        repository.insertSigningKey(
                entity.getKid(),
                entity.getAlgorithm(),
                entity.getPublicKey(),
                entity.getEncryptedPrivateKey(),
                entity.getWrappedDek(),
                entity.getStatus().name(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getActivatedAt(),
                entity.getRetiredAt(),
                entity.getExpiredAt()
        );
    }

    @Override
    public boolean existsAnyKey() {
        return repository.existsAnyKey();
    }

    @Override
    public Optional<SigningKeys> findActiveSigningKey() {
        return repository.findActiveSigningKey()
                .map(Signingkeysmapper::todomain);
    }
    @Override
    public Optional<String> findPublicKeyByKid(String kid) {
        return repository.findPublicKeyByKid(kid);
    }
}
