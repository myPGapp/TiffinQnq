package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VendorIdSpinner extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> list2=new ArrayList<>();
    ArrayAdapter<String> adapter;
    private Spinner spinner;
    FirebaseFirestore fstore;
    FirebaseAuth auth;
    String userID;
    private  String[] vendorlist = {"Please select vendor id","0001", "0002", "0003","0004","0005","0006",
            "0007","0008","0009","0010","0011","0012","0013","0014","0015","0016","0017","0018","0019","0020"};


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_id_spinner);
        auth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        //userID= getIntent().getStringExtra("uid").toString();
        spinner = (Spinner)findViewById(R.id.vendorSpinner);
        list2.addAll(Arrays.asList(vendorlist));
//        list2.remove(paths[1]);
//        System.out.println(list2.get(1));
        adapter = new ArrayAdapter<String>(VendorIdSpinner.this, android.R.layout.simple_spinner_item,list2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0) {
            String vendorid = (String) parent.getItemAtPosition(position);
            System.out.println("position.." + position + ".." + vendorid);
             Intent intent = new Intent(this, RvUserswithid.class);
            intent.putExtra("vendorid", vendorid);
            startActivity(intent);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}