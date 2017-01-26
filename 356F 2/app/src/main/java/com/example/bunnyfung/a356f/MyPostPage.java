package com.example.bunnyfung.a356f;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class MyPostPage extends AppCompatActivity {
    private ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_page);
        //connect server
        connect();


        listView  = (ListView) findViewById(R.id.myPostList);
        String[] values = new String[]{
                "1",
                "2",
                "3",
                "4"
        };
        adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 ,values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+ " is select",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void connect(){
        Thread thread = new Thread(){
            public void run(){
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;
                JSONObject acc = new JSONObject();

                //URL url = new URL("http://s356fproject.mybluemix.net/api/login");

            }
        };
        thread.start();
    }

}
