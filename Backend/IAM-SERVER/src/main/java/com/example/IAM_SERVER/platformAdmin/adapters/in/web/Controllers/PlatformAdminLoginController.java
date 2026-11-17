
package com.example.IAM_SERVER.platformAdmin.adapters.in.web.Controllers;

import com.example.IAM_SERVER.Keys.Config.CookieProperties;
import com.example.IAM_SERVER.platformAdmin.Dtos.PlatformAdminAuthenticationResult;
import com.example.IAM_SERVER.platformAdmin.domain.exceptions.TokenGenerationException;


import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.LoginUseCase;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1/admin/login")
public class PlatformAdminLoginController {

    private final LoginUseCase loginUseCase;
    private final CookieProperties cookieProperties;
    private final Clock clock;

    public PlatformAdminLoginController(
            LoginUseCase loginUseCase,
            CookieProperties cookieProperties,
            Clock clock
    ) {
        this.loginUseCase = loginUseCase;
        this.cookieProperties = cookieProperties;
        this.clock = clock;
    }

    @PostMapping
    public ResponseEntity<Void> login(
            @Valid @RequestBody PlatformAdminLoginRequest request
    ) {

        // 1. Convert HTTP request into application command.
       PlatformAdminLoginCommand command =
                new PlatformAdminLoginCommand(
                        request.username(),
                        request.password()
                );

        // 2. Execute login.
        PlatformAdminAuthenticationResult result =
                loginUseCase.login(command);

        // 3. Read cookie configurations.
        CookieProperties.AccessTokenCookie accessConfig =
                cookieProperties.getAccessToken();

        CookieProperties.RefreshTokenCookie refreshConfig =
                cookieProperties.getRefreshToken();

        // 4. Capture current time once.
        Instant now = clock.instant();

        // 5. Calculate remaining token lifetimes.
        Duration accessMaxAge = Duration.between(
                now,
                result.accessToken().expiresAt()
        );

        Duration refreshMaxAge = Duration.between(
                now,
                result.refreshToken().expiresAt()
        );

        // 6. Validate remaining lifetimes.
        validateTokenLifetime(accessMaxAge, "access");
        validateTokenLifetime(refreshMaxAge, "refresh");

        // 7. Create access-token cookie.
        ResponseCookie accessCookie = ResponseCookie
                .from(accessConfig.getName(), result.accessToken().token())
                .httpOnly(cookieProperties.isHttpOnly())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .path(accessConfig.getPath())
                .maxAge(accessMaxAge)
                .build();

        // 8. Create refresh-token cookie.
        ResponseCookie refreshCookie = ResponseCookie
                .from(refreshConfig.getName(), result.refreshToken().token())
                .httpOnly(cookieProperties.isHttpOnly())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .path(refreshConfig.getPath())
                .maxAge(refreshMaxAge)
                .build();

        // 9. Return both cookies.
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        // Authentication responses should not be cached.
        headers.setCacheControl("no-store");
        headers.setPragma("no-cache");

        return new ResponseEntity<>(
                null,
                headers,
                HttpStatus.OK
        );
    }

    private void validateTokenLifetime(
            Duration remainingLifetime,
            String tokenType
    ) {
        if (remainingLifetime == null
                || remainingLifetime.isNegative()
                || remainingLifetime.isZero()
                || remainingLifetime.getSeconds() <= 0) {

            throw new TokenGenerationException(
                    "Generated " + tokenType
                            + " token has expired or insufficient remaining lifetime"
            );
        }
    }
}
