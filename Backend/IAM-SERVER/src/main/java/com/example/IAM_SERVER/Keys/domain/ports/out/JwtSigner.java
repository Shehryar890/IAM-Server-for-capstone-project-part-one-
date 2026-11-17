package com.example.IAM_SERVER.Keys.domain.ports.out;

public interface JwtSigner<T> {

    String sign(T token);

}