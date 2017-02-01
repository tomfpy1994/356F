package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Post;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailPage extends AppCompatActivity{
    private Post post;
    private String showType;
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3, ivSellerIcon;
    private TextView tvTitle, tvPrice, tvName, tvBrand, tvType, tvSize, tvDec, owner1, tvSellerName, tvPhone, tvCred;
    private LinearLayout owner2;
    private Button btnSubmit;
    private final String doller = "$";
    private final String sizeUnit = "US ";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_page);

        try {
            Intent intent = getIntent();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("post"));
            showType = intent.getStringExtra("showType");

            post = new Post(jsonObject);

            //Testing Log
            System.out.println("ProductEdtailPage_" + post.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ivPhoto1 = (ImageView) findViewById(R.id.ivPhoto1);
        ivPhoto2 = (ImageView) findViewById(R.id.ivPhoto2);
        ivPhoto3 = (ImageView) findViewById(R.id.ivPhoto3);
        ivSellerIcon = (ImageView) findViewById(R.id.ivSellerIcon);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        tvType = (TextView) findViewById(R.id.tvType);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvDec = (TextView) findViewById(R.id.tvDec);
        owner1 = (TextView) findViewById(R.id.owner1);
        tvSellerName = (TextView) findViewById(R.id.tvSellerName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvCred = (TextView) findViewById(R.id.tvCred);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        owner2 = (LinearLayout) findViewById(R.id.owner2);

        ivPhoto1.setImageBitmap(post.getPhoto());
        tvPrice.setText(doller + post.getPrice());
        tvName.setText(post.getName());
        tvBrand.setText(post.getBrand());
        tvType.setText(post.getType());
        tvSize.setText(sizeUnit + post.getSize());
        tvDec.setText(post.getDescription());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailPage.this,ProfileEditPage.class);
                intent.putExtra("post",post.passToJsonObjectStr());
                startActivity(intent);
            }
        });

        if (showType.equals("edit")) {
            owner1.setVisibility(View.GONE);
            owner2.setVisibility(LinearLayout.GONE);
            btnSubmit.setText("Edit");
        } else if (showType.equals("show")) {
            owner1.setVisibility(View.VISIBLE);
            owner2.setVisibility(View.VISIBLE);
            btnSubmit.setText("Make Offer");
        }



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ProductDetailPage Page") // TODO: Define a title for the content shown.
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


}
