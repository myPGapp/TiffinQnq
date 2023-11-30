package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RvUserswithid extends AppCompatActivity implements ProfileInterface {
    RecyclerView recView;
    ImageView img1;
    ArrayList<model> datalist;
    FirebaseFirestore db;
    myadapter adapter;
    Button btnhome;
    String VID;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_userswithid);

        //setHasOptionsMenu(true);
        recView=findViewById(R.id.recView);
//        Toolbar toolbar = view. findViewById((R.id.toolbar));

//       toolbar.setTitle("current Users with Vendor ID");
        String vendorID=getIntent().getStringExtra("vendorid");
        recView.setHasFixedSize(true);
        img1=findViewById(R.id.img1);
        recView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new myadapter(this,datalist);
        recView.setAdapter(adapter);
        btnhome=findViewById(R.id.btnhome);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("Users with Vendor ID:"+vendorID);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RvUserswithid.this,MainActivity.class);
                startActivity(intent);

            }
        });
        db= FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("vendorId", vendorID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            model obj=d.toObject(model.class);
                            datalist.add(obj);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        //MenuInflater inflater = getActivity().getMenuInflater();
        getMenuInflater().inflate(R.menu.search_menu, menu);
        // inside inflater we are inflating our menu file.
        //inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
         return true;
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<model> filteredlist = new ArrayList<model>();

        // running a for loop to compare elements.
        for (model item : datalist) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {

                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);

            }

        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();        }
        else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}