package com.example.IAM_SERVER.platformAdmin.adapters.in.web.Controllers;

import jakarta.validation.constraints.NotBlank;

public record PlatformAdminLoginCommand(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password

) {
}