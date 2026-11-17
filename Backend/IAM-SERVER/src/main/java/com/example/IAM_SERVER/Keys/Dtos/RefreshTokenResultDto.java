package com.example.IAM_SERVER.Keys.Dtos;


import java.time.Instant;

public record RefreshTokenResultDto(
        String token,
        Instant expiresAt
) {
}