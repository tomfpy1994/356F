package com.example.bunnyfung.a356f;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.Object.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OfferDetailPage extends AppCompatActivity {
    private Offer offer;
    private TextView tvName, tvBrand, tvSize, tvDesc, tvBuyerID, tvOfferedPrice, tvDateTime, tvPlace;
    private Post post;
    private JSONObject resultObject;
    private JSONArray jsonArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail_page);

        tvBuyerID = (TextView) findViewById(R.id.tvBuyerID);
        tvOfferedPrice = (TextView) findViewById(R.id.tvOfferedPrice);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvDesc = (TextView) findViewById(R.id.tvDesc);

        try {
            JSONObject jsonOffer = new JSONObject(getIntent().getStringExtra("offer"));
            offer = new Offer(jsonOffer);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvBuyerID.setText(offer.getBuyerName());
        tvOfferedPrice.setText(offer.getPrice()+"");
        tvDateTime.setText(offer.getDateTime());
        tvPlace.setText(offer.getPlace());

        //Testing Log
        System.out.print("Offer productID:"+ offer.getPostId());

        Connection conn = new Connection();
        resultObject = conn.getOneProduct(offer.getPostId());

        //Testing Log
        System.out.println("resultObject: "+resultObject.toString());

        try {
            jsonArray = resultObject.getJSONArray("products");
            post = new Post(jsonArray.getJSONObject(0));

            tvName.setText(""+post.getName());
            tvBrand.setText(""+post.getBrand());
            tvSize.setText(""+post.getSize());
            tvDesc.setText(""+post.getDescription());

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
