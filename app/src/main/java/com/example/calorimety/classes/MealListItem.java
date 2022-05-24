package com.example.calorimety.classes;

import com.example.calorimety.database.MealItem;

import java.util.List;

public class MealListItem {
    private final String meal_name;
    private final float totalWeight;
    private final List<MealItem> productList;

    public MealListItem(String meal_name, float totalWeight, List<MealItem> productList) {
        this.meal_name = meal_name;
        this.totalWeight = totalWeight;
        this.productList = productList;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public List<MealItem> getProductList() {
        return productList;
    }

    public float getTotalWeight() {
        return totalWeight;
    }
}
