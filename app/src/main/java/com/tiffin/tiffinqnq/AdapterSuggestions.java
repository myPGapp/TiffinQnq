package com.tiffin.tiffinqnq;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdapterSuggestions extends RecyclerView.Adapter<AdapterSuggestions.myviewHolder> {
    ArrayList<ModelSuggestions>datalist;
    private Context context;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userID;
    public AdapterSuggestions(Context context, ArrayList<ModelSuggestions> datalist) {

        this.datalist = datalist;
        this.context=context;

    }
    @Override
    public AdapterSuggestions.myviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singlerowsuggestionsadmin,parent,false);
        return new myviewHolder(view);

    }
    @Override
    public void onBindViewHolder(final AdapterSuggestions.myviewHolder holder, int position) {
        //String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        getItemId(position);
        {
            FirebaseAuth auth;
            auth=FirebaseAuth.getInstance();
            FirebaseFirestore firestore;
            firestore=FirebaseFirestore.getInstance();
            String userID;
            userID=datalist.get(position).getUid();
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.
                    MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.desciption.setText(datalist.get(position).getDescription());
            holder.dateSuggestions.setText(datalist.get(position).getDateSuggestions());
            holder.tvreply.setText(datalist.get(position).getReply());
            if(userID.equals(auth.getCurrentUser().getUid())){
                holder.reply.setVisibility(View.GONE);
                holder.btnreply.setVisibility(View.GONE);
            }
            if(!datalist.get(position).reply.equals("")){
                holder.btnreply.setEnabled(false);
                holder.reply.setVisibility(View.GONE);

            }
            String id= datalist.get(position).id.toString();
            holder.btnreply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("iddd.."+id);

                    String reply=holder.reply.getText().toString();
                    Map<String, Object> user = new HashMap<>();
                    user.put("reply", reply);
                    DocumentReference documentReference=firestore.collection("users").document(userID).collection("suggestions").document(id);
                    documentReference.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            holder.btnreply.setEnabled(false);
                            holder.reply.setEnabled(false);
                        }
                    });

                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentReference documentReference = firestore.collection("users").document(userID);
                    documentReference.collection("suggestions").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "feedback removed", Toast.LENGTH_SHORT).show();
                            datalist.remove(position);
                            notifyItemRemoved(position);
                        }
                    });

                }
            });




            //holder.name.setText("");

        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public static class myviewHolder extends RecyclerView.ViewHolder
    {
        TextView desciption,dateSuggestions,tvreply,delete;

        EditText reply;
        Button btnreply;
        public myviewHolder(View itemView) {
            super(itemView);
            //ProfRecyclerView=itemView.findViewById(R.id.profRecView);
            desciption=itemView.findViewById(R.id.description);
            dateSuggestions=itemView.findViewById(R.id.dateSuggestions);
            reply=itemView.findViewById(R.id.edtreply);
            tvreply=itemView.findViewById(R.id.tvreply);
            btnreply=itemView.findViewById(R.id.btnreply);
            delete=itemView.findViewById(R.id.delete);
           // name=itemView.findViewById(R.id.name);


        }
    }
}
