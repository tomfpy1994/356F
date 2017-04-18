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

public class WishList {
    private String _id;
    private String productID;


    public WishList(JSONObject jsonObj){
        try {
            this._id = jsonObj.getString("_id");
            this.productID = jsonObj.getString("productID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public WishList(String _id, String productID){
        this._id = _id;
        this.productID = productID;

    }

    public void setId(String _id){this._id = _id;}
    public void setProductID(String productID){this.productID = productID;}

    public String getId(){return _id;}
    public String getProductId(){return productID;}


    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        if (_id != null) {
            jsonObj.put("uid", getId());
         }
         jsonObj.put("productID", getProductId());


        return jsonObj.toString();
    }
    }
