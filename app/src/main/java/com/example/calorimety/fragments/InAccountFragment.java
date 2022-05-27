package com.example.calorimety.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calorimety.MainActivity;
import com.example.calorimety.R;
import com.example.calorimety.database.MealDB;
import com.example.calorimety.domain.User;
import com.example.calorimety.rest.CalorimetryApiVolley;
import com.example.calorimety.rest.ServerCallbackUser;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class InAccountFragment extends Fragment {

    View view;
    AppCompatButton button, deleteBtn;
    SharedPreferences preferences;
    MealDB db;

    CalorimetryApiVolley apiVolley;


    String salt = "V000SGJ2TnU5ZjRLeXNWaDR0YkkxZz09";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_in_account, container, false);
        init();
        button.setOnClickListener(view1 -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("userid").apply();
            ((MainActivity) requireActivity()).changeFragments();
            clearDB();
            ((MainActivity)requireContext()).changeFragmentNav(R.id.navigateToAccountFragment, R.id.navigateToMainFragment);
            Navigation.findNavController(view).navigate(R.id.inAccount_to_account);
        });
        deleteBtn.setOnClickListener(view1 -> {
            final EditText password = new EditText(getActivity());
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
            builder.setMessage("Для подтверждения введите пароль:")
                    .setTitle("Удаление аккаунта")
                    .setPositiveButton("Удалить", (dialogInterface, i) -> {
                        apiVolley.getUser(preferences.getString("username", ""), new ServerCallbackUser() {
                            @Override
                            public void onSuccess(User user) {
                                try {
                                    KeySpec spec = new PBEKeySpec(password.getText().toString().toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 65536, 128);
                                    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                                    byte[] hash = factory.generateSecret(spec).getEncoded();
                                    Base64.Encoder enc = Base64.getEncoder();
                                    if (!user.getPassword().equals(enc.encodeToString(hash)))
                                        Toast.makeText(getContext(), "Пароль неверный", Toast.LENGTH_SHORT).show();
                                    else {
                                        apiVolley.deleteUser(user.getId(), () -> {
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.remove("userid");
                                            editor.remove("username").apply();
                                            Toast.makeText(getContext(), "Аккаунт удален", Toast.LENGTH_SHORT).show();
                                            clearDB();
                                            ((MainActivity)requireContext()).changeFragmentNav(R.id.navigateToAccountFragment, R.id.navigateToMainFragment);
                                            Navigation.findNavController(view).navigate(R.id.inAccount_to_account);
                                        });

                                    }
                                }
                                catch (Exception ignored){}
                            }
                            @Override
                            public void onNoUser() {
                            }
                        });

                    })
                    .setNegativeButton("Отмена", null)
                    .setView(password)
                    .show();
        });
        return view;
    }

    private void init() {
        button = view.findViewById(R.id.btn_signOut);
        deleteBtn = view.findViewById(R.id.btnDeleteAccount);
        deleteBtn.setBackgroundColor(Color.RED);
        apiVolley = new CalorimetryApiVolley(requireContext());
        preferences = requireActivity().getSharedPreferences(MainActivity.SP_NAME, Context.MODE_PRIVATE);
        db = Room.databaseBuilder(requireContext(), MealDB.class, "mealDB").build();
    }

    private void clearDB() {
        Thread thread = new Thread(() -> {
            requireActivity().getDatabasePath("mealDB").delete();
            requireActivity().getDatabasePath("mealDB-shm").delete();
            requireActivity().getDatabasePath("mealDB-wal").delete();
        });
        thread.start();
    }
}