package com.example.calorimety;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;

import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    static int accountFragmentNav1 = R.id.navigateToAccountFragment;
    static int accountFragmentNav2 = R.id.navigateToMainFragment;
    public static String SP_NAME = "SPrefs";
    SharedPreferences preferences;
    BottomNavigationView bnv;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        bnv = findViewById(R.id.bottom_navigation);
        if(preferences.contains("username")) {
            accountFragmentNav1 = R.id.main_to_inAccount;
            accountFragmentNav2 = R.id.inAccount_to_main;
        }
        bnv.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            NavHostFragment navHostFragment =
                    (NavHostFragment) fragmentManager.getPrimaryNavigationFragment();
            assert navHostFragment != null;
            NavController navController = navHostFragment.getNavController();
            switch (id){
                case R.id.page_1:
                    try {
                        navController.navigate(accountFragmentNav2);
                        if(accountFragmentNav2 == R.id.registration_to_mainFragment)
                            accountFragmentNav2 = R.id.navigateToMainFragment;
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

    public void changeFragmentNav(int id1, int id2){
        accountFragmentNav1 = id1;
        accountFragmentNav2 = id2;
    }



    @Override
    public void onBackPressed() {

    }

}