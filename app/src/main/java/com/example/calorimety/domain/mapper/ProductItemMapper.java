package com.example.calorimety.domain.mapper;


import com.example.calorimety.domain.Product;
import com.example.calorimety.domain.ProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductItemMapper {


    public static List<ProductItem> productItemFromJson(JSONArray array) {
        List<ProductItem> productList = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                productList.add(new ProductItem(
                        jsonObject.getString("name"),
                        jsonObject.getLong("value"),
                        jsonObject.getLong("weight"))
                );
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return productList;
    }

}
