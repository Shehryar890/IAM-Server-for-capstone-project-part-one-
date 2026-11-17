package com.example.IAM_SERVER.Keys.domain.ports.in;

import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;

public interface RefreshTokenRotationWorkerPort {

    String rotate(RefreshToken existingToken);
}