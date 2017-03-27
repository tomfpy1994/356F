package com.example.bunnyfung.a356f.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

    public OfferAdapter(Context context, int resource, ArrayList<Offer> alOffer) {
        super(context, resource, alOffer);
        this.alOffer = alOffer;
        this.resource = resource;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(resource,null);
        }

        ImageView ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBuyerID = (TextView) convertView.findViewById(R.id.tvBuyerID);
        TextView tvOfferedPrice = (TextView) convertView.findViewById(R.id.tvOfferedPrice);
        Button btnAccept = (Button) convertView.findViewById(R.id.btnAccept);
        Button btnDecline = (Button) convertView.findViewById(R.id.btnDecline);

        Offer offer = alOffer.get(position);
        if (offer.getStat()==1){
            btnAccept.setVisibility(View.INVISIBLE);
        }else if (offer.getStat()==2){
            btnAccept.setVisibility(View.INVISIBLE);
            btnDecline.setVisibility(View.INVISIBLE);
        }else {
            btnAccept.setVisibility(View.VISIBLE);
            btnDecline.setVisibility(View.VISIBLE);
        }

        //TODO:get Product Img
        //ivImg.setImageBitmap(alOffer.get(position).getPhoto());
        //TODO: get product title
        tvName.setText(offer.getPostId());
        //TODO: get buyerName
        tvBuyerID.setText(offer.getBuyerID());
        tvOfferedPrice.setText(offer.getPrice()+"");


        return convertView;
    }
}