package com.example.bunnyfung.a356f.Object;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BunnyFung on 1/3/2017.
 */

public class Offer {
    private String _id, postID, ownerID, buyerID, dateTime, place, ownerCode, buyerCode;
    private double price;
    private int stat;
    private int[] statTpye =  new int[] {0,1,2,3};

    //for new offer
    public Offer(String postID, String ownerID, String buyerID, String dateTime, String place, double price){
        this.postID = postID;
        this.ownerID = ownerID;
        this.buyerID = buyerID;
        this.dateTime = dateTime;
        this.place = place;
        ownerCode = "";
        buyerCode = "";
        stat = statTpye[0];
        this.price = price;
    }

    //for json object
    public Offer(JSONObject object){
        try {
            this._id = object.getString("_id");
            this.postID = object.getString("PostID");
            this.ownerID = object.getString("OwnerID");
            this.buyerID = object.getString("BuyerID");
            this.dateTime = object.getString("DateTime");
            this.place = object.getString("place");
            this.ownerCode = object.getString("OwnerCode");
            this.buyerCode = object.getString("BuyerCode");
            this.stat = object.getInt("stat");
            this.price = object.getDouble("price");
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
            jsonObj.put("DateTime",getDateTime());
            jsonObj.put("place",getPlace());
            jsonObj.put("OwnerCode",getOwnerCode());
            jsonObj.put("BuyerCode",getBuyerCode());
            jsonObj.put("stat", getStat());
            jsonObj.put("price", getPrice());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj.toString();
    }

    public String get_id(){return _id;}
    public String getPostId(){return postID;}
    public String getOwnerID(){return ownerID;}
    public String getBuyerID(){return buyerID;}
    public String getDateTime(){return dateTime;}
    public String getPlace(){return place;}
    public String getOwnerCode(){return ownerCode;}
    public String getBuyerCode(){return buyerCode;}
    public int getStat(){return stat;}
    public double getPrice(){return price;}

    public void setDateTime(String dateTime){this.dateTime = dateTime;}
    public void setPlace(String place){this.place = place;}
    public void setStat(){}
    public void setOwnerCode(String ownerCode){this.ownerCode = ownerCode;}
    public void setBuyerCode(String buyerCode){this.buyerCode = buyerCode;}

    public String toString(){return get_id()+","+getPostId()+","+getOwnerID()+","+getBuyerID()+","
            +getDateTime()+","+getPlace()+","+getStat()+","+getPrice();}

}
