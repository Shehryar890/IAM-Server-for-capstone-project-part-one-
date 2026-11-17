package com.example.IAM_SERVER.platformAdmin.domain.port.in.web;


import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminAuthenticationResult;

import com.example.IAM_SERVER.platformAdmin.adapters.in.web.Controllers.PlatformAdminLoginCommand;
import org.springframework.stereotype.Component;

@Component
public interface LoginUseCase {
    public PlatformAdminAuthenticationResult login(PlatformAdminLoginCommand command);

}