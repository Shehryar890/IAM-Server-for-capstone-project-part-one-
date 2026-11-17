package com.example.IAM_SERVER.Keys.domain.ports.out.concretInterfaces;

import com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces.GenerateKeyInterface;
import com.example.IAM_SERVER.Keys.domain.ports.out.Keystorerecord;

import javax.swing.*;
import java.security.KeyStore;

public interface GenerateKeyStore extends GenerateKeyInterface<Keystorerecord, Void> {

}
