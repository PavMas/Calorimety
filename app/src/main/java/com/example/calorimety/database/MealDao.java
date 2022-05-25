package com.example.calorimety.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface MealDao {

    @Insert
    void insert(MealItem item);

    @Update
    void update(MealItem item);

    @Query("SELECT DISTINCT mealName FROM MealItem")
    List<String> getMealName();

    @Query("SELECT * FROM MealItem WHERE mealName = :name")
    List<MealItem> getByName(String name);

    @Query("DELETE FROM MealItem WHERE mealName = :meal_name")
    void delete(String meal_name);



}
