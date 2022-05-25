package com.example.calorimety.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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
import com.example.calorimety.rest.ServerCallbackUser;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class RegistrationFragment extends Fragment {
     View view;
     EditText password, username;
     AppCompatButton button;
     CalorimetryApiVolley apiVolley;
     TextView error;
     String salt = "V000SGJ2TnU5ZjRLeXNWaDR0YkkxZz09";
     SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        init();
        button.setOnClickListener(view1 -> {
            String name = username.getText().toString();
            String pass = password.getText().toString();
            if(!pass.equals("")&&!name.equals("")) {
                apiVolley.getUser(name, new ServerCallbackUser() {
                    @Override
                    public void onSuccess(User user) {
                        error.setTextColor(Color.RED);
                        error.setText("Пользователь с таким именем уже существует");
                    }
                    @Override
                    public void onNoUser() {
                        try {
                            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt.getBytes(), 65536, 128);
                            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                            byte[] hash = factory.generateSecret(spec).getEncoded();
                            Base64.Encoder enc = Base64.getEncoder();
                            apiVolley.addUser(new User(name, enc.encodeToString(hash)), () -> {
                                preferences = getContext().getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                apiVolley.getUser(name, new ServerCallbackUser() {
                                    @Override
                                    public void onSuccess(User user) {
                                        editor.putInt("userId", user.getId());
                                    }

                                    @Override
                                    public void onNoUser() {

                                    }
                                });
                                editor.putString("username", name).apply();
                                Toast.makeText(getContext(), "Вход выполнен", Toast.LENGTH_SHORT).show();
                                // ((MainActivity)getContext()).changeAccountFragmentNav();
                                Navigation.findNavController(view).navigate(R.id.reg_to_inAccount);
                            });
                        }
                        catch (Exception ignore){}
                    }
                });
            }
        });
        return view;
    }

    private void init(){
        apiVolley = new CalorimetryApiVolley(requireActivity().getApplicationContext());
        username = view.findViewById(R.id.et_name);
        password = view.findViewById(R.id.et_pass);
        button = view.findViewById(R.id.regBtn);
        error = view.findViewById(R.id.tv_err);
    }
}