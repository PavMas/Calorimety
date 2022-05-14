package com.example.calorimety.rest;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.calorimety.domain.User;
import com.example.calorimety.domain.mapper.UserMapper;

import org.json.JSONException;
import org.json.JSONObject;

public class CalorimetryApiVolley implements CalorimetryApi {

    private final Context context;
    public static final String BASE_URL = "http://192.168.1.7:8080";

    public CalorimetryApiVolley(Context context) {
        this.context = context;
    }
    private Response.ErrorListener errorListener;



    @Override
    public User getUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = BASE_URL+"/user";
        User user = null;
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    user = new UserMapper().userFromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, errorListener);
        return user;
    }

    @Override
    public void addUser() {

    }

    @Override
    public void deleteUser() {

    }
}
