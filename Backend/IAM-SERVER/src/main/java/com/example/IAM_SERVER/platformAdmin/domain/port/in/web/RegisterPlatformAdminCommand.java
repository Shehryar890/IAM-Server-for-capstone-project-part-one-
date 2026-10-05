package com.example.IAM_SERVER.platformAdmin.domain.port.in.web;

public record RegisterPlatformAdminCommand(
        String username,
        String password
) {
}
