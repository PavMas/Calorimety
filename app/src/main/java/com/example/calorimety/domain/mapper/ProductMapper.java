package com.example.calorimety.domain.mapper;

import com.example.calorimety.domain.Product;
import com.example.calorimety.domain.User;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static List<Product> productsFromJson(JSONArray array) {
        List<Product> productList = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                productList.add(new Product(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getLong("value"))
                );
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return productList;
    }
}
