package com.example.calorimety.domain;


public class ProductItem {
    private final String name;
    private final float value;
    private final float weight;


    public ProductItem(String name, float value, float weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }


    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public float getWeight() {
        return weight;
    }
}
