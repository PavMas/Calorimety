package com.example.calorimety.rest;

import com.example.calorimety.domain.User;

/**
 * Created by trifcdr.
 */
public interface CalorimetryApi {
    void getUser(String name, ServerCallback callback);
    void addUser();
    void deleteUser();
    void fillGroups();
}
