package com.tiffin.tiffinqnq;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {
    TextInputLayout t1, t2, t3,t4;
    EditText name, email, password, confirmPassword,phone;
    Button register, signIn,btn;
    FirebaseAuth auth;
    FirebaseFirestore dbroot;
    String userID,imageurl,month;
    Spinner designation;
    Fragment  temp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //t1 = findViewById(R.id.loginscreen);
        t3 = (TextInputLayout) findViewById(R.id.name);
        t1 = (TextInputLayout) findViewById(R.id.email);
        t2 = (TextInputLayout) findViewById(R.id.password);
        t4 = (TextInputLayout) findViewById(R.id.phoneNo);
        //confirmPassword = findViewById(R.id.confirmPassword);
        register = findViewById(R.id.update);
        signIn=findViewById(R.id.SignIn);
        // signUp = findViewById(R.id.);
        auth = FirebaseAuth.getInstance();
        //userID = auth.getCurrentUser().getUid();
        dbroot = FirebaseFirestore.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        imageurl="";

        if (auth.getCurrentUser() != null) {
            //getSupportFragmentManager().beginTransaction().add(android.R.id.content, new homefragment()).commit();// this launches homefragment
            startActivity(new Intent(LogIn.this, MainActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name = t3.getEditText().getText().toString();
                String email = t1.getEditText().getText().toString();
                String password = t2.getEditText().getText().toString();
                String phone = t4.getEditText().getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)||TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)) {
                    Toast.makeText(LogIn.this, "empty credential", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(LogIn.this, "password must be greater than 6 character", Toast.LENGTH_SHORT).show();
                } else {
                    auth = FirebaseAuth.getInstance();
                    //progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = auth.getCurrentUser().getUid();
                                FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                                //DocumentReference documentReference=fstore.collection("users").document(userID);
                                DocumentReference documentReference = fstore.collection("users").document(userID);
                                Map<String, Object> user1 = new HashMap<>();
                                user1.put("January 2023", month);
                                documentReference.collection("events").document("dinner").set(user1).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(Task<Void> task) {

                                            }
                                        });
                                documentReference.collection("events").document("lunch").set(user1).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(Task<Void> task) {

                                            }
                                        });
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", name);
                                user.put("email", email);
                                user.put("phone", phone);
                                user.put("assignment", "");
                                user.put("designation", "");
                                user.put("imageUrl", imageurl);
                                user.put("timestamp", FieldValue.serverTimestamp());
                                user.put("userid",userID);
                                //dbroot was in place of documentreference
                                documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {

                                        Toast.makeText(LogIn.this, "Details updated successfully", Toast.LENGTH_SHORT).show();

                                        //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    }
                                });
                                t3.getEditText().setText("");
                                t1.getEditText().setText("");
                                t2.getEditText().setText("");
                                t4.getEditText().setText("");
                                Toast.makeText(getApplicationContext(), "sign up success", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(getApplicationContext(),CustomCalendar.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "sign up failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    //userID = auth.getCurrentUser().getUid();
                    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                    //DocumentReference documentReference=fstore.collection("users").document(userID);

//                    DocumentReference documentReference2 = fstore.collection("users").document(userID).
//                            collection("events").document("lunch");


                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterByEmail.class));
            }
        });

    }
}