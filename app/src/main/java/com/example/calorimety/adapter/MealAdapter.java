package com.example.calorimety.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.calorimety.R;
import com.example.calorimety.classes.MealListItem;
import com.example.calorimety.database.DatabaseCallback;
import com.example.calorimety.database.MealDB;
import com.example.calorimety.database.MealItem;
import com.example.calorimety.database.ProductItemDB;
import com.example.calorimety.rest.CalorimetryApiVolley;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealHolder> {

    List<MealListItem> mealListItems;

    CalorimetryApiVolley apiVolley;

    MealDB db;
    public MealAdapter(Context context) {
        apiVolley = new CalorimetryApiVolley(context);
        db = Room.databaseBuilder(context, MealDB.class, "mealDB").build();
    }

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
        holder.delete.setOnClickListener(view -> {
            delete(item, position);
        });
        holder.description.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("mealData", item);
            Navigation.findNavController(view).navigate(R.id.mainFragment_to_showFragment, bundle);
        });
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
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
                notifyDataSetChanged();
        }
    };
    private void delete(MealListItem item, int position){
            Thread thread = new Thread(() -> {
            apiVolley.deleteMeal(item.getMeal_name());
            mealListItems.remove(position);
            db.mealtDao().delete(item.getMeal_name());
            handler.sendEmptyMessage(0);
        });
        thread.start();
    }
}
