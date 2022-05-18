package com.example.calorimety.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorimety.R;

public class ProductHolder extends RecyclerView.ViewHolder {
    TextView name, weight, kcal;

    public ProductHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    private void init(){
        name = itemView.findViewById(R.id.item_name);
        weight = itemView.findViewById(R.id.item_weight);
        kcal = itemView.findViewById(R.id.item_kcal);
    }
}
