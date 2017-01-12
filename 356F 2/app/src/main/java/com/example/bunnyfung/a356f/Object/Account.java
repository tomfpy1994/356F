package com.example.bunnyfung.a356f.Object;

/**
 * Created by BunnyFung on 10/11/2016.
 */

public class Account {
    private String email;
    private String userid;
    private String password;

    public Account(String userid, String email, String password){
        this.email = email;
        this.userid = userid;
        this.password = password;
    }

    public void setEmail (String email){this.email = email;}
    public void setUserid (String userid){this.userid = userid;}
    public void setPassword (String password){this.password = password;}

    public String getEmail(){return email;}
    public String getUserid(){return userid;}
    public String getPassword(){return password;}
}
