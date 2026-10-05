package com.example.IAM_SERVER.platformAdmin.domain.port.in.web;

import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.RegisterPlatformAdminCommand;
import org.springframework.stereotype.Component;

@Component
 public interface RegisterPlatformAdminUseCase {
    void register(RegisterPlatformAdminCommand command);
        }