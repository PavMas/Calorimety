package com.example.calorimety.domain.mapper;


import com.example.calorimety.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMapper {

    public static User userFromJson(JSONObject jsonObject){
        User user = null;
        try{
            user = new User(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("password"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
