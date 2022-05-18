package com.example.calorimety.rest;


import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.calorimety.database.AppDB;
import com.example.calorimety.database.DatabaseCallback;
import com.example.calorimety.database.ProductItem;
import com.example.calorimety.domain.Product;
import com.example.calorimety.domain.ProductGroup;
import com.example.calorimety.domain.User;
import com.example.calorimety.domain.mapper.GroupMapper;
import com.example.calorimety.domain.mapper.UserMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CalorimetryApiVolley implements CalorimetryApi {

    private final Context context;
    public static final String BASE_URL = "http://192.168.1.7:8080";
    private AppDB db;

    int requests = 0;

    List<ProductItem> voids;

    public CalorimetryApiVolley(Context context) {
        this.context = context;
    }
    private final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("TSET1", "responseError");
        }
    };


    @Override
    public void getUser(final String name, final ServerCallbackUser callback) {
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
    public void fillGroups(final ServerCompleteCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        db = Room.databaseBuilder(context, AppDB.class, "productDB").build();
        String url = BASE_URL + "/groups";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for(int i = 0; i<response.length(); i++){
                try{
                    JSONObject jsonObject = response.getJSONObject(i);
                    ProductGroup productGroup = new GroupMapper().groupFromJson(jsonObject);
                    List<Product> list = productGroup.getProductList();
                    requests += list.size();
                    for(int j = 0; j < list.size(); j++) {
                        Product product = list.get(j);
                        ProductItem item = new ProductItem(product.getName(), product.getValue(), productGroup.getGroup_name());
                        insert(item, () -> {
                            requests--;
                            if(requests == 0)
                                callback.onComplete();
                        });
                    }
                    Log.d("DB_TEST", "end itters");
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

    private void insert(ProductItem item, DatabaseCallback callback){
        Thread thread = new Thread(() -> {
                try {
                    db.productDao().insert(item);
                    Log.d("insert", item.toString()+" "+item.group_name);
                    callback.onComplete();
                } catch (Exception e) {
                    db.productDao().update(item);
                    callback.onComplete();
                }
            });
        thread.start();
    }
}
