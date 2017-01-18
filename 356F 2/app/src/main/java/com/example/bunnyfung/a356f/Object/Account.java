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
    private String name;
    private String phoneNo;
    private String sex;

    public Account(JSONObject jsonObj){
        try {
            this.email = jsonObj.getString("email");
            this.userid = jsonObj.getString("userid");
            this.password = jsonObj.getString("password");
            this.name = jsonObj.getString("name");
            this.phoneNo = jsonObj.getString("phoneNo");
            this.sex = jsonObj.getString("sex");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Account(String userid, String email, String password){
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.name = "";
        this.phoneNo = "";
        this.sex = "";
    }

    public void setEmail (String email){this.email = email;}
    public void setUserid (String userid){this.userid = userid;}
    public void setPassword (String password){this.password = password;}
    public void setName (String name){this.name = name;}
    public void setPhoneNo (String number){this.phoneNo = number;}
    public void setSex (String sex){
        if (sex.equals("M") ||sex.equals("W")){
            this.sex = sex;
        }
    }


    public String getEmail(){return email;}
    public String getUserid(){return userid;}
    public String getPassword(){return password;}
    public String getName(){return name;}
    public String getPhoneNo(){return phoneNo;}
    public String getSex(){return sex;}

    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("email",getEmail());
        jsonObj.put("userid",getUserid());
        jsonObj.put("password",getPassword());
        jsonObj.put("name",getName());
        jsonObj.put("phoneNo",getPhoneNo());
        jsonObj.put("sex",getSex());
        return jsonObj.toString();
    }
    public String toString(){return  "Acc:"+getUserid()+","+getEmail()+","+getPassword()+","+getName()+","+getPhoneNo()+","+getSex();}
}
