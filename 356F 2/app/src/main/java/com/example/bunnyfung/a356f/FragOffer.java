package com.example.bunnyfung.a356f;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.bunnyfung.a356f.Adapter.MyProductAdapter;
import com.example.bunnyfung.a356f.Adapter.OfferAdapter;
import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class FragOffer extends Fragment {
    private Account acc;
    private Button btnProcessing,btnInvitation,btnMyOffer;
    private ListView lvOfferList;
    private JSONArray jsonArray = null;
    private Offer offer;
    private ArrayList<Offer> alOffer = new ArrayList<Offer>();

    public FragOffer() {}
    public FragOffer(Account ac){ this.acc=ac;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frag_offer, null);

        //Testing Log
        System.out.println(acc.toString());

        btnProcessing = (Button)rootView.findViewById(R.id.btnProcessing);
        btnInvitation = (Button)rootView.findViewById(R.id.btnInvitation);
        btnMyOffer = (Button)rootView.findViewById(R.id.btnMyOffer);
        lvOfferList = (ListView)rootView.findViewById(R.id.lvOfferList);


        Connection conn = new Connection();
        try {
            jsonArray = conn.listOffer(acc, 0).getJSONArray("offers");

            alOffer.clear();
            if (jsonArray!=null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Offer temOffer = new Offer(jsonArray.getJSONObject(i));

                    if (temOffer.getStat()==1){
                        alOffer.add(temOffer);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Testing Log
        System.out.println(alOffer.size());

        OfferAdapter adapter = new OfferAdapter(getActivity(), R.layout.offer_list_item, alOffer);
        lvOfferList.setAdapter(adapter);

        btnProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: request server query processing data
            }
        });

        btnInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: request server query Invitation data
                alOffer.clear();
                if (jsonArray!=null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Offer temOffer = null;
                        try {
                            temOffer = new Offer(jsonArray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (temOffer.getOwnerID().equals(acc.getId())){
                            alOffer.add(temOffer);
                        }
                    }
                }

                OfferAdapter adapter = new OfferAdapter(getActivity(), R.layout.offer_list_item, alOffer);
                lvOfferList.setAdapter(adapter);
                lvOfferList.invalidateViews();

            }
        });

        btnMyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: request server query My Offer data
                alOffer.clear();
                if (jsonArray!=null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Offer temOffer = null;
                        try {
                            temOffer = new Offer(jsonArray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (temOffer.getBuyerID().equals(acc.getId())){
                            alOffer.add(temOffer);

                        }
                    }
                }

                lvOfferList.invalidateViews();
                OfferAdapter adapter = new OfferAdapter(getActivity(), R.layout.offer_list_item, alOffer);
                lvOfferList.setAdapter(adapter);

            }
        });


        return rootView;
    }

}
