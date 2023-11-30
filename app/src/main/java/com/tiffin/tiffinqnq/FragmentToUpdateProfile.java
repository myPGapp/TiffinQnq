package com.tiffin.tiffinqnq;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiffin.tiffinqnq.R;

public class FragmentToUpdateProfile extends Fragment {
    public FragmentToUpdateProfile() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_to_update_profile, container, false);
//        auth = FirebaseAuth.getInstance();
//        fstore=FirebaseFirestore.getInstance();
//        userID=auth.getCurrentUser().getUid();
//        System.out.println("..kk");
//        DocumentReference docRef = fstore.collection("users").document(userID);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document != null) {
//                        phone= (String) document.get("phoneNo");
//                        //Toast.makeText(getContext(), phone, Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        Intent intent=new Intent(getContext(),OtpUpdateProfile.class);
        //Toast.makeText(getContext(), phone+"..", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        return view;
    }
}