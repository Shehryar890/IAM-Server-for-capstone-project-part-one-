package com.example.IAM_SERVER.Keys.domain.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;

public record KeyMangerRecord(

         String key ,
        PrivateKey privatekey,
        PublicKey publickey ,
         Algoritham algoritham ,
         Keystatus status ,
         Instant activated_at


) {
}

