package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodPreference1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    FirebaseFirestore fstore;
    FirebaseAuth auth;
    String userID;
    Button save;
    TextView preference1,preference2,preference3,preference4,preference5,preference6;
    ImageView cancel1,cancel2,cancel3,cancel4,cancel5,cancel6;
    ArrayList<String>list=new ArrayList<>();
    ArrayList<String>list2=new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> arr;
    ArrayAdapter<String> adapter;
    private  String[] paths = {"Please select your preference","Bihari", "Bengali", "Punjabi","South Indian","North East","North Indian"};


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_preference1);
        save=findViewById(R.id.save);
        listView=findViewById(R.id.lv);
//        list.add(paths[0]);
//        list.add(paths[1]);
        auth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        userID=auth.getCurrentUser().getUid();
        spinner = (Spinner)findViewById(R.id.spinner);
        list2.addAll(Arrays.asList(paths));
//        list2.remove(paths[1]);
//        System.out.println(list2.get(1));
        adapter = new ArrayAdapter<String>(FoodPreference1.this, android.R.layout.simple_spinner_item,list2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        fstore.collection("users").document(userID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if(documentSnapshot.exists()){
                                ArrayList<String> preference = (ArrayList<String>) documentSnapshot.get("foodpreference");
                                assert preference != null;
                                System.out.println("..food");
                                arr = new ArrayAdapter<String>(FoodPreference1.this, android.R.layout.simple_spinner_dropdown_item, list);
                                listView.setAdapter(arr);
                                arr.addAll(preference);
                                arr.notifyDataSetChanged();
                                System.out.println("..food...");
                            }

                        }

                    }
                });
       // arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //listView.setAdapter(arr);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(FoodPreference1.this, "Toast", Toast.LENGTH_SHORT).show();
                String item=arr.getItem(position);
                arr.remove(item);

                fstore.collection("users").document(userID)
                        .update("foodpreference", FieldValue.arrayRemove(item))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println(("preference removed"));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                adapter.add(item);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int k=0;k< arr.getCount();k++) {
                    fstore.collection("users").document(userID)
                            .update("foodpreference", FieldValue.arrayUnion(arr.getItem(k)))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    System.out.println(("preference updated"));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                }
                Toast.makeText(FoodPreference1.this, "Your preference saved successfully", Toast.LENGTH_SHORT).show();


            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(FoodPreference1.this, android.R.layout.simple_spinner_dropdown_item, list);
        listView.setAdapter(arr);
//        String selectedSpinner = spinner.getSelectedItem().toString();
//        list2.remove(selectedSpinner);
//        adapter.notifyDataSetChanged();

        switch (position){
            case 0:
                break;
            case 1:
                list.add(paths[1]);
                arr.notifyDataSetChanged();


                break;
            case 2:
                list.add(paths[2]);
                arr.notifyDataSetChanged();


                break;
            case 3:
                list.add(paths[3]);
                arr.notifyDataSetChanged();

                break;
            case 4:
                list.add(paths[4]);
                arr.notifyDataSetChanged();


                break;
            case 5:
                list.add(paths[5]);
                arr.notifyDataSetChanged();


                break;
            case 6:
                list.add(paths[6]);
                arr.notifyDataSetChanged();


                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}