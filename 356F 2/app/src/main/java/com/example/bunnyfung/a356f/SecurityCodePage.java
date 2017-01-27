package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.bunnyfung.a356f.Object.Account;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecurityCodePage extends AppCompatActivity {
    private Account acc;
    private EditText edtOldPw, edtNewPw, edtCNewPw;
    private Button btnSubmit, btnCancel;
    private String stu ="";


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
                boolean setCode = true;
                View focusView = null;

                if (!acc.getsCode().equals("")){
                    if (!acc.getsCode().equals((edtOldPw.getText()).toString())){
                        setCode = false;
                        edtOldPw.setError("Security Code wrong!");
                        focusView = edtOldPw;
                    }
                }
                if (edtNewPw.equals("")){
                    setCode = false;
                    edtNewPw.setError("Must be fill!");
                    focusView = edtNewPw;
                }
                if (((edtNewPw.getText()).toString()).length()!=6){
                    setCode = false;
                    edtNewPw.setError("Must be 6 digit number!");
                    focusView = edtNewPw;
                }
                if (!((edtNewPw.getText()).toString()).equals((edtCNewPw.getText()).toString())){
                    setCode = false;
                    edtCNewPw.setError("Security Code Not mathch!");
                    focusView = edtCNewPw;
                }


                if (setCode) {
                    acc.setsCode((edtNewPw.getText()).toString());
                    try {
                        System.out.println(acc.passToJsonObjectStr());
                        JSONObject jsonObject = new JSONObject(acc.passToJsonObjectStr());

                        //Testing Log
                        System.out.println("sCode accString: " + acc.toString());

                        doUpdate(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    while (stu.equals("")) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(stu);

                    Intent intent = new Intent();
                    try {
                        intent.putExtra("acc",acc.passToJsonObjectStr());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                   focusView.requestFocus();
                }
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
