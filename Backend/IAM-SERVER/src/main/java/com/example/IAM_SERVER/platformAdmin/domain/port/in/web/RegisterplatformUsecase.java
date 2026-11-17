package com.example.IAM_SERVER.platformAdmin.domain.port.in.web;

import com.example.IAM_SERVER.platformAdmin.adapters.in.Cli.PlatformAdminRegisterCommand;

public interface RegisterplatformUsecase {
    public void register(PlatformAdminRegisterCommand command);
}