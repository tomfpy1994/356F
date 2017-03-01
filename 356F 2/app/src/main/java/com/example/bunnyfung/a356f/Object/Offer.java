package com.example.bunnyfung.a356f.Object;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BunnyFung on 1/3/2017.
 */

public class Offer {
    private String _id, ownerID, buyerID, dateTime, place, ownerCode, buyerCode;
    private int stat;
    private int[] statTpye =  new int[] {0,1,2,3};
    private String[] rsp = new String [] {""};

    //for new offer
    public Offer(String ownerID, String buyerID, String dateTime, String place){
        this.ownerID = ownerID;
        this.buyerID = buyerID;
        this.dateTime = dateTime;
        this.place = place;
        ownerCode = "";
        buyerCode = "";
        stat = statTpye[0];
    }

    //for json object
    public Offer(JSONObject object){
        try {
            this._id = object.getString("_id");
            this.ownerID = object.getString("ownerID");
            this.buyerID = object.getString("buyerID");
            this.dateTime = object.getString("dateTime");
            this.place = object.getString("place");
            this.ownerCode = object.getString("ownerCode");
            this.buyerCode = object.getString("buyerCode");
            this.stat = object.getInt("stat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String passToJsonObjectStr() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("_id",get_id());
            jsonObj.put("ownerID",getOwnerID());
            jsonObj.put("buyerID",getBuyerID());
            jsonObj.put("dateTime",getDateTime());
            jsonObj.put("place",getPlace());
            jsonObj.put("ownerCode",getOwnerCode());
            jsonObj.put("buyerCode",getBuyerCode());
            jsonObj.put("stat", getStat());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj.toString();
    }

    public String get_id(){return _id;}
    public String getOwnerID(){return ownerID;}
    public String getBuyerID(){return buyerID;}
    public String getDateTime(){return dateTime;}
    public String getPlace(){return place;}
    public String getOwnerCode(){return ownerCode;}
    public String getBuyerCode(){return buyerCode;}
    public int getStat(){return stat;}

    public void setDateTime(String dateTime){this.dateTime = dateTime;}
    public void setPlace(String place){this.place = place;}
    //TODO: set stat
    public void setStat(){}
    public void setOwnerCode(String ownerCode){this.ownerCode = ownerCode;}
    public void setBuyerCode(String buyerCode){this.buyerCode = buyerCode;}

    public String toStrng(){return get_id()+","+getOwnerID()+","+getBuyerID()+","+getDateTime()+","+getPlace();}

}
