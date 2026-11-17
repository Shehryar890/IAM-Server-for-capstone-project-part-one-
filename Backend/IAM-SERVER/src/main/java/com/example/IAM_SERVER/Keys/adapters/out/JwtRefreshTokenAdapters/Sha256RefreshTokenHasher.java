package com.example.IAM_SERVER.Keys.adapters.out.JwtRefreshTokenAdapters;



import com.example.IAM_SERVER.Keys.domain.model.TokenHash;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenHasherPort;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;
@Component
public final class Sha256RefreshTokenHasher
        implements RefreshTokenHasherPort {

    @Override
    public TokenHash hash(String rawToken) {

        Objects.requireNonNull(
                rawToken,
                "Raw refresh token cannot be null"
        );

        if (rawToken.isBlank()) {
            throw new IllegalArgumentException(
                    "Raw refresh token cannot be blank"
            );
        }

        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(
                    rawToken.getBytes(StandardCharsets.UTF_8)
            );

            String hashHex = HexFormat.of()
                    .formatHex(hashBytes);

            return new TokenHash(hashHex);

        } catch (NoSuchAlgorithmException exception) {

            throw new IllegalStateException(
                    "SHA-256 algorithm is unavailable",
                    exception
            );
        }
    }
}
