package com.tiffin.tiffinqnq;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BlankFragmentComplaint extends Fragment {


    public BlankFragmentComplaint() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        startActivity(new Intent(getContext(), DropdownComplaint.class));
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_blank_complaint, container, false);
        return null;
    }
}