package com.example.IAM_SERVER.Keys.domain.model;

import java.security.PrivateKey;

public record EncryptionKey(

        PrivateKey encryptionkey
) {
}

