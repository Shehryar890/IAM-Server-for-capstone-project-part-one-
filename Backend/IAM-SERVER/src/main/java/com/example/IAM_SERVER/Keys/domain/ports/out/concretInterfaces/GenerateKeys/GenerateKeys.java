package com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces.GenerateKeys;

import com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces.GenerateKeyInterface;
import jakarta.validation.constraints.Null;

import java.security.KeyPair;

public interface GenerateKeys  extends GenerateKeyInterface<KeyPair , Void> {
    
}
