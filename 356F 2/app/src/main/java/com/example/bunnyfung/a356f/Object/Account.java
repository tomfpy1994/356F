package com.example.bunnyfung.a356f.Object;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BunnyFung on 10/11/2016.
 */

public class Account {
    private String email;
    private String userid;
    private String password;

    public Account(JSONObject jsonObj){
        try {
            this.email = jsonObj.getString("email");
            this.userid = jsonObj.getString("userid");
            this.password = jsonObj.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

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
    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("email",getEmail());
        jsonObj.put("userid",getUserid());
        jsonObj.put("password",getPassword());
        return jsonObj.toString();
    }
}
