package com.example.calorimety.rest;

import com.example.calorimety.domain.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface ServerCallbackUser {

    void onSuccess(User user);

    void onNoUser();

}
