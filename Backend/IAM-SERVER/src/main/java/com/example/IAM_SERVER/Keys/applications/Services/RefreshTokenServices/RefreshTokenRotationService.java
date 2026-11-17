package com.example.IAM_SERVER.Keys.applications.Services.RefreshTokenServices;


import com.example.IAM_SERVER.Keys.Dtos.AccessTokenResultDto;

import com.example.IAM_SERVER.Keys.Dtos.RefreshAuthenticationResult;
import com.example.IAM_SERVER.Keys.Dtos.RefreshTokenResultDto;
import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;
import com.example.IAM_SERVER.Keys.domain.ports.out.PlatformAdminJwtService;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenRotationPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenRotationWorkerPort;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenValidatorPort;

import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminAuthenticationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenRotationService
        implements RefreshTokenRotationPort {

    private final RefreshTokenValidatorPort refreshTokenValidator;
    private final RefreshTokenRotationWorkerPort rotationWorker;
    private final PlatformAdminJwtService platformAdminJwtService;

    @Override
    @Transactional
    public
    RefreshAuthenticationResult  rotateRefreshToken(
            String rawRefreshToken
    ) {

        // 1. Validate incoming refresh token.
        RefreshToken existingToken =
                refreshTokenValidator.validateRefreshToken(rawRefreshToken);

        // 2. Generate new access token for the same admin.
        AccessTokenResultDto accessToken =
                platformAdminJwtService.generateToken(
                        existingToken.getPlatformAdminId()
                );

        // 3. Rotate old refresh token.
        RefreshTokenResultDto refreshToken =
                rotationWorker.rotate(existingToken);

        // 4. Combine both results.
        return new RefreshAuthenticationResult(
                accessToken,
                refreshToken
        );
    }
}