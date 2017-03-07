package com.example.bunnyfung.a356f.Connection;

import com.example.bunnyfung.a356f.Object.Offer;

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("POST","Login",null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject getProduct(){

        try {
            url = new URL("http://s356fproject.mybluemix.net/api/list/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("GET","getProduct",null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject addOffer(Offer offer){

        try {
            url = new URL("http://s356fproject.mybluemix.net/api/addoffer");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("POST","addOffer", offer);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;

    }



    public void queryServer(final String method, final String action, final Offer offer) {
        resultObject = null;
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    System.out.println("acc JsonObject: " + acc.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(method);
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setConnectTimeout(20000);
                    connection.setReadTimeout(20000);


                    if (method.equals("POST")) {
                        connection.setDoOutput(true);
                        connection.setDoInput(true);
                        connection.setUseCaches(false);
                    }


                    //Testing Log
                    System.out.println("URL:" + url.toString());


                    if (method.equals("POST")) {
                        OutputStream os = connection.getOutputStream();

                        switch (action){
                            case "addOffer":
                                //Testing Log
                                System.out.println("queryServer jsonObj: " + offer.toString());

                                os.write(offer.toString().getBytes("UTF-8"));
                                break;

                            case "Login":
                                //Testing Log
                                System.out.println("queryServer jsonObj: " + acc.toString());

                                os.write(acc.toString().getBytes("UTF-8"));
                                break;
                        }
                        os.close();
                    }


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

                        switch (action){
                            case "Login":
                                //Testing Log
                                System.out.println("" + sb.toString());
                                resultObject = new JSONObject(sb.toString());

                                //Testing Log
                                System.out.println("responesJsonObject" + resultObject);
                                break;

                            case "getProduct":
                                //Testing Log
                                System.out.println(sb.toString());
                                System.out.println("sb Length: "+ sb.length());

                                String sbStr = "{products:"+sb+"}";
                                resultObject = new JSONObject(sbStr);
                                break;

                            case "addOffer":
                                //Testing Log
                                System.out.println("" + sb.toString());

                                resultObject = new JSONObject(sb.toString());
                                System.out.println("responesStatud: "+resultObject.getString("status"));
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
