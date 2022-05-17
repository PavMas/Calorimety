package com.example.calorimety.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(ProductItem item);

    @Update
    void update(ProductItem item);

    @Query("SELECT DISTINCT group_name FROM ProductItem")
    List<String> getGroups();

    @Query("SELECT * FROM ProductItem WHERE group_name =:name")
    List<ProductItem> getProducts(String name);

    @Query("SELECT * FROM ProductItem WHERE group_name =:name AND name = 'void'")
    List<ProductItem> getVoidProducts(String name);






}
