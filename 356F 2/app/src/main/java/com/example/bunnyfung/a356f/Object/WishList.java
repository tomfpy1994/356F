package com.example.bunnyfung.a356f.Object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.bunnyfung.a356f.Connection.Connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.Object.Post;




/**
 * Created by BunnyFung on 10/11/2016.
 */

public class WishList {
    private String uid;
    private String productID;

    //for list wishlist
    private String ownerId;
    private String description;
    private int price;
    private Bitmap photo;


    public WishList(JSONObject jsonObj){
        try {
            this.uid = jsonObj.getString("uid");
            this.productID = jsonObj.getString("productID");
            this.ownerId = jsonObj.getString("owner");
            this.description = jsonObj.getString("description");
            this.price = jsonObj.getInt("price");
            this.photo = base64ToBitmap(jsonObj.getString("photo1data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public WishList(String uid, String productID){
        this.uid = uid;
        this.productID = productID;

    }

    public void setId(String _id){this.uid = _id;}
    public void setProductID(String productID){this.productID = productID;}

    public String getUid(){return uid;}
    public String getProductId(){return productID;}

    public void setOwnerId(String ownerID){this.ownerId = ownerID;}
    public String getOwnerId(){return ownerId;}

    public void setDescription(String description){this.description = description;}
    public String getDescription() {return description;}

    public void setPrice(int price){this.price = price;}
    public int getPrice(){return price;}

    public void setPhoto(Bitmap photo){this.photo = photo;}
    public Bitmap getPhoto(){return photo;}



    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        if (uid != null) {
            jsonObj.put("uid", getUid());
        }
        jsonObj.put("productID", getProductId());


        return jsonObj.toString();
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
