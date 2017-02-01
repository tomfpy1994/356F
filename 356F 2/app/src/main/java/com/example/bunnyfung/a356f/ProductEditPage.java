package com.example.bunnyfung.a356f;

import android.content.Intent;
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

public class ProductEditPage extends AppCompatActivity {
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

    //constructor
    public ProductEditPage(Account acc,Post post){
        this.acc=acc;
        this.post=post;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit_page);
        // initialized the item in layout
        photo = (ImageView) findViewById(R.id.ivBigPhoto);
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    sizeNum+=0.5;
                    System.out.println("check box is select");
                }else{
                    sSize = size.getText().toString();
                    sizeNum = Integer.valueOf(sSize);
                }
            }

        });
    }
}
