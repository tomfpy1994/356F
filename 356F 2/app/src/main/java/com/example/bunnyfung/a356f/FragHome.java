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
    public FragHome(Post p){ this.post=p; }
    @Override

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_frag_home, null);

        listView = (ListView) rootView.findViewById(R.id.allProductList);
        searchButton = (Button) rootView.findViewById(R.id.btnSearch);
        getProduct();
        while (jsonArray==null){
            try {
                Thread.sleep(500);
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
                    System.out.println("All_Post:"+clickedPostStr);

                    Intent intent = new Intent(getActivity(), ProductPage.class);
                    intent.putExtra("post", clickedPostStr);
                    intent.putExtra("showType","show");
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
                startActivity(i);
            }
        });
        return rootView;
    }

    public void getProduct(){
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("http://s356fproject.mybluemix.net/api/list/");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setConnectTimeout(20000);
                    connection.setReadTimeout(20000);


                    //Testing Log
                    System.out.println("URL:" + url.toString());


                    int HttpResult = connection.getResponseCode();
                    System.out.println("resopnseCode: " + HttpResult);

                    if (HttpResult == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                connection.getInputStream()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        System.out.println(sb.toString());
                        System.out.println("sb Length: "+ sb.length());

                        String sbStr = "{products:"+sb+"}";

                        JSONObject productsJson = new JSONObject(sbStr);
                        jsonArray = productsJson.getJSONArray("products");
                    }
                    connection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
