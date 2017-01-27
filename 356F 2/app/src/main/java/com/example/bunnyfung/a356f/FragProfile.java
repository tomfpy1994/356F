package com.example.bunnyfung.a356f;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Account;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FragProfile extends Fragment {
    private Account acc = null;
<<<<<<< HEAD
    private Button btnLogout, btnEdit, btnMyPost, btnMyScore, btnHistory, btnWishList, btnSecurityCode, btnTransaction;

=======
    private Button btnLogout, btnEdit;
>>>>>>> parent of c7ae66e... 26/1
    private boolean logout = false;
    private TextView tvUserid, tvName, tvEmail;
    private ImageView ivIcon;

    public FragProfile(Account acc) {
        this.acc = acc;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_frag_profile, null);
        try {
            System.out.println("FragProfile: "+acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnEdit = (Button) rootView.findViewById(R.id.btnEdit);
<<<<<<< HEAD

        btnMyPost = (Button) rootView.findViewById(R.id.btnMyPost);
        btnMyScore = (Button) rootView.findViewById(R.id.btnMyScore);
        btnHistory = (Button) rootView.findViewById(R.id.btnHistory);
        btnWishList = (Button) rootView.findViewById(R.id.btnWishList);
        btnSecurityCode = (Button) rootView.findViewById(R.id.btnSecurityCode);
        btnTransaction = (Button) rootView.findViewById(R.id.btnTransaction);

        btnSecurityCode = (Button) rootView.findViewById(R.id.btnSecurityCode);

=======
>>>>>>> parent of c7ae66e... 26/1

        tvUserid = (TextView) rootView.findViewById(R.id.tvUserid);
        tvName = (TextView) rootView.findViewById(R.id.tvName);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);
        ivIcon = (ImageView) rootView.findViewById(R.id.ivIcon);

        if (acc!=null){
            tvUserid.setText(acc.getUserid());
            tvEmail.setText(acc.getEmail());
            tvName.setText(acc.getName());
            ivIcon.setImageBitmap(acc.getIcon());
        }


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogout();
                while (!logout){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Logout");

                Intent intent = new Intent(getActivity(), LoginPage.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProfileEditPage.class);
                try {
                    intent.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

<<<<<<< HEAD
        btnMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyPostPage.class);
                try {
                    intent.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        // go to my score page
        btnMyScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyScorePage.class);
                try {
                    intent.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        // go to my history page
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProfileHistoryPage.class);
                try {
                    intent.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        // go to wish list page
        btnWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WishListPage.class);
                try {
                    intent.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });


        btnSecurityCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SecurityCodePage.class);
                try {
                    intent.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        // go to transaction page
        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TransactionPage.class);
                try {
                    intent.putExtra("acc",acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

=======
>>>>>>> parent of c7ae66e... 26/1
        return rootView;
    }

    public void doLogout() {

        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("http://s356fproject.mybluemix.net/api/logout");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);


                    //Testing Log
                    System.out.println("URL:" + url.toString());


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
                        System.out.println(sb.toString());

                        JSONObject jsonObject = new JSONObject(sb.toString());
                        if (jsonObject.getString("status").equals("logout success")){
                            System.out.println("resultJsonObject: "+jsonObject.toString());
                            logout = true;
                        }

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
