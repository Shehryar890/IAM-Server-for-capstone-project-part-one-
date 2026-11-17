package com.example.IAM_SERVER.Keys.domain.ports.out;

public interface RefreshTokenGeneratorPort {

    /**
     * Generates a cryptographically secure refresh token.
     *
     * will return  Base64URL-encoded random token without padding.
     */
    String generateRefreshToken();
}
