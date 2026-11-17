
package com.example.IAM_SERVER.Keys.adapters.out.EncryptionKeyAdapters.GenerateKeyPair;



import com.example.IAM_SERVER.Keys.domain.ports.out.MasterEncryptionKeyManager;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public final class AesGcmMasterEncryptionKeyManager
        implements MasterEncryptionKeyManager {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";

    private static final int NONCE_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;
    private static final int TAG_LENGTH_BYTES = TAG_LENGTH_BITS / 8;

    private static final byte FORMAT_VERSION = 1;

    private final SecretKey secretKey;
    private final SecureRandom secureRandom;

    public AesGcmMasterEncryptionKeyManager(SecretKey encryptionKey) {
        Objects.requireNonNull(
                encryptionKey,
                "Encryption key must not be null."
        );

        if (!"AES".equalsIgnoreCase(encryptionKey.getAlgorithm())) {
            throw new IllegalArgumentException(
                    "A valid AES key is required."
            );
        }

        this.secretKey = encryptionKey;
        this.secureRandom = new SecureRandom();
    }


    private String encryptBytes(byte[] plaintext) {
        Objects.requireNonNull(
                plaintext,
                "Plaintext must not be null."
        );

        byte[] nonce = new byte[NONCE_LENGTH_BYTES];
        secureRandom.nextBytes(nonce);

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey,
                    new GCMParameterSpec(TAG_LENGTH_BITS, nonce)
            );

            // Authenticate the format version.
            cipher.updateAAD(new byte[]{FORMAT_VERSION});

            byte[] ciphertext = cipher.doFinal(plaintext);

            ByteBuffer payload = ByteBuffer.allocate(
                    1 + NONCE_LENGTH_BYTES + ciphertext.length
            );

            payload.put(FORMAT_VERSION);
            payload.put(nonce);
            payload.put(ciphertext);

            return Base64.getEncoder()
                    .encodeToString(payload.array());

        } catch (GeneralSecurityException exception) {
            throw new EncryptionFailureException(
                    "Encryption failed.",
                    exception
            );
        }
    }


    private byte[] decryptBytes(String encryptedPayload) {
        if (encryptedPayload == null || encryptedPayload.isBlank()) {
            throw new IllegalArgumentException(
                    "Encrypted payload must not be blank."
            );
        }

        final byte[] payload;

        try {
            payload = Base64.getDecoder().decode(encryptedPayload);
        } catch (IllegalArgumentException exception) {
            throw new DecryptionFailureException(
                    "Encrypted payload is not valid Base64.",
                    exception
            );
        }

        if (payload.length <
                1 + NONCE_LENGTH_BYTES + TAG_LENGTH_BYTES) {
            throw new DecryptionFailureException(
                    "Encrypted payload is incomplete."
            );
        }

        ByteBuffer buffer = ByteBuffer.wrap(payload);
        byte version = buffer.get();

        if (version != FORMAT_VERSION) {
            throw new DecryptionFailureException(
                    "Unsupported encrypted payload version."
            );
        }

        byte[] nonce = new byte[NONCE_LENGTH_BYTES];
        buffer.get(nonce);

        byte[] ciphertext = new byte[buffer.remaining()];
        buffer.get(ciphertext);

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    new GCMParameterSpec(TAG_LENGTH_BITS, nonce)
            );

            // Must match the AAD used during encryption.
            cipher.updateAAD(new byte[]{version});

            return cipher.doFinal(ciphertext);

        } catch (AEADBadTagException exception) {
            throw new DecryptionFailureException(
                    "Authentication failed: wrong key or modified payload.",
                    exception
            );
        } catch (GeneralSecurityException exception) {
            throw new DecryptionFailureException(
                    "Decryption failed.",
                    exception
            );
        }
    }

    @Override
    public String encrypt(SecretKey dek) {
        Objects.requireNonNull(dek, "DEK must not be null.");

        if (!"AES".equalsIgnoreCase(dek.getAlgorithm())) {
            throw new IllegalArgumentException(
                    "A valid AES DEK is required."
            );
        }

        byte[] dekBytes = dek.getEncoded();

        if (dekBytes == null || dekBytes.length != 32) {
            throw new IllegalArgumentException(
                    "A 256-bit AES DEK is required."
            );
        }

        try {
            return encryptBytes(dekBytes);
        } finally {
            Arrays.fill(dekBytes, (byte) 0);
        }
    }

    @Override
    public SecretKey decrypt(String wrappedDek) {
        byte[] dekBytes = decryptBytes(wrappedDek);

        try {
            if (dekBytes.length != 32) {
                throw new DecryptionFailureException(
                        "Decrypted DEK is not 256 bits."
                );
            }

            return new SecretKeySpec(dekBytes, "AES");
        } finally {
            Arrays.fill(dekBytes, (byte) 0);
        }
    }
    public static final class EncryptionFailureException
            extends RuntimeException {

        public EncryptionFailureException(
                String message,
                Throwable cause) {
            super(message, cause);
        }
    }

    public static final class DecryptionFailureException
            extends RuntimeException {

        public DecryptionFailureException(String message) {
            super(message);
        }

        public DecryptionFailureException(
                String message,
                Throwable cause) {
            super(message, cause);
        }
    }
}
