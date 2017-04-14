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
 * Created by BunnyFung on 1/3/2017.
 */

public class Offer {
    private String _id, postID, ownerID, buyerID, buyerName, dateTime, place, ownerCode, buyerCode, title;
    private int price;
    private int stat;
    private int[] statTpye =  new int[] {0,1,2};
    // 0 = waiting, 1 = processing, 2 = deal
    private Bitmap photo;

    //for new offer
    public Offer(String postID, String ownerID, String buyerID, String buyerName, String dateTime, String place, int price, String title, Bitmap photo){
        this.postID = postID;
        this.ownerID = ownerID;
        this.buyerID = buyerID;
        this.buyerName = buyerName;
        this.dateTime = dateTime;
        this.place = place;
        ownerCode = "";
        buyerCode = "";
        stat = statTpye[0];
        this.price = price;
        this.title = title;
        this.photo = photo;
    }

    //for json object
    public Offer(JSONObject object){
        try {
            this._id = object.getString("_id");
            this.postID = object.getString("PostID");
            this.ownerID = object.getString("OwnerID");
            this.buyerID = object.getString("BuyerID");
            this.buyerName = object.getString("BuyerName");
            this.dateTime = object.getString("DateTime");
            this.place = object.getString("place");
            this.ownerCode = object.getString("OwnerCode");
            this.buyerCode = object.getString("BuyerCode");
            this.stat = object.getInt("stat");
            this.price = object.getInt("price");
            this.title = object.getString("title");
            this.photo = base64ToBitmap(object.getString("photo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String passToJsonObjectStr() {
        JSONObject jsonObj = new JSONObject();
        try {
            if (_id!=null){
                jsonObj.put("_id",get_id());
            }
            jsonObj.put("PostID",getPostId());
            jsonObj.put("OwnerID",getOwnerID());
            jsonObj.put("BuyerID",getBuyerID());
            jsonObj.put("BuyerName",getBuyerName());
            jsonObj.put("DateTime",getDateTime());
            jsonObj.put("place",getPlace());
            jsonObj.put("OwnerCode",getOwnerCode());
            jsonObj.put("BuyerCode",getBuyerCode());
            jsonObj.put("stat", getStat());
            jsonObj.put("price", getPrice());
            jsonObj.put("title", getTitle());
            jsonObj.put("photo",bitmapToBase64(photo));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj.toString();
    }

    public String get_id(){return _id;}
    public String getPostId(){return postID;}
    public String getOwnerID(){return ownerID;}
    public String getBuyerID(){return buyerID;}
    public String getBuyerName(){return buyerName;}
    public String getDateTime(){return dateTime;}
    public String getPlace(){return place;}
    public String getOwnerCode(){return ownerCode;}
    public String getBuyerCode(){return buyerCode;}
    public int getStat(){return stat;}
    public int getPrice(){return price;}
    public String getTitle(){return title;}
    public Bitmap getPhoto(){return photo;}

    public void setDateTime(String dateTime){this.dateTime = dateTime;}
    public void setPlace(String place){this.place = place;}
    public void setStatProccessing(){this.stat = statTpye[1];}
    public void setStatDeal(){this.stat = statTpye[2];}
    public void setOwnerCode(String ownerCode){this.ownerCode = ownerCode;}
    public void setBuyerCode(String buyerCode){this.buyerCode = buyerCode;}

    public String toString(){return get_id()+","+getPostId()+","+getOwnerID()+","+getBuyerID()+","
            +getDateTime()+","+getPlace()+","+getStat()+","+getPrice()+","+getBuyerCode();}

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
