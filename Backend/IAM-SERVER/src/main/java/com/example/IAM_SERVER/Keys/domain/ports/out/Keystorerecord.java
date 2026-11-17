package com.example.IAM_SERVER.Keys.domain.ports.out;

import java.security.KeyStore;

public record Keystorerecord(
        KeyStore store ,
        String password
        ){};
