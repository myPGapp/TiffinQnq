package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RvSuggestions extends AppCompatActivity implements ProfileInterface{
RecyclerView recViewComplaints;
AdapterSuggestions adapter;
FirebaseFirestore db;
String userID,name2;
TextView tvname;
FirebaseAuth auth;
    ArrayList<ModelSuggestions> datalist;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_complaints);
        recViewComplaints=findViewById(R.id.recViewComplaints);
        recViewComplaints.setHasFixedSize(true);
        tvname=findViewById(R.id.name);
        recViewComplaints.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new AdapterSuggestions(this,datalist);
        recViewComplaints.setAdapter(adapter);
        userID=getIntent().getStringExtra("uid").toString();
        name2=getIntent().getStringExtra("uname").toString();
        //Toast.makeText(this, name2, Toast.LENGTH_SHORT).show();
        //tvname.setText(name2);
        tvname.setText("List of Feedbacks provided by "+System.getProperty ("line.separator")+name2);
        db= FirebaseFirestore.getInstance();
        db.collection("users").document(userID).collection("suggestions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    ModelSuggestions obj=d.toObject(ModelSuggestions.class);
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onItemClick(int position) {

    }
}