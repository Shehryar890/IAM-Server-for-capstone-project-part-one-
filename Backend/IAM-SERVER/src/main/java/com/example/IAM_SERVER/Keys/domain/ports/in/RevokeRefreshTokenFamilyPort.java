package com.example.IAM_SERVER.Keys.domain.ports.in;


public interface RevokeRefreshTokenFamilyPort {

    void revokeFamily(String rawRefreshToken);
}