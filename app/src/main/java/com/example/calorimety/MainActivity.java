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
import android.content.SharedPreferences;
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
    static int accountFragmentNav1 = R.id.navigateToAccountFragment;
    static int accountFragmentNav2 = R.id.navigateToMainFragment;
    public static String SP_NAME = "SPrefs";
    SharedPreferences preferences;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        if(preferences.contains("username")) {
            accountFragmentNav1 = R.id.main_to_inAccount;
            accountFragmentNav2 = R.id.inAccount_to_main;
        }
        bnv.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            NavHostFragment navHostFragment =
                    (NavHostFragment) fragmentManager.getPrimaryNavigationFragment();;
            assert navHostFragment != null;
            NavController navController = navHostFragment.getNavController();
            switch (id){
                case R.id.page_1:
                    try {
                        navController.navigate(accountFragmentNav2);
                    }
                    catch (Exception ignore){}
                            //fragment = R.id.mainFragment;
                    break;
                case R.id.page_2:
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

    public void changeFragments(){
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