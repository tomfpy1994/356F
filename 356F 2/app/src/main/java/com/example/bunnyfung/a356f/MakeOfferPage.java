package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bunnyfung.a356f.Connection.Connection;
import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Offer;
import com.example.bunnyfung.a356f.Object.Post;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakeOfferPage extends AppCompatActivity {
    private Account acc, ownerAcc;
    private Post post;
    private ImageView ivPhoto1;
    private TextView tvTitle, tvPrice, tvName, tvBrand, tvType, tvSize;
    private EditText edtDateTime, edtPlace, edtPrice;
    private Button btnSubmit;
    private final String doller = "$";
    private final String sizeUnit = "US ";
    private SimpleDateFormat mFormatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
    private SimpleDateFormat dbDateFormatter = new SimpleDateFormat("ddMMyyyyhhmm");
    private String strDate ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer_page);


        Intent intent = getIntent();
        try {
            //JSONObject jsonObject = new JSONObject(intent.getStringExtra("post"));
            //post = new Post(jsonObject);

            String postId = intent.getStringExtra("postId");
            Connection conn = new Connection();
            JSONObject jsonObject = conn.getOneProduct(postId);
            JSONArray jsonArray = jsonObject.getJSONArray("products");

            post = new Post(jsonArray.getJSONObject(0));

            JSONObject accJsonObject = new JSONObject(intent.getStringExtra("acc"));
            acc = new Account(accJsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ivPhoto1 = (ImageView) findViewById(R.id.ivPhoto1);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPrice = (TextView) findViewById(R.id.tvOfferedPrice);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        tvType = (TextView) findViewById(R.id.tvType);
        tvSize = (TextView) findViewById(R.id.tvSize);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        edtDateTime = (EditText) findViewById(R.id.edtDateTime);
        edtPlace = (EditText) findViewById(R.id.edtPlace);
        edtPrice = (EditText) findViewById(R.id.edtPrice);

        ivPhoto1.setImageBitmap(post.getPhoto());
        tvPrice.setText(doller + post.getPrice());
        tvName.setText(post.getName());
        tvBrand.setText(post.getBrand());
        tvType.setText(post.getType());
        tvSize.setText(sizeUnit + post.getSize());
        tvTitle.setText(post.getBrand()+" "+post.getName()+" US"+post.getSize());
        edtDateTime.setCursorVisible(false);

        edtDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .setMinDate(new Date())
                        //.setMaxDate(maxDate)
                        .setIs24HourTime(true)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                int price = 0;
                if (!edtPrice.getText().equals(""))
                    price = Integer.parseInt(edtPrice.getText().toString());

                try {
                    if (!strDate.equals("")) {
                        date = mFormatter.parse(strDate);
                        System.out.println(dbDateFormatter.format(date));
                        String dbStrDate = dbDateFormatter.format(date);

                        Offer offer = new Offer(post.getProductID(), post.getOwner(), acc.getId(),
                                acc.getUserid(), dbStrDate, edtPlace.getText().toString(),price,
                                post.getName(), post.getPhoto());
                        //Testing Log
                        System.out.println(offer.toString());
                        System.out.print(offer.passToJsonObjectStr());

                        Connection conn = new Connection();
                        JSONObject resultObject = conn.addOffer(offer);
                        System.out.println(resultObject.getString("status"));
                        if (resultObject.getString("status").equals("add success")){
                            finish();
                        }
                    }

                    //TODO: do server
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date)
        {
            strDate = mFormatter.format(date);
            edtDateTime.setText(strDate);
            Toast.makeText(getApplication(), strDate, Toast.LENGTH_SHORT).show();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
        }
    };
}
