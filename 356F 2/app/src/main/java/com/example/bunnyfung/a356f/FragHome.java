package com.example.bunnyfung.a356f;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Button;

import com.example.bunnyfung.a356f.Adapter.MyProductAdapter;
import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragHome extends Fragment {
    private ListView listView;
    private Account acc = null;
    private Post post;
    private ArrayList<Post> alPost = new ArrayList<Post>();
    private JSONArray jsonArray = null;
    private Button searchButton;

    public FragHome() {}
    public FragHome(Account ac){ this.acc=ac; }
    @Override

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_frag_home, null);

        listView = (ListView) rootView.findViewById(R.id.allProductList);
        searchButton = (Button) rootView.findViewById(R.id.btnSearch);
        getProduct();
        while (jsonArray==null){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            //Testing Log
            System.out.println("allProductList resultObject: "+jsonArray.getJSONObject(0));
            if (jsonArray!=null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Post post = new Post(jsonArray.getJSONObject(i));
                    if (!(post.getState().equals("3"))){
                        alPost.add(post);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Testing Log
        System.out.println(alPost.size());

        MyProductAdapter adapter = new MyProductAdapter(getActivity(), R.layout.post_list_item, alPost);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Post clickedPost = alPost.get(position);
                    String clickedPostStr = clickedPost.passToJsonObjectStr();

                    //Testing Log
                    System.out.println("Clicked_Post:"+clickedPostStr);
                    System.out.println("Acc:"+acc.toString());
                    System.out.println("Clicked_Post_OwnerID:"+ clickedPost.getOwner());
                    System.out.println("AccID: "+acc.getId());


                    Intent intent = new Intent(getActivity(), ProductPage.class);
                    intent.putExtra("post", clickedPostStr);
                    intent.putExtra("acc", acc.passToJsonObjectStr());

                    if (clickedPost.getOwner().equals(acc.getId())){
                        intent.putExtra("showType","edit");
                    }else {
                        intent.putExtra("showType","show");
                    }

                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //go to search page
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
             Intent i = new Intent(getActivity(), SearchPage.class);
                try {
                    i.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(i);
            }
        });
        return rootView;
    }

    public void getProduct(){
        Connection conn = new Connection();
        try {
            jsonArray = conn.getProduct().getJSONArray("products");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
