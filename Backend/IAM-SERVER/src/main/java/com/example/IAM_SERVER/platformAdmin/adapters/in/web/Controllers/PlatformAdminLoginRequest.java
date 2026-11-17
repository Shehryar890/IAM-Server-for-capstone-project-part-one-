package com.example.IAM_SERVER.platformAdmin.adapters.in.web.Controllers;

import jakarta.validation.constraints.NotBlank;

public record PlatformAdminLoginRequest(

        @NotBlank
        String username,

        @NotBlank
        String password

) {}