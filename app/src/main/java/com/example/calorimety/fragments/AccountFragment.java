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
import android.widget.Toast;

import com.example.calorimety.MainActivity;
import com.example.calorimety.R;
import com.example.calorimety.domain.User;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.example.calorimety.rest.ServerCallbackUser;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class AccountFragment extends Fragment {


    View view;
    public EditText et_name, et_password;
    Button btn_signIn, btn_reg;
    CalorimetryApiVolley apiVolley;
    SharedPreferences preferences;

    String salt = "V000SGJ2TnU5ZjRLeXNWaDR0YkkxZz09";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        btn_signIn = view.findViewById(R.id.signIn_btn);
        btn_reg = view.findViewById(R.id.goReg);
        et_name = view.findViewById(R.id.et_log);
        et_password = view.findViewById(R.id.et_pass);
        apiVolley = new CalorimetryApiVolley(getContext());
        btn_signIn.setOnClickListener(view1 -> {
            apiVolley.getUser(et_name.getText().toString(), new ServerCallbackUser() {
                @Override
                public void onSuccess(User user) {
                    if(user != null) {
                        String pass = et_password.getText().toString();
                        try {
                            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 65536, 128);
                            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                            byte[] hash = factory.generateSecret(spec).getEncoded();
                            Base64.Encoder enc = Base64.getEncoder();
                            if (!user.getPassword().equals(enc.encodeToString(hash)))
                                Toast.makeText(getContext(), "Пароль неверный", Toast.LENGTH_SHORT).show();
                            else {
                                preferences = getContext().getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("userid", user.getId());
                                editor.putString("username", user.getName()).apply();
                                Toast.makeText(getContext(), "Вход выполнен", Toast.LENGTH_SHORT).show();
                                // ((MainActivity)getContext()).changeAccountFragmentNav();
                                ((MainActivity)requireActivity()).changeFragmentNav(R.id.main_to_inAccount, R.id.inAccount_to_main);
                                Navigation.findNavController(view).navigate(R.id.account_to_inAccount);
                            }
                        }
                        catch (Exception ignore){}
                    }
                }
                @Override
                public void onNoUser() {
                    Toast.makeText(requireContext(), "Такого пользоваьеля не существует", Toast.LENGTH_SHORT).show();
                }
            });
        });
        btn_reg.setOnClickListener(view1 -> {
            ((MainActivity)requireContext()).changeFragmentNav(R.id.navigateToAccountFragment, R.id.registration_to_mainFragment);
            Navigation.findNavController(view).navigate(R.id.account_to_reg);
        });
        return view;

    }
}