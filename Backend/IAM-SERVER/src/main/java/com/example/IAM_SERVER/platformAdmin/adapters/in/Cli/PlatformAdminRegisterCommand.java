package com.example.IAM_SERVER.platformAdmin.adapters.in.Cli;

import jakarta.validation.constraints.NotBlank;

public record PlatformAdminRegisterCommand(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password

) {
}