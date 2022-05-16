package com.example.calorimety.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calorimety.R;
import com.example.calorimety.adapter.GroupNameSpinnerAdapter;
import com.example.calorimety.database.AppDB;
import com.example.calorimety.database.DatabaseCallback;

import java.util.ArrayList;
import java.util.List;

public class AddMealFragment extends Fragment {

    View view;

    private AppCompatSpinner sp_groupNames;
    AppDB db;

    List<String> list;
    GroupNameSpinnerAdapter spinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_meal, container, false);

        sp_groupNames = view.findViewById(R.id.sp_group);
        db = Room.databaseBuilder(getContext(), AppDB.class, "productDB").build();
        getNames(() -> {spinnerAdapter = new GroupNameSpinnerAdapter(
                getActivity(), R.layout.spinner_item, list);
                sp_groupNames.setAdapter(spinnerAdapter);
        });
        return  view;
    }

    private void getNames(DatabaseCallback callback){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                list = db.productDao().getGroups();
                callback.onComplete();
            }
        });
        thread.start();
    }
}