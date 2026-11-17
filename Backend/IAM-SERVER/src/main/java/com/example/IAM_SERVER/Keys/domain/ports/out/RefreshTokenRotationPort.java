package com.example.IAM_SERVER.Keys.domain.ports.out;

import com.example.IAM_SERVER.Keys.Dtos.RefreshAuthenticationResult;
import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminAuthenticationResult;

public interface RefreshTokenRotationPort {
    public RefreshAuthenticationResult rotateRefreshToken(String rawRefreshToken);
}
