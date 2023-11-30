package com.tiffin.tiffinqnq;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterByEmail extends AppCompatActivity {
    TextInputLayout t1;
    TextInputLayout t2;
    Button fp,btn;
    FirebaseAuth auth;
    FirebaseFirestore dbroot;
    String userID;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        t1 = (TextInputLayout) findViewById(R.id.email);
        t2 = (TextInputLayout)findViewById(R.id.password);
        btn=findViewById(R.id.button2);
        fp=findViewById(R.id.fpassword);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        //register = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();
        dbroot = FirebaseFirestore.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterByEmail.this,LogIn.class));
                finish();
            }
        });
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
                finish();
            }
        });


    }

    public void signinhere(View view) {

        String email= t1.getEditText().getText().toString();
        String password=t2.getEditText().getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterByEmail.this, "empty credential", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(RegisterByEmail.this, "password must be greater than 6 character", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(RegisterByEmail.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(RegisterByEmail.this, MainActivity.class));
                            }
                            else{
                                t1.getEditText().setText("");
                                t2.getEditText().setText("");
                                Toast.makeText(getApplicationContext(), "login failed, retry", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}