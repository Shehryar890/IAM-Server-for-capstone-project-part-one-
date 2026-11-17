package com.example.IAM_SERVER.Keys.applications.Services.ActiveSigningJwtKey;

import com.example.IAM_SERVER.Keys.Utility.RsaPublicKeyConverterUtility;
import com.example.IAM_SERVER.Keys.domain.exceptions.SigningKeyCreationException;
import com.example.IAM_SERVER.Keys.domain.model.ActiveKey;
import com.example.IAM_SERVER.Keys.domain.model.Algoritham;
import com.example.IAM_SERVER.Keys.domain.model.Keystatus;
import com.example.IAM_SERVER.Keys.domain.model.Kid;
import com.example.IAM_SERVER.Keys.domain.model.SigningKeys;
import com.example.IAM_SERVER.Keys.domain.model.publickey;
import com.example.IAM_SERVER.Keys.domain.ports.out.DekKeyEncryptor;



import com.example.IAM_SERVER.Keys.domain.ports.out.GenerateDekKey;
import com.example.IAM_SERVER.Keys.domain.ports.out.MasterEncryptionKeyManager;
import com.example.IAM_SERVER.Keys.domain.ports.out.SaveJwtKey;
import com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.GenerateKeys.GenerateJwtKeys;

import com.example.IAM_SERVER.Keys.domain.ports.out.database.KeyRepository;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.time.Instant;
import java.util.UUID;

@Service
public class SaveSigningKeyService implements SaveJwtKey {

    private final GenerateJwtKeys rsaKeyGenerator;
    private final GenerateDekKey dekGenerator;
    private final DekKeyEncryptor keyEncryptor;
    private final MasterEncryptionKeyManager encryptionKeyManager;
    private final KeyRepository keyRepository;

    public SaveSigningKeyService(
            GenerateJwtKeys rsaKeyGenerator,
            GenerateDekKey dekGenerator,
            DekKeyEncryptor keyEncryptor,
            MasterEncryptionKeyManager encryptionKeyManager,
            KeyRepository keyRepository
    ) {
        this.rsaKeyGenerator = rsaKeyGenerator;
        this.dekGenerator = dekGenerator;
        this.keyEncryptor = keyEncryptor;
        this.encryptionKeyManager = encryptionKeyManager;
        this.keyRepository = keyRepository;
    }

    @Override
    public ActiveKey saveJwtKey() {
        try {
            // 1. Generate the RSA public/private key pair.
            KeyPair keyPair = rsaKeyGenerator.generatekey();

            // 2. Generate a fresh AES DEK for this RSA private key.
            SecretKey dek = dekGenerator.generate();

            // 3. Encrypt the RSA private key using the DEK.
            String encryptedPrivateKey =
                    keyEncryptor.encrypt(dek, keyPair.getPrivate());

            // 4. Wrap the DEK using the master encryption key.
            String wrappedDek =
                    encryptionKeyManager.encrypt(dek);

            // 5. Create the key identifier.
            String kidValue = UUID.randomUUID().toString();

            // 6. Encode the public key for storage.
            String publicKeyValue = RsaPublicKeyConverterUtility.toString(keyPair.getPublic());

            // 7. Establish lifecycle timestamps.
            Instant now = Instant.now();

            // 8. Create domain value objects.
            Kid kid = new Kid(kidValue);
            publickey publicKey = new publickey(publicKeyValue);

            // 9. Build the domain model for persistence.
            SigningKeys signingKeys = new SigningKeys(
                    kid,
                    publicKey,
                    encryptedPrivateKey,
                    wrappedDek,
                    Algoritham.RSA,
                    Keystatus.ACTIVE,
                    now,       // created_at
                    now,       // updated_at
                    now,       // activated_at
                    null,      // expired_at
                    null       // retire_at
            );

            // 10. Persist the signing-key record.
            keyRepository.save(signingKeys);

            // 11. Return the usable in-memory key only after saving.
            return new ActiveKey(
                    kidValue,
                    keyPair.getPrivate(),
                    keyPair.getPublic(),
                    Algoritham.RSA,
                    Keystatus.ACTIVE,
                    now
            );

        } catch (Exception exception) {
            throw new SigningKeyCreationException(
                    "Failed to generate and save the signing key.",
                    exception
            );
        }
    }
}