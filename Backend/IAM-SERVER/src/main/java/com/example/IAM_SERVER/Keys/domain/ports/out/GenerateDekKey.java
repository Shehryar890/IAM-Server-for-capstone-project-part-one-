package com.example.IAM_SERVER.Keys.domain.ports.out;

import javax.crypto.SecretKey;

public interface GenerateDekKey {
    SecretKey generate();
}