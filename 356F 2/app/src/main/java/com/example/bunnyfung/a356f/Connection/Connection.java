package com.example.bunnyfung.a356f.Connection;

import android.util.Log;

import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.Object.Post;
import com.example.bunnyfung.a356f.Object.WishList;

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



    //Account Methods

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
        queryServer("POST","Login",null, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject getOneAccount(String _id){

        try {
            url = new URL("http://s356fproject.mybluemix.net/api/listac/_id/"+_id);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("GET","ListAccount",null, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject updateAcc(Account acc){
        String urlStr = "http://s356fproject.mybluemix.net/api/updateac";
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("POST","updateAcc", null, null, acc, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }



    //Product Methods

    public JSONObject getProduct(){

        try {
            url = new URL("http://s356fproject.mybluemix.net/api/list/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("GET","getProduct",null, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject getOneProduct(String prodID){

        String urlStr = "http://s356fproject.mybluemix.net/api/list/_id/"+prodID;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("GET","getProduct", null, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject updatePost(Post post){
        String urlStr = "http://s356fproject.mybluemix.net/api/updateproduct";
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("POST","updatePost", null, post, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }



    // Offer methods
    public JSONObject addOffer(Offer offer){

        try {
            url = new URL("http://s356fproject.mybluemix.net/api/addoffer");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("POST","addOffer", offer, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;

    }

    public JSONObject listOffer(Account acc, int stat){

        String urlStr = "http://s356fproject.mybluemix.net/api/listoffer/?";
        urlStr = urlStr+"/"+acc.getId()+ "/"+'"'+stat+'"';
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("GET","listOffer", null, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject getOneOffer(String offerid){

        String urlStr = "http://s356fproject.mybluemix.net/api/listoffer/offerid/"+offerid;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("GET","listOffer", null, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject updateOffer(Offer offer){
        String urlStr = "http://s356fproject.mybluemix.net/api/updateoffer";
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("POST","updateOffer", offer, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject dealOffer(Offer offer, Post post, Account buyer, Account seller){

        try {
            //update offer
            this.resultObject = updateOffer(offer);
            if (resultObject.getString("status").equals("update offer success")){
                //update post
                this.resultObject = updatePost(post);
                if (resultObject.getString("status").equals("update product success")){
                    //update buyer acc
                    this.resultObject = updateAcc(buyer);
                    if (resultObject.getString("status").equals("update success")){
                        //update seller acc
                        this.resultObject = updateAcc(seller);
                        if (resultObject.getString("status").equals("update success")){
                            return resultObject;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultObject;
    }


    //WishList Method
    public JSONObject addWishList(WishList wishList){
        try{
            url = new URL("http://s356fproject.mybluemix.net/api/addwishlist");
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

        queryServer("POST","addWishList", null, null, null, wishList);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return resultObject;
    }

    public JSONObject listWishList(){

        String urlStr = "http://s356fproject.mybluemix.net/api/listwishlist";
        //urlStr = urlStr+"/'_id'/"+acc.getId();
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("GET","listWishList", null, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject deleteWishList(WishList wishList){
        String urlStr = "http://s356fproject.mybluemix.net/api/delwishlist";
        // urlStr = urlStr+"/'productID'/"+wishList.getProductId()+ "/'uid'/"+wishList.getId();
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("POST","deleteWishList", null, null, null, wishList);
        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }

    public JSONObject getWishList(WishList wishList){

        String urlStr = "http://s356fproject.mybluemix.net/api/listwishlist/?";
        urlStr = urlStr+"/uid/" +wishList.getUid();
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        queryServer("GET","getWishList", null, null, null, null);

        while (resultObject == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultObject;
    }



    public void queryServer(final String method, final String action, final Offer offer, final Post post, final Account tmpacc,final WishList wishList) {
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
                                os.write(offer.passToJsonObjectStr().getBytes("UTF-8"));
                                break;

                            case "Login":
                                //Testing Log
                                System.out.println("queryServer jsonObj: " + acc.toString());
                                os.write(acc.toString().getBytes("UTF-8"));
                                break;

                            case "updateOffer":
                                //Testing Log
                                System.out.println("queryServer jsonObj: " + offer.toString());
                                os.write(offer.passToJsonObjectStr().getBytes("UTF-8"));
                                break;

                            case "updatePost":
                                //Testing Log
                                System.out.println("queryServer jsonObj: " + post.toString());
                                os.write(post.passToJsonObjectStr().getBytes("UTF-8"));
                                break;

                            case "updateAcc":
                                //Testing Log
                                System.out.println("queryServer jsonObj: " + tmpacc.toString());
                                os.write(tmpacc.passToJsonObjectStr().getBytes("UTF-8"));
                                break;

                            case "addWishList":
                                //Testing Log
                                System.out.println("queryServer jsonObj: " + wishList.toString());
                                os.write(wishList.passToJsonObjectStr().getBytes("UTF-8"));
                                break;

                            case "deleteWishList":
                                System.out.println("queryServer jsonObj: " + wishList.toString());
                                os.write(wishList.passToJsonObjectStr().getBytes("UTF-8"));
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

                        System.out.println(sb.toString());
                        switch (action){
                            case "Login":
                                resultObject = new JSONObject(sb.toString());

                                //Testing Log
                                System.out.println("responesJsonObject" + resultObject);
                                break;

                            case "getProduct":
                                System.out.println("sb Length: "+ sb.length());

                                String sbStr = "{products:"+sb+"}";
                                resultObject = new JSONObject(sbStr);
                                break;

                            case "addOffer":
                                resultObject = new JSONObject(sb.toString());
                                System.out.println("responesStatud: "+resultObject.getString("status"));
                                break;

                            case  "listOffer":
                                System.out.println("sb Length: "+ sb.length());

                                String sbStr1 = "{offers:"+sb+"}";
                                resultObject = new JSONObject(sbStr1);
                                break;

                            case "updateOffer":
                                resultObject = new JSONObject(sb.toString());
                                System.out.println("responesStatud: "+resultObject.getString("status"));
                                break;

                            case "ListAccount":
                                System.out.println("sb Length: "+ sb.length());

                                String sbStr2 = "{account:"+sb+"}";
                                resultObject = new JSONObject(sbStr2);
                                break;

                            case "updatePost":
                                resultObject = new JSONObject(sb.toString());
                                System.out.println("responesStatud: "+resultObject.getString("status"));
                                break;

                            case "updateAcc":
                                resultObject = new JSONObject(sb.toString());
                                System.out.println("responesStatud: "+resultObject.getString("status"));
                                break;

                            case "addWishList":
                                resultObject = new JSONObject(sb.toString());
                                System.out.println("responesStatud: "+resultObject.getString("status"));
                                break;

                            case "listWishList":
                                System.out.println("sb Length: "+ sb.length());
                                System.out.println("WishList: " + sb);

                                String sbStr3 = "{wishLists:"+sb+"}";
                                resultObject = new JSONObject(sbStr3);
                                break;

                            case "deleteWishList":
                                resultObject = new JSONObject(sb.toString());
                                System.out.println("responesStatud: "+resultObject.getString("status"));
                                break;

                            case "getWishList":
                                System.out.println("sb Length: "+ sb.length());
                                System.out.println("WishList: " + sb);
                                String sbStr4 = "{wishList:"+sb+"}";
                                resultObject = new JSONObject(sbStr4);
                                break;
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
