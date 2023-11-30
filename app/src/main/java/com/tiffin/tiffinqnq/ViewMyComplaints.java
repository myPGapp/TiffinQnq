package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewMyComplaints extends AppCompatActivity implements ProfileInterface{
    RecyclerView recVdelivery,recVquality,recVquantity,recVpayment,recVother;
    Adapterdelivery adapterdelivery;
    Adapterquality adapterquality;
    Adapterquantity adapterquantity;
    Adapterpayment adapterpayment;
    Adapterother adapterother;

    ArrayList<Modeldelivery>data;
    ArrayList<Modelquality>dataquality;
    ArrayList<Modelquantity>dataquantity;
    ArrayList<Modelpayment>datapayment;
    ArrayList<Modelother>dataother;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String userID,name;
    TextView username;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints);
        recVdelivery=findViewById(R.id.RecVdelivery);
        recVquality=findViewById(R.id.RecVquality);
        recVquantity=findViewById(R.id.RecVquantity);
        recVpayment=findViewById(R.id.RecVpayment);
        recVother=findViewById(R.id.RecVother);
        username=findViewById(R.id.name);

        recVdelivery.setHasFixedSize(true);
        recVdelivery.setLayoutManager(new LinearLayoutManager(this));
        data=new ArrayList<>();
        adapterdelivery=new Adapterdelivery(this,data);
        recVdelivery.setAdapter(adapterdelivery);

        recVquality.setHasFixedSize(true);
        recVquality.setLayoutManager(new LinearLayoutManager(this));
        dataquality=new ArrayList<>();
        adapterquality=new Adapterquality(this,dataquality);
        recVquality.setAdapter(adapterquality);

        recVquantity.setHasFixedSize(true);
        recVquantity.setLayoutManager(new LinearLayoutManager(this));
        dataquantity=new ArrayList<>();
        adapterquantity=new Adapterquantity(this,dataquantity);
        recVquantity.setAdapter(adapterquantity);

        recVpayment.setHasFixedSize(true);
        recVpayment.setLayoutManager(new LinearLayoutManager(this));
        datapayment=new ArrayList<>();
        adapterpayment=new Adapterpayment(this,datapayment);
        recVpayment.setAdapter(adapterpayment);

        recVother.setHasFixedSize(true);
        recVother.setLayoutManager(new LinearLayoutManager(this));
        dataother=new ArrayList<>();
        adapterother=new Adapterother(this,dataother);
        recVother.setAdapter(adapterother);

        userID=getIntent().getStringExtra("uid").toString();
        name=getIntent().getStringExtra("name").toString();
        username.setText("Complaints of "+name);
        firestore= FirebaseFirestore.getInstance();
        //userID=auth.getCurrentUser().getUid();
        firestore.collection("users").document(userID).collection("complaints").
                document("food delivery").collection("Food Delivery").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            Modeldelivery obj=d.toObject(Modeldelivery.class);
                            data.add(obj);
                        }
                        adapterdelivery.notifyDataSetChanged();
                    }
                });
        firestore.collection("users").document(userID).collection("complaints").
                document("food quality").collection("Food Quality").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            Modelquality obj=d.toObject(Modelquality.class);
                            dataquality.add(obj);
                        }
                        adapterquality.notifyDataSetChanged();
                    }
                });
        firestore.collection("users").document(userID).collection("complaints").
                document("food quantity").collection("Food Quantity").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            Modelquantity obj=d.toObject(Modelquantity.class);
                            dataquantity.add(obj);
                        }
                        adapterquantity.notifyDataSetChanged();
                    }
                });
        firestore.collection("users").document(userID).collection("complaints").
                document("payments").collection("Payments").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            Modelpayment obj=d.toObject(Modelpayment.class);
                            datapayment.add(obj);
                        }
                        adapterpayment.notifyDataSetChanged();
                    }
                });
        firestore.collection("users").document(userID).collection("complaints").
                document("other").collection("Other").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            Modelother obj=d.toObject(Modelother.class);
                            dataother.add(obj);
                        }
                        adapterother.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void onItemClick(int position) {

    }
}