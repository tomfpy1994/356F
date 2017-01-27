package com.example.bunnyfung.a356f;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bunnyfung.a356f.Object.Account;
import org.json.JSONException;
import org.json.JSONObject;

public class SecurityCodePage extends AppCompatActivity {
    private Account acc;
    private EditText edtOldPw, edtNewPw, edtCNewPw;
    private Button btnSubmit, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_code_page);

        edtOldPw = (EditText) findViewById(R.id.edtOldPw);
        edtNewPw = (EditText) findViewById(R.id.edtNewPw);
        edtCNewPw = (EditText) findViewById(R.id.edtCNewPw);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        String str = getIntent().getStringExtra("acc");
        try {
            JSONObject jsonObject = new JSONObject(str);
            acc = new Account(jsonObject);

            System.out.println("SecurityCodePage: "+acc.getsCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (acc.getsCode().equals("")){
            System.out.println("sCode is null");
            edtOldPw.setEnabled(false);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }
}
