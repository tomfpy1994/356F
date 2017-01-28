package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class TransactionPage extends AppCompatActivity {
    private Account acc;
    private RadioButton rbtnDeposit, rbtnWithdraw;
    private EditText edtAmount, edtC_Card, edtScode;
    private TextView tvAmount;
    private final String tvAmountStr = "Current Amount: $";
    private Button btnSubmit, btnCancel;
    private int stu1 = 0;
    private String stu ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_page);

        String str = getIntent().getStringExtra("acc");
        try {
            JSONObject jsonObject = new JSONObject(str);
            acc = new Account(jsonObject);

            //Testing Log
            System.out.println(acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rbtnDeposit = (RadioButton) findViewById(R.id.rbtnDeposit);
        rbtnWithdraw = (RadioButton) findViewById(R.id.rbtnWithdraw);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        edtC_Card = (EditText) findViewById(R.id.edtC_Card);
        edtScode = (EditText) findViewById(R.id.edtScode);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        rbtnDeposit.setChecked(true);
        rbtnWithdraw.setChecked(false);
        tvAmount.setText(tvAmountStr+acc.getBalance());

        if (acc.getsCode().equals("")){
            edtScode.setError("Please Set Security Code Frist!");
            edtScode.setEnabled(false);
            edtAmount.setEnabled(false);
            edtC_Card.setEnabled(false);
            btnSubmit.setEnabled(false);
            View focusView =edtScode;
            focusView.requestFocus();
        }


        rbtnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbtnWithdraw.isChecked()){
                    rbtnWithdraw.setChecked(false);
                    stu1 = 0;
                }
            }
        });
        rbtnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbtnDeposit.isChecked()){
                    rbtnDeposit.setChecked(false);
                    stu1 = 1;
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean submit = true;
                View focusView = null;

                if (edtAmount.getText().toString().isEmpty()){
                    edtAmount.setError("Amount can NOT 0!");
                    focusView = edtAmount;
                    submit = false;
                }
                if (edtC_Card.getText().toString().isEmpty()){
                    edtC_Card.setError("Enter Credit Card!");
                    focusView = edtC_Card;
                    submit = false;
                }
                if (edtScode.getText().toString().isEmpty()){
                    edtScode.setError("Enter Security Code!");
                    focusView = edtScode;
                    submit = false;
                }
                if (!(edtScode.getText().toString().equals(acc.getsCode()))){
                    edtScode.setError("Wrong Security Code!");
                    focusView = edtScode;
                    submit = false;
                }

                int tampBalance = Integer.parseInt((edtAmount.getText()).toString());

                if (stu1==0){
                    acc.setBalance(acc.getBalance()+tampBalance);
                }else {
                    if (tampBalance<acc.getBalance()){
                        acc.setBalance(acc.getBalance()-Integer.parseInt((edtAmount.getText()).toString()));
                    }else {
                        edtAmount.setError("Out of Amount!");
                        focusView = edtAmount;
                        submit = false;
                    }
                }


                if (submit) {
                    try {
                        System.out.println(acc.passToJsonObjectStr());
                        JSONObject jsonObject = new JSONObject(acc.passToJsonObjectStr());
                        //Testing Log
                        System.out.println("accBalance: " + acc.getBalance());

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
