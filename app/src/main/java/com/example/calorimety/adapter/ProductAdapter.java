package com.example.calorimety.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorimety.R;
import com.example.calorimety.database.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

    private List<ProductRV> list = new ArrayList<>();

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        ProductRV productRV = list.get(position);
        holder.name.setText(productRV.name);
        holder.weight.setText(productRV.weight+"");
        holder.kcal.setText(productRV.value+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addItems(List<ProductRV> items){
        list = items;
        notifyDataSetChanged();
    }
}
