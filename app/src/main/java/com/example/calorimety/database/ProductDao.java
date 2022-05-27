package com.example.calorimety.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(ProductItemDB item);

    @Update
    void update(ProductItemDB item);

    @Query("SELECT DISTINCT group_name FROM ProductItemDB")
    List<String> getGroups();

    @Query("SELECT * FROM ProductItemDB WHERE group_name =:name")
    List<ProductItemDB> getProducts(String name);


}
