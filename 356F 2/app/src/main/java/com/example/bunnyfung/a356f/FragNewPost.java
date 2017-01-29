package com.example.bunnyfung.a356f;

import com.example.bunnyfung.a356f.Object.Account;
import com.example.bunnyfung.a356f.Object.Post;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;

public class FragNewPost extends Fragment {
    public Account acc;
    public Post post;
    public ImageView photo;
    public TextView name, brand, type, size, price, description;
    public int sizeNum, priceNum;
    public Button submit, cancel;
    public CheckBox halfSize;
    FragmentTransaction fragTransaction;
    Fragment frag;
    public String stu ="";
    public String sName, sBand, sType, sSize,sPrice, sDescription;
    private static final int SELECTED_PICTURE = 1;
    View rootView;
    private String userID;

    // constructor
    public FragNewPost(Account acc){
        this.acc=acc;
        userID = acc.get_id();
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
                        sizeNum = Integer.parseInt(sSize);
                        priceNum = Integer.parseInt(sPrice);

                        System.out.println(sName+" "+sBand+" "+sType+" "+sizeNum+" "+priceNum+" "+sDescription);



                }

            }
        });


        return rootView;
    }




}
