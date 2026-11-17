package com.example.IAM_SERVER.Keys.Config.EnnConfigClasses;




import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "iam.keystore")
public record KeyStoreEnvProperties(

        Path path,
        String type
) {
}
