package com.tiffin.tiffinqnq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterComplaints extends RecyclerView.Adapter<AdapterComplaints.myviewholder2> {
    ArrayList<ModelComplaints>datalist;
    private Context context;
    public AdapterComplaints(Context context, ArrayList<ModelComplaints>datalist){
        this.context=context;
        this.datalist=datalist;

    }

    @NonNull
    @Override
    public myviewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singlerowcomplaints,parent,false);
        return new myviewholder2(view);
    }

    @Override
    public void onBindViewHolder(final AdapterComplaints.myviewholder2 holder, int position){
        getItemId(position); {
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.description.setText(datalist.get(position).getDescription());
            holder.resolution.setText(datalist.get(position).getResolution());
            holder.category.setText(datalist.get(position).getCategory());
            holder.dateComplaints.setText(datalist.get(position).getDateComplaints());
            holder.dateResolution.setText(datalist.get(position).getDateResolution());
        }

    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public  int getItemCount(){
        return datalist.size();

    }
    public static class myviewholder2 extends RecyclerView.ViewHolder{
        TextView description,dateComplaints,category,resolution,dateResolution;
        public myviewholder2 (View itemView){
            super(itemView);
            description=itemView.findViewById(R.id.description);
            dateComplaints=itemView.findViewById(R.id.dateComplaints);
            dateResolution=itemView.findViewById(R.id.dateResolution);
            category=itemView.findViewById(R.id.category);
            resolution=itemView.findViewById(R.id.edtResolution);

        }

    }

}
