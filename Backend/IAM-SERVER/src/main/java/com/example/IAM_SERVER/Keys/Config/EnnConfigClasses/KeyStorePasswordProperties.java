package com.example.IAM_SERVER.Keys.Config.EnnConfigClasses;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "iam.security.keystore-password")
public record KeyStorePasswordProperties(


 Path path

){};
