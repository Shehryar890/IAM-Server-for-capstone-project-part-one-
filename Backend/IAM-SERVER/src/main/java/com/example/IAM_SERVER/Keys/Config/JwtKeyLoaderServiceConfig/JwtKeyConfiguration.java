
package com.example.IAM_SERVER.Keys.Config.JwtKeyLoaderServiceConfig;

import com.example.IAM_SERVER.Keys.domain.model.ActiveKey;
import com.example.IAM_SERVER.Keys.domain.ports.out.GetJwtKeys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtKeyConfiguration {

    @Bean
    public ActiveKey activeKey(GetJwtKeys getJwtKeys) {

        return getJwtKeys.getJwtkeys();
    }
}
