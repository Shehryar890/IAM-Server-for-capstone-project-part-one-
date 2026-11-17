package com.example.IAM_SERVER.Config.security;

import com.example.IAM_SERVER.Keys.Config.EnnConfigClasses.KeyStoreEnvProperties;
import com.example.IAM_SERVER.Keys.Config.EnnConfigClasses.KeyStorePasswordProperties;
import com.example.IAM_SERVER.Keys.Config.EnnConfigClasses.KeystoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyStorePropertiesConfig {

    @Bean
    public KeystoreProperties createKeyStoreProperties(
            KeyStoreEnvProperties env,
            KeyStorePasswordProperties passwordProperties
    ) {
        return new KeystoreProperties(env, passwordProperties);
    }
}