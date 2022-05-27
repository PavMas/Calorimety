package com.example.calorimety.rest;


import com.example.calorimety.domain.Meal;
import com.example.calorimety.domain.User;

/**
 * Created by trifcdr.
 */
public interface CalorimetryApi {
    void getUser(String name, ServerCallbackUser callback);

    void addUser(User user, ServerCompleteCallback callback);

    void deleteMeal(String name);

    void deleteUser(int id, ServerCompleteCallback callback);

    void fillGroups(ServerCompleteCallback callback);

    void addMeal(Meal meal, ServerCompleteCallback callback);

    void fillMeals(int uid, ServerMealCallback callback);
}
