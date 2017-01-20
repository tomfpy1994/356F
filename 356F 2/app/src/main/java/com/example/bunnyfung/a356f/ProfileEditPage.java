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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileEditPage extends AppCompatActivity {
    private Account acc;
    private TextView tvUserid;
    private EditText edtName, edtPhoneNum;
    private RadioButton rbM, rbF;
    private ImageView ivIcon;
    private Button btnCancel,btnSubmit;
    private String stu ="";

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
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        edtName.setText(acc.getName());
        tvUserid.setText(acc.getUserid());
        edtPhoneNum.setText(acc.getPhone());
        ivIcon.setImageBitmap(acc.getIcon());

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
                acc.setName(edtName.getText().toString());
                acc.setPhone(edtPhoneNum.getText().toString());
                if (rbF.isChecked()){
                    acc.setSex("F");
                }else if (rbM.isChecked()){
                    acc.setSex("M");
                }

                try {
                    System.out.println(acc.passToJsonObjectStr());
                    JSONObject jsonObject = new JSONObject(acc.passToJsonObjectStr());
                    //Testing Log
                    System.out.println("accString: "+acc.toString());

                    doUpdate(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                while (stu.equals("")){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(stu);
                finish();
            }
        });
    }

    public void doUpdate(final JSONObject acc) {
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://s356fproject.mybluemix.net/api/updateac");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);


                    //Testing Log
                    System.out.println("URL:" + url.toString());
                    String strJsonobj = acc.toString();

                    //Testing Log
                    System.out.println("doLogin Method jsonObj: " + strJsonobj);

                    OutputStream os = connection.getOutputStream();
                    os.write(acc.toString().getBytes("UTF-8"));
                    os.close();

                    int HttpResult = connection.getResponseCode();

                    //Testing Log
                    System.out.println("resopnseCode: " + HttpResult);

                    if (HttpResult == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                connection.getInputStream()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();

                        //Testing Log
                        System.out.println("sb: " + sb.toString());

                        JSONObject resultObject = new JSONObject(sb.toString());
                        stu = resultObject.getString("status");

                        //Testing Log
                        System.out.println("responesStatud: "+stu);

                    } else if (HttpResult == 402) {
                        stu = "402";
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
