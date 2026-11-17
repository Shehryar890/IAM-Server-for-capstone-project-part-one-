package com.example.IAM_SERVER.Security.PlatformAdmin;


import com.example.IAM_SERVER.Keys.domain.ports.out.PlatformAdminJwtService;
import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminData;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class PlatformAdminJwtAuthenticationProvider
        implements AuthenticationProvider {

    private final PlatformAdminJwtService platformAdminJwtService;

    public PlatformAdminJwtAuthenticationProvider(
            PlatformAdminJwtService platformAdminJwtService
    ) {
        this.platformAdminJwtService = platformAdminJwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        String jwt = (String) authentication.getCredentials();

        try {
            // Validate JWT and retrieve the Platform Admin data.
            PlatformAdminData platformAdminData =
                    platformAdminJwtService.validateToken(jwt);

            // Convert verified admin data into Spring Security principal.
            PlatformAdminPrincipal principal =
                    new PlatformAdminPrincipal(
                            platformAdminData.id(),
                            platformAdminData.username()
                    );

            // Create authenticated Spring Security token.
            return PlatformAdminJwtAuthenticationToken.authenticated(
                    principal,
                    List.of(
                            new SimpleGrantedAuthority("ROLE_PLATFORM_ADMIN")
                    )
            );

        } catch (Exception ex) {

            throw new BadCredentialsException(
                    "Invalid or expired Platform Admin access token",
                    ex
            );
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return PlatformAdminJwtAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}
