package com.example.bunnyfung.a356f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bunnyfung.a356f.Object.Account;

import org.json.JSONException;
import org.json.JSONObject;


public class SearchPage extends AppCompatActivity {
    private RadioGroup rgroup;
    private RadioButton rb;
    private TextView t,searchInput;
    private Button btnSearch;
    private String chose, input;
    private Account acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        String str = getIntent().getStringExtra("acc");
        try {
            JSONObject jsonObject = new JSONObject(str);
            acc = new Account(jsonObject);

            System.out.println(acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(acc.toString());


        t = (TextView) findViewById(R.id.text);
        rgroup = (RadioGroup) findViewById(R.id.group);
        btnSearch = (Button) findViewById(R.id.btnGoToSearch);
        searchInput = (TextView) findViewById(R.id.tvSearch);


        btnSearch.setOnClickListener(go);
        rgroup.setOnCheckedChangeListener(listener);
    }
    private Button.OnClickListener go = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            input = searchInput.getText().toString();
            //t.setText(input);
            // go to result search
           // ResultOfSearch run = new ResultOfSearch(chose, input);
            Intent intent1 = new Intent(getApplication(), ResultOfSearch.class);
            try{
                intent1.putExtra("result", chose+"/"+input);
                //intent1.putExtra("input", input);
                intent1.putExtra("acc", acc.passToJsonObjectStr());
            } catch (Exception e){}

            startActivityForResult(intent1,0);

        }
    };
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int p = group.indexOfChild((RadioButton) findViewById(checkedId));
            int count = group.getChildCount();
            switch (checkedId) {
                case R.id.rbName:
                    t.setText("you select name");
                    chose = "pname";
                    break;
                case R.id.rbBrand:
                    t.setText("you select brand");
                    chose = "brand";
                    break;
                case R.id.rbPrice:
                    t.setText("you select price");
                    chose = "price";
                    break;
                case R.id.rbSize:
                    t.setText("you select size");
                    chose = "size";
                    break;
                case R.id.rbType:
                    t.setText("you select type");
                    chose = "ptype";
                    break;



            }

        }

    };
}
