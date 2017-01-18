package com.example.bunnyfung.a356f;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bunnyfung.a356f.Object.Account;

import org.json.JSONException;
import org.json.JSONObject;

public class MainPage extends AppCompatActivity {
    public static Account acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        String str = getIntent().getStringExtra("acc");
        JSONObject accJsonObj;
        try {
            accJsonObj = new JSONObject(str);
            acc = new Account(accJsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("Account acc:"+acc.getUserid()+acc.getEmail()+acc.getPassword());

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.meunContainer, new FragMeun(acc)).commit();
        }

    }
}
