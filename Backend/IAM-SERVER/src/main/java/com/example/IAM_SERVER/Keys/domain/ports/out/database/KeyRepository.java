package com.example.IAM_SERVER.Keys.domain.ports.out.database;

import com.example.IAM_SERVER.Keys.domain.model.SigningKeys;

import java.util.Optional;

public interface KeyRepository {

    void save(SigningKeys singkeyobject);


}
