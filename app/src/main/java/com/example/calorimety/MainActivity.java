package com.example.calorimety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.calorimety.domain.User;
import com.example.calorimety.fragments.AccountFragment;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    int fragment;
    int accountFragmentNav1 = R.id.navigateToAccountFragment;
    int accountFragmentNav2 = R.id.navigateToMainFragment;
    public static String SP_NAME = "SPrefs";


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
                    //if(fragment != R.id.mainFragment)
                    try {
                        navController.navigate(accountFragmentNav2);
                    }
                    catch (Exception ignore){}
                            //fragment = R.id.mainFragment;
                    break;
                case R.id.page_2:
                    navHostFragment =
                            (NavHostFragment) fragmentManager.getPrimaryNavigationFragment();
                    assert navHostFragment != null;
                    navController = navHostFragment.getNavController();
                    //if(fragment != R.id.accountFragment)
                    try {
                        navController.navigate(accountFragmentNav1);
                    }
                    catch (Exception ignore){}
                       // fragment = R.id.accountFragment;

                    break;
            }
            return true;
        });

    }
    public void changeAccountFragmentNav(){
        if(accountFragmentNav1 == R.id.navigateToAccountFragment)
            accountFragmentNav1 = R.id.main_to_inAccount;
        else
            accountFragmentNav1 = R.id.navigateToAccountFragment;
        if(accountFragmentNav2 == R.id.navigateToMainFragment)
            accountFragmentNav2 = R.id.inAccount_to_main;
        else
            accountFragmentNav2 = R.id.navigateToMainFragment;
    }
}