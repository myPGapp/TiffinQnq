package com.tiffin.tiffinqnq;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OtpUpdateProfile extends AppCompatActivity {
    CheckBox male,female,student,working;
    EditText name, address, pincode;
    Button save, skip;
    FirebaseAuth auth;
    FirebaseFirestore dbroot;
    String phone,token,userID,imageurl,currentvendorid,month,sex,profession,phoneNo,name2,profession2,sex2,pincode2,phone2,address2,imageurl2;
    Fragment  temp;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_update_profile);
        //String phone=getIntent().getStringExtra("phone");
        System.out.println(phone+"l..");
        auth = FirebaseAuth.getInstance();
        dbroot=FirebaseFirestore.getInstance();
        userID=auth.getCurrentUser().getUid();
        System.out.println("..kk");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String phone = user.getPhoneNumber();
        String email = user.getEmail();
        System.out.println(phone);

        name = findViewById(R.id.name);
        address =findViewById(R.id.address);
        pincode =  findViewById(R.id.pincode);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        student = findViewById(R.id.student);
        working = findViewById(R.id.working);
        skip=findViewById(R.id.skip);
        save=findViewById(R.id.save);
        auth = FirebaseAuth.getInstance();
        dbroot = FirebaseFirestore.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        imageurl="";
        auth = FirebaseAuth.getInstance();

        userID = auth.getCurrentUser().getUid();

        FirebaseFirestore fstore=FirebaseFirestore.getInstance();
        DocumentReference docId = fstore.collection("Vendors").document("currentvendorid");
        docId.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        currentvendorid=document.getString("currentvendorid");


                    }
                }
            }
        });

        if(userID !=null)
        {

            DocumentReference docIdRef = fstore.collection("users").document(userID);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name1=document.getString("name");
                            String profession1=document.getString("profession");
                            String address1=document.getString("address");
                            String pincode1=document.getString("pincode");
                            String sex1=document.getString("sex");
                            if(sex1.equals("male")){
                                male.setChecked(true);
                            }
                            if(sex1.equals("female")){
                                female.setChecked(true);
                            }
                            if(profession1.equals("student")){
                                student.setChecked(true);
                            }
                            if(profession1.equals("working")){
                                working.setChecked(true);
                            }
                            name.setText(name1);
                            address.setText(address1);
                            pincode.setText(pincode1);
                            //(!document.get("name").equals("") || !document.get("name").equals(null))

                        }
                    }
                }
            });
        }
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    female.setEnabled(false);
                    sex="male";
                }else {
                    female.setEnabled(true);

                }

            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    male.setEnabled(false);
                    sex="female";
                }else {
                    male.setEnabled(true);

                }

            }
        });
        student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    working.setEnabled(false);
                    profession="student";
                }else {
                    working.setEnabled(true);

                }

            }
        });
        working.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    student.setEnabled(false);
                    profession="working";
                }else {
                    student.setEnabled(true);

                }

            }
        });

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()) {
                    token = task.getResult();
                    Log.i("token",token);


                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                progressBar.setVisibility(View.VISIBLE);
                String name1 = name.getText().toString();
                String address1 = address.getText().toString();
                String pin = pincode.getText().toString();
                if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(address1)||TextUtils.isEmpty(pin)) {
                    Toast.makeText(OtpUpdateProfile.this, "please add details ", Toast.LENGTH_SHORT).show();
                } else if (pin.length() != 6) {
                    Toast.makeText(OtpUpdateProfile.this, "pin code must be  of 6 digit No.", Toast.LENGTH_SHORT).show();
                } else {
                    auth = FirebaseAuth.getInstance();

                    userID = auth.getCurrentUser().getUid();
                    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = fstore.collection("users").document(userID);
                    Map<String, Object> user1 = new HashMap<>();
                   CollectionReference reference= documentReference.collection("events");
                    reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.isEmpty()){
                                user1.put("January 2023", month);
                                documentReference.collection("events").document("dinner").set(user1).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                                documentReference.collection("events").document("lunch").set(user1).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(Task<Void> task) {

                                            }
                                        });
                                documentReference.collection("absent").document("dinner").set(user1).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                                documentReference.collection("absent").document("lunch").set(user1).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(Task<Void> task) {

                                            }
                                        });


                            }
                        }
                    });

                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name1);
                        user.put("address", address1);
                        user.put("pincode", pin);
                        user.put("phoneNo", phone);
                        user.put("sex", sex);
                        user.put("profession",profession);
                        user.put("imageUrl", imageurl);
                        user.put("timestamp", FieldValue.serverTimestamp());
                        user.put("userid",userID);
                        user.put("vendorId",currentvendorid);
                        user.put("foodpreference", Arrays.asList("North Indian"));
                        user.put("token",token);

                        //dbroot was in place of documentreference
                        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {

                                Toast.makeText(OtpUpdateProfile.this, "Details updated successfully", Toast.LENGTH_SHORT).show();

                            }
                        });

                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(getApplicationContext(),DatePicker1.class));
                        finish();


                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name1 = name.getText().toString();
                String address1 = address.getText().toString();
                String pin = pincode.getText().toString();

                    auth = FirebaseAuth.getInstance();
                    userID = auth.getCurrentUser().getUid();
                    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = fstore.collection("users").document(userID);
                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("January 2023", month);



                     CollectionReference reference= documentReference.collection("events");
                     reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                         @Override
                         public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                             if(queryDocumentSnapshots.isEmpty()){
                                 user1.put("January 2023", month);
                                 documentReference.collection("events").document("dinner").set(user1).
                                         addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {

                                             }
                                         });
                                 documentReference.collection("events").document("lunch").set(user1).
                                         addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(Task<Void> task) {

                                             }
                                         });

                             }
                         }
                     });
                reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()){
                            user1.put("January 2023", month);
                            documentReference.collection("absent").document("dinner").set(user1).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                            documentReference.collection("absent").document("lunch").set(user1).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(Task<Void> task) {

                                        }
                                    });

                        }
                    }
                });
                     DocumentReference docIdRef = fstore.collection("users").document(userID);
                     docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                         @Override
                         public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<DocumentSnapshot> task) {
                             if (task.isSuccessful()) {
                                 DocumentSnapshot document = task.getResult();
                                 if (document.exists()) {
                                     if (!document.get("name").equals("")|| !document.get("name").equals(null)){
                                         name2= (String) document.get("name");

                                     }

                                     else {
                                         name2="";
                                     }
                                     if (!document.get("profession").equals("") || !document.get("profession").equals(null)){
                                         profession2= (String) document.get("profession");

                                     }

                                     else {
                                         profession2="";
                                     }
                                     if (!document.get("address").equals("")|| !document.get("address").equals(null)){
                                         address2= (String) document.get("address");

                                     }

                                     else {
                                         address2="";
                                     }
                                     if (!document.get("imageUrl").equals("") || !document.get("imageUrl").equals(null)){
                                         imageurl2= (String) document.get("imageUrl");

                                     }

                                     else {
                                         imageurl2="";
                                     }

                                     if (!document.get("pincode").equals("")|| !document.get("pincode").equals(null)){
                                         pincode2= (String) document.get("pincode");

                                     }

                                     else {
                                         pincode2="";
                                     }
                                     if (!document.get("sex").equals("")|| !document.get("sex").equals(null)){
                                         sex2= (String) document.get("sex");

                                     }

                                     else {
                                         sex2="";
                                     }
                                     Map<String, Object> user = new HashMap<>();
                                     user.put("name", name2);
                                     user.put("address", address2);
                                     user.put("pincode", pincode2);
                                     user.put("phoneNo", phone);
                                     user.put("sex", sex2);
                                     user.put("profession",profession2);
                                     user.put("imageUrl", imageurl2);
                                     user.put("timestamp", FieldValue.serverTimestamp());
                                     user.put("userid",userID);
                                     user.put("vendorId",currentvendorid);
                                     user.put("foodpreference", Arrays.asList("North Indian"));
                                     user.put("token",token);

                                     documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(Task<Void> task) {

                                             Toast.makeText(OtpUpdateProfile.this, "Details updated successfully", Toast.LENGTH_SHORT).show();

                                         }
                                     });
                                     progressBar.setVisibility(View.INVISIBLE);
                                     startActivity(new Intent(getApplicationContext(),CustomCalendar.class));
                                     finish();

                                 }
                                 else{
                                     Map<String, Object> user = new HashMap<>();
                                     user.put("name", "");
                                     user.put("address", "");
                                     user.put("pincode", "");
                                     user.put("phoneNo", phone);
                                     user.put("sex", "");
                                     user.put("profession","");
                                     user.put("imageUrl", "");
                                     user.put("timestamp", FieldValue.serverTimestamp());
                                     user.put("userid",userID);
                                     user.put("vendorId",currentvendorid);
                                     user.put("foodpreference", Arrays.asList("North Indian"));
                                     user.put("token",token);

                                     //dbroot was in place of documentreference
                                     documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(Task<Void> task) {

                                             //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                         }
                                     });
                                     progressBar.setVisibility(View.INVISIBLE);
                                     startActivity(new Intent(getApplicationContext(),CustomCalendar.class));
                                     finish();
                                 }
                             }
                         }
                     });

            }
        });
    }
}