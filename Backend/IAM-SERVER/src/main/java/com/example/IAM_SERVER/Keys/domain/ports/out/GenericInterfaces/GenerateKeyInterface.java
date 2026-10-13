package com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces;

public interface GenerateKeyInterface<T , R> {

    T generate(R r );
}
