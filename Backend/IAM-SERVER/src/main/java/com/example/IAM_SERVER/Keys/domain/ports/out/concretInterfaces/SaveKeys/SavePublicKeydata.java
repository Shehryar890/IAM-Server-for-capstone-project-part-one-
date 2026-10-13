package com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.SaveKeys;

import com.example.IAM_SERVER.Keys.domain.model.SigningKeys;
import com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces.SavekeyInterface;

import java.util.Optional;

public interface SavePublicKeydata extends SavekeyInterface<Boolean , SigningKeys >{

    Optional<SigningKeys> findactivekey();
    Optional<Integer> getactivekey_Id();
}
