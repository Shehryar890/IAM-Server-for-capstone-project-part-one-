package com.example.IAM_SERVER.platformAdmin.Dtos;

public record LoginResponse(
        String accessToken,
        String tokenType
) {
}