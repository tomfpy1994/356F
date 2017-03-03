package com.example.bunnyfung.a356f;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.bunnyfung.a356f.Object.Account;

public class FragMeun extends Fragment {
    private Account acc;
    Fragment frag;
    FragmentTransaction fragTransaction;

    public FragMeun() {
        // Required empty public constructor
    }
    public FragMeun(Account acc){
        this.acc = acc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_frag_meun, container, false);

        frag = new FragHome(acc);
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.container, frag);
        fragTransaction.commit();

        ImageButton ibHome = (ImageButton) view.findViewById(R.id.ibHome);
        ImageButton ibMyProduct = (ImageButton) view.findViewById(R.id.ibMyProduct);
        ImageButton ibAdd = (ImageButton) view.findViewById(R.id.ibAdd);
        ImageButton ibOffer = (ImageButton) view.findViewById(R.id.ibOffer);
        ImageButton ibProfile = (ImageButton) view.findViewById(R.id.ibProfile);

        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new FragHome(acc);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                fragTransaction.commit();
            }
        });

        ibMyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new FragMyProduct(acc);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                fragTransaction.commit();
            }
        });

        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new FragNewPost(acc);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                fragTransaction.commit();
            }
        });

        ibOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new FragOffer(acc);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                fragTransaction.commit();
            }
        });

        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new FragProfile(acc);
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                fragTransaction.commit();
            }
        });



        return view;
    }


}
