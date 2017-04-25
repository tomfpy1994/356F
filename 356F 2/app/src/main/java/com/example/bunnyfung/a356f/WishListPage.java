package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Button;
import android.util.Log;

import com.example.bunnyfung.a356f.Adapter.OfferHistoryAdapter;
import com.example.bunnyfung.a356f.Adapter.WishListAdapter;
import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.Object.Post;
import com.example.bunnyfung.a356f.Object.WishList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WishListPage extends AppCompatActivity {
    public Account acc;
    public Post post;
    private JSONArray jsonArray = null;
    private ListView lvWishList;
    private Button btnBack;

    private ArrayList<WishList> alWishLists = new ArrayList<WishList>();

    Connection conn = new Connection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_list);


        lvWishList = (ListView) findViewById(R.id.lvWishList);
        btnBack = (Button) findViewById(R.id.btnBack);

        String str = getIntent().getStringExtra("acc");
        try {
            JSONObject jsonObject = new JSONObject(str);
            acc = new Account(jsonObject);

            System.out.println(acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        printList();
    }

    public void printList(){
        try {
            jsonArray = conn.listWishList().getJSONArray("wishLists");

            System.out.println("ALWishList: " + jsonArray);
            Log.i(null, jsonArray.toString());

            alWishLists.clear();
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    WishList tempWishList = new WishList(jsonArray.getJSONObject(i));
                    Log.i(null, "List " + i + ": " + tempWishList.toString());
//                    //if (tempWishList.getId().equals(acc.getId())){
                    alWishLists.add(tempWishList);
                    Log.i(null, "ALList " + i + ": " + alWishLists.toString());
                }
            }
        } catch(Exception e)  {
            Log.e(null, e.toString());
        }
        //Testing Log
        System.out.println(alWishLists.size());

        Log.i("ALWishList: ", alWishLists.toString());

        WishListAdapter wishListAdapter = new WishListAdapter(getApplicationContext(), alWishLists);
        lvWishList.setAdapter(wishListAdapter);
        //lvWishList.invalidateViews();


        lvWishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    WishList clickedWishList = alWishLists.get(position);
                    String clickedWishListStr = clickedWishList.passToJsonObjectStr();

                    //Testing Log
                    Log.i("WKW2", "clickedWishListStr: " +clickedWishList.passToJsonObjectStr());


                    JSONObject postObj = conn.getOneProduct(clickedWishList.getProductId());
                    Log.i("WKW2", "clickedWishListgetProductId: " +clickedWishList.getProductId());
                    Log.i("WKW2", "postObj: " +postObj.toString());

                    JSONArray products = postObj.getJSONArray("products");
                    JSONObject product = products.getJSONObject(0);

                    Post post = new Post(product);

                    JSONObject accObj = conn.getOneAccount(clickedWishList.getUid());
                    JSONArray accounts = accObj.getJSONArray("account");
                    JSONObject account = accounts.getJSONObject(0);

                    acc = new Account(account);




                    Intent intent = new Intent(getApplication(), ProductPage.class);
                    intent.putExtra("post", product.toString());
                    intent.putExtra("acc", acc.passToJsonObjectStr());

                    if (post.getOwner().equals(acc.getId())){
                        intent.putExtra("showType","edit");
                    }else {
                        intent.putExtra("showType","show");
                    }

                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
