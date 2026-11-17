package com.example.IAM_SERVER.Keys.domain.ports.out;

import com.example.IAM_SERVER.Keys.domain.model.TokenHash;

public interface RefreshTokenHasherPort {

    TokenHash hash(String rawToken);

}