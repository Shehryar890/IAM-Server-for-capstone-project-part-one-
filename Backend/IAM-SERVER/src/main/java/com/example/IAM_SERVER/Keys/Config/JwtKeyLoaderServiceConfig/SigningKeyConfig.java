package com.example.IAM_SERVER.Keys.Config.JwtKeyLoaderServiceConfig;

import com.example.IAM_SERVER.Keys.adapters.out.RsaGenerateKeys;
import com.example.IAM_SERVER.Keys.domain.model.Algoritham;
import com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.GenerateKeys.GenerateJwtKeys;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SigningKeyConfig {

    @Bean
    public Algoritham jwtAlgorithm() {
        return Algoritham.RSA;
    }

    @Bean
    public GenerateJwtKeys generateJwtKeys(Algoritham jwtAlgorithm) {
        return new RsaGenerateKeys(jwtAlgorithm);
    }
}