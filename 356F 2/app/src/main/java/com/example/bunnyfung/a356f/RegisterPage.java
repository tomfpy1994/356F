package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {
    EditText etUserid, etPw, etEmail;
    Button btnLogin,btnLoginPage;
    TextView tvStatu;
    String userid, password, email;
    ImageView ivMsgIcon;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    static String stu = "";

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
        ivMsgIcon = (ImageView) findViewById(R.id.ivMsgIcon);
        btnLoginPage = (Button)findViewById(R.id.btnLoginPage);

        ivMsgIcon.setVisibility(View.INVISIBLE);
        btnLoginPage.setVisibility(View.INVISIBLE);

        btnLoginPage.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                userid = etUserid.getText().toString();
                password = etPw.getText().toString();
                email = etEmail.getText().toString();


                System.out.println(userid + "," + password + "," + email);

                if (!userid.equals("") && !password.equals("") && !email.equals("")){
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put("userid", userid);
                        jsonObj.put("pw", password);
                        jsonObj.put("email", email);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("jsonObj Value", jsonObj.toString());
                    doLogin(jsonObj);
                    while (stu == "") {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {

                        }
                    }
                    switch (stu) {
                        case "createac success":
                            ivMsgIcon.setVisibility(View.VISIBLE);
                            ivMsgIcon.setImageResource(R.drawable.pass);
                            tvStatu.setText("Registion Success! \n Click the Login to start your experience");
                            tvStatu.setTextColor(Color.BLUE);
                            btnLoginPage.setVisibility(View.VISIBLE);
                            stu = "";
                            break;
                        case "402":
                            ivMsgIcon.setVisibility(View.VISIBLE);
                            ivMsgIcon.setImageResource(R.drawable.warning);
                            tvStatu.setText("Registion Failed! \n User ID or Email is used");
                            tvStatu.setTextColor(Color.RED);
                            stu = "";
                    }
                }else {
                    ivMsgIcon.setVisibility(View.VISIBLE);
                    ivMsgIcon.setImageResource(R.drawable.warning);
                    tvStatu.setText("Registion Failed! \n please fill all the bland");
                    tvStatu.setTextColor(Color.RED);
                }
                break;
            case R.id.btnLoginPage:
                Intent intent = new Intent(this, LoginPage.class);
                startActivity(intent);
        }

    }

    public void doLogin(final JSONObject acc) {
        String stus = "";
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://s356fproject.mybluemix.net/api/createac");
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
                    System.out.println("doLogin Method jsonObj: " + strJsonobj);

                    OutputStream os = connection.getOutputStream();
                    os.write(acc.toString().getBytes("UTF-8"));
                    os.close();

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
                        System.out.println("" + sb.toString());

                        JSONObject resultObject = new JSONObject(sb.toString());
                        stu = resultObject.getString("status");
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("RegisterPage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
