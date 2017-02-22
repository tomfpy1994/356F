package com.example.bunnyfung.a356f.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by BunnyFung on 16/2/2017.
 */

public class Connection {
    private String email = "";
    private String pw = "";
    private JSONObject resultObject = null;
    private JSONObject acc = new JSONObject();
    private URL url;

    public Connection() {}


    public JSONObject Login(String email, String pw){
        String method = "";
        try {
            this.email = email;
            this.pw = pw;

            try {
                acc.put("userid", email);
                acc.put("pw", pw);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            url = new URL("http://s356fproject.mybluemix.net/api/login");
            method = "POST";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer(method);

        while (resultObject == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }



    public void queryServer(final String method) {
        resultObject = null;
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    System.out.println("acc JsonObject: " + acc.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(method);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);

                    //Testing Log
                    System.out.println("URL:" + url.toString());
                    String strJsonobj = acc.toString();
                    System.out.println("queryServer jsonObj: " + strJsonobj);

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
                        resultObject = new JSONObject(sb.toString());
                        System.out.println("responesJsonObject" + resultObject);
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
