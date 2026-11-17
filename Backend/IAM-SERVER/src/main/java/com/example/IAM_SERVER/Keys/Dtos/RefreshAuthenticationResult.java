package com.example.IAM_SERVER.Keys.Dtos;



public record RefreshAuthenticationResult(
         AccessTokenResultDto accessToken,
        RefreshTokenResultDto refreshToken
) {}