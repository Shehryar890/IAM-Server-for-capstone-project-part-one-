package com.example.IAM_SERVER.Keys.Utility;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class RsaPublicKeyConverterUtility {

    private RsaPublicKeyConverterUtility() {

    }

    //converting publickey to string to save that in database


    public static String toString(PublicKey publicKey) {

        if (publicKey == null) {
            throw new IllegalArgumentException(
                    "Public key cannot be null"
            );
        }

        if (!(publicKey instanceof RSAPublicKey)) {
            throw new IllegalArgumentException(
                    "Provided public key is not an RSA public key"
            );
        }

        return Base64.getEncoder()
                .encodeToString(publicKey.getEncoded());
    }

 // converting the publickey coming from the database as a base64 encoded string back to java publickey object

    public static RSAPublicKey fromString(String publicKeyValue) {

        if (publicKeyValue == null || publicKeyValue.isBlank()) {
            throw new IllegalArgumentException(
                    "Public key value cannot be null or blank"
            );
        }

        try {
            byte[] publicKeyBytes = Base64.getDecoder()
                    .decode(publicKeyValue);

            X509EncodedKeySpec keySpec =
                    new X509EncodedKeySpec(publicKeyBytes);

            KeyFactory keyFactory =
                    KeyFactory.getInstance("RSA");

            PublicKey publicKey =
                    keyFactory.generatePublic(keySpec);

            if (!(publicKey instanceof RSAPublicKey rsaPublicKey)) {
                throw new IllegalArgumentException(
                        "Decoded key is not an RSA public key"
                );
            }

            return rsaPublicKey;

        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Invalid Base64-encoded RSA public key",
                    exception
            );

        } catch (java.security.GeneralSecurityException exception) {
            throw new IllegalStateException(
                    "Unable to reconstruct RSA public key",
                    exception
            );
        }
    }
}