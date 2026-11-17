package com.example.IAM_SERVER.Keys.domain.ports.out;


import com.example.IAM_SERVER.Keys.Dtos.RefreshAuthenticationResult;
import com.example.IAM_SERVER.Keys.Dtos.RefreshTokenResultDto;
import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;

public interface RefreshTokenRotationWorkerPort {

 RefreshTokenResultDto rotate(RefreshToken existingToken);

}