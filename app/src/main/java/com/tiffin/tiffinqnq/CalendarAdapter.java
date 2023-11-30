package com.tiffin.tiffinqnq;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.tiffin.tiffinqnq.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    FirebaseFirestore fstore;
    FirebaseAuth auth;
    CheckBox cblunch,cbdinner,cblunchdinner,sevendaypaln,fifteendayplan,thirtydayplan;
    String my,mycd,absentlunch,absentdinner;
    int index1,indexofcurrentdate;

    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, String my,String mycd,int index1,int indexofcurrentdate, CheckBox cblunch,CheckBox cbdinner,CheckBox cblunchdinner,CheckBox sevendaypaln,CheckBox fifteendayplan, CheckBox thirtydayplan) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.my= my;
        this.mycd=mycd;
        this.index1=index1;
        this.indexofcurrentdate=indexofcurrentdate;
        this.cblunch=cblunch;
        this.cbdinner=cbdinner;
        this.cblunchdinner=cblunchdinner;
        this.sevendaypaln=sevendaypaln;
        this.fifteendayplan=fifteendayplan;
        this.thirtydayplan=thirtydayplan;
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        auth=FirebaseAuth.getInstance();
        String userID=auth.getCurrentUser().getUid();
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
        //layoutParams.height=(int) (parent.getHeight()*0.166666666);
        layoutParams.height=(int) (parent.getHeight()*0.146666666);
        return new CalendarViewHolder(view,onItemListener,cblunch,cbdinner,cblunchdinner,sevendaypaln,fifteendayplan,thirtydayplan,my,mycd,index1,indexofcurrentdate);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        //holder.dayOfMonth.setTextSize(20);
        //holder.lunch.setBackgroundColor(Color.BLACK);
        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        String userID = auth.getCurrentUser().getUid();
        //if(my.equals("September 2023")) {
        fstore.collection("users").document(userID).collection("events").document("lunch").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<String> arrayList = (ArrayList<String>) document.get(my);
                        if(arrayList != null){
                            //System.out.println(arrayList);
                            for (int i = 0; i < arrayList.size(); i++) {
                                String s = arrayList.get(i);
                                if (holder.dayOfMonth.getText().equals(s)) {
                                    //holder.lunch.setBackgroundColor(Color.BLACK);
                                    holder.lunch.setBackgroundResource(R.drawable.round_green);
                                    //holder.dayOfMonth.setBackgroundColor(Color.RED);
                                } else {


                                }
                            }
                        }else{
                            holder.lunch.setBackgroundResource(R.drawable.round_background_cal);
                            //holder.lunch.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        holder.lunch.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        fstore.collection("users").document(userID).collection("events").document("dinner").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<String> arrayList = (ArrayList<String>) document.get(my);
                        if(arrayList != null){
                            //System.out.println(arrayList);
                            for (int i = 0; i < arrayList.size(); i++) {
                                String s = arrayList.get(i);
                                if (holder.dayOfMonth.getText().equals(s)) {
                                    //holder.dinner.setBackgroundColor(Color.RED);
                                    holder.dinner.setBackgroundResource(R.drawable.round_grey);
                                } else {
                                }

                            }
                        }else{
                            holder.dinner.setBackgroundResource(R.drawable.round_background_cal);
                            //holder.dinner.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        holder.dinner.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        fstore.collection("holidays").document("Ml4ziSIN1dW84IWC0YYD").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        ArrayList<String> holidays = (ArrayList<String>) document.get(my);
                        if(holidays != null){
                            for (int i = 0; i < holidays.size(); i++){
                                String holiday = holidays.get(i);
                                if (holder.dayOfMonth.getText().equals(holiday)){
//                                    holder.dayOfMonth.setBackgroundColor(Color.RED);
                                    holder.dayOfMonth.setBackgroundResource(R.color.red);
                                    //holder.celldaytext.setTextColor(Color.RED);

                                }

                            }

                        }

                    }
                }

            }
        }).
                addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

            }
        });
//        fstore.collection("users").document(userID).collection("absent").document("dinner").
//                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(Task<DocumentSnapshot> task) {
//                        if(task.isSuccessful()){
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()){
//                                ArrayList<String> absent = (ArrayList<String>) document.get(my);
//                                if(absent != null){
//                                    for (int i = 0; i < absent.size(); i++){
//                                        String absentdinner = absent.get(i);
//                                        if (holder.dayOfMonth.getText().equals(absentdinner)){
//                                            holder.dayOfMonth.setBackgroundColor(Color.GRAY);
//                                            //holder.celldaytext.setTextColor(Color.RED);
//
//                                        }
//
//                                    }
//
//                                }
//
//                            }
//                        }
//
//                    }
//                }).
//                addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//
//                    }
//                });
        fstore.collection("users").document(userID).collection("absent").document("lunch").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()){
                                ArrayList<String> absentLunch = (ArrayList<String>) document.get(my);

                                {
                                    if(absentLunch != null) {
                                        for (int i = 0; i < absentLunch.size(); i++) {
                                            absentlunch = absentLunch.get(i);
                                        }
                                    }

                                    //holder.celldaytext.setTextColor(Color.RED);
                                    fstore.collection("users").document(userID).collection("absent").document("dinner").
                                            get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @SuppressLint("ResourceAsColor")
                                                @Override
                                                public void onComplete(Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()){
                                                            ArrayList<String> absentDinner = (ArrayList<String>) document.get(my);
                                                            {
                                                                if(absentDinner != null) {
                                                                    for (int j = 0; j < absentDinner.size(); j++) {
                                                                        absentdinner = absentDinner.get(j);
                                                                    }
                                                                }

                                                                if (holder.dayOfMonth.getText().equals(absentdinner)){
                                                                    if(holder.dayOfMonth.getText().equals(absentlunch)){
                                                                        holder.dayOfMonth.setBackgroundResource(R.color.blue);
                                                                        //holder.dayOfMonth.setBackgroundColor(R.color.blue);  //for lunch plus dinner
                                                                    }else{
                                                                        holder.dayOfMonth.setBackgroundColor(Color.LTGRAY);//for dinner only
                                                                    }

                                                                }
                                                                else if(holder.dayOfMonth.getText().equals(absentlunch)){
                                                                    if(holder.dayOfMonth.getText().equals(absentdinner)){
                                                                        //holder.dayOfMonth.setBackgroundColor(R.color.blue);
                                                                        holder.dayOfMonth.setBackgroundResource(R.color.blue);
                                                                    }else{
                                                                        holder.dayOfMonth.setBackgroundColor(Color.CYAN);

                                                                    }
                                                                }

                                                                //holder.celldaytext.setTextColor(Color.RED);





                                                                //}

                                                            }

                                                        }
                                                    }

                                                }
                                            }).
                                            addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(Exception e) {

                                                }
                                            });

                                    //}

                                    //}

                                }

                            }
                        }

                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {

                    }
                });
        /*
        fstore.collection("users").document(userID).collection("absent").document("lunch").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()){
                                ArrayList<String> absentLunch = (ArrayList<String>) document.get(my);

                                if(absentLunch != null){
                                    for (int i = 0; i < absentLunch.size(); i++){
                                        String absentlunch = absentLunch.get(i);

                                            //holder.celldaytext.setTextColor(Color.RED);
                                            fstore.collection("users").document(userID).collection("absent").document("dinner").
                                                    get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @SuppressLint("ResourceAsColor")
                                                        @Override
                                                        public void onComplete(Task<DocumentSnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                DocumentSnapshot document = task.getResult();
                                                                if (document.exists()){
                                                                    ArrayList<String> absentDinner = (ArrayList<String>) document.get(my);
                                                                    if(absentDinner != null){
                                                                        for (int j = 0; j < absentDinner.size(); j++){
                                                                            String absentdinner = absentDinner.get(j);

                                                                            if (holder.dayOfMonth.getText().equals(absentdinner)){
                                                                                if(holder.dayOfMonth.getText().equals(absentlunch)){
                                                                                    holder.dayOfMonth.setBackgroundResource(R.color.blue);  //for lunch plus dinner
                                                                                }else{
                                                                                    holder.dayOfMonth.setBackgroundColor(Color.LTGRAY);//for dinner only
                                                                                }

                                                                            }
                                                                            else if(holder.dayOfMonth.getText().equals(absentlunch)){
                                                                                if(holder.dayOfMonth.getText().equals(absentdinner)){
                                                                                    holder.dayOfMonth.setBackgroundResource(R.color.blue);
                                                                                }else{
                                                                                    holder.dayOfMonth.setBackgroundColor(Color.CYAN);

                                                                                }
                                                                            }

                                                                                //holder.celldaytext.setTextColor(Color.RED);





                                                                        }

                                                                    }

                                                                }
                                                            }

                                                        }
                                                    }).
                                                    addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(Exception e) {

                                                        }
                                                    });

                                        //}

                                    }

                                }

                            }
                        }

                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {

                    }
                });
        */

    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }
    public interface OnItemListener{
        void onItemClick(int position,String dayText);

    }
}


