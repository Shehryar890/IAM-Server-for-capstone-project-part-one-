
package com.example.IAM_SERVER.Security.PlatformAdmin;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ConditionalOnWebApplication(
        type = ConditionalOnWebApplication.Type.SERVLET
)
public class PlatformAdminSecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain platformAdminSecurityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .securityMatcher(
                        "/api/v1/platform-admin/**",
                        "/api/v1/admin/**"
                )

                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(
                                "/api/v1/admin/login",
                                "/api/v1/admin/signup",
                                "/api/v1/platform-admin/login",
                                "/api/v1/platform-admin/signup"
                        ).permitAll()

                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
