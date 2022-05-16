package com.example.calorimety.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProductItem {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public float value;
    public String group_name;

    public ProductItem(String name, float value, String group_name) {
        this.name = name;
        this.value = value;
        this.group_name = group_name;
    }

    @NonNull
    @Override
    public String toString() {
        return id+", "+name+", "+value+", "+group_name;
    }
}
