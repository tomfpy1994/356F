package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;

import com.example.bunnyfung.a356f.Adapter.OfferAdapter;
import com.example.bunnyfung.a356f.Adapter.OfferHistoryAdapter;
import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileHistoryPage extends AppCompatActivity {
    public Account acc;
    public Offer offer;
    private ArrayList<Offer> alOffer = new ArrayList<Offer>();
    private JSONArray jsonArray = null;
    private ListView lvOfferList;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_history_page);

        lvOfferList = (ListView) findViewById(R.id.history_list);
        back = (Button) findViewById(R.id.btnGoBack);

        String str = getIntent().getStringExtra("acc");
        try {
            JSONObject jsonObject = new JSONObject(str);
            acc = new Account(jsonObject);

            System.out.println(acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        printList();
    }

    public void printList(){
        Connection conn = new Connection();
        try {
            jsonArray = conn.listOffer(acc, 0).getJSONArray("offers");

            alOffer.clear();
            if (jsonArray!=null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Offer temOffer = new Offer(jsonArray.getJSONObject(i));

                    //if ((temOffer.getStat()==2 && temOffer.getOwnerID().equals(acc.getId()))||(temOffer.getStat()==2 && temOffer.getBuyerID().equals(acc.getId()))){
                    if(((temOffer.getStat()==2)||(temOffer.getStat()==3)||(temOffer.getStat()==4))&&((temOffer.getOwnerID().equals(acc.getId()))||(temOffer.getBuyerID().equals(acc.getId())))){
                        alOffer.add(temOffer);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Testing Log
        System.out.println(alOffer.size());

        OfferHistoryAdapter a = new OfferHistoryAdapter(getApplicationContext(), alOffer);
        lvOfferList.setAdapter(a);
        lvOfferList.invalidateViews();
    }
}
