package com.example.IAM_SERVER.Keys.Config;

import com.example.IAM_SERVER.Keys.Config.CookieProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CookieProperties.class)
@RequiredArgsConstructor
public class CookieConfiguration {

    private final CookieProperties cookieProperties;


    @PostConstruct
    public void validateCookieConfiguration() {

        validateSameSite();
        validateHttpOnly();
        validateAccessTokenCookie();
        validateRefreshTokenCookie();
    }


    private void validateSameSite() {

        String sameSite = cookieProperties.getSameSite();

        if (sameSite == null ||
                !(sameSite.equals("Strict")
                        || sameSite.equals("Lax")
                        || sameSite.equals("None"))) {

            throw new IllegalStateException(
                    "Invalid SameSite cookie configuration"
            );
        }

        if (sameSite.equals("None") && !cookieProperties.isSecure()) {

            throw new IllegalStateException(
                    "SameSite=None requires Secure=true"
            );
        }
    }


    private void validateHttpOnly() {

        if (!cookieProperties.isHttpOnly()) {

            throw new IllegalStateException(
                    "HttpOnly must be enabled for authentication cookies"
            );
        }
    }


    private void validateAccessTokenCookie() {

        var accessToken = cookieProperties.getAccessToken();

        if (accessToken == null
                || isBlank(accessToken.getName())
                || isBlank(accessToken.getPath())) {

            throw new IllegalStateException(
                    "Invalid access token cookie configuration"
            );
        }
    }


    private void validateRefreshTokenCookie() {

        var refreshToken = cookieProperties.getRefreshToken();

        if (refreshToken == null
                || isBlank(refreshToken.getName())
                || isBlank(refreshToken.getPath())) {

            throw new IllegalStateException(
                    "Invalid refresh token cookie configuration"
            );
        }
    }


    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
