
package com.example.IAM_SERVER.Keys.adapters.out.EncryptionKeyAdapters.GenerateKeyPair;

import com.example.IAM_SERVER.Keys.domain.ports.out.GenerateDekKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

public final class AesGcmKeyGeneratorAdapter implements GenerateDekKey {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE_BITS = 256;

    @Override
    public SecretKey generate() {
        try {
            KeyGenerator keyGenerator =
                    KeyGenerator.getInstance(ALGORITHM);

            keyGenerator.init(KEY_SIZE_BITS);

            return keyGenerator.generateKey();

        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException(
                    "Failed to generate AES-256 DEK.",
                    exception
            );
        }
    }
}
