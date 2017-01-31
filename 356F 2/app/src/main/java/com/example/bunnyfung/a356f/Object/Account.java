package com.example.bunnyfung.a356f.Object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by BunnyFung on 10/11/2016.
 */

public class Account {
    private String email;
    private String userid;
    private String pw;
    private String phone;
    private Bitmap icon;
    private String _id;
    private int balance;
    private String scode;

    public Account(JSONObject jsonObj){
        try {
            this.email = jsonObj.getString("email");
            this.userid = jsonObj.getString("userid");
            this.pw = jsonObj.getString("pw");
            this.phone = jsonObj.getString("phone");
            this.icon = base64ToBitmap(jsonObj.getString("irondata"));
            this._id = jsonObj.getString("_id");
            this.balance = jsonObj.getInt("balance");
            this.scode = jsonObj.getString("scode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Account(String userid, String email, String password, Bitmap icon){
        this.email = email;
        this.userid = userid;
        this.pw = password;
        this.phone = "";
        this.icon = icon;
        this.balance = 0;
        this.scode = "";

    }

    public void setEmail (String email){this.email = email;}
    public void setUserid (String userid){this.userid = userid;}
    public void setPassword (String password){this.pw = password;}
    public void setPhone (String number){this.phone = number;}
    public void setIcon(Bitmap icon){this.icon = icon;}
    public void setBalance(int balance){this.balance = balance;}
    public void setsCode(String sCode){this.scode = sCode;}



    public String getEmail(){return email;}
    public String getUserid(){return userid;}
    public String getPassword(){return pw;}
    public String getPhone(){return phone;}
    public Bitmap getIcon(){return icon;}
    public String getId(){return _id;}
    public int getBalance(){return balance;}
    public String getsCode(){return scode;}

    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        if (_id!=null){
            jsonObj.put("_id",getId());
        }
        jsonObj.put("email",getEmail());
        jsonObj.put("userid",getUserid());
        jsonObj.put("pw",getPassword());
        jsonObj.put("phone",getPhone());
        jsonObj.put("irondata",bitmapToBase64(icon));
        jsonObj.put("balance",getBalance());
        jsonObj.put("scode", getsCode());

        return jsonObj.toString();
    }

    public String toString(){
        return  "Acc:"+getUserid()+","+getEmail()+","+getPassword()+","+
            ","+getPhone()+","+","+getId();

    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String iconStr){
        InputStream stream = new ByteArrayInputStream(Base64.decode(iconStr.getBytes(), Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        return bitmap;
    }
}
