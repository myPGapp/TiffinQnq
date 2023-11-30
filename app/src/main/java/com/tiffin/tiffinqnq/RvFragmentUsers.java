package com.tiffin.tiffinqnq;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import android.widget.Toast;

import com.tiffin.tiffinqnq.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RvFragmentUsers extends Fragment implements ProfileInterface{
    RecyclerView recView;
    ImageView img1;
    ArrayList<model> datalist;
    FirebaseFirestore db;
    myadapter adapter;
    Button btnhome;
    public RvFragmentUsers() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rv_users, container, false);
        setHasOptionsMenu(true);
        recView=view.findViewById(R.id.recView);
        recView.setHasFixedSize(true);
        img1=view.findViewById(R.id.img1);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        datalist=new ArrayList<>();
        adapter=new myadapter(getContext(),datalist);
        recView.setAdapter(adapter);
        btnhome=view.findViewById(R.id.btnhome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);

            }
        });

        db= FirebaseFirestore.getInstance();
        db.collection("users").orderBy("name").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        return view;
    }
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // below line is to get our inflater
        //MenuInflater inflater = getActivity().getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

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
       // return true;
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
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();        }
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