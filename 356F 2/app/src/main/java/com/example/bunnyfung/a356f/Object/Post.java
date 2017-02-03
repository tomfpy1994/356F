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

public class Post {
    private String productID, owner, name, brand, type, description, state;
    private int price;
    private double size;
    private Bitmap photo;

    //constructor
    public Post(JSONObject jsonObject){
        try{
            this.productID = jsonObject.getString("_id");
            this.name = jsonObject.getString("pname");
            this.type = jsonObject.getString("ptype");
            this.brand = jsonObject.getString("brand");
            this.size = jsonObject.getDouble("size");
            this.price = jsonObject.getInt("price");
            this.owner = jsonObject.getString("owner");
            this.state = jsonObject.getString("state");
            this.photo = base64ToBitmap(jsonObject.getString("photo1data"));
            this.description = jsonObject.getString("description");
        }catch (JSONException j){
            j.printStackTrace();
        }
    }
    //constructor 2
    public Post(String name,String brand,String type,double size,int price,String description,String owner, Bitmap photo){
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.size = size;
        this.price = price;
        this.description = description;
        this.owner = owner;
        this.photo = photo;
        this.state = "";
    }
    //get method
    public String getName(){ return name; }
    public String getBrand(){ return brand;}
    public String getType(){ return type; }
    public double getSize(){ return size; }
    public int getPrice(){ return price; }
    public String getDescription(){ return description; }
    public String getOwner(){ return owner; }
    public Bitmap getPhoto(){ return photo;}
    public String getState(){ return state;}
    public String getProductID(){ return productID; }


    //set method
    public void setPhoto(Bitmap photo){this.photo = photo;}
    public void setName(String name){this.name = name; }
    public void setBrand(String brand){ this.brand = brand;}
    public void setType(String type){ this.type = type;}
    public void setSize(double size){ this.size = size;}
    public void setPrice(int price){ this.price = price;}
    public void setDescription(String description){ this.description = description;}
    public void setOwner(String owner){ this.owner = owner;}
    public void setState(String state){ this.state = state;}

    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("_id",getProductID());
        jsonObj.put("pname",getName());
        jsonObj.put("ptype",getType());
        jsonObj.put("brand",getBrand());
        jsonObj.put("size",getSize());
        jsonObj.put("price",getPrice());
        jsonObj.put("photo1data",bitmapToBase64(photo));
        jsonObj.put("owner", getOwner());
        jsonObj.put("state", getState());
        jsonObj.put("description",getDescription());

        return jsonObj.toString();
    }

    // to string
    public String toString(){
        return "product detail and information: "+getProductID()+","+getName()+","+getType()+","+getBrand()+
                ","+getSize()+","+getPrice()+","+getOwner()+","+getDescription();
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //TODO:png or jpeg
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String iconStr){
        InputStream stream = new ByteArrayInputStream(Base64.decode(iconStr.getBytes(), Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        return bitmap;
    }
}
