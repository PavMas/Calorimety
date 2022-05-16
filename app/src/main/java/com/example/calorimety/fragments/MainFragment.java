package com.example.calorimety.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calorimety.R;


public class MainFragment extends Fragment {

    AppCompatButton btnAdd;

    View view;

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
}