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
import android.widget.Toast;

import com.example.calorimety.MainActivity;
import com.example.calorimety.R;
import com.example.calorimety.adapter.MealAdapter;
import com.example.calorimety.classes.MealListItem;
import com.example.calorimety.database.MealDB;
import com.example.calorimety.database.MealItem;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.example.calorimety.rest.ServerMealCallback;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    AppCompatButton btnAdd;

    MealAdapter adapter;

    View view;

    MealDB db;

    LinearProgressIndicator indicator;
    RecyclerView recyclerView;
    CalorimetryApiVolley apiVolley;
    SharedPreferences preferences;

    List<MealListItem> mealListItems = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        init();
        if (preferences.contains("userid")) {
            MyThread myThread = new MyThread();
            myThread.start();
            btnAdd.setOnClickListener(view1 -> {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_addMealFragment);
            });
        }
        else
            btnAdd.setOnClickListener(view1 -> {
                Toast.makeText(requireContext(), "Пожалуйста, войдите в аккаунт", Toast.LENGTH_SHORT).show();
            });
        return view;
    }


    private void init(){
        apiVolley = new CalorimetryApiVolley(requireContext());
        btnAdd = view.findViewById(R.id.btn_add);
        recyclerView = view.findViewById(R.id.rv_meal);
        indicator = view.findViewById(R.id.pb_main);
        preferences = requireActivity().getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                indicator.setVisibility(LinearProgressIndicator.VISIBLE);
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
            mealListItems.clear();
            apiVolley.fillMeals(preferences.getInt("userid", 0), new ServerMealCallback() {
                @Override
                public void onComplete() {
                    db = Room.databaseBuilder(requireContext(), MealDB.class, "mealDB").build();
                    List<String> list = db.mealtDao().getMealName();
                    float totalWeight;
                    for(String name : list){
                        totalWeight = 0;
                        List<MealItem> list1 = db.mealtDao().getByName(name);
                        for(MealItem item : list1)
                            totalWeight += item.weight;
                        mealListItems.add(new MealListItem(name, totalWeight, list1));
                        handler.sendEmptyMessage(1);
                    }
                }

                @Override
                public void onNothing() {
                    handler.sendEmptyMessage(1);
                }
            });
        }
    }
    private void setManagerAndAdapter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        adapter = new MealAdapter(requireContext());
        adapter.setList(mealListItems);
        recyclerView.setAdapter(adapter);

    }

}