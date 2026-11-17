package com.example.IAM_SERVER.Keys.domain.model;

import java.time.Instant;

public record JwtClaims(
        String issuer,
        String subject,
        String audience,
        Instant issuedAt,
        Instant expirationTime,
        Instant notBefore,
        String jwtId
) {
}