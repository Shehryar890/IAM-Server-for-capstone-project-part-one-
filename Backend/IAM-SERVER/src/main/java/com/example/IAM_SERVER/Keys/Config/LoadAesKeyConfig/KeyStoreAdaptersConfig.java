package com.example.IAM_SERVER.Keys.Config.LoadAesKeyConfig;


import com.example.IAM_SERVER.Keys.adapters.out.EncryptionKeyAdapters.GenerateKeyPair.GetAesKey;
import com.example.IAM_SERVER.Keys.adapters.out.LoadKeyStoreFromfile.FileLoadKeyStore;
import com.example.IAM_SERVER.Keys.adapters.out.ReadPasswordFromFile.FileReadPassword;
import com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces.ReadPassword;
import com.example.IAM_SERVER.Keys.domain.ports.out.GetMasterEncryptionKey;
import com.example.IAM_SERVER.Keys.domain.ports.out.LoadKeyStore;


import com.example.IAM_SERVER.Keys.Config.EnnConfigClasses.KeyStoreEnvProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.file.Path;

@Configuration
public class KeyStoreAdaptersConfig {

    @Bean
    public ReadPassword<char[], Path> passwordReader() {
        return new FileReadPassword();
    }

    @Bean
    public LoadKeyStore keyStoreLoader(
                    KeyStoreEnvProperties properties) {
        return new FileLoadKeyStore(properties);
    }

    @Bean
    public GetMasterEncryptionKey<SecretKey> aesKeyRetriever() {
        return new GetAesKey();
    }
}
