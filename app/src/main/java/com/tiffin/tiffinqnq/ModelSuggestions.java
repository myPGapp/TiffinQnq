package com.tiffin.tiffinqnq;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentId;

public class ModelSuggestions
{
    String description,uid,dateSuggestions,reply,id;
    public ModelSuggestions() {
    }
    public ModelSuggestions(String description, String uid,String id,String dateSuggestions,String reply){
        this.description=description;
        this.uid=uid;
        this.id=id;
        this.dateSuggestions=dateSuggestions;
        this.reply=reply;


    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }


    public String getDateSuggestions() {
        return dateSuggestions;
    }

    public void setDateSuggestions(String dateSuggestions) {
        this.dateSuggestions = dateSuggestions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    @DocumentId
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

}
