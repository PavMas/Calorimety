package com.example.calorimety.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calorimety.MainActivity;
import com.example.calorimety.R;
import com.example.calorimety.adapter.MealAdapter;
import com.example.calorimety.classes.MealListItem;
import com.example.calorimety.database.MealDB;
import com.example.calorimety.database.MealItem;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    AppCompatButton btnAdd;

    MealAdapter adapter;

    View view;

    MealDB db;

    CircularProgressIndicator indicator;
    RecyclerView recyclerView;
    CalorimetryApiVolley apiVolley;
    SharedPreferences preferences;

    List<MealListItem> mealListItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        init();
        new MyThread().start();
        btnAdd.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_addMealFragment);
        });

        return view;
    }


    private void init(){
        apiVolley = new CalorimetryApiVolley(requireContext());
        btnAdd = view.findViewById(R.id.btn_add);
        recyclerView = view.findViewById(R.id.rv_meal);
        indicator = (CircularProgressIndicator) view.findViewById(R.id.progress_circ);
        preferences = requireActivity().getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                indicator.setVisibility(CircularProgressIndicator.VISIBLE);
            }
            if(msg.what == 1){
                indicator.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                setManagerAndAdapter();
            }
        }
    };

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            handler.sendEmptyMessage(0);
            apiVolley.fillMeals(preferences.getInt("userid", 0), () -> {
                db = Room.databaseBuilder(requireContext(), MealDB.class, "mealDB").build();
                List<String> list = db.mealtDao().getMealName();
                float totalWeight = 0;
                for(String name : list){
                    List<MealItem> list1 = db.mealtDao().getByName(name);
                    for(MealItem item : list1)
                        totalWeight += item.weight;
                    mealListItems.add(new MealListItem(name, totalWeight, list1));
                }
               handler.sendEmptyMessage(1);
            });
        }
    }
    private void setManagerAndAdapter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        adapter = new MealAdapter();
        adapter.setList(mealListItems);
        recyclerView.setAdapter(adapter);

    }

/*
    @SuppressLint("HandlerLeak")
    Handler  handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                btnAdd.setVisibility(View.GONE);
                indicator.setVisibility(LinearProgressIndicator.VISIBLE);
            }
            if(msg.what == 1){
                btnAdd.setVisibility(View.VISIBLE);
                indicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }
        }
    };

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            handler.sendEmptyMessage(0);
            apiVolley.fillGroups(() -> handler.sendEmptyMessage(1));
        }
    }

 */
}