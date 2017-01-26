package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.bunnyfung.a356f.Object.Account;

public class SecurityCodePage extends AppCompatActivity {
    private Button btnCancel, btnSubmit;
    private Account acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_code_page);

        String str = getIntent().getStringExtra("acc");

        //Testing Log
        System.out.println("SecurityCodePage_");

    }
}
