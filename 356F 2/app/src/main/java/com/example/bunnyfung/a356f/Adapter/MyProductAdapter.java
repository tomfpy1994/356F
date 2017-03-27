package com.example.bunnyfung.a356f.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Post;
import com.example.bunnyfung.a356f.R;

import java.util.ArrayList;


/**
 * Created by BunnyFung on 31/1/2017.
 */

public class MyProductAdapter extends ArrayAdapter{

    private ArrayList<Post> alPosts;
    private int resource;
    private LayoutInflater inflater;

    public MyProductAdapter(Context context, int resource, ArrayList<Post> alPosts) {
        super(context, resource, alPosts);
        this.alPosts = alPosts;
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
        TextView tvBand = (TextView) convertView.findViewById(R.id.tvBand);
        TextView tvSize = (TextView) convertView.findViewById(R.id.tvSize);
        TextView tvType = (TextView) convertView.findViewById(R.id.tvType);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvOfferedPrice);

        ivImg.setImageBitmap(alPosts.get(position).getPhoto());
        tvName.setText(alPosts.get(position).getName());
        tvBand.setText(alPosts.get(position).getBrand());
        tvSize.setText(""+alPosts.get(position).getSize());
        tvType.setText(alPosts.get(position).getType());
        tvPrice.setText(""+alPosts.get(position).getPrice());

        return convertView;
    }
}
