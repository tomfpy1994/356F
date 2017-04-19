package com.example.bunnyfung.a356f.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.R;

import java.util.ArrayList;

/**
 * Created by OliverLeung on 18/4/2017.
 */

public class OfferHistoryAdapter extends ArrayAdapter<Offer> {

    private ArrayList<Offer> alOffer;
    private LayoutInflater inflater;
    private Context context;


    public OfferHistoryAdapter(Context context, ArrayList<Offer> alOffer) {
        super(context, R.layout.offer_list_item, alOffer);
        this.alOffer = alOffer;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater layoutInflaterter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //convertView = layoutInflaterter.inflate(R.layout.offer_list_item, parent, false);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.offer_list_item,null);
        }

        ImageView ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBuyerID = (TextView) convertView.findViewById(R.id.tvBuyerID);
        TextView tvOfferedPrice = (TextView) convertView.findViewById(R.id.tvOfferedPrice);


        Offer offer = alOffer.get(position);



        //TODO:get Product Img
        ivImg.setImageBitmap(offer.getPhoto());
        //TODO: get product title
        tvName.setText(offer.getTitle());
        System.out.println("title :"+ offer.getTitle());
        tvBuyerID.setText(offer.getBuyerName());
        System.out.println("name :" + offer.getBuyerName());
        tvOfferedPrice.setText(offer.getPrice()+"");
        System.out.println("$$$$$ "+offer.getPrice());




        return convertView;
    }
}
