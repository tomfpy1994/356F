package com.example.bunnyfung.a356f.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.R;

import java.util.ArrayList;

/**
 * Created by BunnyFung on 27/3/2017.
 */

public class OfferAdapter extends ArrayAdapter {

    private ArrayList<Offer> alOffer;
    private int resource;
    private LayoutInflater inflater;
    private int isMyOffer;

    public OfferAdapter(Context context, int resource, ArrayList<Offer> alOffer, int isMyOffer) {
        super(context, resource, alOffer);
        this.isMyOffer = isMyOffer;
        this.alOffer = alOffer;
        this.resource = resource;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(resource,null);
        }

        ImageView ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBuyerID = (TextView) convertView.findViewById(R.id.tvBuyerID);
        TextView tvOfferedPrice = (TextView) convertView.findViewById(R.id.tvOfferedPrice);
//        Button btnAccept = (Button) convertView.findViewById(R.id.btnAccept);
//        Button btnDecline = (Button) convertView.findViewById(R.id.btnDecline);


        Offer offer = alOffer.get(position);

        //Testing Log
        System.out.println(isMyOffer+"");

//        if (offer.getStat()==1){
//            btnAccept.setVisibility(View.INVISIBLE);
//        }else if (offer.getStat()==2){
//            btnAccept.setVisibility(View.INVISIBLE);
//            btnDecline.setVisibility(View.INVISIBLE);
//        }else {
//            btnAccept.setVisibility(View.VISIBLE);
//            btnDecline.setVisibility(View.VISIBLE);
//        }
//        if (isMyOffer==1){
//            btnAccept.setVisibility(View.INVISIBLE);
//        }

        //TODO:get Product Img
        ivImg.setImageBitmap(offer.getPhoto());
        //TODO: get product title
        tvName.setText(offer.getTitle());
        tvBuyerID.setText(offer.getBuyerName());
        tvOfferedPrice.setText(offer.getPrice()+"");

//        btnDecline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("321");
//            }
//        });


        return convertView;
    }
}
