package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Post;
import com.example.bunnyfung.a356f.Object.WishList;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductPage extends AppCompatActivity {
    private Account acc, ownerAcc;
    private Post post;
    private String showType;
    private ImageView ivPhoto1, ivWishList;
    private TextView tvTitle, tvPrice, tvName, tvBrand, tvType, tvSize, tvDec, owner1, tvOwnerName, tvOwnerCred;
    private LinearLayout owner2;
    private Button btnSubmit;
    private ImageButton ibShare;
    private final String doller = "$";
    private final String sizeUnit = "US ";

    private boolean inWishList;
    private GoogleApiClient client;

    //constructor
    public ProductPage(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        ivPhoto1 = (ImageView) findViewById(R.id.ivPhoto1);
        ivWishList = (ImageView) findViewById(R.id.ivWishList);


        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPrice = (TextView) findViewById(R.id.tvOfferedPrice);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        tvType = (TextView) findViewById(R.id.tvType);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvDec = (TextView) findViewById(R.id.tvDec);
        owner1 = (TextView) findViewById(R.id.owner1);
        tvOwnerName = (TextView) findViewById(R.id.tvOwnerName);
        tvOwnerCred = (TextView) findViewById(R.id.tvOwnerCred);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        ibShare = (ImageButton) findViewById(R.id.ibShare);


        owner2 = (LinearLayout) findViewById(R.id.owner2);

        try {
            Intent intent = getIntent();
//            JSONObject jsonObject = new JSONObject(intent.getStringExtra("post"));
//            post = new Post(jsonObject);

            String postId = intent.getStringExtra("postId");
            Connection conn = new Connection();
            JSONObject jsonObject = conn.getOneProduct(postId);
            JSONArray jsonArray = jsonObject.getJSONArray("products");

            post = new Post(jsonArray.getJSONObject(0));

            showType = intent.getStringExtra("showType");
            System.out.println(showType);


            JSONObject accJsonObject = new JSONObject(intent.getStringExtra("acc"));
            acc = new Account(accJsonObject);

            JSONObject jsonObjectAcc = conn.getOneAccount(post.getOwner());
            JSONArray jsonArrayAcc = jsonObjectAcc.getJSONArray("account");

            ownerAcc = new Account(jsonArrayAcc.getJSONObject(0));

            //WishList
            String uid = new JSONObject(intent.getStringExtra("acc")).getString("_id");
            JSONArray wishListArray = conn.listWishList().getJSONArray("wishLists");

            for(int i = 0; i < wishListArray.length(); i++)
            {
                JSONObject obj = wishListArray.getJSONObject(i);
                if(obj.getString("productID").equals(postId) && obj.getString("uid").equals(uid)){
                    inWishList = true;
                    break;
                }
            }

            if(inWishList)
            {
                ivWishList.setImageResource(android.R.drawable.btn_star_big_on);
            }

            //Testing Log
            System.out.println("ProductPage" + post.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }



        ivPhoto1.setImageBitmap(post.getPhoto());
        final String price = doller + post.getPrice();
        tvPrice.setText(price);
        final String name = post.getName();
        tvName.setText(name);
        final String brand = post.getBrand();
        tvBrand.setText(brand);
        String type = post.getType();
        tvType.setText(type);
        final String size = sizeUnit + post.getSize();
        tvSize.setText(size);
        final String dec = post.getDescription();
        tvDec.setText(dec);
        final String title = post.getBrand()+" "+post.getName()+" US"+post.getSize();
        tvTitle.setText(title);


        tvOwnerName.setText(ownerAcc.getUserid()+"");
        tvOwnerCred.setText(ownerAcc.getGrade()+"/10");


        if (showType.equals("edit")) {
            owner1.setVisibility(View.GONE);
            owner2.setVisibility(LinearLayout.GONE);
            btnSubmit.setText("Edit");
        } else if (showType.equals("show")) {
            owner1.setVisibility(View.VISIBLE);
            owner2.setVisibility(View.VISIBLE);
            btnSubmit.setText("Make Offer");
        }

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Message Shared From android app, Title:"
                        +title+" Name:"+name+" Brand:"+brand+" Size:"+size+" Price:"+price+" Description:"+dec);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1;
                if (showType.equals("edit")) {
                    intent1 = new Intent(getApplication(), ProductEditPage.class);
                    try {
                        //intent1.putExtra("post", post.passToJsonObjectStr());
                        intent1.putExtra("postId", post.getProductID());
                        intent1.putExtra("acc", acc.passToJsonObjectStr());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivityForResult(intent1,1);

                }else if (showType.equals("show")){//go to make offer page
                    intent1 = new Intent(getApplication(), MakeOfferPage.class);
                    try {
                        //intent1.putExtra("post", post.passToJsonObjectStr());
                        intent1.putExtra("postId", post.getProductID());
                        intent1.putExtra("acc", acc.passToJsonObjectStr());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent1);
                }
            }
        });

        ivWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    WishList wishList = new WishList(acc.getId(), post.getProductID());
                    Connection conn = new Connection();

                    JSONObject wishListObj = conn.getWishList(wishList);
                    JSONArray wishListsArray = wishListObj.getJSONArray("wishList");

                    JSONObject targetWishListObj = null;
                    WishList targetWishList = null;
                    for(int i = 0; i < wishListsArray.length(); i++)
                    {
                        JSONObject tempWishListObj = wishListsArray.getJSONObject(i);
                        if(tempWishListObj.getString("uid").equals(acc.getId()) && tempWishListObj.getString("productID").equals(post.getProductID())) {
                            targetWishListObj = tempWishListObj;
                            break;
                        }
                    }
                    if(targetWishListObj != null)
                    {
                        targetWishList = new WishList(targetWishListObj);
                    }


                    if(inWishList){
                        //disable  !work
                        JSONObject resultObject = conn.deleteWishList(targetWishList);
                        if (resultObject.getString("status").equals("del success")) {
                            finish();
                            Toast.makeText(ProductPage.this, "Delete your wish list", Toast.LENGTH_LONG).show();
                        }
                        ivWishList.setImageResource(android.R.drawable.btn_star_big_off);
                        inWishList = false;


                    }else{
                        //enable
                        JSONObject resultObject = conn.addWishList(wishList);
                        if (resultObject.getString("status").equals("add success")) {
                            Toast.makeText(ProductPage.this, "Added to the wish list", Toast.LENGTH_LONG).show();
                        }
                        ivWishList.setImageResource(android.R.drawable.btn_star_big_on);
                        inWishList = true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ProductDetailPage Page")
                // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String str=data.getStringExtra("post");
                System.out.println("ActivityResult post:"+str);
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                post = new Post(jsonObject);
            }
            ivPhoto1.setImageBitmap(post.getPhoto());
            tvPrice.setText(doller + post.getPrice());
            tvName.setText(post.getName());
            tvBrand.setText(post.getBrand());
            tvType.setText(post.getType());
            tvSize.setText(sizeUnit + post.getSize());
            tvDec.setText(post.getDescription());
            tvTitle.setText(post.getBrand()+" "+post.getName()+" US"+post.getSize());

        }
    }



}
