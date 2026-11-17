
package com.example.IAM_SERVER.Keys.Config.JwtKeyLoaderServiceConfig;

import com.example.IAM_SERVER.Keys.adapters.out.EncryptionKeyAdapters.GenerateKeyPair.AesGcmKeyEncryptor;

import com.example.IAM_SERVER.Keys.adapters.out.EncryptionKeyAdapters.GenerateKeyPair.AesGcmKeyGeneratorAdapter;
import com.example.IAM_SERVER.Keys.domain.ports.out.DekKeyEncryptor;
import com.example.IAM_SERVER.Keys.domain.ports.out.GenerateDekKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DekConfiguration {

    @Bean
    public GenerateDekKey dekKeyGenerator() {
        return new AesGcmKeyGeneratorAdapter();
    }

    @Bean
    public DekKeyEncryptor dekKeyEncryptor() {
        return new AesGcmKeyEncryptor();
    }
}
