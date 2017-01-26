package com.example.bunnyfung.a356f.Object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private String name;
    private String phone;
    private String sex;
    private Bitmap icon;
    private String _id;

    public Account(JSONObject jsonObj){
        try {
            this.email = jsonObj.getString("email");
            this.userid = jsonObj.getString("userid");
            this.pw = jsonObj.getString("pw");
            this.name = jsonObj.getString("name");
            this.phone = jsonObj.getString("phone");
            this.sex = jsonObj.getString("sex");
            this.icon = base64ToBitmap(jsonObj.getString("irondata"));
            this._id = jsonObj.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Account(String userid, String email, String password, Bitmap icon){
        this.email = email;
        this.userid = userid;
        this.pw = password;
        this.name = "";
        this.phone = "";
        this.sex = "";
        this.icon = icon;

    }

    public void setEmail (String email){this.email = email;}
    public void setUserid (String userid){this.userid = userid;}
    public void setPassword (String password){this.pw = password;}
    public void setName (String name){this.name = name;}
    public void setPhone (String number){this.phone = number;}
    public void setSex (String sex){
        if (sex.equals("M") ||sex.equals("F")){
            this.sex = sex;
        }
    }
    public void setIcon(Bitmap icon){this.icon = icon;}

    public String getEmail(){return email;}
    public String getUserid(){return userid;}
    public String getPassword(){return pw;}
    public String getName(){return name;}
    public String getPhone(){return phone;}
    public String getSex(){return sex;}
    public Bitmap getIcon(){return icon;}
    public String get_id(){return _id;}

    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        if (_id!=null){
            jsonObj.put("_id",get_id());
        }
        jsonObj.put("email",getEmail());
        jsonObj.put("userid",getUserid());
        jsonObj.put("pw",getPassword());
        jsonObj.put("name",getName());
        jsonObj.put("phone",getPhone());
        jsonObj.put("sex",getSex());
        jsonObj.put("irondata",bitmapToBase64(icon));
        return jsonObj.toString();
    }

    public String toString(){
        return  "Acc:"+getUserid()+","+getEmail()+","+getPassword()+","+
            getName()+","+getPhone()+","+getSex()+","+get_id();
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
