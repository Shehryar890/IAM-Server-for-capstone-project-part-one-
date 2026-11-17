package com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces;


import javax.net.ssl.KeyManager;

public interface SaveSigningKey {

    KeyManager saveNewActiveKey();

}