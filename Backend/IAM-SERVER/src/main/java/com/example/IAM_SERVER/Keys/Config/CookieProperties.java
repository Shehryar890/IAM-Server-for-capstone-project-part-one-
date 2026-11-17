package com.example.IAM_SERVER.Keys.Config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "iam.cookies")
public class CookieProperties {

    private boolean secure;

    private boolean httpOnly;

    private String sameSite;

    private AccessTokenCookie accessToken = new AccessTokenCookie();

    private RefreshTokenCookie refreshToken = new RefreshTokenCookie();


    @Getter
    @Setter
    public static class AccessTokenCookie {

        private String name;

        private String path;
    }


    @Getter
    @Setter
    public static class RefreshTokenCookie {

        private String name;

        private String path;
    }
}
