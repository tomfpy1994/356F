package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Account;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEditPage extends AppCompatActivity {
    private Account acc;
    private TextView tvUserid;
    private EditText edtPhoneNum;
    private ImageView ivIcon;
    private Button btnCancel,btnSubmit;
    private String stu ="";
    private static final int SELECTED_PICTURE = 1;


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
        edtPhoneNum = (EditText) findViewById(R.id.edtPhoneNum);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        tvUserid.setText(acc.getUserid());
        edtPhoneNum.setText(acc.getPhone());
        ivIcon.setImageBitmap(acc.getIcon());


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acc.setPhone(edtPhoneNum.getText().toString());
                try {
                    System.out.println(acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });






    }
}
