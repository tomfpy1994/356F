package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Adapter.MyProductAdapter;
import com.example.bunnyfung.a356f.Object.Post;
import com.example.bunnyfung.a356f.Object.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ResultOfSearch extends AppCompatActivity {
    private TextView text;
    private ListView list;
    private JSONArray jsonArray = null;
    private ArrayList<Post> alPost = new ArrayList<Post>();
    private String temp;
    private Account acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_search);

        text = (TextView) findViewById(R.id.tvResult);
        list = (ListView) findViewById(R.id.resultList);

        String str = getIntent().getStringExtra("acc");
        try {
            JSONObject jsonObject = new JSONObject(str);
            acc = new Account(jsonObject);

            System.out.println(acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(acc.toString());

        // ouput the result
        String s = getIntent().getStringExtra("result");
        text.setText(s);


        getResult(s);
        while (jsonArray==null){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            //Testing Log
            System.out.println("search result: "+jsonArray.getJSONObject(0));
            if (jsonArray!=null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Post post = new Post(jsonArray.getJSONObject(i));
                    if (!(post.getState().equals("2"))){
                        alPost.add(post);
                        System.out.println("add  to post!!");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyProductAdapter adapter = new MyProductAdapter(getApplication(), R.layout.post_list_item, alPost);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Post clickedPost = alPost.get(position);
                    String clickedPostStr = clickedPost.passToJsonObjectStr();

                    //Testing Log
                    System.out.println("All_Post:"+clickedPostStr);

                    Intent intent = new Intent(getApplicationContext(), ProductPage.class);
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

    }

    public void getResult(String s){
        temp = s;
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    //URL url = new URL("http://s356fproject.mybluemix.net/api/list/price/100");
                    // URL url = new URL("http://s356fproject.mybluemix.net/api/list/price/"+100);
                    URL url = new URL("http://s356fproject.mybluemix.net/api/list/"+temp);
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
