package com.tiffin.tiffinqnq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Adapterdelivery extends RecyclerView.Adapter<Adapterdelivery.myviewholder> {
    ArrayList<Modeldelivery> data;
    private Context context;
    public Adapterdelivery(Context context,ArrayList<Modeldelivery> data) {

        this.data = data;
        this.context=context;


    }
    @Override
    public Adapterdelivery.myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singlerowdelivery,parent,false);
        return new Adapterdelivery.myviewholder(view);

    }
    @Override
    public void onBindViewHolder(final Adapterdelivery.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        getItemId(position);
        {
            //holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.
                    MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.description.setText(data.get(position).getDescription());
            holder.date.setText(data.get(position).getDate());

/*
            holder.resolved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent4=new Intent(holder.viewplan.getContext(), ViewCustomerPlan.class);
                    Intent intent4=new Intent(context, ViewCustomerPlan.class);
                    intent4.putExtra("description", data.get(position).getDescription());
                    intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.description.getContext().startActivity(intent4);

                }
            });
*/
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class myviewholder extends RecyclerView.ViewHolder
    {
        TextView description,date,resolved;
        public myviewholder(View itemView) {
            super(itemView);
            description=itemView.findViewById(R.id.descdelivery);
            date=itemView.findViewById(R.id.date);
            resolved=itemView.findViewById(R.id.delresolved);

        }
    }
}
