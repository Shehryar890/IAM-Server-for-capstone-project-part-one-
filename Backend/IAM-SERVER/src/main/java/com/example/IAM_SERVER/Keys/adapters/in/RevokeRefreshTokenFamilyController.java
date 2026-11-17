
package com.example.IAM_SERVER.Keys.adapters.in;

import com.example.IAM_SERVER.Keys.Config.CookieProperties;
import com.example.IAM_SERVER.Keys.domain.ports.in.RevokeRefreshTokenFamilyPort;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RevokeRefreshTokenFamilyController {

    private final RevokeRefreshTokenFamilyPort revokeRefreshTokenFamilyPort;
    private final CookieProperties cookieProperties;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(
                    name = "${iam.cookies.refresh-token.name}",
                    required = false
            )
            String rawRefreshToken
    ) {

        // 1. Revoke the refresh-token family.
        if (rawRefreshToken != null && !rawRefreshToken.isBlank()) {
            revokeRefreshTokenFamilyPort.revokeFamily(rawRefreshToken);
        }

        // 2. Read cookie configurations.
        CookieProperties.AccessTokenCookie accessConfig =
                cookieProperties.getAccessToken();

        CookieProperties.RefreshTokenCookie refreshConfig =
                cookieProperties.getRefreshToken();

        // 3. Expire the access-token cookie.
        ResponseCookie accessTokenCookie = ResponseCookie
                .from(accessConfig.getName(), "")
                .httpOnly(cookieProperties.isHttpOnly())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .path(accessConfig.getPath())
                .maxAge(Duration.ZERO)
                .build();

        // 4. Expire the refresh-token cookie.
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from(refreshConfig.getName(), "")
                .httpOnly(cookieProperties.isHttpOnly())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .path(refreshConfig.getPath())
                .maxAge(Duration.ZERO)
                .build();

        // 5. Send both cookie expiration instructions.
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        // 6. Return successful logout.
        return new ResponseEntity<>(
                null,
                headers,
                HttpStatus.NO_CONTENT
        );
    }
}
