
package com.example.IAM_SERVER.platformAdmin.Dtos;

import com.example.IAM_SERVER.Keys.Dtos.AccessTokenResultDto;

import com.example.IAM_SERVER.Keys.Dtos.RefreshTokenResultDto;



public record PlatformAdminAuthenticationResult(
        AccessTokenResultDto accessToken,
        RefreshTokenResultDto refreshToken
) {
}
