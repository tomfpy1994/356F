package com.example.bunnyfung.a356f.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;


import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.WishList;
import com.example.bunnyfung.a356f.Object.Post;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.R;

import java.util.ArrayList;
/**
 * Created by kelvin on 19/4/2017.
 */

public class WishListAdapter extends ArrayAdapter<WishList>
{
    //    private ArrayList<WishList> alWishLists;
//    private int resource;
//    private LayoutInflater inflater;
//
//    public WishListAdapter(Context context,  ArrayList<WishList> alWishLists) {
//        super(context, R.layout.activity_wish_list_page, alWishLists);
//        this.alWishLists = alWishLists;
//        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null){
//            convertView = inflater.inflate(resource,null);
//        }
//
//        ImageView ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
//        TextView tvUser = (TextView) convertView.findViewById(R.id.textUser);
//        TextView tvInformation = (TextView) convertView.findViewById(R.id.textInformation);
//        TextView tvPrice = (TextView) convertView.findViewById(R.id.textPrice);
//
////        WishList wishList = alWishLists.get(position);
//       // Connection conn = new Connection();
//       // JSONObject postObj = conn.getOneProduct(wishList.getProductId());
//       // Post post = new Post(postObj);
//
//       // JSONObject accObj = conn.getOneAccount(post.getOwner());
//       // Account acc = new Account(accObj);
//
//
//
//
////
////        //TODO:get Product Img
////        ivImg.setImageBitmap(post.getPhoto());
////        //TODO: get product title
////        tvUser.setText(acc.getUserid());
////        tvInformation.setText(post.getDescription());
////        tvPrice.setText(post.getPrice()+"");
//
//        return convertView;
//    }
    private ArrayList<WishList> alWishList;
    private LayoutInflater inflater;
    private Context context;



    public WishListAdapter(Context context, ArrayList<WishList> alWishList) {
        super(context, R.layout.wish_list_item, alWishList);
        this.alWishList = alWishList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater layoutInflaterter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //convertView = layoutInflaterter.inflate(R.layout.offer_list_item, parent, false);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.wish_list_item,null);
        }


        ImageView ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
        TextView tvUser = (TextView) convertView.findViewById(R.id.textUser);
        TextView tvInformation = (TextView) convertView.findViewById(R.id.textInformation);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.textPrice);
        TextView tvProductName = (TextView) convertView.findViewById(R.id.tvProductName);

        Log.i("WKW", "Position: " +position);
        WishList wishList = alWishList.get(position);
        Connection conn = new Connection();

        JSONObject product = null;
        JSONObject postObj = conn.getOneProduct(wishList.getProductId());


        try {
            JSONArray products = postObj.getJSONArray("products");
            product = products.getJSONObject(0);

        }
        catch(JSONException e)
        {
            Log.e("WKW2", "Error: " + e.toString());
        }
        if(product != null)
        {
            Post post = new Post(product);

            JSONObject accountObj = null;
            JSONObject accObj = conn.getOneAccount(post.getOwner());

            try{
                JSONArray accounts = accObj.getJSONArray("account");
                accountObj = accounts.getJSONObject(0);
            }
            catch(JSONException e)
            {
                Log.e(null, "Error on account: " + e.toString());
            }

            tvProductName.setText(post.getName());
            ivImg.setImageBitmap(post.getPhoto());
            tvInformation.setText(post.getDescription());
            tvPrice.setText(post.getPrice()+"");

            if(accountObj != null) {
                Account account = new Account(accountObj);
                Log.i(null, "account: " +account.toString());
                tvUser.setText(account.getUserid());
                Log.i(null, "accountgetUserId: " +account.getUserid());
            }
        }

//        try{


        //Post post = new Post(postObj.getJSONArray("products").getJSONObject(0));

        //JSONObject accObj = conn.getOneAccount(post.getOwner());
        //Account acc = new Account(accObj.getJSONArray("account").getJSONObject(0));

        Log.i("WKW2", "postObj: " +postObj.toString());
        Log.i("WKW2", "wishListgetProductID: " +wishList.getProductId());

        // tvUser.setText(acc.getUserid());
//            tvProductName.setText(post.getName());
//            ivImg.setImageBitmap(post.getPhoto());
//            tvInformation.setText(post.getDescription());
//            tvPrice.setText(post.getPrice()+"");
//        }catch (JSONException e){
//
//        }



        //Log.i("WKW", "UserName: " + wishList.getOwnerName());


//        //TODO:get Product Img
//        ivImg.setImageBitmap(offer.getPhoto());
//        //TODO: get product title
//        if(wishList.getOwnerName() != null)
//          tvUser.setText(wishList.getOwnerName());
//        tvInformation.setText(wishList.getBuyerName());
//        tvOfferedPrice.setText(offer.getPrice()+"");




        return convertView;
    }
}
