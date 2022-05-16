package com.example.calorimety.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorimety.MainActivity;
import com.example.calorimety.R;
import com.example.calorimety.domain.User;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.example.calorimety.rest.ServerCallback;


public class AccountFragment extends Fragment {


    View view;
   public EditText tv_name, tv_password;
    Button btn_signIn;
    CalorimetryApiVolley apiVolley;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        btn_signIn = view.findViewById(R.id.signIn_btn);
        tv_name = view.findViewById(R.id.et_log);
        tv_password = view.findViewById(R.id.et_pass);
        apiVolley = new CalorimetryApiVolley(getContext());
        btn_signIn.setOnClickListener(view1 -> {
            apiVolley.getUser(tv_name.getText().toString(), user -> {
                if(user != null)
                    if(!user.getPassword().equals(tv_password.getText().toString()))
                        Toast.makeText(getContext(), "Пароль неверный", Toast.LENGTH_SHORT).show();
                    else {
                        preferences = getContext().getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("userid", user.getId());
                        editor.putString("username", user.getName()).apply();
                        Toast.makeText(getContext(), "Вход выполнен", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getContext()).changeAccountFragmentNav();
                        Navigation.findNavController(view).navigate(R.id.account_to_inAccount);
                    }
            });
        });
        return view;

    }
}