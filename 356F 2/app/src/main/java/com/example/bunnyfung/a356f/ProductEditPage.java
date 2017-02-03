package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Post;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductEditPage extends AppCompatActivity {
    public Account acc;
    public Post post;
    public JSONObject j;
    public ImageView photo, ivPhoto1, ivPhoto2, ibAddPhoto;
    public TextView name, brand, type, size, price, description, statu;
    public int priceNum;
    public double sizeNum, half;
    public Button submit, cancel, delete;
    public CheckBox halfSize;
    public String stu ="";
    public String sName, sBrand, sType, sSize, sPrice, sDescription;
    public static final int SELECTED_PICTURE = 1;
    FragmentTransaction fragTransaction;
    Fragment frag;
    public String owner,userID;
    private GoogleApiClient client;

    //constructor

    public ProductEditPage() {}

    public ProductEditPage(Post post){
        this.post = post;
        System.out.println("success get dat!! " + post.toString());
    }
    public ProductEditPage(JSONObject j){ this.j = j; }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit_page);

        String str = getIntent().getStringExtra("post");
        try {
            JSONObject jsonObject = new JSONObject(str);
            post = new Post(jsonObject);

            System.out.println("jsonObject value(in product edit page): "+post.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // initialized the item in layout
        photo = (ImageView) findViewById(R.id.ivBigPhoto);
        ivPhoto1 = (ImageView) findViewById(R.id.ivPhoto1);
        ivPhoto2 = (ImageView) findViewById(R.id.ivPhoto2);
        ibAddPhoto = (ImageView) findViewById(R.id.ibAddPhoto);
        name = (TextView) findViewById(R.id.edtName);
        brand = (TextView) findViewById(R.id.tfBrand);
        type = (TextView) findViewById(R.id.tfType);
        size = (TextView) findViewById(R.id.tfSize);
        price = (TextView) findViewById(R.id.tfPrice);
        description = (TextView) findViewById(R.id.tfDescription);
        submit = (Button) findViewById(R.id.btnPostSubmit);
        cancel = (Button) findViewById(R.id.btnPostCancel);
        halfSize = (CheckBox) findViewById(R.id.cbSize);
        statu = (TextView) findViewById(R.id.tvStatu);
        delete = (Button) findViewById(R.id.btnDeletePost);

        name.setText(post.getName());
        brand.setText(post.getBrand());
        type.setText(post.getType());
        size.setText(post.getSize()+"");
        price.setText(post.getPrice()+"");
        description.setText(post.getDescription());
        photo.setImageBitmap(post.getPhoto());

        //Demo Block
        ivPhoto1.setVisibility(View.INVISIBLE);
        ivPhoto2.setVisibility(View.INVISIBLE);
        ibAddPhoto.setVisibility(View.INVISIBLE);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

        halfSize.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    half=0.5;
                    System.out.println("check box is select");
                }else{
                    sSize = size.getText().toString();
                    sizeNum = Double.valueOf(sSize);
                    System.out.println(sizeNum);
                }
            }

        });

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btnPostSubmit:
                        //get user input
                        sName = name.getText().toString();
                        sBrand = brand.getText().toString();
                        sType = type.getText().toString();
                        sSize = size.getText().toString();
                        sPrice = price.getText().toString();
                        sDescription = description.getText().toString();

                        //convert to integer
                        sizeNum = Double.valueOf(sSize);
                        priceNum = Integer.valueOf(sPrice);

                        sizeNum+=half;

                        // modify post data
                        post.setName(sName);
                        post.setBrand(sBrand);
                        post.setType(sType);
                        post.setSize(sizeNum);
                        post.setPrice(priceNum);
                        post.setDescription(sDescription);

                        System.out.println(sName+" "+sBrand+" "+sType+" "+sizeNum+" "+priceNum+" "+sDescription);
                        if(!sName.equals("")&&!sBrand.equals("")&&!sType.equals("")&&!sSize.equals("")&&!sPrice.equals("")&&!sDescription.equals("")){
                            //post = new Post(sName, sBrand, sType, sizeNum, priceNum, sDescription, post.getOwner(), photo);
                            //
                            System.out.println(post.toString());
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject(post.passToJsonObjectStr());
                                System.out.println("pass to do method"+jsonObj);
                                doModifyPost(jsonObj);
                                System.out.println("doModifyPost was done!");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            while (stu == "") {
                                try {
                                    System.out.println("The program will sleep!!");
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {

                                }
                            }
                            switch (stu) {
                                case "update success":
                                    statu.setText("modify post Success! ");
                                    statu.setTextColor(Color.BLUE);
                                    submit.setVisibility(View.VISIBLE);
                                    stu = "";
                                    Intent intent = new Intent();
                                    try {
                                        intent.putExtra("post",post.passToJsonObjectStr());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    setResult(RESULT_OK, intent);
                                    finish();
                                    break;
                                case "500":
                                    statu.setText("modify post fail! ");
                                    statu.setTextColor(Color.RED);
                                    submit.setVisibility(View.VISIBLE);
                                    stu = "";
                                    break;

                            }

                        }else{
                            statu.setText("Add Post fail! You must fill all the bland");
                            statu.setTextColor(Color.RED);
                        }
                        break;

                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post.setState("3");
                try{
                    JSONObject j = new JSONObject(post.passToJsonObjectStr());
                    deletePost(j);
                }catch(JSONException e){
                    e.printStackTrace();
                }

                while (stu == "") {
                    try {
                        System.out.println("The program will sleep!!");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
                switch (stu) {
                    case "update success":
                        statu.setText("delete post Success! ");
                        statu.setTextColor(Color.BLUE);
                        submit.setVisibility(View.VISIBLE);
                        stu = "";
                        break;
                    case "500":
                        statu.setText("delete post fail! ");
                        statu.setTextColor(Color.RED);
                        submit.setVisibility(View.VISIBLE);
                        stu = "";
                        break;

                }

            }
        });
    }

    public void doModifyPost(final JSONObject j){
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("http://s356fproject.mybluemix.net/api/updateproduct");
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
                    String strJsonobj = j.toString();
                    System.out.println("doModifyPost Method jsonObj: " + strJsonobj);

                    OutputStream os = connection.getOutputStream();
                    os.write(j.toString().getBytes("UTF-8"));
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

                    } else if (HttpResult == 500) {
                        stu = "500";
                    }

                    connection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void deletePost(final JSONObject j){
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("http://s356fproject.mybluemix.net/api/updateproduct");
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
                    String strJsonobj = j.toString();
                    System.out.println("deletePost Method jsonObj: " + strJsonobj);

                    OutputStream os = connection.getOutputStream();
                    os.write(j.toString().getBytes("UTF-8"));
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

                    } else if (HttpResult == 500) {
                        stu = "500";
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

                    photo.setImageBitmap(selectedImag);
                    post.setPhoto(selectedImag);
                }
        }
    }
}
