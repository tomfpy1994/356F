package com.example.bunnyfung.a356f;

import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Post;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FragNewPost extends Fragment {
    public Account acc;
    public Post post;
    public ImageView photo;
    public TextView name, brand, type, size, price, description, statu;
    public int sizeNum, priceNum;
    public Button submit, cancel;
    public CheckBox halfSize;
    FragmentTransaction fragTransaction;
    Fragment frag;
    public String stu ="";
    public String sName, sBand, sType, sSize,sPrice, sDescription;
    public static final int SELECTED_PICTURE = 1;
    View rootView;
    public String userID;
    private GoogleApiClient client;

    // constructor
    public FragNewPost(Account acc){
        this.acc=acc;
        userID = acc.getId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_frag_new_post, null);

        // test of get user information
        try{
            System.out.print("FragNewPost: "+ acc.passToJsonObjectStr());
        }catch(JSONException e){
            e.printStackTrace();
        }


        // initialized the item in layout
        photo = (ImageView) rootView.findViewById(R.id.ivBigPhoto);
        name = (TextView) rootView.findViewById(R.id.edtName);
        brand = (TextView) rootView.findViewById(R.id.tfBrand);
        type = (TextView) rootView.findViewById(R.id.tfType);
        size = (TextView) rootView.findViewById(R.id.tfSize);
        price = (TextView) rootView.findViewById(R.id.tfPrice);
        description = (TextView) rootView.findViewById(R.id.tfDescription);
        submit = (Button) rootView.findViewById(R.id.btnPostSubmit);
        cancel = (Button) rootView.findViewById(R.id.btnPostCancel);
        halfSize = (CheckBox) rootView.findViewById(R.id.cbSize);
        statu = (TextView) rootView.findViewById(R.id.tvStatu);






        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag = new FragHome();
                fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                fragTransaction.commit();
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
                    System.out.println("check box is select");
                }
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.btnPostSubmit:
                        //get user input
                        sName = name.getText().toString();
                        sBand = brand.getText().toString();
                        sType = type.getText().toString();
                        sSize = size.getText().toString();
                        sPrice = price.getText().toString();
                        sDescription = description.getText().toString();
                        //convert to integer
                        sizeNum = Integer.valueOf(sSize);
                        priceNum = Integer.valueOf(sPrice);

                        System.out.println(sName+" "+sBand+" "+sType+" "+sizeNum+" "+priceNum+" "+sDescription);
                        if(!sName.equals("")&&!sBand.equals("")&&!sType.equals("")&&!sSize.equals("")&&!sPrice.equals("")&&!sDescription.equals("")){
                            Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.default_icon);
                            post = new Post(sName, sBand, sType, sizeNum, priceNum, sDescription, userID, photo);
                            //
                            System.out.println(post.toString());
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject(post.passToJsonObjectStr());
                                System.out.println("jsonOb value: "+jsonObj);
                                doAddPost(jsonObj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            while (stu == "") {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {

                                }
                            }
                            switch (stu) {
                                case "add success":
                                    statu.setText("Add post Success! ");
                                    statu.setTextColor(Color.BLUE);
                                    submit.setVisibility(View.VISIBLE);
                                    stu = "";
                                    break;
                                case "500":
                                    statu.setText("Add post fail! ");
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

        return rootView;
    }

    public void doAddPost(final JSONObject j){
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("http://s356fproject.mybluemix.net/api/addproduct");
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
                    System.out.println("doAddPost Method jsonObj: " + strJsonobj);

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

}

