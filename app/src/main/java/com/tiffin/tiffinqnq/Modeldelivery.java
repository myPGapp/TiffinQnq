package com.tiffin.tiffinqnq;

import androidx.recyclerview.widget.RecyclerView;

public class Modeldelivery {
    String description;
    String date;
    public Modeldelivery(){

    }
    public Modeldelivery( String description, String date) {
        this.description=description;
        this.date = date;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
