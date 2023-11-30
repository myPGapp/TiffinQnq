package com.tiffin.tiffinqnq;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>{
    ArrayList<model> datalist;
    private Context context;
    String imageurl;
    String itemId,vid;



    public myadapter(Context context,ArrayList<model> datalist) {

        this.datalist = datalist;
        this.context=context;


    }

    public void filterList(ArrayList<model> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        datalist = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);

    }
    @Override
    public void onBindViewHolder(final myviewholder holder, @SuppressLint("RecyclerView") int position) {
        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        getItemId(position);
        if (datalist.get(position).getUserid().equals("test") ) {
            holder.itemView.setVisibility(View.INVISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }else{
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.
                    MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.name.setText(datalist.get(position).getName());
            holder.address.setText(datalist.get(position).getAddress());
            holder.pincode.setText("PIN-"+datalist.get(position).getPincode());
            holder.sex.setText(datalist.get(position).getSex());
            holder.phone.setText(datalist.get(position).getPhoneNo());
            holder.profession.setText(datalist.get(position).getProfession());
            imageurl = "https://firebasestorage.googleapis.com/v0/b/mydatabase-b7d66.appspot.com/o/images%2Fimage.jpg?alt=media&token=369cff00-e621-481b-87c8-c6d3edfb9272";

            if (datalist.get(position).getImageUrl() == null || datalist.get(position).getImageUrl() == "") {
                if(datalist.get(position).getSex().equals("female")){
                    Glide.with(context).load(R.drawable.female).into(holder.img1);
                }else{
                    Glide.with(context).load(R.drawable.image).into(holder.img1);
                }
                System.out.println("OK");

            } else {
                Glide.with(context).load(datalist.get(position).getImageUrl()).into(holder.img1);
            }
            holder.adminbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent=new Intent(holder.t1.getContext(),details.class);
                    //Intent intent = new Intent(context, ViewCustomerPlan.class);
                    Intent intent = new Intent(context, AdminView.class);
                    intent.putExtra("uname", datalist.get(position).getName());
                    intent.putExtra("address", datalist.get(position).getAddress());
                    intent.putExtra("pincode", datalist.get(position).getPincode());
                    intent.putExtra("singleImage", datalist.get(position).getImageUrl());
                    intent.putExtra("uid", datalist.get(position).getUserid());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                    //holder.t1.getContext().startActivity(intent);


                }
            });
            holder.btncall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+datalist.get(position).getPhoneNo()));
                    context.startActivity(intent);
                }
            });
            holder.viewfeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent2=new Intent(holder.viewfeedback.getContext(), RvSuggestions.class);
                    Intent intent2=new Intent(context, RvSuggestions.class);
                    intent2.putExtra("uid", datalist.get(position).getUserid());
                    intent2.putExtra("uname", datalist.get(position).getName());
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.name.getContext().startActivity(intent2);

                }
            });
            holder.viewcomplain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent3=new Intent(holder.viewcomplain.getContext(), RvComplaints.class);
                    Intent intent3=new Intent(context, ViewComplaints.class);
                    intent3.putExtra("uid", datalist.get(position).getUserid());
                    intent3.putExtra("name", datalist.get(position).getName());
                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.viewcomplain.getContext().startActivity(intent3);

                }
            });
            holder.viewplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent4=new Intent(holder.viewplan.getContext(), ViewCustomerPlan.class);
                    Intent intent4=new Intent(context, ViewCustomerPlan.class);
                    intent4.putExtra("uid", datalist.get(position).getUserid());
                    intent4.putExtra("uname", datalist.get(position).getName());
                    intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.name.getContext().startActivity(intent4);

                }
            });
            /*
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    final EditText edittext = new EditText(context);
                    alert.setMessage("Enter vendor id");
                    alert.setTitle("please enter correct id only");

                    alert.setView(edittext);

                    alert.setPositiveButton("save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value
                            vid = edittext.getText().toString();
                            FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                            DocumentReference documentReference = fstore.collection("users").document(datalist.get(position).getUserid());
                            Map<String, Object> user = new HashMap<>();
                            user.put("vendorId", vid);

                            //dbroot was in place of documentreference
                            documentReference.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {

                                    Toast.makeText(context, "vendor id successfully allotted", Toast.LENGTH_SHORT).show();

                                    //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });
                    alert.show();


                }
            });
            */
            holder.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,Allotvendorid.class);
                    intent.putExtra("uid",datalist.get(position).getUserid());
                    context.startActivity(intent);
                }
            });
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
    public static class myviewholder extends RecyclerView.ViewHolder
    {
        public RecyclerView ProfRecyclerView;
        public ImageView img1;
        public CardView rtf;
        public LinearLayout llimage;
        Button btncall;
        TextView name,address,pincode,phone,sex,profession,adminbook,viewplan,viewcomplain,viewfeedback;
        public myviewholder(View itemView) {
            super(itemView);
            //ProfRecyclerView=itemView.findViewById(R.id.profRecView);
            name=itemView.findViewById(R.id.name);
            btncall=itemView.findViewById(R.id.btncall);
            address=itemView.findViewById(R.id.address);
            rtf=itemView.findViewById(R.id.rtf);
            llimage=itemView.findViewById(R.id.llimage);
            pincode=itemView.findViewById(R.id.pincode);
            btncall=itemView.findViewById(R.id.btncall);
            phone=itemView.findViewById(R.id.phone);
            sex=itemView.findViewById(R.id.sex);
            adminbook=itemView.findViewById(R.id.adminbook);
            viewcomplain=itemView.findViewById(R.id.view_complain);
            viewfeedback=itemView.findViewById(R.id.viewfeedback);
            viewplan=itemView.findViewById(R.id.viewplan);
            profession=itemView.findViewById(R.id.profession);
            img1=itemView.findViewById(R.id.img1);

        }
    }

}
