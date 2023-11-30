package com.tiffin.tiffinqnq;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.datatransport.BuildConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiffin.tiffinqnq.Notification.NotSend;
import com.tiffin.tiffinqnq.SendNotification.NotificationActivity;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    BottomNavigationView bnav;
    //ProfileFragment profileFragment=new ProfileFragment();
    MealFragment homeFragment=new MealFragment();
    FragmentMeal fragmentMeal=new FragmentMeal();

    FragmentToUpdateProfile fragmentToUpdateProfile=new FragmentToUpdateProfile();
    BlankFragment blankFragment=new BlankFragment();
    BlankFragmentComplaint blankFragmentComplaint=new BlankFragmentComplaint();
    BlankFragmentSuggestion blankFragmentSuggestion=new BlankFragmentSuggestion();
    FragmentToLogout fragmentToLogout=new FragmentToLogout();
    RvFragmentUsers rvFragmentUsers=new RvFragmentUsers();
    RvFragmentwithID rvFragmentwithID=new RvFragmentwithID();
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FirebaseAuth auth;
    String appVersion,userID,imageurl,phone,name,sex;
    TextView nav_username1,nav_user_phone;
    ImageView imageView;
FirebaseFirestore firestore;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        userID=auth.getCurrentUser().getUid();
        Toolbar toolbar = (Toolbar) findViewById((R.id.toolbar));
        //setSupportActionBar(toolbar);
        nav = (NavigationView) findViewById(R.id.navmenu);
        if(userID !=null){


            firestore=FirebaseFirestore.getInstance();


            DocumentReference docRef = firestore.collection("users").document(userID);

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            imageurl=document.getString("imageUrl");
                            phone=document.getString("phoneNo");
                            name=document.getString("name");
                            sex=document.getString("sex");
                            System.out.println("sanjayaa");
                            //(!document.get("name").equals("") || !document.get("name").equals(null))
                            if(!imageurl.equals("")){
                                Glide.with(getApplicationContext()).load(imageurl).into((imageView) );
                            }else{
                                if(sex.equals("male")){
                                    Glide.with(getApplicationContext()).load(R.drawable.image).into((imageView) );
                                }else{
                                    Glide.with(getApplicationContext()).load(R.drawable.female).into((imageView) );
                                }

                            }
                            nav_username1.setText(name);
                            nav_user_phone.setText(phone);
                        }
                    }
                }
            });
        }
        System.out.println("sanjaya");
        System.out.println(name+" "+phone+"/"+imageurl);
        View header = nav.getHeaderView(0);
        nav_username1 = (TextView) header.findViewById(R.id.nav_username1);
        nav_user_phone=(TextView) header.findViewById(R.id.nav_user_phone);
        imageView=(ImageView) header.findViewById(R.id.nav_user_photo);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UploadProfileImage.class);
                startActivity(intent);
            }
        });

        bnav=(BottomNavigationView)findViewById(R.id.bottomNav);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new BlankFragment()).commit();//this is default fragment on app launch
        String versionName = BuildConfig.VERSION_NAME;
        int versionCode = BuildConfig.VERSION_CODE;
        //Toast.makeText(this, versionCode, Toast.LENGTH_SHORT).show();
        try {
            String appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            NavigationView navigation = this.findViewById(R.id.navmenu);
            Menu menu = navigation.getMenu();
            menu.findItem(R.id.appVersion).setTitle("App Version:  "+appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        nav.setCheckedItem(R.id.menu_home);//this line don't understand
        //nav.setItemBackgroundResource(R.color.red);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frg=null;
                int id=item.getItemId();
                if(id==R.id.menu_home){
//                    startActivity(new Intent(getApplicationContext(), CustomCalendar.class));
//                    finish();
                    frg= blankFragment;
                }else if(id==R.id.menu_mymealplan){
                    frg=homeFragment;
                }else if (id==R.id.menu_contactus) {
                    frg= blankFragment;
//                    startActivity(new Intent(getApplicationContext(), ActivityTest.class));
//                    finish();
                }

                else if (id==R.id.menu_customer) {
                    frg= rvFragmentUsers;
                }else if (id==R.id.menu_userbyvid) {
                    startActivity(new Intent(getApplicationContext(), VendorIdSpinner.class));
                    finish();
                }


                //above commented code is for Admin only

                else if (id==R.id.menu_profile) {
//                    startActivity(new Intent(getApplicationContext(), UploadProfileData.class));
//                    finish();
                    frg= fragmentToUpdateProfile;
                }
                else if (id==R.id.Logout) {
                     //String appVersion = String.valueOf((R.id.Logout));
                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(getApplicationContext(), PhoneOTP1.class);
                    startActivity(intent);
                    finish();

//                    startActivity(new Intent(getApplicationContext(), ScrollViewTest.class));
//                    finish();
//                    frg= fragmentToLogout;
                }
                drawerLayout.closeDrawer(GravityCompat.START); //this line hide menu item on item selection
                //return true;
                getSupportFragmentManager().beginTransaction().replace(R.id.container,frg ).commit();
                return true;
            }
        });
        bnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment=null; //bottom nav item selected by default

                int id=item.getItemId();
                if(id==R.id.preferedmealtype){
                    //selectedFragment= blankFragment;
                    startActivity(new Intent(getApplicationContext(), FoodPreference1.class));
                    finish();
                }else if (id==R.id.suggestion){
//                    startActivity(new Intent(getApplicationContext(), CustomerSuggestion.class));
//                    finish();
                    selectedFragment= blankFragmentSuggestion;
                }else if (id==R.id.mycomplaints){
//                    startActivity(new Intent(getApplicationContext(), DropdownComplaint.class));
//                    finish();
                    selectedFragment=blankFragmentComplaint;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment ).commit();
                return true; //if false selected item will not be highlighted
            }
        });
    }

}
