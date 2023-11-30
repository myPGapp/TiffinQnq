package com.tiffin.tiffinqnq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DropdownComplaint extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private static final String[] paths = {"Select", "Food Delivery", "Food Quality","Food Quantity","Payment","Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown_complaint);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(DropdownComplaint.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                Intent intent=new Intent(getApplicationContext(),ComplaintDelivery.class);
                startActivity(intent);
                finish();
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                Intent intent2=new Intent(getApplicationContext(),ComplaintQuality.class);
                startActivity(intent2);
                finish();
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 3:
                Intent intent3=new Intent(getApplicationContext(),ComplaintQuantity.class);
                startActivity(intent3);
                finish();
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 4:
                Intent intent4=new Intent(getApplicationContext(),ComplaintPayments.class);
                startActivity(intent4);
                finish();
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 5:
                Intent intent5=new Intent(getApplicationContext(),ComplaintOthers.class);
                startActivity(intent5);
                finish();
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

}