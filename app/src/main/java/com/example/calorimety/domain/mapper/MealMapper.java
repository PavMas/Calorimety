package com.example.calorimety.domain.mapper;


import com.example.calorimety.domain.Meal;

import org.json.JSONException;
import org.json.JSONObject;

public class MealMapper {

    public static Meal mealFromJson(JSONObject object) {
        Meal meal = null;
        try {
            meal = new Meal(
                    object.getString("name"),
                    new ProductItemMapper().productItemFromJson(object.getJSONArray("list"))
            );
        } catch (JSONException ignore) {

        }
        return meal;
    }
}
