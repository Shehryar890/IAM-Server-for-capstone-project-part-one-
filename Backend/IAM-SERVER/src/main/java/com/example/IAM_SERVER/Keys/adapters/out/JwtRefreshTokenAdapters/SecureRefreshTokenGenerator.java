package com.example.IAM_SERVER.Keys.adapters.out.JwtRefreshTokenAdapters;

import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenGeneratorPort;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
@Component
public final class SecureRefreshTokenGenerator
        implements RefreshTokenGeneratorPort {

    private static final int TOKEN_BYTE_LENGTH = 32;

    private static final Base64.Encoder BASE64_URL_ENCODER =
            Base64.getUrlEncoder().withoutPadding();

    private final SecureRandom secureRandom;

    public SecureRefreshTokenGenerator() {
        this.secureRandom = new SecureRandom();
    }

    @Override
    public String generateRefreshToken() {

        byte[] randomBytes = new byte[TOKEN_BYTE_LENGTH];

        secureRandom.nextBytes(randomBytes);

        return BASE64_URL_ENCODER.encodeToString(randomBytes);
    }
}
