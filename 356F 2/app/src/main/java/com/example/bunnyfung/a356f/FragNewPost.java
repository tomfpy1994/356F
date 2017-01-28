package com.example.bunnyfung.a356f;

import com.example.bunnyfung.a356f.Object.Account;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
    private Account acc;
    private ImageView photo;
    private TextView name, band, type, size, price, Description;
    private Button submit, cancel;
    private CheckBox halfSize;
    FragmentTransaction fragTransaction;
    Fragment frag;
    private String stu ="";
    private static final int SELECTED_PICTURE = 1;


    // constructor
    public FragNewPost(Account acc){this.acc=acc;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frag_new_post, null);

        // test of get user information
        try{
            System.out.print("FragNewPost: "+ acc.passToJsonObjectStr());
        }catch(JSONException e){
            e.printStackTrace();
        }

        // initialized the item in layout
        photo = (ImageView) rootView.findViewById(R.id.ivBigPhoto);
        name = (TextView) rootView.findViewById(R.id.edtName);
        band = (TextView) rootView.findViewById(R.id.tfBrand);
        type = (TextView) rootView.findViewById(R.id.tfType);
        size = (TextView) rootView.findViewById(R.id.tfSize);
        price = (TextView) rootView.findViewById(R.id.tfPrice);
        Description = (TextView) rootView.findViewById(R.id.tfDescription);
        submit = (Button) rootView.findViewById(R.id.btnPostSubmit);
        cancel = (Button) rootView.findViewById(R.id.btnPostCancel);
        halfSize = (CheckBox) rootView.findViewById(R.id.cbSize);

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        return rootView;
    }


    // not modify
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
