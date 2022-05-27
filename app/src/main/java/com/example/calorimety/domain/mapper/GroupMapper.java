package com.example.calorimety.domain.mapper;

import com.example.calorimety.domain.ProductGroup;


import org.json.JSONException;
import org.json.JSONObject;

public class GroupMapper {

        public static ProductGroup groupFromJson(JSONObject object) {
            ProductGroup group = null;
            try {
                group = new ProductGroup(
                        object.getInt("id"),
                        object.getString("group_name"),
                        new ProductMapper().productsFromJson(object.getJSONArray("productList"))
                );
            } catch (JSONException ignore) {

            }
            return group;
        }
}
