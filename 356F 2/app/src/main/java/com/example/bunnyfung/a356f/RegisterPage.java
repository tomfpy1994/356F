package com.example.bunnyfung.a356f;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {
    EditText etUserid,  etPw, etEmail;
    Button btnLogin;
    TextView tvStatu;
    String userid, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etUserid = (EditText) findViewById(R.id.etUserid);
        etPw = (EditText) findViewById(R.id.etPw);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvStatu = (TextView) findViewById(R.id.tvStatu);


        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                userid = etUserid.getText().toString();
                password = etPw.getText().toString();
                email = etEmail.getText().toString();

                tvStatu.setText(userid+","+password+","+email);
                JSONObject jsonObj = new JSONObject();
                try{
                    jsonObj.put("userid",userid);
                    jsonObj.put("pw", password);
                    jsonObj.put("email", email);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Log.i("jsonObj Value", jsonObj.toString());
                doLogin(jsonObj);
                break;
        }

    }

    public void doLogin(final JSONObject acc){
        Thread thread = new Thread() {
            public void run(){
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;
                try{
                    URL url = new URL("http://s356fproject.mybluemix.net/api/createac");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);


                    //Testing Log
                    System.out.println("URL:"+url.toString());
                    String strJsonobj = acc.toString();
                    System.out.println("doLogin Method jsonObj: "+ strJsonobj);

                    OutputStream os = connection.getOutputStream();
                    os.write(acc.toString().getBytes("UTF-8"));
                    os.close();

                    int HttpResult =connection.getResponseCode();
                    System.out.println("resopnseCode: "+HttpResult);

//                    if(HttpResult==HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(""+sb.toString());


//                    }else{
//                        System.out.println("ResponseMessage:"+connection.getResponseMessage());
//                    }
                    connection.disconnect();

                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

}
