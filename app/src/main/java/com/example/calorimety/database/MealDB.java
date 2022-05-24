package com.example.calorimety.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MealItem.class}, version = 1)
public abstract class MealDB extends RoomDatabase {
    public abstract MealDao mealtDao();
}
