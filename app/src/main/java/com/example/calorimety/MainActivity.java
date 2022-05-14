package com.example.calorimety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.calorimety.fragments.AccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    int fragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        bnv.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            NavHostFragment navHostFragment;
            NavController navController;
            switch (id){
                case R.id.page_1:
                    navHostFragment =
                            (NavHostFragment) fragmentManager.getPrimaryNavigationFragment();
                    assert navHostFragment != null;
                    navController = navHostFragment.getNavController();
                    if(fragment != R.id.mainFragment)
                    navController.navigate(R.id.navigateToMainFragment);
                    fragment = R.id.mainFragment;
                    break;
                case R.id.page_2:
                    navHostFragment =
                            (NavHostFragment) fragmentManager.getPrimaryNavigationFragment();
                    assert navHostFragment != null;
                    navController = navHostFragment.getNavController();
                    if(fragment != R.id.accountFragment)
                    navController.navigate(R.id.navigateToAccountFragment);
                    fragment = R.id.accountFragment;
                    break;
            }
            return true;
        });

    }
}