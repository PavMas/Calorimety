package com.example.calorimety.adapter;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorimety.R;
import com.google.android.material.button.MaterialButton;

public class MealHolder extends RecyclerView.ViewHolder {

    MaterialButton description, delete;
    TextView name, value;

    public MealHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    private void init(){
        delete = itemView.findViewById(R.id.card_btn_delete);
        description = itemView.findViewById(R.id.card_btn_des);
        value = itemView.findViewById(R.id.card_item_secondary_text);
        name = itemView.findViewById(R.id.card_item_text);
    }
}
