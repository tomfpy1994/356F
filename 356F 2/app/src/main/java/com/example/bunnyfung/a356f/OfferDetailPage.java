package com.example.bunnyfung.a356f;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.Object.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.security.AccessController.getContext;

public class OfferDetailPage extends AppCompatActivity {
    private Offer offer;
    private Account acc;
    private TextView tvName, tvBrand, tvSize, tvDesc, tvBuyerID, tvOfferedPrice, tvDateTime, tvPlace, tvAgreeTitle;
    private ImageView ivProductImg;
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

        ivProductImg = (ImageView) findViewById(R.id.ivProductImg);


        try {
            JSONObject jsonOffer = new JSONObject(getIntent().getStringExtra("offer"));
            offer = new Offer(jsonOffer);

            JSONObject jsonAcc = new JSONObject(getIntent().getStringExtra("acc"));
            acc = new Account(jsonAcc);

            String isMyOffer = getIntent().getStringExtra("isMyOffer");

            //Testing Log
            System.out.println("isMyOffer: "+isMyOffer);

            if (isMyOffer.equals("1")){
                btnAccept.setVisibility(View.INVISIBLE);
            }

            switch (offer.getStat()){
                case 0: if (acc.getId().equals(offer.getBuyerID())){
                            btnAccept.setText("DEAL");
                        }else
                            btnAccept.setText("ACCEPT");
                        break;

                case 1: btnAccept.setText("DEAL");
                        tvAgreeTitle.setVisibility(View.INVISIBLE);
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
        tvOfferedPrice.setText("$"+offer.getPrice()+"");

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        SimpleDateFormat showFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(offer.getDateTime());
            tvDateTime.setText(""+showFormat.format(convertedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
            ivProductImg.setImageBitmap(offer.getPhoto());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog1 = new AlertDialog.Builder(OfferDetailPage.this)
                        .setTitle("Warning ")
                        .setMessage("Are you comfirm to DECLINE the offer?")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                offer.setStatDeal();
                                System.out.println(""+offer.toString());

                                Connection conn = new Connection();
                                resultObject = conn.updateOffer(offer);

                                System.out.println(resultObject.toString()+"");

                                dialog.dismiss();
                                finish();

                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

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

                        if (offer.getBuyerID().equals(acc.getId())) {
                            //Testing Log
                            System.out.println("buyer enter code");

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(OfferDetailPage.this);
                            alertDialog.setTitle("Enter seller deal Code");

                            final EditText input = new EditText(OfferDetailPage.this);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            input.setLayoutParams(lp);
                            alertDialog.setView(input);

                            alertDialog.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String dealCode = input.getText().toString();

                                            //Testing Log
                                            System.out.println("entered dealcode : " + dealCode);

                                            if (dealCode.equals(offer.getOwnerCode())) {
                                                //Testing Log
                                                System.out.println("acc balance:" + acc.getBalance()+"  offer price: "+offer.getPrice());
                                                if (acc.getBalance()-offer.getPrice()>0) {
                                                    //Testing Log
                                                    System.out.println("dealcode same");

                                                    //Do update buyerAcc, SellerAcc, post, offer
                                                    //set offer
                                                    offer.setBuyerCode(dealCode);
                                                    offer.setStatDeal();
                                                    //Testing Log
                                                    System.out.println("Offer: " + offer.toString());


                                                    //set post
                                                    post.setState("1");
                                                    //Testing Log
                                                    System.out.println("Post: " + post.toString());


                                                    //set buyerAcc balance
                                                    int balance = acc.getBalance() - offer.getPrice();
                                                    acc.setBalance(balance);
                                                    //Testing Log
                                                    System.out.println("buyerAcc: " + acc.toString());


                                                    //set sellerAcc balance
                                                    //TODO: get sellerAcc and update balance
                                                    Connection conn = new Connection();
                                                    resultObject = conn.getOneAccount(offer.getOwnerID());
                                                    try {
                                                        jsonArray = resultObject.getJSONArray("account");
                                                        Account ownerAcc = new Account(jsonArray.getJSONObject(0));
                                                        ownerAcc.setBalance(ownerAcc.getBalance()+offer.getPrice());

                                                        //Testing Log
                                                        System.out.println("ownerAcc:"+ ownerAcc.getBalance());

                                                        resultObject = conn.dealOffer(offer,post, acc, ownerAcc);
                                                        System.out.println(resultObject.toString());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    finish();
                                                }else {
                                                    AlertDialog dialog1 = new AlertDialog.Builder(OfferDetailPage.this)
                                                            .setTitle("Payment Error")
                                                            .setMessage("You have not enough balance.\n Please deposit money.")
                                                            .setCancelable(false)
                                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            }).show();
                                                }

                                            }else{
                                                AlertDialog dialog1 = new AlertDialog.Builder(OfferDetailPage.this)
                                                        .setTitle("Payment Error")
                                                        .setMessage("Confirm code wrong, Please try again! ")
                                                        .setCancelable(false)
                                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        }).show();
                                            }
                                        }
                                    });

                            alertDialog.setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            alertDialog.show();
                        }else{

                            AlertDialog dialog = new AlertDialog.Builder(OfferDetailPage.this)
                                    .setTitle("Deal Code")
                                    .setMessage(offer.getOwnerCode()+"")
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
                        System.out.println("Accept");
                        if (offer.getBuyerCode().equals("")){
                            //Testing Log
                            System.out.println("buyerCode is null");
                            Random r = new Random();
                            int intOwnerCode = r.nextInt(10000);
                            String strOwnerCode = String.format("%04d", intOwnerCode);

                            //Testing Log
                            System.out.println("gened buyerCode is "+strOwnerCode);

                            offer.setOwnerCode(strOwnerCode);
                            offer.setStatProccessing();

                            //Testing Log
                            System.out.println(""+offer.toString());

                            Connection conn = new Connection();
                            resultObject = conn.updateOffer(offer);

                            System.out.println(resultObject.toString()+"");
                        }

                        AlertDialog dialog = new AlertDialog.Builder(OfferDetailPage.this)
                                .setTitle("Deal Code")
                                .setMessage(offer.getOwnerCode()+"")
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
