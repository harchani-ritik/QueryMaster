package com.example.android.querymaster;

public class User {
    private String uid;
    private String name;
    private String email;
    private boolean userTypeAdmin;

    public User(){
    }

    public User(String name,String email,boolean userTypeAdmin,String uid)
    {
        this.name=name;
        this.email=email;
        this.userTypeAdmin=userTypeAdmin;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isUserTypeAdmin() {
        return userTypeAdmin;
    }
}
