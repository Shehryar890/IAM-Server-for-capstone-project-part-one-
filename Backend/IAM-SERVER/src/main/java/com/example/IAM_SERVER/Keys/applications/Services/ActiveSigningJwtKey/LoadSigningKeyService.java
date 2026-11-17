
package com.example.IAM_SERVER.Keys.applications.Services.ActiveSigningJwtKey;

import com.example.IAM_SERVER.Keys.Utility.RsaPublicKeyConverterUtility;
import com.example.IAM_SERVER.Keys.domain.model.ActiveKey;
import com.example.IAM_SERVER.Keys.domain.model.SigningKeys;
import com.example.IAM_SERVER.Keys.domain.ports.out.DekKeyEncryptor;



import com.example.IAM_SERVER.Keys.domain.ports.out.LoadJwtKeys;
import com.example.IAM_SERVER.Keys.domain.ports.out.MasterEncryptionKeyManager;
import com.example.IAM_SERVER.Keys.domain.ports.out.database.KeyRepository;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Objects;

@Service
public final class LoadSigningKeyService  implements LoadJwtKeys {

    private final KeyRepository keyRepository;
    private final MasterEncryptionKeyManager encryptionKeyManager;
    private final DekKeyEncryptor keyEncryptor;

    public LoadSigningKeyService(
            KeyRepository keyRepository,
            MasterEncryptionKeyManager  encryptionKeyManager,
            DekKeyEncryptor keyEncryptor
    ) {
        this.keyRepository = Objects.requireNonNull(keyRepository);
        this.encryptionKeyManager =
                Objects.requireNonNull(encryptionKeyManager);
        this.keyEncryptor = Objects.requireNonNull(keyEncryptor);
    }

    public ActiveKey loadkey() {

        // 1. Retrieve the active signing-key record.
        SigningKeys signingKey = keyRepository
                .findActiveSigningKey()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No active signing key exists in the database."
                        )
                );

        // 2. Unwrap the DEK using the master encryption key.
        SecretKey dek = encryptionKeyManager.decrypt(
                signingKey.getWrapped_dek()
        );

        // 3. Decrypt and reconstruct the RSA private key.
        PrivateKey privateKey = keyEncryptor.decrypt(
                dek,
                signingKey.getEncrypted_private_key()
        );

        // 4. Decode the stored Base64 public key.
        byte[] publicKeyBytes;

        try {
            publicKeyBytes = Base64.getDecoder().decode(
                    signingKey.getPublickey()
            );
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(
                    "Stored public key is not valid Base64.",
                    exception
            );
        }

        // 5. Reconstruct the Java PublicKey object.
        final PublicKey publicKey;


            publicKey =
                    RsaPublicKeyConverterUtility.fromString(
                            signingKey.getPublickey()
                    );




        // 6. Return the fully reconstructed active key.
        return new ActiveKey(
                signingKey.getKid(),
                privateKey,
                publicKey,
                signingKey.getalgoritham(),
                signingKey.getstatus(),
                signingKey.getActivated_at()
        );
    }
}
