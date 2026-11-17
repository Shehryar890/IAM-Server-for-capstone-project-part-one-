package com.example.IAM_SERVER.Keys.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

public record TokenHash(String value) {

    private static final Pattern SHA256_HEX_PATTERN =
            Pattern.compile("^[0-9a-f]{64}$");

    public TokenHash {
        Objects.requireNonNull(value, "Token hash cannot be null");

        if (!SHA256_HEX_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "Token hash must be a 64-character lowercase SHA-256 hex string"
            );
        }
    }
}

