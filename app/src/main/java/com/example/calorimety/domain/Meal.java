package com.example.calorimety.domain;



import java.util.List;

public class Meal {

    private int uid;
    private final String name;
    private final List<ProductItem> list;

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
