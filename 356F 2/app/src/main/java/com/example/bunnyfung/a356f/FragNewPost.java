package com.example.bunnyfung.a356f;


import com.example.bunnyfung.a356f.Object.Account;
import android.content.Context;
import android.net.Uri;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class FragNewPost extends Fragment {
    private Account acc;
    // constructor
    public FragNewPost() {}
    public FragNewPost(Account acc){this.acc=acc;}

public class FragNewPost extends Fragment {
    private ListView listView;
    View view;
    //constructor
    public FragNewPost() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_new_post, null);
        return view;

    }


}
