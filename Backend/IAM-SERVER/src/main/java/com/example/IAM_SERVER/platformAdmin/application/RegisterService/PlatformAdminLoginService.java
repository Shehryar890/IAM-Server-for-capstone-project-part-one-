
package com.example.IAM_SERVER.platformAdmin.application.RegisterService;

import com.example.IAM_SERVER.Keys.Dtos.AccessTokenResultDto;
import com.example.IAM_SERVER.Keys.Dtos.RefreshTokenResultDto;
import com.example.IAM_SERVER.Keys.applications.Services.RefreshTokenServices.GenerateRefreshTokenService;

import com.example.IAM_SERVER.Keys.domain.ports.out.PlatformAdminJwtService;
import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminAuthenticationResult;
import com.example.IAM_SERVER.platformAdmin.adapters.in.web.Controllers.PlatformAdminLoginCommand;
import com.example.IAM_SERVER.platformAdmin.domain.exceptions.InvalidCredentialsException;
import com.example.IAM_SERVER.platformAdmin.domain.model.platformAdmin;
import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.LoginUseCase;

import com.example.IAM_SERVER.platformAdmin.domain.port.out.Repository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PlatformAdminLoginService implements LoginUseCase {

    private final Repository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlatformAdminJwtService jwtService;
    private final GenerateRefreshTokenService refreshTokenService;

    public PlatformAdminLoginService(
            Repository adminRepository,
            PasswordEncoder passwordEncoder,
            PlatformAdminJwtService jwtService,
            GenerateRefreshTokenService refreshTokenService
    ) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public PlatformAdminAuthenticationResult login(
            PlatformAdminLoginCommand command
    ) {

        // 1. Fetch platform admin using username
        platformAdmin admin = adminRepository
                .getPlatformAdminData(command.username())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Credentials are invalid")
                );

        // 2. Compare submitted password with stored password hash
        boolean passwordMatches = passwordEncoder.matches(
                command.password(),
                admin.getpassword()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Credentials are invalid");
        }

        // 3. Generate access token
      AccessTokenResultDto accessToken = jwtService.generateToken(
                admin.getIamadmin_id()
        );

        // 4. Generate refresh token
        RefreshTokenResultDto refreshToken =
                refreshTokenService.generateFirstTimeRefreshToken(
                        admin.getIamadmin_id()
                );

        // 5. Return authentication result
        return new PlatformAdminAuthenticationResult(
                accessToken,
                refreshToken
        );
    }


}
