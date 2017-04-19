package com.example.bunnyfung.a356f;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;

import org.json.JSONException;
import org.json.JSONObject;

public class GradingPage extends AppCompatActivity {
    private Account account;
    private Offer offer;
    private RatingBar ratingBar2;
    private Button btnSubmit, btnCancel;
    private JSONObject resultObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading_page);

        try {
            JSONObject jsonOffer = new JSONObject(getIntent().getStringExtra("offer"));
            offer = new Offer(jsonOffer);

            JSONObject jsonAcc = new JSONObject(getIntent().getStringExtra("acc"));
            account = new Account(jsonAcc);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer.setStatBuyerCommed();

                Connection conn = new Connection();

                resultObject = conn.updateOffer(offer);
                System.out.println(resultObject.toString());

                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer.setStatBuyerCommed();

                //Testing Log
                System.out.println("Range :"+ratingBar2.getRating());
                account.setGrade((int)(account.getGrade()+ratingBar2.getRating())/2);

                Connection conn = new Connection();

                resultObject = conn.updateOffer(offer);
                //Testing Log
                System.out.println(resultObject.toString());

                resultObject = conn.updateAcc(account);
                //Testing Log
                System.out.println(resultObject.toString());

                finish();
            }
        });

    }
}
