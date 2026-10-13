package com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces;

public interface SavekeyInterface<T , R>  {
    T savekey(R r);
}

