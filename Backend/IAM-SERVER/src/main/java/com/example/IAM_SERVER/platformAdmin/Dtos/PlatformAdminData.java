package com.example.IAM_SERVER.platformAdmin.Dtos;

import com.example.IAM_SERVER.platformAdmin.domain.model.PasswordHash;

import java.util.UUID;

public record PlatformAdminData(
        UUID id,
        String username,
        PasswordHash passwordHash
) {
}