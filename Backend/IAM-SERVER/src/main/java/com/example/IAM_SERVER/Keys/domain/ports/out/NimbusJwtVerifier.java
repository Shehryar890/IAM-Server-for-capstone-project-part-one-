package com.example.IAM_SERVER.Keys.domain.ports.out;

public interface NimbusJwtVerifier<T> {

    void verify(T token);

}