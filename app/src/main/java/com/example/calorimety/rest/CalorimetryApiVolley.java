package com.example.calorimety.rest;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.calorimety.database.MealDB;
import com.example.calorimety.database.MealItem;
import com.example.calorimety.database.ProductDB;
import com.example.calorimety.database.DatabaseCallback;
import com.example.calorimety.database.ProductItemDB;
import com.example.calorimety.domain.Meal;
import com.example.calorimety.domain.Product;
import com.example.calorimety.domain.ProductGroup;
import com.example.calorimety.domain.ProductItem;
import com.example.calorimety.domain.User;
import com.example.calorimety.domain.mapper.GroupMapper;
import com.example.calorimety.domain.mapper.MealMapper;
import com.example.calorimety.domain.mapper.UserMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalorimetryApiVolley implements CalorimetryApi {

    private final Context context;
    public static final String BASE_URL = "http://192.168.1.5:8080";
    private ProductDB db;
    private MealDB mdb;

    int requests = 0;
    int mRequests = 0;

    List<ProductItemDB> voids;

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
                callback.onNoUser();
                e.printStackTrace();
            }
        }, error -> {
            callback.onNoUser();
        });
        requestQueue.add(objectRequest);
    }


    @Override
    public void fillGroups(final ServerCompleteCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        db = Room.databaseBuilder(context, ProductDB.class, "productDB").build();
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
                        ProductItemDB item = new ProductItemDB(product.getName(), product.getValue(), productGroup.getGroup_name());
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
    public void addMeal(Meal meal, ServerCompleteCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        mdb = Room.databaseBuilder(context, MealDB.class, "mealDB").build();
        String url = BASE_URL + "/meal";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(meal));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            List<ProductItem> list = meal.getProductItemList();
            mRequests+=list.size();
            for(int i = 0; i < list.size(); i++) {
                ProductItem item = list.get(i);
                insertMeal(new MealItem(item.getName(), item.getValue(), item.getWeight(), meal.getName()), () -> {
                    mRequests--;
                    if (mRequests == 0)
                        callback.onComplete();
                });
            }
        }, errorListener);
        queue.add(objectRequest);
    }


    @Override
    public void fillMeals(int uid, ServerMealCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        mdb = Room.databaseBuilder(context, MealDB.class, "mealDB").build();
        String url = BASE_URL+"/user/"+uid+"/meals";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if(response.length() == 0){
                callback.onNothing();
            }
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    Meal meal = new MealMapper().mealFromJson(jsonObject);
                    List<ProductItem> list = meal.getProductItemList();
                    mRequests += list.size();
                    for (int j = 0; j < list.size(); j++) {
                        ProductItem item = list.get(j);
                        MealItem mealItem = new MealItem(item.getName(), item.getValue(), item.getWeight(), meal.getName());
                        insertMeal(mealItem, () -> {
                            mRequests--;
                            if (mRequests == 0)
                                callback.onComplete();
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, errorListener);
        queue.add(arrayRequest);
    }

    @Override
    public void addUser(User user, ServerCompleteCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL+"/user";

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Log.d("resp", response);
            callback.onComplete();

        }, errorListener){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", user.getName());
                map.put("password", user.getPassword());
                return map;
            }

        };
        queue.add(request);

    }

    @Override
    public void deleteMeal(String name) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/meal/"+name;

        StringRequest request = new StringRequest(Request.Method.DELETE, url, response -> {

        }, errorListener);
        queue.add(request);
    }

    @Override
    public void deleteUser(int id, ServerCompleteCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/user/"+id;

        StringRequest request = new StringRequest(Request.Method.DELETE, url, response -> {
            callback.onComplete();
        }, errorListener);
        queue.add(request);
    }


    private void insert(ProductItemDB item, DatabaseCallback callback){
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
    private void insertMeal(MealItem item, DatabaseCallback callback){
        Thread thread = new Thread(() -> {
                    List<String> list = mdb.mealtDao().getMealName();
                    if(!list.contains(item.mealName))
                    mdb.mealtDao().insert(item);
                    callback.onComplete();
            });
        thread.start();
    }
}
