package com.tiffin.tiffinqnq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomerSuggestion extends AppCompatActivity {
    Button btnpost,btnpostlater,btnshowsuggestion;
    FirebaseFirestore fstore;
    FirebaseAuth auth;
    ProgressBar progressBar;
    EditText description;
    ImageView img;
    String imageurl,userID,edtdescription,name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_suggestion);
        btnpost=findViewById(R.id.btnpost);
        btnpostlater=findViewById(R.id.btnpostlater);
        btnshowsuggestion=findViewById(R.id.btnshowsuggestion);
        progressBar=findViewById(R.id.progressBar);
//        Calendar calendar=Calendar.getInstance();
//        String currentDate= DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));
        description=findViewById(R.id.edtDescription);
        img=findViewById(R.id.img);
        imageurl="";
        auth = FirebaseAuth.getInstance();
        userID= auth.getCurrentUser().getUid();
        fstore=FirebaseFirestore.getInstance();

            FirebaseFirestore fstore=FirebaseFirestore.getInstance();
            DocumentReference docIdRef = fstore.collection("users").document(userID);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                             name=document.getString("name");

                            //(!document.get("name").equals("") || !document.get("name").equals(null))

                        }
                    }
                }
            });


        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtdescription=description.getText().toString();
                Map<String, Object> data = new HashMap<>();
                data.put("description", edtdescription);
                data.put("uid",userID);
                data.put("dateSuggestions",date);
                data.put("reply","");
                if(edtdescription.isEmpty()){
                    Toast.makeText(CustomerSuggestion.this, "Please add description first", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    DocumentReference docIdRef = fstore.collection("users").document(userID);
                    docIdRef.collection("suggestions").document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Toast.makeText(CustomerSuggestion.this, "feedback submitted..Thank you.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(CustomerSuggestion.this, "Thank You, Feedback submitted", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                }

            }
        });
        btnpostlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnshowsuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RvSuggestions.class);
                intent.putExtra("uid",userID);
                intent.putExtra("uname",name);
                startActivity(intent);
            }
        });

    }
}