package com.example.calorimety.rest;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.calorimety.MainActivity;
import com.example.calorimety.domain.ProductGroup;
import com.example.calorimety.domain.User;
import com.example.calorimety.domain.mapper.GroupMapper;
import com.example.calorimety.domain.mapper.UserMapper;
import com.example.calorimety.fakeDB.FakeDB;
import com.example.calorimety.fragments.AccountFragment;
import com.example.calorimety.fragments.MainFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CalorimetryApiVolley implements CalorimetryApi {

    private final Context context;
    public static final String BASE_URL = "http://192.168.1.105:8080";

    public CalorimetryApiVolley(Context context) {
        this.context = context;
    }
    private final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("TSET1", "responseError");
        }
    };
    User user;




    @Override
    public void getUser(final String name, final ServerCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/user/" + name;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                User user = new UserMapper().userFromJson(response);
                Log.d("TEST1", user.toString());
                callback.onSuccess(user);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }, errorListener);
        requestQueue.add(objectRequest);
    }


    @Override
    public void fillGroups() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/groups";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for(int i = 0; i<response.length(); i++){
                try{
                    JSONObject jsonObject = response.getJSONObject(i);
                    ProductGroup productGroup = new GroupMapper().groupFromJson(jsonObject);
                    FakeDB.PRODUCT_GROUPS.add(productGroup);
                    Log.d("GROUPS_TEST", productGroup.toString());
                }
                catch (JSONException e){

                }
            }
        }, errorListener);
        requestQueue.add(arrayRequest);
    }

    @Override
    public void addUser() {

    }

    @Override
    public void deleteUser() {

    }


}
