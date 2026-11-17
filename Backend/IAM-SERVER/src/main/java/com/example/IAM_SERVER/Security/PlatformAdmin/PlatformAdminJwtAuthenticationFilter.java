package com.example.IAM_SERVER.Security.PlatformAdmin;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public final class PlatformAdminJwtAuthenticationFilter
        extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;

    public PlatformAdminJwtAuthenticationFilter(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader(AUTHORIZATION_HEADER);

        // No Bearer token: continue the chain.
        // Protected endpoints will be rejected later by Spring Security.
        if (authorizationHeader == null
                || !authorizationHeader.startsWith(BEARER_PREFIX)) {

            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader
                .substring(BEARER_PREFIX.length())
                .trim();

        // Reject an empty Bearer token.
        if (jwt.isEmpty()) {
            SecurityContextHolder.clearContext();
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid bearer token"
            );
            return;
        }

        try {
            // Create an unauthenticated authentication request.
            PlatformAdminJwtAuthenticationToken authenticationRequest =
                    PlatformAdminJwtAuthenticationToken.unauthenticated(jwt);

            authenticationRequest.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            // Delegate actual JWT verification to the AuthenticationProvider.
            Authentication authenticated =
                    authenticationManager.authenticate(authenticationRequest);

            // Store successfully authenticated identity for this request.
            SecurityContextHolder.getContext()
                    .setAuthentication(authenticated);

        } catch (org.springframework.security.core.AuthenticationException ex) {

            SecurityContextHolder.clearContext();

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired access token"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.equals("/api/v1/platform-admin/signup")
                || path.equals("/api/v1/platform-admin/login");
    }
}
