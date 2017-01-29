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
 * Created by Oliver on 29/1/2017.
 */

public class AddPost {
    private String name, brand, type, description;
    private int size, price;
    private String userID;
    private Bitmap photo;
    //constructor
    public AddPost(JSONObject jsonObject){
        try{
            this.name = jsonObject.getString("pname");
            this.type = jsonObject.getString("ptype");
            this.brand = jsonObject.getString("brand");
            this.size = jsonObject.getInt("size");
            this.price = jsonObject.getInt("price");
            this.photo = base64ToBitmap(jsonObject.getString("photo1data"));
            this.userID = jsonObject.getString("user");
        }catch (JSONException j){
            j.printStackTrace();
        }
    }
    //constructor 2
    public AddPost(String name,String brand,String type,int size,int price,String description,String userID){
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.size = size;
        this.price = price;
        this.description = description;
        this.userID = userID;
    }
    //get method
    public String getName(){ return name; }
    public String getBrand(){ return brand;}
    public String getType(){ return type; }
    public int getSize(){ return size; }
    public int getPrice(){ return price; }
    public String getDescription(){ return description; }
    public String getUserID(){ return userID; }

    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        /*
        if (userID!=null){
            jsonObj.put("_id",get_id());
        }*/
        jsonObj.put("pname",getName());
        jsonObj.put("ptype",getType());
        jsonObj.put("brand",getBrand());
        jsonObj.put("size",getSize());
        jsonObj.put("price",getPrice());
        jsonObj.put("photo1data",bitmapToBase64(photo));
        jsonObj.put("user", getUserID());

        return jsonObj.toString();
    }
    // to string
    public String toString(){
        return "product detail and information: "+getName()+" "+getType()+" "+getBrand()+" "+getSize()+" "+getPrice()+" "+getUserID();
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
