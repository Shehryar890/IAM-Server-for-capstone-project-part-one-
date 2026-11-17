package com.example.IAM_SERVER.Keys.domain.model;

public record AdminJwtHeader(
        String algorithm,
        String keyId,
        String type
) {
}