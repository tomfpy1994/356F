package com.example.bunnyfung.a356f;
import com.example.bunnyfung.a356f.Adapter.MyProductAdapter;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragMyProduct extends Fragment {

    private ListView listView;
    private Account acc = null;
    private ArrayList<Post>  alPost = new ArrayList<Post>();
    private JSONArray jsonArray = null;

    // constructor
    public FragMyProduct() {}
    public FragMyProduct(Account acc){this.acc = acc;}
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frag_my_product, null);

        // testing of get user information
        try {
            //Testing Log
            System.out.println("FragMyProduct: "+acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) rootView.findViewById(R.id.myPostList);

        getProducts(acc.getId());

        while (jsonArray==null){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            if (jsonArray!=null && jsonArray.length()!=0) {
                //Testing Log
                System.out.println("FragMyProduct resultObject: "+jsonArray.getJSONObject(0).getString("owner"));

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


        //arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        //listView.setAdapter(arrayAdapter);
        MyProductAdapter adapter = new MyProductAdapter(getActivity(), R.layout.post_list_item, alPost);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Post clickedPost = alPost.get(position);
                    String clickedPostStr = clickedPost.passToJsonObjectStr();

                    //Testing Log
                    System.out.println("MyProduct_Post:"+clickedPostStr);

                    Intent intent = new Intent(getActivity(), ProductPage.class);
                    intent.putExtra("post", clickedPostStr);
                    intent.putExtra("showType","edit");
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        // function of click item in list view
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(),parent.getItemAtPosition(position)+ " is select",Toast.LENGTH_LONG).show();
//            }
//        });
        return rootView;
    }

    public void getProducts(final String accId) {

        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("http://s356fproject.mybluemix.net/api/list/owner/"+accId);
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
