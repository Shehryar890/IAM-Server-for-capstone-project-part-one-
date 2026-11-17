package com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces;

import java.security.KeyStore;

public interface SaveKeyStore<T,R,S> {
    T save(R r , S s);
}
