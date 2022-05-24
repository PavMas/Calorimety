package com.example.calorimety.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calorimety.MainActivity;
import com.example.calorimety.R;


public class InAccountFragment extends Fragment {

    View view;
    AppCompatButton button;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_in_account, container, false);
        init();
        button.setOnClickListener(view1 -> {
            preferences = requireActivity().getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username").apply();
            ((MainActivity)requireActivity()).changeFragments();
            Navigation.findNavController(view).navigate(R.id.inAccount_to_account);
        });
        return view;

    }


    private void init(){
        button = view.findViewById(R.id.btn_signOut);
    }

}