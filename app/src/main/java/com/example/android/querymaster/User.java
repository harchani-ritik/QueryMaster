package com.example.android.querymaster;

public class User {
    private String name;
    private String email;
    private boolean userTypeAdmin;

    public User(){
    }

    public User(String name,String email,boolean userTypeAdmin)
    {
        this.name=name;
        this.email=email;
        this.userTypeAdmin=userTypeAdmin;
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
