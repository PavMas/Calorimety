package com.example.calorimety.domain;

import androidx.annotation.NonNull;

public class Product {

    private int id;
    private String name;
    private float value;

    public Product(int id, String name, float value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return "name: "+name
                +", value: "+value;
    }
}
