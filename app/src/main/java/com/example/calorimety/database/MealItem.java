package com.example.calorimety.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MealItem {


    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public float value;
    public float weight;
    public String mealName;

    public MealItem(String name, float value, float weight, String mealName) {
        this.name = name;
        this.value = value;
        this.weight = weight;
        this.mealName = mealName;
    }


    @NonNull
    @Override
    public String toString() {
        return mealName;
    }
}
