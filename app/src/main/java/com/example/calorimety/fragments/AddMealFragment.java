package com.example.calorimety.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.calorimety.MainActivity;
import com.example.calorimety.R;
import com.example.calorimety.adapter.GroupNameSpinnerAdapter;
import com.example.calorimety.adapter.ProductAdapter;
import com.example.calorimety.adapter.ProductsSpinnerAdapter;
import com.example.calorimety.database.ProductDB;
import com.example.calorimety.database.DatabaseCallback;
import com.example.calorimety.database.ProductItemDB;
import com.example.calorimety.domain.Meal;
import com.example.calorimety.domain.ProductItem;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class AddMealFragment extends Fragment {

    View view;

    private AppCompatSpinner sp_groupNames, sp_products;
    ProductDB db;

    LinearLayout layout;

    List<String> list;
    List<ProductItemDB> productItemDBS;
    GroupNameSpinnerAdapter groupNameSpinnerAdapter;
    ProductsSpinnerAdapter productsSpinnerAdapter;

    CalorimetryApiVolley apiVolley;

    LinearProgressIndicator indicator;

    AppCompatButton addBtn, saveBtn;

    RecyclerView recyclerView;

    ProductAdapter adapter;

    EditText name, weight;

    String product;

    float value;

    List<ProductItem> products = new ArrayList<>();

    SharedPreferences preferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);
        init();
        db = Room.databaseBuilder(requireContext(), ProductDB.class, "productDB").build();
        new MyThread().start();
        addBtn.setOnClickListener(view1 -> {
            product = ((ProductItemDB) sp_products.getSelectedItem()).name;
            value = ((ProductItemDB) sp_products.getSelectedItem()).value;
            products.add(new ProductItem(product, value/100*Float.parseFloat(weight.getText().toString()), Float.parseFloat(weight.getText().toString())));
            adapter.addItems(products);
        });
        saveBtn.setOnClickListener(view1 -> {
            if(products.size() != 0){
                preferences = requireActivity().getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
                int uid = preferences.getInt("userid", 0);
                apiVolley.addMeal(new Meal(uid, name.getText().toString(), products),() -> {
                    handler.sendEmptyMessage(3);
                });
            }
        });
        return  view;
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                productsSpinnerAdapter = new ProductsSpinnerAdapter(requireActivity(), R.layout.spinner_item, productItemDBS);
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
            if(msg.what == 3)
                Navigation.findNavController(view).navigate(R.id.action_addMealFragment_to_mainFragment);
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
            productItemDBS = db.productDao().getProducts(name);
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
                        requireActivity(), R.layout.spinner_item, list);
                handler.sendEmptyMessage(2);
            }
        ));
        }
    }
    private void init(){
        layout = view.findViewById(R.id.LL_add);
        indicator = view.findViewById(R.id.pb_add);
        apiVolley = new CalorimetryApiVolley(requireActivity().getApplicationContext());
        sp_groupNames = view.findViewById(R.id.sp_group);
        sp_products = view.findViewById(R.id.sp_product);
        addBtn = view.findViewById(R.id.btn_add);
        saveBtn = view.findViewById(R.id.saveBtn);
        recyclerView = view.findViewById(R.id.rv_products);
        name = view.findViewById(R.id.et_meal);
        weight = view.findViewById(R.id.et_weight);
        setManagerAndAdapter();
    }
    private void setManagerAndAdapter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);
    }
}