package com.example.IAM_SERVER.Keys.domain.ports.out;

import com.example.IAM_SERVER.Keys.Dtos.AccessTokenResultDto;

import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminData;

import java.util.UUID;

public interface PlatformAdminJwtService {

    AccessTokenResultDto generateToken(UUID subjectId);

    PlatformAdminData validateToken(String token);
}