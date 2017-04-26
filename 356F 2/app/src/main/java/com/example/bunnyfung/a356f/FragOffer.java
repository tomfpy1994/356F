package com.example.bunnyfung.a356f;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.bunnyfung.a356f.Adapter.OfferAdapter;
import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class FragOffer extends Fragment {
    private Account acc;
    private Button btnProcessing,btnInvitation,btnMyOffer, btnHistory;
    private ImageButton ivReload;
    private ListView lvOfferList;
    private JSONArray jsonArray = null;
    private ArrayList<Offer> alOffer = new ArrayList<Offer>();
    private int isMyOffer = 0;

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
        btnHistory = (Button)rootView.findViewById(R.id.btnHistory);
        lvOfferList = (ListView)rootView.findViewById(R.id.lvOfferList);

        ivReload = (ImageButton)rootView.findViewById(R.id.ivReload);
        btnProcessing.setTextColor(Color.MAGENTA);
        btnInvitation.setTextColor(Color.LTGRAY);
        btnMyOffer.setTextColor(Color.LTGRAY);


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
        isMyOffer = 0;

        //Testing Log
        System.out.println(alOffer.size());

        OfferAdapter adapter = new OfferAdapter(getActivity(), R.layout.offer_list_item, alOffer, 0);
        lvOfferList.setAdapter(adapter);

        lvOfferList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Offer clickedOffer = alOffer.get(position);
                String clickedOfferStr = clickedOffer.passToJsonObjectStr();

                //Testing Log
                System.out.println("Clicked_Offer:"+clickedOfferStr);

                Intent intent = new Intent(getActivity(), OfferDetailPage.class);
                intent.putExtra("offerId", clickedOffer.get_id());
                try {
                    intent.putExtra("acc", acc.passToJsonObjectStr());
                    intent.putExtra("isMyOffer", isMyOffer+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        });

        ivReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alOffer.clear();

                getPost();

                OfferAdapter adapter = new OfferAdapter(getActivity(), R.layout.offer_list_item, alOffer, 0);
                lvOfferList.setAdapter(adapter);

            }
        });

        // go to my history page
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProfileHistoryPage.class);
                try {
                    intent.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent,1);
            }
        });

        btnProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnProcessing.setTextColor(Color.MAGENTA);
                btnInvitation.setTextColor(Color.LTGRAY);
                btnMyOffer.setTextColor(Color.LTGRAY);
                alOffer.clear();
                if (jsonArray!=null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Offer temOffer = null;
                        try {
                            temOffer = new Offer(jsonArray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (temOffer.getStat()==1){
                            alOffer.add(temOffer);

                        }
                    }
                }

                isMyOffer = 0;
                lvOfferList.invalidateViews();
                OfferAdapter adapter = new OfferAdapter(getActivity(), R.layout.offer_list_item, alOffer, 0);
                lvOfferList.setAdapter(adapter);
            }
        });

        btnInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnProcessing.setTextColor(Color.LTGRAY);
                btnInvitation.setTextColor(Color.MAGENTA);
                btnMyOffer.setTextColor(Color.LTGRAY);

                alOffer.clear();
                if (jsonArray!=null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Offer temOffer = null;
                        try {
                            temOffer = new Offer(jsonArray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (temOffer.getStat()<1) {
                            if (temOffer.getOwnerID().equals(acc.getId())) {
                                alOffer.add(temOffer);
                            }
                        }
                    }
                }

                isMyOffer = 0;
                OfferAdapter adapter = new OfferAdapter(getActivity(), R.layout.offer_list_item, alOffer, 0);
                lvOfferList.setAdapter(adapter);
                lvOfferList.invalidateViews();

            }
        });

        btnMyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnProcessing.setTextColor(Color.LTGRAY);
                btnInvitation.setTextColor(Color.LTGRAY);
                btnMyOffer.setTextColor(Color.MAGENTA);

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
                            if (temOffer.getStat()<=1) {
                                alOffer.add(temOffer);
                            }
                        }
                    }
                }

                isMyOffer = 1;
                lvOfferList.invalidateViews();
                OfferAdapter adapter = new OfferAdapter(getActivity(), R.layout.offer_list_item, alOffer, 1);
                lvOfferList.setAdapter(adapter);
            }
        });

        return rootView;
    }

    public void getPost(){
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
        isMyOffer = 0;

        //Testing Log
        System.out.println(alOffer.size());
    }

}
