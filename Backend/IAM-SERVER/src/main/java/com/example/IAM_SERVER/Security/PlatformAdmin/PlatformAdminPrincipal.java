package com.example.IAM_SERVER.Security.PlatformAdmin;

import java.util.UUID;

public record PlatformAdminPrincipal(
        UUID id,
        String username
) {
}