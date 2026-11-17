package com.example.IAM_SERVER.Keys.domain.ports.out;

import java.nio.file.Path;
import java.security.KeyStore;

public interface LoadKeyStore {
    KeyStore loadkeyStore(Path path , char[] c );
}
