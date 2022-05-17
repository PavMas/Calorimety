package com.example.calorimety.rest;

/**
 * Created by trifcdr.
 */
public interface CalorimetryApi {
    void getUser(String name, ServerCallbackUser callback);
    void addUser();
    void deleteUser();
    void fillGroups(ServerCompleteCallback callback);
}
