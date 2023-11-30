package com.tiffin.tiffinqnq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.tiffin.tiffinqnq.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;

public class UploadProfileData extends AppCompatActivity {
    private EditText edtName, edtEmail, edtPhone, edtDesignation, edtAssignment;
    private Button SelectPhoto,btnhome;
    private ImageView ProfileImageView;
    private Uri ImageUri;
    private Bitmap bitmap;
    String pimg,fbdesignation,fbassignment,fbphonno,imageUrl;
    TextView textv;
    String l,pic;
    // views for button
    private Button btnSelect, btnUpload;
    private String CurrentUserId;
    // view for image view
    private ImageView imageView;
    private FirebaseFirestore firestore;
    String PhotoUrl;
    FirebaseAuth firebaseAuth;
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    private Button UploadProfileInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_data);
        firestore = FirebaseFirestore.getInstance();
        edtName = findViewById(R.id.edtName);;
        edtPhone = findViewById(R.id.edtPhone);
        edtDesignation = findViewById(R.id.edtDesignation);
        edtAssignment = findViewById(R.id.edtAssignment);
        SelectPhoto = findViewById(R.id.selectPhoto);
        ProfileImageView = findViewById(R.id.Image);
        UploadProfileInfoButton = findViewById(R.id.UploadProfileInfo);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserId = firebaseAuth.getCurrentUser().getUid();
        btnhome=findViewById(R.id.btnhome);
        imageUrl="https://firebasestorage.googleapis.com/v0/b/mydatabase-b7d66.appspot.com/o/images%2Fimage.png?alt=media&token=eec0fba9-2c34-43bf-ab27-13f06bb2b3c4";
        // initialise views

        // get the Firebase storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        // on pressing btnSelect SelectImage() is called
        DocumentReference docr=firestore.collection("users").document(CurrentUserId);
        docr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        pimg = document.getString("imageUrl").toString();
                        String name=document.getString("name");
                        fbassignment=document.getString("assignment");
                        fbdesignation=document.getString("designation");
                        fbphonno=document.getString("phone");
                        edtPhone.setText(fbphonno);
                        edtAssignment.setText(fbassignment);
                        edtDesignation.setText(fbdesignation);
                        edtName.setText(name);
                        //Toast.makeText(UploadPhoto.this, "oo   "+name, Toast.LENGTH_SHORT).show();
                        if(pimg==null || pimg==""){

                            Glide.with(getApplicationContext()).load(imageUrl).into(ProfileImageView);
                        }else {
                            Glide.with(getApplicationContext()).load(pimg).into(ProfileImageView);
                        }
                        //Glide.with(UploadProfileData.this).load(pimg).into(ProfileImageView);

                    } else{
                        Log.d("name", "No such document");
                    }
                } else{
                    Log.d("name", "get failed with ", task.getException());
                }

            }
        });

        SelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        // on pressing btnUpload uploadImage() is called

        UploadProfileInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(UploadProfileData.this, "Plz wait...", Toast.LENGTH_SHORT).show();

                uploadImage();
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomCalendar.class));
                finish();
            }
        });
    }


    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                ProfileImageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + filePath.getLastPathSegment());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            if (uri != null) {
                                                PhotoUrl = uri.toString();

                                                UploadUserInfo();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {

                                        }
                                    });


                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(UploadProfileData.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(UploadProfileData.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
        else {
            //Toast.makeText(UploadProfileData.this, "Plz wait...", Toast.LENGTH_SHORT).show();
            uploadData();


        }
    }
    private void UploadUserInfo() {

        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String designation = edtDesignation.getText().toString().trim();
        String assignment = edtAssignment.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(designation) || TextUtils.isEmpty(assignment)) {
            Toast.makeText(UploadProfileData.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            DocumentReference documentReference = firestore.collection("users").document(CurrentUserId);
            ProfileImageModel profileImageModel = new ProfileImageModel(PhotoUrl, name,  phone, designation, assignment);
            documentReference.set(profileImageModel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        if (task.isSuccessful()) {
                            documentReference.set(profileImageModel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(UploadProfileData.this, "Details updated Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UploadProfileData.this, MainActivity.class));
                                        finish();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadProfileData.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }

            });

        }
    }
    private  void uploadData(){
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String designation = edtDesignation.getText().toString().trim();
        String assignment = edtAssignment.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(designation) || TextUtils.isEmpty(assignment)) {
            Toast.makeText(UploadProfileData.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            DocumentReference documentReference = firestore.collection("users").document(CurrentUserId);
            ProfileImageModel profileImageModel = new ProfileImageModel(pimg, name,  phone, designation, assignment);
            documentReference.set(profileImageModel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        if (task.isSuccessful()) {
                            documentReference.set(profileImageModel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(UploadProfileData.this, "Details updated Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UploadProfileData.this, MainActivity.class));
                                        finish();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadProfileData.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }

            });

        }

    }

}
