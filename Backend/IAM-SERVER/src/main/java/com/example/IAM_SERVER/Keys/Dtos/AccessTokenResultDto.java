package com.example.IAM_SERVER.Keys.Dtos;


import java.time.Instant;

public record AccessTokenResultDto(
        String token,
        Instant expiresAt
) {
}