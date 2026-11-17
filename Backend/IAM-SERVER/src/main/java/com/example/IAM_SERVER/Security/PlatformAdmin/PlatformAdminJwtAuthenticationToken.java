package com.example.IAM_SERVER.Security.PlatformAdmin;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class PlatformAdminJwtAuthenticationToken
        extends AbstractAuthenticationToken {

    private final Object principal;
    private String credentials;

    private PlatformAdminJwtAuthenticationToken(
            Object principal,
            String credentials,
            Collection<? extends GrantedAuthority> authorities,
            boolean authenticated
    ) {
        super(authorities);

        this.principal = principal;
        this.credentials = credentials;

        super.setAuthenticated(authenticated);
    }

    // Creates an authentication request containing an unverified JWT.
    public static PlatformAdminJwtAuthenticationToken unauthenticated(
            String jwt
    ) {
        if (jwt == null || jwt.isBlank()) {
            throw new IllegalArgumentException("JWT cannot be empty");
        }

        return new PlatformAdminJwtAuthenticationToken(
                null,
                jwt,
                List.of(),
                false
        );
    }

    // Creates an authenticated object after successful JWT verification.
    public static PlatformAdminJwtAuthenticationToken authenticated(
            PlatformAdminPrincipal principal,
            Collection<? extends GrantedAuthority> authorities
    ) {
        Objects.requireNonNull(principal, "Principal cannot be null");
        Objects.requireNonNull(authorities, "Authorities cannot be null");

        return new PlatformAdminJwtAuthenticationToken(
                principal,
                null,
                authorities,
                true
        );
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public String getCredentials() {
        return credentials;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Use the authenticated factory method"
            );
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        credentials = null;
        super.eraseCredentials();
    }
}
