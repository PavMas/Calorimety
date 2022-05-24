package com.example.calorimety.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ProductItemDB {

    @PrimaryKey
    @NonNull
    //public int id;
    public String name;
    public float value;
    public String group_name;


    public ProductItemDB(String name, float value, String group_name) {
        this.name = name;
        this.value = value;
        this.group_name = group_name;
    }



    @NonNull
    @Override
    public String toString() {
        return name+", "+value+" ккал";
    }
}
