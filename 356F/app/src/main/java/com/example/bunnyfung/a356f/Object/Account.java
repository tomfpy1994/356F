package com.example.bunnyfung.a356f.Object;

/**
 * Created by BunnyFung on 10/11/2016.
 */

public class Account {
    private String email;
    private String userName;
    private String password;

    public Account(String email, String userName, String password){
        this.email = email;
        this.userName = userName;
        this.password = password;
    }
    public Account(String email, String password){
        this.email = email;
        this.password = password;
        String[] parts = email.split("@");
        this.userName = parts[0];
    }

    public void setEmail (String email){this.email = email;}
    public void setUserName (String userName){this.userName = userName;}
    public void setPassword (String password){this.password = password;}

    public String getEmail(){return email;}
    public String getUserName(){return userName;}
    public String getPassword(){return password;}
}
