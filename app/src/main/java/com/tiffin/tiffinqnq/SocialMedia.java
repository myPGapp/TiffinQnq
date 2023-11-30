package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SocialMedia extends AppCompatActivity {
    ImageView imgtwitter,imgfb,imginsta,imglinkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imgfb=findViewById(R.id.fbLink);
        imgtwitter=findViewById(R.id.twitterLink);
        imginsta=findViewById(R.id.instagramLink);
        imglinkedin=findViewById(R.id.linkedinLink);
        imgfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/102406278380608"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ParamGuruapp")));
                }
            }
        });
        imgtwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=sanjaysahani4u"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/sanjaysahani4u")));
                }
            }
        });
        imglinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://profile/shyamdeo-ranjan-7a5b26188"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/shyamdeo-ranjan-7a5b26188")));
                }
            }
        });
    }

}