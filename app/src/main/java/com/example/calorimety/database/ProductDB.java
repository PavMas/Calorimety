package com.example.calorimety.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductItemDB.class}, version = 1)
public abstract class ProductDB extends RoomDatabase {
    public abstract ProductDao productDao();
}
