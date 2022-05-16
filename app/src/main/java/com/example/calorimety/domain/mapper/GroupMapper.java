package com.example.calorimety.domain.mapper;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.calorimety.domain.Product;
import com.example.calorimety.domain.ProductGroup;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupMapper {

        public static ProductGroup groupFromJson(JSONObject object) {
            ProductGroup group = null;
            try {
                group = new ProductGroup(
                        object.getInt("id"),
                        object.getString("group_name"),
                        new ProductMapper().productsFromJson(object.getJSONArray("productList"))
                );
            } catch (JSONException e) {

            }
            return group;
        }
}
