package com.example.calorimety.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductItem.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract ProductDao productDao();
}
