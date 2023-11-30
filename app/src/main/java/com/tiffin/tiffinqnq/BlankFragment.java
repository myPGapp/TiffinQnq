package com.tiffin.tiffinqnq;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiffin.tiffinqnq.R;

import org.checkerframework.checker.nullness.qual.NonNull;

public class BlankFragment extends Fragment {
    Button btnbookmeal,btnpay,users;
    ImageButton btncall;
    ImageView imgtwitter,imgfb,imginsta,imglinkedin;
    String userID,count;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    TextView txtusercount;

    public BlankFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blank, container, false);
        btnbookmeal=view.findViewById(R.id.btnbookmeal);
        btnpay=view.findViewById(R.id.btnpay);
        imgfb=view.findViewById(R.id.fbLink);
        imgtwitter=view.findViewById(R.id.twitterLink);
        imginsta=view.findViewById(R.id.instagramLink);
        imglinkedin=view.findViewById(R.id.linkedinLink);
        btncall=view.findViewById(R.id.btncall);
        users=view.findViewById(R.id.usersNo);
        btnbookmeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),DatePicker1.class);
                startActivity(intent);

            }
        });
firestore=FirebaseFirestore.getInstance();
        firestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            int userscount=task.getResult().size();
                            count=(String.valueOf(userscount));

                            Log.d(TAG, "No. of users :"+ task.getResult().size());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

users.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        users.setText(count);

    }
});

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),PaymentActivityy.class);
                startActivity(intent);

            }
        });



        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+"9873080062"));
                startActivity(intent);

            }
        });
        imgfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openSocialMediaApp("com.facebook.katana","fb://page/102406278380608");
                //openFacebookIntent(getContext(), "102406278380608");
//                String uri = "fb://page/102406278380608";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
                //sharingToSocialMedia("com.facebook.katana","www.google.com");
            }
        });

        return view;


    }
    public static void openFacebookIntent(Context context, String facebookID) {

        Intent intent;

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/"+facebookID));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+facebookID));
        }

        context.startActivity(intent);
    }
    private void allclicklistener() {
        imgtwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharingToSocialMedia("com.twitter.android.lite","www.google.com");
            }
        });
        imgfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "fb://page/102406278380608";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                //sharingToSocialMedia("com.facebook.katana","www.google.com");
            }
        });
        imginsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharingToSocialMedia("com.instagram.android","www.google.com");

            }
        });
        imglinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharingToSocialMedia("com.linkedin.android","www.google.com");

            }
        });

    }
    private void sharingToSocialMedia(String application, String linkopen){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,linkopen);
        boolean installed=checkAppInstall(application);
        if(installed){
            intent.setPackage(application);
            startActivity(intent);

        }else {
            Toast.makeText(getContext(), "install application first", Toast.LENGTH_SHORT).show();
        }
    }
    private void openSocialMediaApp(String application, String linkopen){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,linkopen);
        boolean installed=checkAppInstall(application);
        if(installed){
            intent.setPackage(application);
            startActivity(intent);

        }else {
            Toast.makeText(getContext(), "install application first", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkAppInstall(String uri) {
        PackageManager pm= getContext().getPackageManager();
        try{
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            return true;
        }catch(PackageManager.NameNotFoundException e){
            return false;
        }
    }


}