
package com.example.IAM_SERVER.Keys.adapters.out.EncryptionKeyAdapters.GenerateKeyPair;



import com.example.IAM_SERVER.Keys.domain.ports.out.DekKeyEncryptor;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public final class AesGcmKeyEncryptor implements DekKeyEncryptor {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String ALGORITHM = "AES";
    private static final String RSA_ALGORITHM = "RSA";

    private static final int KEY_SIZE_BYTES = 32;
    private static final int NONCE_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;
    private static final int TAG_LENGTH_BYTES = TAG_LENGTH_BITS / 8;

    private static final byte VERSION = 1;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String encrypt(SecretKey dek, PrivateKey privateKey) {
        validateKey(dek);
        Objects.requireNonNull(privateKey, "Private key must not be null.");

        if (!RSA_ALGORITHM.equalsIgnoreCase(privateKey.getAlgorithm())) {
            throw new IllegalArgumentException(
                    "An RSA private key is required."
            );
        }

        byte[] privateKeyBytes = privateKey.getEncoded();

        if (privateKeyBytes == null) {
            throw new IllegalArgumentException(
                    "Private key encoding is not available."
            );
        }

        try {
            byte[] nonce = new byte[NONCE_LENGTH_BYTES];
            secureRandom.nextBytes(nonce);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    dek,
                    new GCMParameterSpec(TAG_LENGTH_BITS, nonce)
            );

            cipher.updateAAD(new byte[]{VERSION});

            byte[] ciphertext = cipher.doFinal(privateKeyBytes);

            // Format: version | nonce | ciphertext + authentication tag
            ByteBuffer payload = ByteBuffer.allocate(
                    1 + NONCE_LENGTH_BYTES + ciphertext.length
            );

            payload.put(VERSION);
            payload.put(nonce);
            payload.put(ciphertext);

            return Base64.getEncoder().encodeToString(payload.array());

        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException(
                    "RSA private-key encryption failed.",
                    exception
            );
        } finally {
            Arrays.fill(privateKeyBytes, (byte) 0);
        }
    }

    @Override
    public PrivateKey decrypt(SecretKey dek, String encryptedPayload) {
        validateKey(dek);

        Objects.requireNonNull(
                encryptedPayload,
                "Encrypted payload must not be null."
        );

        final byte[] payload;

        try {
            payload = Base64.getDecoder().decode(encryptedPayload);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Encrypted payload is not valid Base64.",
                    exception
            );
        }

        int minimumLength =
                1 + NONCE_LENGTH_BYTES + TAG_LENGTH_BYTES;

        if (payload.length < minimumLength) {
            throw new IllegalArgumentException(
                    "Encrypted payload is incomplete."
            );
        }

        ByteBuffer buffer = ByteBuffer.wrap(payload);

        byte version = buffer.get();

        if (version != VERSION) {
            throw new IllegalArgumentException(
                    "Unsupported encrypted payload version."
            );
        }

        byte[] nonce = new byte[NONCE_LENGTH_BYTES];
        buffer.get(nonce);

        byte[] ciphertext = new byte[buffer.remaining()];
        buffer.get(ciphertext);

        byte[] privateKeyBytes = null;

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    dek,
                    new GCMParameterSpec(TAG_LENGTH_BITS, nonce)
            );

            cipher.updateAAD(new byte[]{version});

            // Recover the encoded RSA private key.
            privateKeyBytes = cipher.doFinal(ciphertext);

            // Reconstruct the Java PrivateKey object.
            PKCS8EncodedKeySpec keySpec =
                    new PKCS8EncodedKeySpec(privateKeyBytes);

            return KeyFactory.getInstance(RSA_ALGORITHM)
                    .generatePrivate(keySpec);

        } catch (AEADBadTagException exception) {
            throw new IllegalArgumentException(
                    "AES-GCM authentication failed: wrong DEK or modified payload.",
                    exception
            );
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException(
                    "Private-key decryption or reconstruction failed.",
                    exception
            );
        } finally {
            if (privateKeyBytes != null) {
                Arrays.fill(privateKeyBytes, (byte) 0);
            }
        }
    }

    private static void validateKey(SecretKey key) {
        Objects.requireNonNull(key, "DEK must not be null.");

        if (!ALGORITHM.equalsIgnoreCase(key.getAlgorithm())) {
            throw new IllegalArgumentException(
                    "An AES-256 DEK is required."
            );
        }

        byte[] encoded = key.getEncoded();

        if (encoded == null) {
            throw new IllegalArgumentException(
                    "AES key material is not exportable."
            );
        }

        try {
            if (encoded.length != KEY_SIZE_BYTES) {
                throw new IllegalArgumentException(
                        "DEK must be exactly 256 bits."
                );
            }
        } finally {
            Arrays.fill(encoded, (byte) 0);
        }
    }
}
