package com.example.bunnyfung.a356f;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.Object.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class OfferDetailPage extends AppCompatActivity {
    private Offer offer;
    private Account acc;
    private TextView tvName, tvBrand, tvSize, tvDesc, tvBuyerID, tvOfferedPrice, tvDateTime, tvPlace, tvAgreeTitle;
    private Button btnAccept, btnDecline;
    private CheckBox cbAgreement;
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
        tvAgreeTitle = (TextView) findViewById(R.id.tvAgreeTitle);

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnDecline = (Button) findViewById(R.id.btnDecline);

        cbAgreement = (CheckBox) findViewById(R.id.cbAgreement);


        try {
            JSONObject jsonOffer = new JSONObject(getIntent().getStringExtra("offer"));
            offer = new Offer(jsonOffer);

            JSONObject jsonAcc = new JSONObject(getIntent().getStringExtra("acc"));
            acc = new Account(jsonAcc);

            switch (offer.getStat()){
                case 0: if (acc.getId().equals(offer.getBuyerID())){
                            btnAccept.setText("DEAL");
                        }else
                            btnAccept.setText("ACCEPT");
                        break;

                case 1: btnAccept.setText("DEAL");
                        tvAgreeTitle.setVisibility(View.INVISIBLE);
                        cbAgreement.setVisibility(View.INVISIBLE);
                        break;

                case 2: btnDecline.setVisibility(View.INVISIBLE);
                        btnAccept.setVisibility(View.INVISIBLE);
                        tvAgreeTitle.setVisibility(View.INVISIBLE);
                        cbAgreement.setVisibility(View.INVISIBLE);
                        break;

            }
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

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                offer.setStatDeal();
                System.out.println(""+offer.toString());

                Connection conn = new Connection();
                resultObject = conn.updateOffer(offer);

                System.out.println(resultObject.toString()+"");
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbAgreement.isChecked()){
                    //Testing Log
                    System.out.println("checkbox clicked GO");
                    if (btnAccept.getText().equals("DEAL")){
                        //Testing Log
                        System.out.println("Deal");
                    }else {
                        System.out.println("Accept");
                        if (offer.getBuyerCode().equals("")){
                            //Testing Log
                            System.out.println("buyerCode is null");
                            Random r = new Random();
                            int intBuyerCode = r.nextInt(10000);
                            String strBuyerCode = String.format("%04d", intBuyerCode);

                            //Testing Log
                            System.out.println("gened buyerCode is "+strBuyerCode);

                            offer.setBuyerCode(strBuyerCode);
                            offer.setStatProccessing();

                            //Testing Log
                            System.out.println(""+offer.toString());

                            Connection conn = new Connection();
                            resultObject = conn.updateOffer(offer);

                            System.out.println(resultObject.toString()+"");
                        }

                        AlertDialog dialog = new AlertDialog.Builder(OfferDetailPage.this)
                                .setTitle("Deal Code")
                                .setMessage(offer.getBuyerCode()+"")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Whatever...
                                    }
                                }).show();


                        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                        textView.setTextSize(50);
                        textView.setGravity(Gravity.CENTER_HORIZONTAL);


                    }
                }else {
                    System.out.println("checkbox Not checked");
                    cbAgreement.setFocusable(true);
                    cbAgreement.setTextColor(Color.RED);

                }
            }
        });



    }
}
