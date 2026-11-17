
package com.example.IAM_SERVER.Keys.domain.ports.out;

import com.example.IAM_SERVER.Keys.domain.model.RefreshToken;

public interface RefreshTokenValidatorPort {

    RefreshToken validateRefreshToken(String rawToken);

}