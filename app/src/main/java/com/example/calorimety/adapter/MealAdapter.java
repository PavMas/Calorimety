package com.example.calorimety.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.calorimety.R;
import com.example.calorimety.classes.MealListItem;
import com.example.calorimety.database.MealDB;
import com.example.calorimety.database.MealItem;
import com.example.calorimety.database.ProductItemDB;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealHolder> {

    List<MealListItem> mealListItems;

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_card, parent, false);
        return new MealHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        MealListItem item = mealListItems.get(position);
        holder.name.setText(item.getMeal_name());
        holder.value.setText(item.getTotalWeight()+" ккал");
    }

    @Override
    public int getItemCount() {
        return mealListItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<MealListItem> list){
        mealListItems = list;
        notifyDataSetChanged();
    }
}
