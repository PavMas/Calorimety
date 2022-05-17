package com.example.calorimety.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calorimety.R;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.google.android.material.progressindicator.LinearProgressIndicator;


public class MainFragment extends Fragment {

    AppCompatButton btnAdd;

    View view;

    LinearProgressIndicator indicator;
    CalorimetryApiVolley apiVolley;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        btnAdd = view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_addMealFragment);
        });

        return view;
    }
/*
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
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