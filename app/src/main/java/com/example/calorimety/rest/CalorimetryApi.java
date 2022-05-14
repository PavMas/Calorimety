package com.example.calorimety.rest;

import com.example.calorimety.domain.User;

/**
 * Created by trifcdr.
 */
public interface CalorimetryApi {
    User getUser();
    void addUser();
    void deleteUser();
}
