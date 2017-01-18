package com.example.bunnyfung.a356f;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Account;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEditPage extends AppCompatActivity {
    private Account acc;
    private TextView tvUserid;
    private EditText edtName, edtPhoneNum;
    private RadioButton rbM, rbF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_page);

        String str = getIntent().getStringExtra("acc");
        try {
            JSONObject jsonObject = new JSONObject(str);
            acc = new Account(jsonObject);

            System.out.println(acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvUserid = (TextView) findViewById(R.id.tvUserid);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhoneNum = (EditText) findViewById(R.id.edtPhoneNum);
        rbF = (RadioButton) findViewById(R.id.rbF);
        rbM = (RadioButton) findViewById(R.id.rbM);


        edtName.setText(acc.getName());
        tvUserid.setText(acc.getUserid());
        edtPhoneNum.setText(acc.getPhoneNo());

        if (!(acc.getSex().equals(""))){
            if (acc.getSex().equals("M")){
                rbM.setChecked(true);
            }else
                rbF.setChecked(true);
        }

        rbF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbM.isChecked()){
                    rbM.setChecked(false);
                }
            }
        });

        rbM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbF.isChecked()){
                    rbF.setChecked(false);
                }
            }
        });






    }
}
