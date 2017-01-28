package com.example.bunnyfung.a356f;
import com.example.bunnyfung.a356f.Object.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONException;
import org.json.JSONException;

public class FragMyProduct extends Fragment {
    public ListView listView;
    public ArrayAdapter<String> arrayAdapter;
    View view;
    String[] values = new String[]{
            "Apple",
            "Banana",
            "Cat",
            "Dog"
    };
    //contructor
    public FragMyProduct() {}


public class FragMyProduct extends Fragment {
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Account acc = null;
    String[] values = new String[]{"one","two","three"};


    public FragMyProduct() {}
    public FragMyProduct(Account acc){this.acc = acc;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_frag_my_product, null);
        
        // testing of get user information
        try {
            System.out.println("FragMyProduct: "+acc.passToJsonObjectStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) rootView.findViewById(R.id.myPostList);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        listView.setAdapter(arrayAdapter);

        // function of click item in list view

        view = inflater.inflate(R.layout.fragment_frag_my_product, null);
        listView = (ListView) view.findViewById(R.id.myPostList);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),parent.getItemAtPosition(position)+ " is select",Toast.LENGTH_LONG).show();
            }
        });

    }

}
