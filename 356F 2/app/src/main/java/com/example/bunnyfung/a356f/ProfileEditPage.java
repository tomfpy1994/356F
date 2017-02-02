package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
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
import java.net.InterfaceAddress;
import java.net.URL;

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
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acc.setPhone(edtPhoneNum.getText().toString());
                try {
                    System.out.println(acc.passToJsonObjectStr());
                    JSONObject jsonObject = new JSONObject(acc.passToJsonObjectStr());
                    //Testing Log
                    System.out.println("accString: " + acc.toString());

                    doUpdate(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                while (stu.equals("")) {
                    try {
                        System.out.println("sleep!!!");
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
                    } else if(HttpResult == 413){
                        stu = "413";
                    }

                    connection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap selectedImag = BitmapFactory.decodeFile(filePath);
                    ivIcon.setImageBitmap(selectedImag);
                    acc.setIcon(selectedImag);
                }
        }
    }

}
