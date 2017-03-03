package com.example.bunnyfung.a356f;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bunnyfung.a356f.Object.Account;

public class FragOffer extends Fragment {
    private Account acc;
    private Button btnProcessing,btnInvitation,btnMyOffer;

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
            }
        });

        btnMyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: request server query My Offer data
            }
        });


        return rootView;
    }

}
