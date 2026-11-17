package com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces;

public interface ReadPassword<T,R> {
    T readPassword(R r);
}
