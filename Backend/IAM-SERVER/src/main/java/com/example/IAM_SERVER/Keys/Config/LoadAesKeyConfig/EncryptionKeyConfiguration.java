
package com.example.IAM_SERVER.Keys.Config.LoadAesKeyConfig;

import com.example.IAM_SERVER.Keys.applications.Services.EncryptionKeyServices.AesEncryptionKeyLoaderService;

import com.example.IAM_SERVER.Keys.domain.ports.out.MasterEncryptionKeyManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionKeyConfiguration {

    @Bean
    public MasterEncryptionKeyManager encryptionKeyManager(
            AesEncryptionKeyLoaderService loaderService) {

        return loaderService.loadEncryptionKey();
    }
}
