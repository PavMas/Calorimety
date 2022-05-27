package com.example.calorimety.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorimety.R;
import com.example.calorimety.database.MealItem;
import com.example.calorimety.domain.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

    private List<ProductItem> list = new ArrayList<>();



    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            ProductItem productItem = list.get(position);
            holder.name.setText(productItem.getName());
            holder.weight.setText(productItem.getWeight() + " г");
            holder.kcal.setText(productItem.getValue() + " ккал");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<ProductItem> items){
        list = items;
        notifyDataSetChanged();
    }

}
