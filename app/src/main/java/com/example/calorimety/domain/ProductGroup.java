package com.example.calorimety.domain;

import androidx.annotation.NonNull;

import java.util.List;

public class ProductGroup {

    private int id;
    private String group_name;
    private List<Product> productList;

    public ProductGroup(int id, String group_name, List<Product> productList) {
        this.id = id;
        this.group_name = group_name;
        this.productList = productList;
    }

    public int getId() {
        return id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: "+id+", "
                +"name: "+group_name
                +"products: "+productList.toString();
    }
}
