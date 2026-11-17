
package com.example.IAM_SERVER.Keys.adapters.in;

import com.example.IAM_SERVER.Keys.Config.CookieProperties;

import com.example.IAM_SERVER.Keys.Dtos.RefreshAuthenticationResult;
import com.example.IAM_SERVER.Keys.domain.ports.out.RefreshTokenRotationPort;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenRotationPort refreshTokenRotationPort;
    private final CookieProperties cookieProperties;
    private final Clock clock;

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(

            @CookieValue(
                    name = "${iam.cookies.refresh-token.name}",
                    required = false
            )
            String rawRefreshToken

    ) {

        // 1. Execute the refresh-token rotation use case.
        RefreshAuthenticationResult result =
                refreshTokenRotationPort.rotateRefreshToken(rawRefreshToken);

        // 2. Get the current time.
        Instant now = clock.instant();

        // 3. Calculate remaining lifetime of both tokens.
        Duration accessTokenMaxAge = Duration.between(
                now,
                result.accessToken().expiresAt()
        );

        Duration refreshTokenMaxAge = Duration.between(
                now,
                result.refreshToken().expiresAt()
        );

        // 4. Validate access-token lifetime.
        if (accessTokenMaxAge.isNegative()
                || accessTokenMaxAge.getSeconds() <= 0) {

            throw new IllegalStateException(
                    "Generated access token has expired or has insufficient remaining lifetime"
            );
        }

        // 5. Validate refresh-token lifetime.
        if (refreshTokenMaxAge.isNegative()
                || refreshTokenMaxAge.getSeconds() <= 0) {

            throw new IllegalStateException(
                    "Generated refresh token has expired or has insufficient remaining lifetime"
            );
        }

        // 6. Get cookie configuration.
        CookieProperties.AccessTokenCookie accessConfig =
                cookieProperties.getAccessToken();

        CookieProperties.RefreshTokenCookie refreshConfig =
                cookieProperties.getRefreshToken();

        // 7. Create access-token cookie.
        ResponseCookie accessTokenCookie = ResponseCookie
                .from(
                        accessConfig.getName(),
                        result.accessToken().token()
                )
                .httpOnly(cookieProperties.isHttpOnly())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .path(accessConfig.getPath())
                .maxAge(accessTokenMaxAge)
                .build();

        // 8. Create refresh-token cookie.
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from(
                        refreshConfig.getName(),
                        result.refreshToken().token()
                )
                .httpOnly(cookieProperties.isHttpOnly())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .path(refreshConfig.getPath())
                .maxAge(refreshTokenMaxAge)
                .build();

        // 9. Add both cookies to the HTTP response.
        HttpHeaders headers = new HttpHeaders();

        headers.add(
                HttpHeaders.SET_COOKIE,
                accessTokenCookie.toString()
        );

        headers.add(
                HttpHeaders.SET_COOKIE,
                refreshTokenCookie.toString()
        );

        // 10. Return successful response.
        return new ResponseEntity<>(
                null,
                headers,
                HttpStatus.OK
        );
    }
}
