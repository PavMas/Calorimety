package com.example.calorimety.domain;


import androidx.annotation.NonNull;

public class User {
    private int id;

    private String name;
    private String password;



    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    @NonNull
    @Override
    public String toString() {
        return "id: "+id+"\n"
                +"name: "+name;
    }
}
