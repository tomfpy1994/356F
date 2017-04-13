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
    private EditText edtBuyerID, edtPrice, edtDateTime, edtPlace;
    private TextView tvTitle, tvName, tvBrand, tvType, tvSize, tvOfferedPrice;
    private Button
    private Post post;
    private JSONObject resultObject;
    private JSONArray jsonArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail_page);

        edtBuyerID = (EditText) findViewById(R.id.edtBuyerID);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtDateTime = (EditText) findViewById(R.id.edtDateTime);
        edtPlace = (EditText) findViewById(R.id.edtPlace);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        tvType = (TextView) findViewById(R.id.tvType);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvOfferedPrice = (TextView) findViewById(R.id.tvOfferedPrice);

        try {
            JSONObject jsonOffer = new JSONObject(getIntent().getStringExtra("offer"));
            offer = new Offer(jsonOffer);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        edtBuyerID.setText(offer.getBuyerName());
        edtBuyerID.setEnabled(false);
        edtPrice.setText(offer.getPrice()+"");
        edtPrice.setEnabled(false);
        edtDateTime.setText(offer.getDateTime());
        edtDateTime.setEnabled(false);
        edtPlace.setText(offer.getPlace());
        edtPlace.setEnabled(false);

        //Testing Log
        System.out.print("Offer productID:"+ offer.getPostId());

        Connection conn = new Connection();
        resultObject = conn.getOneProduct(offer.getPostId());

        //Testing Log
        System.out.println("resultObject: "+resultObject.toString());

        try {
            jsonArray = resultObject.getJSONArray("products");
            post = new Post(jsonArray.getJSONObject(0));

            tvOfferedPrice.setText(offer.getPrice()+"");
            tvName.setText(""+post.getName());
            tvBrand.setText(""+post.getBrand());
            tvType.setText(""+post.getType());
            tvSize.setText(""+post.getSize());
            tvTitle.setText(post.getBrand()+" "+post.getName()+" US"+post.getSize());

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
