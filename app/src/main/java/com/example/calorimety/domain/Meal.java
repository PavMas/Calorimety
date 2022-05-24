package com.example.calorimety.domain;


import com.example.calorimety.domain.ProductItem;

import java.util.List;

public class Meal {

    private int uid;
    private String name;
    private List<ProductItem> list;

    public Meal(String name, List<ProductItem> list) {
        this.name = name;
        this.list = list;
    }

    public Meal(int uid, String name, List<ProductItem> productItemList) {
        this.uid = uid;
        this.name = name;
        this.list = productItemList;
    }


    public String getName() {
        return name;
    }

    public List<ProductItem> getProductItemList() {
        return list;
    }
}
