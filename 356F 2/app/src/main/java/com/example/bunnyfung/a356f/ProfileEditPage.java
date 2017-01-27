package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Account;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEditPage extends AppCompatActivity {
    private Account acc;
    private TextView tvUserid;
    private EditText edtName, edtPhoneNum;
    private RadioButton rbM, rbF;
    private ImageView ivIcon;
    private Button btnCancel,btnSubmit;
<<<<<<< HEAD
    private String stu ="";
    private static final int SELECTED_PICTURE = 1;
=======
>>>>>>> parent of 55bf23c... ProfileEditPage work

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_page);

        String str = getIntent().getStringExtra("acc");
        try {
            JSONObject jsonObject = new JSONObject(str);
            acc = new Account(jsonObject);

            System.out.println(acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvUserid = (TextView) findViewById(R.id.tvUserid);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhoneNum = (EditText) findViewById(R.id.edtPhoneNum);
        rbF = (RadioButton) findViewById(R.id.rbF);
        rbM = (RadioButton) findViewById(R.id.rbM);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        edtName.setText(acc.getName());
        tvUserid.setText(acc.getUserid());
        edtPhoneNum.setText(acc.getPhone());
        ivIcon.setImageBitmap(acc.getIcon());

        if (!(acc.getSex().equals(""))){
            if (acc.getSex().equals("M")){
                rbM.setChecked(true);
            }else
                rbF.setChecked(true);
        }

        rbF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbM.isChecked()){
                    rbM.setChecked(false);
                }
            }
        });

        rbM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbF.isChecked()){
                    rbF.setChecked(false);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acc.setName(edtName.getText().toString());
                acc.setPhone(edtPhoneNum.getText().toString());
                if (rbF.isChecked()){
                    acc.setSex("F");
                }else if (rbM.isChecked()){
                    acc.setSex("M");
                }

                try {
                    System.out.println(acc.passToJsonObjectStr());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });






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
                    ivIcon.setImageBitmap(selectedImag);
                    acc.setIcon(selectedImag);
                }
        }
    }
}
