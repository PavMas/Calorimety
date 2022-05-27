package com.example.calorimety.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calorimety.R;
import com.example.calorimety.adapter.ProductAdapter;
import com.example.calorimety.adapter.ShowMealAdapter;
import com.example.calorimety.classes.MealListItem;
import com.example.calorimety.database.MealItem;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ShowFragment extends Fragment {

    View view;

    TextView name;

    RecyclerView meal_rv;

    ShowMealAdapter adapter;

    List<MealItem> list;

    AppCompatButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show, container, false);
        init();
        Bundle bundle = this.getArguments();
        assert bundle != null;
        MealListItem item = bundle.getParcelable("mealData");
        name.setText(item.getMeal_name());
        setManagerAndAdapter();
        list = item.getProductList();
        list.sort((item1, t1) -> (int) (item1.getValue() - t1.getValue()));
        adapter.addItems(item.getProductList());
        button.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.show_to_mainFragment));
        return view;
    }

    private void init(){
        name = view.findViewById(R.id.tV_meal_name);
        meal_rv = view.findViewById(R.id.rv_showMeal);
        button = view.findViewById(R.id.showBack);
    }
    private void setManagerAndAdapter(){
        meal_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ShowMealAdapter();
        meal_rv.setAdapter(adapter);
    }
}