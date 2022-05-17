package com.example.calorimety.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.calorimety.R;
import com.example.calorimety.adapter.GroupNameSpinnerAdapter;
import com.example.calorimety.adapter.ProductsSpinnerAdapter;
import com.example.calorimety.database.AppDB;
import com.example.calorimety.database.DatabaseCallback;
import com.example.calorimety.database.ProductItem;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

public class AddMealFragment extends Fragment {

    View view;

    private AppCompatSpinner sp_groupNames, sp_products;
    AppDB db;

    LinearLayout layout;

    List<String> list;
    List<ProductItem> productItems;
    GroupNameSpinnerAdapter groupNameSpinnerAdapter;
    ProductsSpinnerAdapter productsSpinnerAdapter;

    CalorimetryApiVolley apiVolley;

    LinearProgressIndicator indicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_meal, container, false);
        layout = view.findViewById(R.id.LL_add);
        indicator = view.findViewById(R.id.pb_add);
        apiVolley = new CalorimetryApiVolley(getActivity().getApplicationContext());
        sp_groupNames = view.findViewById(R.id.sp_group);
        sp_products = view.findViewById(R.id.sp_product);
        db = Room.databaseBuilder(getContext(), AppDB.class, "productDB").build();
        new MyThread().start();
        return  view;
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                productsSpinnerAdapter = new ProductsSpinnerAdapter(getActivity(), R.layout.spinner_item, productItems);
                sp_products.setAdapter(productsSpinnerAdapter);
            }
            if(msg.what == 0) {
                indicator.setVisibility(LinearProgressIndicator.VISIBLE);
            }
            if(msg.what == 2){
                sp_groupNames.setAdapter(groupNameSpinnerAdapter);
                layout.setVisibility(View.VISIBLE);
                indicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                sp_groupNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        getProducts(((String) sp_groupNames.getSelectedItem()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                getProducts(((String) sp_groupNames.getSelectedItem()));
            }
        }
    };
    private void getNames(DatabaseCallback callback){
        Thread thread = new Thread(() -> {
            list = db.productDao().getGroups();
            callback.onComplete();
        });
        thread.start();
    }
    private void getProducts(final String name){
        Thread thread = new Thread(() -> {
            productItems = db.productDao().getProducts(name);
            handler.sendEmptyMessage(1);
        });
        thread.start();
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            handler.sendEmptyMessage(0);
            apiVolley.fillGroups(() ->
                    getNames(() -> {
                groupNameSpinnerAdapter = new GroupNameSpinnerAdapter(
                        getActivity(), R.layout.spinner_item, list);
                handler.sendEmptyMessage(2);
            }
        ));
        }
    }
}