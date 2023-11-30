package com.tiffin.tiffinqnq;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tiffin.tiffinqnq.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


public class FragmentMeal extends Fragment implements CalendarAdapter.OnItemListener {
    protected TextView monthYearText,textView,startDate,endDate;
    RecyclerView calendarRecyclerVIew;
    LocalDate selectedDate;
    FirebaseAuth auth;
    int dinneroverlapthis,dinneroverlapnext,dinneroverlapthis1,dinneroverlapnext2,
            alreadybookedthis,alreadybookednext,addmealthis1,addmealthis2,addmealnext1,addmealnext2;
    String meal_this1,meal_this2,meal_next1,meal_next2;
    CheckBox cblunch,cbdinner,cblunchdinner,sevendaypaln,fifteendayplan,thirtydayplan;
    FirebaseFirestore dbroot;
    String userID,stDate,enDate;
    FirebaseFirestore fstore;
    int indexofcurrentdate;
    Button save;
    int cssd;
    int index1,index7,index15,index30;
    String my,mynext,mynextnext;
    ProgressBar progressBar;
    @TargetApi(Build.VERSION_CODES.O)


    public FragmentMeal() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view1= inflater.inflate(R.layout.fragment_meal, container, false);

        cbdinner=view1.findViewById(R.id.cbdinner);
        cblunch=view1.findViewById(R.id.cblunch);
        textView=view1.findViewById(R.id.textView);
        startDate=view1.findViewById(R.id.startDate);
        endDate=view1.findViewById(R.id.endDate);
        sevendaypaln=view1.findViewById(R.id.sevendayplan);
        fifteendayplan=view1.findViewById(R.id.fifteendayplan);
        thirtydayplan=view1.findViewById(R.id.thirtydayplan);
        cblunchdinner=view1.findViewById(R.id.cblunchdinner);
        initWidgets();
//        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate= LocalDate.now();
        }
        setMonthView();
        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        startDate.setText("");
        endDate.setText("");
        DocumentReference documentReference=fstore.collection("users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot!=null){
                        String name=(documentSnapshot.getString("name"));
                        textView.setText(" Welcome Mr. "+name+System.getProperty ("line.separator")+"Here is your meal plan");
                    }
                }
            }
        });
        sevendaypaln.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fifteendayplan.setEnabled(false);
                    thirtydayplan.setEnabled(false);
                }else{
                    fifteendayplan.setEnabled(true);
                    thirtydayplan.setEnabled(true);
                }

            }
        });
        fifteendayplan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sevendaypaln.setEnabled(false);
                    thirtydayplan.setEnabled(false);
                }else{
                    sevendaypaln.setEnabled(true);
                    thirtydayplan.setEnabled(true);
                }

            }
        });
        thirtydayplan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sevendaypaln.setEnabled(false);
                    fifteendayplan.setEnabled(false);
                }else{
                    sevendaypaln.setEnabled(true);
                    fifteendayplan.setEnabled(true);
                }

            }
        });
        cbdinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
                if(b){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle(" Please choose starting date");
                    alert.setMessage("").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Thanks", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.show();
                    cblunch.setEnabled(false);
                    cblunchdinner.setEnabled(false);

                }else{
                    cblunch.setEnabled(true);
                    cblunchdinner.setEnabled(true);
                }
            }
        });
        cblunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Please choose starting date");
                    alert.setMessage("").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           // Toast.makeText(getContext(), "Thanks", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.show();
                    cbdinner.setEnabled(false);
                    cblunchdinner.setEnabled(false);

                }else {
                    cbdinner.setEnabled(true);
                    cblunchdinner.setEnabled(true);
                }
            }
        });
        cblunchdinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Please choose starting date");
                    alert.setMessage("").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           // Toast.makeText(getContext(), "Thanks", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.show();
                    cbdinner.setEnabled(false);
                    cblunch.setEnabled(false);
                }else{
                    cbdinner.setEnabled(true);
                    cblunch.setEnabled(true);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            String[] arr2= daysInMonthArray(selectedDate).toArray(new String[0]);
            @Override
            public void onClick(View view) {
                {
                }

            }
        });

        return view1;
    }
    private void setMonthView() {
        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        monthYearText.setText(monthYearFromDate(selectedDate));
        my=monthYearFromDate(selectedDate);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mynext=monthYearFromDate(selectedDate.plusMonths(1));
            mynextnext=monthYearFromDate(selectedDate.plusMonths(2));
        }
        String mycd= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mycd = monthYearFromDate(LocalDate.now());
        }
        ArrayList<String> daysInMonth=daysInMonthArray(selectedDate);
        CalendarAdapter calendarAdapter=new CalendarAdapter(daysInMonth,this,my,mycd,index1,indexofcurrentdate,cblunch,cbdinner,cblunchdinner,sevendaypaln,fifteendayplan,thirtydayplan);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),7);
        calendarRecyclerVIew.setLayoutManager(layoutManager);
        calendarRecyclerVIew.setAdapter(calendarAdapter);

    }

    @TargetApi(Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String>daysInMonthArray=new ArrayList<>();
        YearMonth yearMonth=YearMonth.from(date);
        int daysInMonth=yearMonth.lengthOfMonth()+1;
        LocalDate firstOfMonth=selectedDate.withDayOfMonth(1);
        int dayOfWeek=firstOfMonth.getDayOfWeek().getValue();
        for (int i=1;i<=42;i++){
            if(i<=dayOfWeek ||i>=daysInMonth+dayOfWeek){
                daysInMonthArray.add("");
            }else{
                daysInMonthArray.add(String.valueOf(i-dayOfWeek));
            }

        }return daysInMonthArray;
    }

    private void initWidgets() {
        calendarRecyclerVIew= calendarRecyclerVIew.findViewById(R.id.calendarRecyclerView);
        monthYearText= monthYearText.findViewById(R.id.monthYearTV);
    }
    @TargetApi(Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MMMM YYYY");
        return date.format(formatter);
    }
    @TargetApi(Build.VERSION_CODES.O)
    public void previousMonthAction(View view){
        alreadybookednext=0;
        alreadybookedthis=0;
        addmealnext1=0;
        addmealnext2=0;
        addmealthis1=0;
        addmealthis2=0;
        dinneroverlapnext=0;
        dinneroverlapnext2=0;
        dinneroverlapthis=0;
        dinneroverlapthis1=0;

//        if(sevendaypaln.isChecked()||fifteendayplan.isChecked()||thirtydayplan.isChecked()){
//            Toast.makeText(this, "Previous day can not be booked", Toast.LENGTH_SHORT).show();
//        }else {
        if(monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now()))) {
            selectedDate = selectedDate.minusMonths(1);
            sevendaypaln.setChecked(false);
            fifteendayplan.setChecked(false);
            thirtydayplan.setChecked(false);
            cbdinner.setChecked(false);
            cblunch.setChecked(false);
            cblunchdinner.setChecked(false);
            sevendaypaln.setEnabled(false);
            fifteendayplan.setEnabled(false);
            thirtydayplan.setEnabled(false);
            cbdinner.setEnabled(false);
            cblunch.setEnabled(false);
            cblunchdinner.setEnabled(false);
            setMonthView();
        }else if (monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now().plusMonths(3)))){
            sevendaypaln.setEnabled(true);
            fifteendayplan.setEnabled(true);
            thirtydayplan.setEnabled(true);
            cbdinner.setEnabled(true);
            cblunch.setEnabled(true);
            cblunchdinner.setEnabled(true);
            selectedDate = selectedDate.minusMonths(1);
            setMonthView();
        }else{
            selectedDate = selectedDate.minusMonths(1);
            setMonthView();
        }
        //}
    }
    @TargetApi(Build.VERSION_CODES.O)
    public void nextMonthAction(View view){
        alreadybookednext=0;
        alreadybookedthis=0;
        addmealnext1=0;
        addmealnext2=0;
        addmealthis1=0;
        addmealthis2=0;
        dinneroverlapnext=0;
        dinneroverlapnext2=0;
        dinneroverlapthis=0;
        dinneroverlapthis1=0;
        if(monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now().minusMonths(1)))) {
            sevendaypaln.setEnabled(true);
            fifteendayplan.setEnabled(true);
            thirtydayplan.setEnabled(true);
            cbdinner.setEnabled(true);
            cblunch.setEnabled(true);
            cblunchdinner.setEnabled(true);
            selectedDate = selectedDate.plusMonths(1);
            setMonthView();
        }else if (monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now().plusMonths(2)))){
            sevendaypaln.setChecked(false);
            fifteendayplan.setChecked(false);
            thirtydayplan.setChecked(false);
            cbdinner.setChecked(false);
            cblunch.setChecked(false);
            cblunchdinner.setChecked(false);
            sevendaypaln.setEnabled(false);
            fifteendayplan.setEnabled(false);
            thirtydayplan.setEnabled(false);
            cbdinner.setEnabled(false);
            cblunch.setEnabled(false);
            cblunchdinner.setEnabled(false);
            selectedDate = selectedDate.plusMonths(1);
            setMonthView();
        }else{
            selectedDate = selectedDate.plusMonths(1);
            setMonthView();
        }
    }
    public void addMeal(int days,String mealtype){

        fstore.collection("holidays").document("Ml4ziSIN1dW84IWC0YYD").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot.exists()) {
                        ArrayList<String> holidays = (ArrayList<String>) documentSnapshot.get(my);
                        ArrayList<String>holidaysnext=(ArrayList<String>) documentSnapshot.get(mynext);
                        ArrayList<String>daysthismonth=new ArrayList<>();
                        if (holidays != null && holidaysnext !=null){

                            System.out.println("not null...+not null...");
                            ArrayList<String> commondays = new ArrayList<>();
                            int i = index1, j,validdayscounter=0;
                            while (i <daysInMonthArray(selectedDate).size() ) {
                                if(!daysInMonthArray(selectedDate).get(i).equals("")){
                                    for (j = 0; j < holidays.size(); j++) {
                                        if (daysInMonthArray(selectedDate).get(i).equals(holidays.get(j))) {
                                            commondays.add(holidays.get(j));

                                            i++;
                                            System.out.println("mmm.."+daysInMonthArray(selectedDate).get(i));
                                            continue;
                                        }
                                    }
                                    cssd = commondays.size(); //commonsize of sevendays dinner

                                    String getdays = daysInMonthArray(selectedDate).get(i);
                                    if(getdays.equals("")){
                                        break;
                                    }
                                    daysthismonth.add(getdays);
                                    System.out.println(daysthismonth);

                                    fstore.collection("users").document(userID).collection("events").document(mealtype)
                                            .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });
                                    i++;
                                    validdayscounter++;
                                    if(daysthismonth.size()==days) {

                                        Toast.makeText(getContext(), mealtype+  " date added", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), CustomCalendar.class);
                                        startActivity(intent);
                                        System.out.println("final break this");
                                        break;
                                    }


                                }else{
                                    System.out.println("this month valid days.. ="+daysthismonth.size());
                                    break;

                                }

                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            ArrayList<String>nextmonthdays=new ArrayList<>();
                            ArrayList<String> commondaysnextmonth=new ArrayList<>();;
                            int counterdashnextmonth=0;
                            int sizecommondaysnextmonth=0;
                            //for(int k=0;k<43;k++){
                            //for(int k=0;k<days+counterdashnextmonth-validdayscounter+sizecommondaysnextmonth;k++){
                            int k=0;
                            while(k<days+counterdashnextmonth-validdayscounter+sizecommondaysnextmonth){

                                if(daysInMonthArray(selectedDate).get(k).equals("")){
                                    k++;
                                    counterdashnextmonth++;
                                    System.out.println("counter dash next.."+commondaysnextmonth);
                                    continue;
                                }

                                System.out.println("next month days are.."+daysInMonthArray(selectedDate).get(k));
                                nextmonthdays.add(daysInMonthArray(selectedDate).get(k));
                                System.out.println("nnn.."+daysInMonthArray(selectedDate).get(k));
                                for(int p=0;p<holidaysnext.size();p++)
                                    if(daysInMonthArray(selectedDate).get(k).equals(holidaysnext.get(p))){
                                        commondaysnextmonth.add(holidaysnext.get(p));
                                        k++;
                                        System.out.println("value of k    "+k);
                                        continue;

                                    }
                                sizecommondaysnextmonth=commondaysnextmonth.size();
                                System.out.println("Size of holidays next month in this range"+sizecommondaysnextmonth);
                                fstore.collection("users").document(userID).collection("events").
                                        document(mealtype).update(monthYearFromDate(selectedDate),FieldValue.arrayUnion(daysInMonthArray(selectedDate).get(k))).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                                System.out.println("val of k  "+k);

                                k++;

                                if(nextmonthdays.size()==days-daysthismonth.size()){
                                    Toast.makeText(getContext(), mealtype+  " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    System.out.println(" last value of k..."+k);
                                    break;
                                }
                                //}

                            }
                            System.out.println("no of blank in next month:.."+counterdashnextmonth);
                            int nextmonthdaysize=nextmonthdays.size();
                            System.out.println("next month size..."+nextmonthdaysize);
                        }else if (holidays ==null & holidaysnext ==null){

                            System.out.println(" null...+null...");
                            ArrayList<String>thismonthdays=new ArrayList<>();
                            ArrayList<String>nextmonthdays=new ArrayList<>();

                            for (int i = index1; i <43; i++) {
                                //for (int i = index1; i <= index1 + thismonthdays.size() + 1; i++) {
                                String getdays = daysInMonthArray(selectedDate).get(i);
                                if (getdays.equals("")) {
                                    i++;
                                    //continue;
                                    break;
                                }
                                thismonthdays.add(getdays);
                                fstore.collection("users").document(userID).collection("events").document(mealtype)
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
//                                    thismonthdays.add(getdays);
                                int d = thismonthdays.size();
                                if(d==days){
                                    Toast.makeText(getContext(), mealtype+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);

                                    break;
                                }


                            }
                            System.out.println("OOOO..."+thismonthdays.size());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            int counterdashnext=0;
                            int i=0;
                            while(i<days-thismonthdays.size()+counterdashnext){
                                //while(i<days+counterdashnext-thismonthdays.size()){
                                String s=daysInMonthArray(selectedDate).get(i);

                                if(daysInMonthArray(selectedDate).get(i).equals("")){
                                    i++;
                                    counterdashnext++;
                                    continue;

                                }

                                nextmonthdays.add(s);
                                fstore.collection("users").document(userID).collection("events").document(mealtype)
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(s))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                i++;

                                System.out.println("count is..."+counterdashnext);
                                if(thismonthdays.size()+nextmonthdays.size()==days) {
                                    Toast.makeText(getContext(), mealtype + " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    break;
                                }
                            }


                        } else if (holidays ==null & holidaysnext !=null){
                            System.out.println("   null...+not null...");

                            ArrayList<String>thismonthdays=new ArrayList<>();
                            int i = index1;
                            while ( i <43) {
                                String getdays = daysInMonthArray(selectedDate).get(i);

                                if(getdays.equals("")){
//                                    i++;
                                    break;
//                                    continue;
                                }
                                fstore.collection("users").document(userID).collection("events").document(mealtype)
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                thismonthdays.add(getdays);
                                int d = thismonthdays.size();
                                if(d==days){
                                    Toast.makeText(getContext(), mealtype+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);

                                    break;
                                }
                                i++;

                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            ArrayList<String>nextmonthdays=new ArrayList<>();
                            ArrayList<String> commondaysnextmonth=new ArrayList<>();;
                            int counterdashnextmonth=0;
                            int sizecommondaysnextmonth=0;
//                            for(int k=0;k<0;k++){
                            //for(int k=0;k<days+counterdashnextmonth-thismonthdays.size()+sizecommondaysnextmonth;k++){
//                                while(k<42){
                            int k=0;
                            while(k<days+counterdashnextmonth-thismonthdays.size()+sizecommondaysnextmonth){
                                if(daysInMonthArray(selectedDate).get(k).equals("")){
                                    k++;
                                    counterdashnextmonth++;
                                    continue;
                                }

                                System.out.println("next month days are.."+daysInMonthArray(selectedDate).get(k));
                                nextmonthdays.add(daysInMonthArray(selectedDate).get(k));
                                System.out.println("nnn.."+daysInMonthArray(selectedDate).get(k));
                                for(int p=0;p<holidaysnext.size();p++)
                                    if(daysInMonthArray(selectedDate).get(k).equals(holidaysnext.get(p))){
                                        commondaysnextmonth.add(holidaysnext.get(p));
                                        k++;

                                        continue;

                                    }
                                sizecommondaysnextmonth=commondaysnextmonth.size();
                                System.out.println("Size of holidays next month in this range"+sizecommondaysnextmonth);
                                fstore.collection("users").document(userID).collection("events").
                                        document(mealtype).update(monthYearFromDate(selectedDate),FieldValue.arrayUnion(daysInMonthArray(selectedDate).get(k))).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                                if(nextmonthdays.size()==days-thismonthdays.size()){
                                    Toast.makeText(getContext(), mealtype+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    break;
                                }
                                k++;


                            }

                            //}
                            System.out.println("no of dash in next month:.."+counterdashnextmonth);
                            int nextmonthdaysize=nextmonthdays.size();
                            System.out.println("next month size..."+nextmonthdaysize);



                        }else {
                            System.out.println("  not null...+null...");

                            ArrayList<String> commondays = new ArrayList<>();
                            ArrayList<String>thismonthdays=new ArrayList<>();
                            ArrayList<String>nextmonthdays=new ArrayList<>();

                            int i = index1, j,validdayscounter=0;
                            while (i <43 ) {
                                if(!daysInMonthArray(selectedDate).get(i).equals("")){
                                    for (j = 0; j < holidays.size(); j++) {
                                        if (daysInMonthArray(selectedDate).get(i).equals(holidays.get(j))) {
                                            commondays.add(holidays.get(j));
                                            i++;
                                            continue;
                                        }
                                    }
                                    cssd = commondays.size(); //commonsize of sevendays dinner

                                    String getdays = daysInMonthArray(selectedDate).get(i);
                                    if(getdays.equals("")){
                                        break;
                                    }
                                    thismonthdays.add(getdays);
                                    fstore.collection("users").document(userID).collection("events").document(mealtype)
                                            .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });

                                }


                                else{
                                    System.out.println("this month valid days ="+validdayscounter);
                                    break;

                                }


                                i++;
                                validdayscounter++;
                                thismonthdays.size();
                                if(thismonthdays.size()==days){
                                    Toast.makeText(getContext(), mealtype+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    break;
                                }

                            }


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            int counterdashnext=0;
                            int u=0;
                            while(u<days-thismonthdays.size()+counterdashnext){

                                if(daysInMonthArray(selectedDate).get(u).equals("")){
                                    u++;
                                    counterdashnext++;
                                    continue;

                                }
                                String s=daysInMonthArray(selectedDate).get(u);
                                nextmonthdays.add(s);

                                fstore.collection("users").document(userID).collection("events").document(mealtype)
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(s))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                u++;
                                if(thismonthdays.size()+nextmonthdays.size()==days){
                                    Toast.makeText(getContext(), mealtype+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    break;
                                }

                            } System.out.println("count is..."+counterdashnext);

                        }

                    }

                }

            }
        });

    }
    public void checkandAddMeal(int days,String mealtype){
        fstore.collection("holidays").document("Ml4ziSIN1dW84IWC0YYD").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                if(task.isSuccessful()){

                    ArrayList<String> holidays = (ArrayList<String>) documentSnapshot.get(my);
                    ArrayList<String>holidaysnext=(ArrayList<String>) documentSnapshot.get(mynext);
//                                    }
//
//                                }
//                            });
                    fstore.collection("users").document(userID).collection("events").document(mealtype).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(Task<DocumentSnapshot> task) {
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    if(documentSnapshot.exists()) {
                                        ArrayList<String> bookeddaysthismonth = (ArrayList<String>) documentSnapshot.get(my);
                                        ArrayList<String> bookeddaysnextmonth = (ArrayList<String>) documentSnapshot.get(mynext);
                                        /*
                                        System.out.println("already booked days this month..." + bookeddaysthismonth);
                                        System.out.println("already booked days next month..." + bookeddaysnextmonth);
                                        */
                                        //System.out.println("holidays.."+holidays.get(0));
                                        //System.out.println(holidaysnext.get(0));
                                        int i = index1;
                                        int counter = 0;
                                        int holidaysthiscounter = 0;
                                        while (i < daysInMonthArray(selectedDate).size()) {
                                            if (bookeddaysthismonth != null) {
                                                if (bookeddaysthismonth.contains(daysInMonthArray(selectedDate).get(i))) {
                                                    Toast.makeText(getContext(), "already booked", Toast.LENGTH_SHORT).show();
                                                    alreadybookedthis=1;
                                                    break;

                                                } else if (!daysInMonthArray(selectedDate).get(i).equals("")) {
                                                    counter++;
                                                    if (holidays != null) {
                                                        if (holidays.contains(daysInMonthArray(selectedDate).get(i))) {
                                                            holidaysthiscounter++;
                                                        }
                                                    }

                                                    if (counter == days + holidaysthiscounter) {
                                                        System.out.println("if all days available in this month ..counter     =" + counter);
                                                        System.out.println("booking days if all fall in this month.." + daysInMonthArray(selectedDate).get(index1 + counter));
                                                        System.out.println("add meal this month1");
                                                        meal_this1=monthYearFromDate(selectedDate);
                                                        addmealthis1=3;
                                                        //addMeal(days, mealtype);
                                                        break;

                                                    }

                                                }

                                                i++;

                                                if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                    System.out.println("last index of blank is .." + i);
                                                    System.out.println("No of days in this month if days gos to next month..counter. if bookd days this not null." + counter);
                                                    break;
                                                }
                                            } else {
                                                if (!daysInMonthArray(selectedDate).get(i).equals("")) {
                                                    counter++;
                                                    if (holidays != null) {
                                                        if (holidays.contains(daysInMonthArray(selectedDate).get(i))) {
                                                            holidaysthiscounter++;
                                                        }
                                                    }

                                                    if (counter == days + holidaysthiscounter) {
                                                        System.out.println("No of days in this month if all days fall in this..counter..if bookd days this is null......" + counter);
                                                        System.out.println(daysInMonthArray(selectedDate).get(index1 + counter));
                                                        System.out.println("add meal this month2");
                                                        meal_this2=monthYearFromDate(selectedDate);
                                                        addmealthis2=4;
                                                        //addMeal(days, mealtype);
                                                        break;

                                                    }

                                                }

                                                i++;

                                                if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                    System.out.println("last index of blank is ..if bookd days this null." + i);
                                                    System.out.println("No of days in this month if some days fall in next.month..counter. if bookd days this  null.." + counter);
                                                    break;
                                                }
                                            }

//                                            if(bookeddaysthismonth.contains(daysInMonthArray(selectedDate).get(i))){
//                                                Toast.makeText(CustomCalendar.this, "already booked", Toast.LENGTH_SHORT).show();
//                                                break;
//                                            }else if (!daysInMonthArray(selectedDate).get(i).equals("")){
//                                                counter++;
//                                                if(holidays!=null){
//                                                    if(holidays.contains(daysInMonthArray(selectedDate).get(i))){
//                                                        holidaysthiscounter++;
//                                                    }
//                                                }
//
//                                                if(counter==days+holidaysthiscounter){
//                                                    System.out.println("counter iss......"+counter);
//                                                    System.out.println(daysInMonthArray(selectedDate).get(index1+counter));
//                                                    System.out.println("add meal this month");
//                                                    addMeal(days,mealtype);
//                                                    break;
//
//                                                }
//
//                                            }
//
//                                            i++;
//
//                                            if(daysInMonthArray(selectedDate).get(i).equals("")){
//                                                System.out.println("i is .."+i);
//                                                System.out.println("counter is.."+counter);
//                                                break;
//                                            }
//
                                        }
                                        System.out.println("holidays this counter=..." + holidaysthiscounter); //no of holidays in this range this month
                                        System.out.println("this month no of days counter.including holidays=..." + counter);
                                        if (counter < days + holidaysthiscounter) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                selectedDate = selectedDate.plusMonths(1);
                                            }
                                            int counternext = 0;
                                            int counterdashnext = 0;
                                            int holidayscounternext = 0;
                                            while (daysInMonthArray(selectedDate).get(counterdashnext).equals("")) {
                                                System.out.println("no of blanks in next month....." + counterdashnext);
                                                counterdashnext++;
                                            }

                                            for (int l = counterdashnext; l < counterdashnext + days - counter + holidaysthiscounter; l++) {
                                                System.out.println("counterdash next..." + counterdashnext + "counter..." + counter + " holidays this counter=" + holidaysthiscounter);
                                                System.out.println("next month days counter (l).." + daysInMonthArray(selectedDate).get(l));
                                                if (holidaysnext != null) {
                                                    if (holidaysnext.contains(daysInMonthArray(selectedDate).get(l))) {
                                                        holidayscounternext++;
                                                        System.out.println("holidaysnextcounter..." + holidayscounternext);
                                                    }
                                                }
                                            }
                                            while (counternext < days  - counter + holidaysthiscounter+holidayscounternext) {
                                                if (daysInMonthArray(selectedDate).get(counternext).equals("")) {
                                                    counternext++;
                                                    continue;

                                                }

                                                counternext++;
                                            }
                                            System.out.println("counternext is.." + counternext);
                                            System.out.println("counterdashnext..." + counterdashnext);
//                                            int counter1 = 1;
//                                            for (int k = 0; k < counterdashnext + counternext + holidayscounternext; k++) {
//                                                System.out.println(monthYearFromDate(selectedDate));
//
//                                                System.out.println("next month days for booking......." + daysInMonthArray(selectedDate).get(k));
//
//                                            }
                                            for (int k = counterdashnext; k < counterdashnext + counternext+ holidayscounternext ; k++) {
                                                System.out.println("Ku.." + k);
                                                if (bookeddaysnextmonth != null) {
                                                    System.out.println("......"+daysInMonthArray(selectedDate).get(k));

                                                    if (bookeddaysnextmonth.contains(daysInMonthArray(selectedDate).get(k))) {
                                                        System.out.println("k++..." + k);
                                                        System.out.println("already booked");
                                                        alreadybookednext = 2;
//                                                        Toast.makeText(CustomCalendar.this, "already booked", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }else if (k ==counterdashnext+counternext-1) {
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            selectedDate = selectedDate.minusMonths(1);
                                                        }
                                                        System.out.println("kii.."+k);
                                                        meal_next1=monthYearFromDate(selectedDate);
                                                        System.out.println(meal_next1);
                                                        addmealnext1=5;
                                                        System.out.println(addmealnext1);
                                                        //addMeal(days, mealtype);
                                                        //System.out.println("addmeal next1..." + counter1);
                                                        break;
                                                    }
                                                }else if (k ==counterdashnext+counternext) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                        selectedDate = selectedDate.minusMonths(1);
                                                    }
                                                    System.out.println("kooo.."+k);
                                                    meal_next1=monthYearFromDate(selectedDate);
                                                    addmealnext2=6;
                                                    System.out.println(addmealnext2);
                                                    //addMeal(days, mealtype);
                                                    //System.out.println("addmeal next1..." + counter1);
                                                    break;
                                                }

                                            }
                                            /*
                                            for (int k = 0; k < counterdashnext + counternext+ holidayscounternext ; k++) {
                                                System.out.println("K.."+k);
                                                System.out.println("next month days for booking...next......" + daysInMonthArray(selectedDate).get(k));
//                                                if (k > counterdashnext - 1) {
//                                                    System.out.println("counter1 is next month days counter .." + counter1);
//                                                    counter1++;
//                                                }
                                                if (bookeddaysnextmonth != null) {
                                                    System.out.println("OOOOO..."+monthYearFromDate(selectedDate));

                                                    if (bookeddaysnextmonth.contains(daysInMonthArray(selectedDate).get(k))) {
                                                        System.out.println("k+k"+k);
                                                        System.out.println("already booked");
                                                        alreadybookednext=2;
//                                                        Toast.makeText(CustomCalendar.this, "already booked", Toast.LENGTH_SHORT).show();
                                                        break;

                                                    }

//                                        System.out.println("counter..."+counter+"...holidaysthiscounter.."+holidaysthiscounter+
//                                     "..counter1..."+counternext+"..holidayscounternext.."+holidayscounternext);

                                                        int totaldaysnext=Integer.sum(counter-holidaysthiscounter,counternext-holidayscounternext);
                                                    if (totaldaysnext == days) {
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            selectedDate = selectedDate.minusMonths(1);
                                                        }
                                                        System.out.println("ki.."+k);
                                                        meal_next1=monthYearFromDate(selectedDate);
                                                        addmealnext1=5;
                                                        System.out.println(addmealnext1);
                                                        //addMeal(days, mealtype);
                                                        //System.out.println("addmeal next1..." + counter1);
                                                        break;
                                                    }
                                                } else {


                                                }

                                                //System.out.println(Integer.sum(counter-holidaysthiscounter,counter1-holidayscounternext));
                                            }
*/

                                        }
                                        if(alreadybookedthis==1||alreadybookednext==2){
                                            System.out.println(".."+alreadybookedthis+".."+alreadybookednext);
                                            System.out.println("already bookedddd");
                                        }
                                        else if(addmealthis1==3||addmealthis2==4){
                                            System.out.println(addmealthis1+"+"+addmealthis2);
                                            System.out.println(meal_this1);
                                            addMeal(days,mealtype);
                                            System.out.println("add meal1");
                                        }else if(addmealnext1==5||addmealnext2==6){
                                            System.out.println(addmealnext1+"+"+addmealnext2);
                                            System.out.println(meal_next1);
                                            addMeal(days,mealtype);
                                            System.out.println("add meal2");

                                        }
                                        // }
                                    }


                                }
                            });
                }

            }
        });
    }
    public void addLunchDinner(int days){
        fstore.collection("holidays").document("Ml4ziSIN1dW84IWC0YYD").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot.exists()) {
                        ArrayList<String> holidays = (ArrayList<String>) documentSnapshot.get(my);
                        ArrayList<String>holidaysnext=(ArrayList<String>) documentSnapshot.get(mynext);
                        ArrayList<String>daysthismonth=new ArrayList<>();
                        if (holidays != null && holidaysnext !=null){

                            System.out.println("not null...+not null...");
                            ArrayList<String> commondays = new ArrayList<>();
                            int i = index1, j,validdayscounter=0;
                            while (i <daysInMonthArray(selectedDate).size() ) {
                                if(!daysInMonthArray(selectedDate).get(i).equals("")){
                                    for (j = 0; j < holidays.size(); j++) {
                                        if (daysInMonthArray(selectedDate).get(i).equals(holidays.get(j))) {
                                            commondays.add(holidays.get(j));

                                            i++;
                                            System.out.println("mmm.."+daysInMonthArray(selectedDate).get(i));
                                            continue;
                                        }
                                    }
                                    cssd = commondays.size(); //commonsize of sevendays dinner

                                    String getdays = daysInMonthArray(selectedDate).get(i);
                                    if(getdays.equals("")){
                                        break;
                                    }
                                    daysthismonth.add(getdays);
                                    System.out.println(daysthismonth);

                                    fstore.collection("users").document(userID).collection("events").document("dinner")
                                            .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });
                                    fstore.collection("users").document(userID).collection("events").document("lunch")
                                            .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });
                                    i++;
                                    validdayscounter++;
                                    if(daysthismonth.size()==days) {

                                        Toast.makeText(getContext(), "dinner lunch"+  " date added", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), CustomCalendar.class);
                                        startActivity(intent);
                                        System.out.println("final break this");
                                        break;
                                    }


                                }else{
                                    System.out.println("this month valid days.. ="+daysthismonth.size());
                                    break;

                                }

                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            ArrayList<String>nextmonthdays=new ArrayList<>();
                            ArrayList<String> commondaysnextmonth=new ArrayList<>();;
                            int counterdashnextmonth=0;
                            int sizecommondaysnextmonth=0;
                            //for(int k=0;k<43;k++){
                            //for(int k=0;k<days+counterdashnextmonth-validdayscounter+sizecommondaysnextmonth;k++){
                            int k=0;
                            while(k<days+counterdashnextmonth-validdayscounter+sizecommondaysnextmonth){

                                if(daysInMonthArray(selectedDate).get(k).equals("")){
                                    k++;
                                    counterdashnextmonth++;
                                    System.out.println("counter dash next.."+commondaysnextmonth);
                                    continue;
                                }

                                System.out.println("next month days are.."+daysInMonthArray(selectedDate).get(k));
                                nextmonthdays.add(daysInMonthArray(selectedDate).get(k));
                                System.out.println("nnn.."+daysInMonthArray(selectedDate).get(k));
                                for(int p=0;p<holidaysnext.size();p++)
                                    if(daysInMonthArray(selectedDate).get(k).equals(holidaysnext.get(p))){
                                        commondaysnextmonth.add(holidaysnext.get(p));
                                        k++;
                                        System.out.println("value of k    "+k);
                                        continue;

                                    }
                                sizecommondaysnextmonth=commondaysnextmonth.size();
                                System.out.println("Size of holidays next month in this range"+sizecommondaysnextmonth);
                                fstore.collection("users").document(userID).collection("events").
                                        document("dinner").update(monthYearFromDate(selectedDate),FieldValue.arrayUnion(daysInMonthArray(selectedDate).get(k))).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                                fstore.collection("users").document(userID).collection("events").
                                        document("lunch").update(monthYearFromDate(selectedDate),FieldValue.arrayUnion(daysInMonthArray(selectedDate).get(k))).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                                System.out.println("val of k  "+k);

                                k++;

                                if(nextmonthdays.size()==days-daysthismonth.size()){
                                    Toast.makeText(getContext(), "dinner lunch"+  " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    System.out.println(" last value of k..."+k);
                                    break;
                                }
                                //}

                            }
                            System.out.println("no of blank in next month:.."+counterdashnextmonth);
                            int nextmonthdaysize=nextmonthdays.size();
                            System.out.println("next month size..."+nextmonthdaysize);
                        }else if (holidays ==null & holidaysnext ==null){

                            System.out.println(" null...+null...");
                            ArrayList<String>thismonthdays=new ArrayList<>();
                            ArrayList<String>nextmonthdays=new ArrayList<>();

                            for (int i = index1; i <43; i++) {
                                //for (int i = index1; i <= index1 + thismonthdays.size() + 1; i++) {
                                String getdays = daysInMonthArray(selectedDate).get(i);
                                if (getdays.equals("")) {
                                    i++;
                                    //continue;
                                    break;
                                }
                                thismonthdays.add(getdays);
                                fstore.collection("users").document(userID).collection("events").document("dinner")
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                fstore.collection("users").document(userID).collection("events").document("lunch")
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
//                                    thismonthdays.add(getdays);
                                int d = thismonthdays.size();
                                if(d==days){
                                    Toast.makeText(getContext(), "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);

                                    break;
                                }


                            }
                            System.out.println("OOOO..."+thismonthdays.size());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            int counterdashnext=0;
                            int i=0;
                            while(i<days-thismonthdays.size()+counterdashnext){
                                //while(i<days+counterdashnext-thismonthdays.size()){
                                String s=daysInMonthArray(selectedDate).get(i);

                                if(daysInMonthArray(selectedDate).get(i).equals("")){
                                    i++;
                                    counterdashnext++;
                                    continue;

                                }

                                nextmonthdays.add(s);
                                fstore.collection("users").document(userID).collection("events").document("dinner")
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(s))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                fstore.collection("users").document(userID).collection("events").document("lunch")
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(s))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                i++;

                                System.out.println("count is..."+counterdashnext);
                                if(thismonthdays.size()+nextmonthdays.size()==days) {
                                    Toast.makeText(getContext(), "dinner lunch" + " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    break;
                                }
                            }


                        } else if (holidays ==null & holidaysnext !=null){
                            System.out.println("   null...+not null...");

                            ArrayList<String>thismonthdays=new ArrayList<>();
                            int i = index1;
                            while ( i <43) {
                                String getdays = daysInMonthArray(selectedDate).get(i);

                                if(getdays.equals("")){
//                                    i++;
                                    break;
//                                    continue;
                                }
                                fstore.collection("users").document(userID).collection("events").document("dinner")
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                fstore.collection("users").document(userID).collection("events").document("lunch")
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                thismonthdays.add(getdays);
                                int d = thismonthdays.size();
                                if(d==days){
                                    Toast.makeText(getContext(), "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);

                                    break;
                                }
                                i++;

                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            ArrayList<String>nextmonthdays=new ArrayList<>();
                            ArrayList<String> commondaysnextmonth=new ArrayList<>();;
                            int counterdashnextmonth=0;
                            int sizecommondaysnextmonth=0;
                            int k=0;
                            while(k<days+counterdashnextmonth-thismonthdays.size()+sizecommondaysnextmonth){
                                if(daysInMonthArray(selectedDate).get(k).equals("")){
                                    k++;
                                    counterdashnextmonth++;
                                    continue;
                                }

                                System.out.println("next month days are.."+daysInMonthArray(selectedDate).get(k));
                                nextmonthdays.add(daysInMonthArray(selectedDate).get(k));
                                System.out.println("nnn.."+daysInMonthArray(selectedDate).get(k));
                                for(int p=0;p<holidaysnext.size();p++)
                                    if(daysInMonthArray(selectedDate).get(k).equals(holidaysnext.get(p))){
                                        commondaysnextmonth.add(holidaysnext.get(p));
                                        k++;

                                        continue;

                                    }
                                sizecommondaysnextmonth=commondaysnextmonth.size();
                                System.out.println("Size of holidays next month in this range"+sizecommondaysnextmonth);
                                fstore.collection("users").document(userID).collection("events").
                                        document("dinner").update(monthYearFromDate(selectedDate),FieldValue.arrayUnion(daysInMonthArray(selectedDate).get(k))).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                                fstore.collection("users").document(userID).collection("events").
                                        document("lunch").update(monthYearFromDate(selectedDate),FieldValue.arrayUnion(daysInMonthArray(selectedDate).get(k))).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                                if(nextmonthdays.size()==days-thismonthdays.size()){
                                    Toast.makeText(getContext(), "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    break;
                                }
                                k++;


                            }

                            //}
                            System.out.println("no of dash in next month:.."+counterdashnextmonth);
                            int nextmonthdaysize=nextmonthdays.size();
                            System.out.println("next month size..."+nextmonthdaysize);



                        }else {
                            System.out.println("  not null...+null...");

                            ArrayList<String> commondays = new ArrayList<>();
                            ArrayList<String>thismonthdays=new ArrayList<>();
                            ArrayList<String>nextmonthdays=new ArrayList<>();

                            int i = index1, j,validdayscounter=0;
                            while (i <43 ) {
                                if(!daysInMonthArray(selectedDate).get(i).equals("")){
                                    for (j = 0; j < holidays.size(); j++) {
                                        if (daysInMonthArray(selectedDate).get(i).equals(holidays.get(j))) {
                                            commondays.add(holidays.get(j));
                                            i++;
                                            continue;
                                        }
                                    }
                                    cssd = commondays.size(); //commonsize of sevendays dinner

                                    String getdays = daysInMonthArray(selectedDate).get(i);
                                    if(getdays.equals("")){
                                        break;
                                    }
                                    thismonthdays.add(getdays);
                                    fstore.collection("users").document(userID).collection("events").document("dinner")
                                            .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });
                                    fstore.collection("users").document(userID).collection("events").document("lunch")
                                            .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(getdays))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });

                                }


                                else{
                                    System.out.println("this month valid days ="+validdayscounter);
                                    break;

                                }


                                i++;
                                validdayscounter++;
                                thismonthdays.size();
                                if(thismonthdays.size()==days){
                                    Toast.makeText(getContext(), "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    break;
                                }

                            }


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            int counterdashnext=0;
                            int u=0;
                            while(u<days-thismonthdays.size()+counterdashnext){

                                if(daysInMonthArray(selectedDate).get(u).equals("")){
                                    u++;
                                    counterdashnext++;
                                    continue;

                                }
                                String s=daysInMonthArray(selectedDate).get(u);
                                nextmonthdays.add(s);

                                fstore.collection("users").document(userID).collection("events").document("dinner")
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(s))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                fstore.collection("users").document(userID).collection("events").document("lunch")
                                        .update(monthYearFromDate(selectedDate), FieldValue.arrayUnion(s))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                u++;
                                if(thismonthdays.size()+nextmonthdays.size()==days){
                                    Toast.makeText(getContext(), "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), CustomCalendar.class);
                                    startActivity(intent);
                                    break;
                                }

                            } System.out.println("count is..."+counterdashnext);

                        }

                    }

                }

            }
        });
    }
    public void checkthenaddLunchDinner(int days){

        fstore.collection("holidays").document("Ml4ziSIN1dW84IWC0YYD").
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                if(task.isSuccessful()){

                    ArrayList<String> holidays = (ArrayList<String>) documentSnapshot.get(my);
                    ArrayList<String>holidaysnext=(ArrayList<String>) documentSnapshot.get(mynext);
                    fstore.collection("users").document(userID).collection("events").document("dinner").get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(Task<DocumentSnapshot> task) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot.exists()) {
                                        ArrayList<String> bookeddaysthismonth = (ArrayList<String>) documentSnapshot.get(my);
                                        ArrayList<String> bookeddaysnextmonth = (ArrayList<String>) documentSnapshot.get(mynext);
                                        System.out.println("already booked days this month..." + bookeddaysthismonth);
                                        System.out.println("already booked days next month..." + bookeddaysnextmonth);
                                        int i = index1;
                                        int counter = 0;
                                        int holidaysthiscounter = 0;
                                        while (i < daysInMonthArray(selectedDate).size()) {
                                            System.out.println(daysInMonthArray(selectedDate).get(i));
//                                            while (i < index1+7+holidaysthiscounter) {
                                            if(counter-holidaysthiscounter==days){
                                                System.out.println("counter reached limit...to .."+counter+",,"+holidaysthiscounter);
                                                System.out.println(daysInMonthArray(selectedDate).get(i));
                                                break;
                                            }
                                            if (bookeddaysthismonth != null) {
                                                if (bookeddaysthismonth.contains(daysInMonthArray(selectedDate).get(i))) {
                                                    dinneroverlapthis = 1;
                                                    //Toast.makeText(CustomCalendar.this, "already booooked..." + dinneroverlapthis, Toast.LENGTH_SHORT).show();
                                                    System.out.println(daysInMonthArray(selectedDate).get(i));
                                                    break;
                                                } else if (!daysInMonthArray(selectedDate).get(i).equals("")) {
                                                    counter++;
                                                    if (holidays != null) {
                                                        if (holidays.contains(daysInMonthArray(selectedDate).get(i))) {
                                                            holidaysthiscounter++;
                                                            System.out.println("HOLI..."+holidaysthiscounter);

                                                        }
                                                    }

                                                }

                                                i++;

                                                if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                    System.out.println("last index of blank is .." + i);
                                                    System.out.println("No of days in this month if days gos to next month..counter. if bookd days this not null." + counter);
                                                    break;
                                                }

                                            } else {
                                                if (!daysInMonthArray(selectedDate).get(i).equals("")) {
                                                    counter++;
                                                    if (holidays != null) {
                                                        if (holidays.contains(daysInMonthArray(selectedDate).get(i))) {
                                                            holidaysthiscounter++;
                                                            System.out.println("HOLI Tihis.."+holidaysthiscounter);
                                                        }
                                                    }

                                                }

                                                i++;

                                                if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                    System.out.println("last index of blank is ..if bookd days this null." + i);
                                                    System.out.println("No of days in this month if some days fall in next.month..counter. if bookd days this  null.." + counter);
                                                    break;
                                                }
                                            }

                                        }
                                        System.out.println("holidays this counter=..." + holidaysthiscounter); //no of holidays in this range this month
                                        System.out.println("this month no of days counter.including holidays=..." + counter);
                                        if (counter < days + holidaysthiscounter) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                selectedDate = selectedDate.plusMonths(1);   //go to next month
                                            }
                                            int counternext = 0;
                                            int counterdashnext = 0;
                                            int holidayscounternext = 0;
                                            while (daysInMonthArray(selectedDate).get(counterdashnext).equals("")) {
                                                System.out.println("no of blanks in next month....." + counterdashnext);
                                                counterdashnext++;
                                            }

                                            for (int l = counterdashnext; l < counterdashnext + days - counter + holidaysthiscounter; l++) {
                                                System.out.println("counterdash next..." + counterdashnext + "counter..." + counter + " holidays this counter=" + holidaysthiscounter);
                                                System.out.println("next month days counter (l).." + daysInMonthArray(selectedDate).get(l));
                                                if (holidaysnext != null) {
                                                    if (holidaysnext.contains(daysInMonthArray(selectedDate).get(l))) {
                                                        holidayscounternext++;
                                                        System.out.println("holidaysnextcounter..." + holidayscounternext);
                                                    }
                                                }
                                            }
                                            while (counternext < days - 1 - counter + holidaysthiscounter) {
                                                if (daysInMonthArray(selectedDate).get(counternext).equals("")) {
                                                    counternext++;
                                                    System.out.println("counter next with blank..." + counternext);
                                                    continue;

                                                }

                                                counternext++;
                                            }
                                            System.out.println("counter next without blank..." + counternext);

                                            System.out.println("counterdashnext..." + counterdashnext);
                                            int counter1 = 1;

                                            for (int k = 0; k < counterdashnext + counternext + holidayscounternext; k++) {
                                                System.out.println("next month days for booking......." + daysInMonthArray(selectedDate).get(k));

                                            }
                                            for (int k = 0; k < counterdashnext + counternext + holidayscounternext+1; k++) {

                                                System.out.println("next month days for booking...next......" + daysInMonthArray(selectedDate).get(k));

                                                if (k > counterdashnext - 1) {
                                                    System.out.println("counter1 is next month days counter .." + counter1);
                                                    counter1++;
                                                }
                                                if (bookeddaysnextmonth != null) {
                                                    System.out.println("k.." + daysInMonthArray(selectedDate).get(k));
                                                    if (bookeddaysnextmonth.contains(daysInMonthArray(selectedDate).get(k))) {
                                                        dinneroverlapnext = 2;
                                                        System.out.println("already booked next month");
                                                        // Toast.makeText(CustomCalendar.this, "already next BBbooked..." + dinneroverlapnext, Toast.LENGTH_SHORT).show();

                                                        break;

                                                    }

                                                } else {

                                                    int totalDays = Integer.sum(counter - holidaysthiscounter, counter1 - holidayscounternext);
                                                    System.out.println(totalDays);

                                                }
                                            }
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                selectedDate = selectedDate.minusMonths(1);   //go to next month
                                            }
                                        }
//
                                        System.out.println("lunch ...."+monthYearFromDate(selectedDate));
                                        fstore.collection("users").document(userID).collection("events").document("lunch").get().
                                                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                        if (documentSnapshot.exists()) {
                                                            ArrayList<String> bookeddaysthismonth = (ArrayList<String>) documentSnapshot.get(my);
                                                            ArrayList<String> bookeddaysnextmonth = (ArrayList<String>) documentSnapshot.get(mynext);
                                                            System.out.println("already booked days this month..." + bookeddaysthismonth);
                                                            System.out.println("already booked days next month..." + bookeddaysnextmonth);
                                                            int i = index1;
                                                            int counter = 0;
                                                            int holidaysthiscounter = 0;
                                                            while (i < daysInMonthArray(selectedDate).size()) {
                                                                System.out.println(daysInMonthArray(selectedDate).get(i));
//                                            while (i < index1+7+holidaysthiscounter) {
                                                                if(counter-holidaysthiscounter==days){
                                                                    System.out.println("counter reached limit...due to lunch.."+counter+",,"+holidaysthiscounter);
                                                                    System.out.println(daysInMonthArray(selectedDate).get(i));
                                                                    break;
                                                                }
                                                                if (bookeddaysthismonth != null) {
                                                                    if (bookeddaysthismonth.contains(daysInMonthArray(selectedDate).get(i))) {
                                                                        dinneroverlapthis1 = 3;
                                                                        //Toast.makeText(CustomCalendar.this, "already booooked.lunch.." + dinneroverlapthis1, Toast.LENGTH_SHORT).show();

                                                                        System.out.println(daysInMonthArray(selectedDate).get(i));
                                                                        break;
                                                                    } else if (!daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                        counter++;
                                                                        if (holidays != null) {
                                                                            if (holidays.contains(daysInMonthArray(selectedDate).get(i))) {
                                                                                holidaysthiscounter++;
                                                                            }
                                                                        }

                                                                    }

                                                                    i++;

                                                                    if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                        System.out.println("last index of blank is .." + i);
                                                                        System.out.println("No of days in this month if days gos to next month..counter. if bookd days this not null." + counter);
                                                                        break;
                                                                    }
                                                                } else {
                                                                    if (!daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                        counter++;
                                                                        if (holidays != null) {
                                                                            if (holidays.contains(daysInMonthArray(selectedDate).get(i))) {
                                                                                holidaysthiscounter++;
                                                                            }
                                                                        }

                                                                    }

                                                                    i++;

                                                                    if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                        System.out.println("last index of blank is ..if bookd days this null." + i);
                                                                        System.out.println("No of days in this month if some days fall in next.month..counter. if bookd days this  null.." + counter);
                                                                        break;
                                                                    }
                                                                }

                                                            }
                                                            System.out.println("holidays this counter=..." + holidaysthiscounter); //no of holidays in this range this month
                                                            System.out.println("this month no of days counter.including holidays=..." + counter);
                                                            if (counter < days + holidaysthiscounter) {
                                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                    selectedDate = selectedDate.plusMonths(1);   //go to next month
                                                                }
                                                                int counternext = 0;
                                                                int counterdashnext = 0;
                                                                int holidayscounternext = 0;
                                                                while (daysInMonthArray(selectedDate).get(counterdashnext).equals("")) {
                                                                    System.out.println("no of blanks in next month....." + counterdashnext);
                                                                    counterdashnext++;
                                                                }

                                                                for (int l = counterdashnext; l < counterdashnext + days - counter + holidaysthiscounter; l++) {
                                                                    System.out.println("counterdash next..." + counterdashnext + "counter..." + counter + " holidays this counter=" + holidaysthiscounter);
                                                                    System.out.println("next month days counter (l).." + daysInMonthArray(selectedDate).get(l));
                                                                    if (holidaysnext != null) {
                                                                        if (holidaysnext.contains(daysInMonthArray(selectedDate).get(l))) {
                                                                            holidayscounternext++;
                                                                            System.out.println("holidaysnextcounter..." + holidayscounternext);
                                                                        }
                                                                    }
                                                                }
                                                                while (counternext < days - 1 - counter + holidaysthiscounter) {
                                                                    if (daysInMonthArray(selectedDate).get(counternext).equals("")) {
                                                                        counternext++;
                                                                        System.out.println("counter next with blank..." + counternext);
                                                                        continue;

                                                                    }

                                                                    counternext++;
                                                                }
                                                                System.out.println("counter next without blank..." + counternext);

                                                                System.out.println("counterdashnext..." + counterdashnext);
                                                                int counter1 = 1;

                                                                for (int k = 0; k < counterdashnext + counternext + holidayscounternext; k++) {
                                                                    System.out.println("next month days for booking......." + daysInMonthArray(selectedDate).get(k));

                                                                }
                                                                for (int k = 0; k < counterdashnext + counternext + holidayscounternext+1; k++) {

                                                                    System.out.println("next month days for booking...next......" + daysInMonthArray(selectedDate).get(k));

                                                                    if (k > counterdashnext - 1) {
                                                                        System.out.println("counter1 is next month days counter .." + counter1);
                                                                        counter1++;
                                                                    }
                                                                    if (bookeddaysnextmonth != null) {
                                                                        System.out.println("k.." + daysInMonthArray(selectedDate).get(k));
                                                                        if (bookeddaysnextmonth.contains(daysInMonthArray(selectedDate).get(k))) {
                                                                            dinneroverlapnext2 = 4;
                                                                            break;

                                                                        }

                                                                    } else {

                                                                        int totalDays = Integer.sum(counter - holidaysthiscounter, counter1 - holidayscounternext);
                                                                        System.out.println(totalDays);

                                                                    }
                                                                }
                                                            }


                                                        }

                                                        System.out.println(dinneroverlapthis+".."+dinneroverlapnext+".."+dinneroverlapthis1+".."+dinneroverlapnext2);
                                                        if (dinneroverlapthis == 1 || dinneroverlapnext == 2|| dinneroverlapnext2 == 4|| dinneroverlapthis1 == 3 ) {
                                                            Toast.makeText(getContext(), "already bookedddd" +dinneroverlapthis+"..."+dinneroverlapnext, Toast.LENGTH_SHORT).show();

                                                        }

                                                        else {
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                if(!monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now()))) {
                                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                        selectedDate = selectedDate.minusMonths(1);   //go to next month
                                                                    }
                                                                }
                                                            }
                                                            System.out.println(monthYearFromDate(selectedDate));
                                                            System.out.println("add luch dinner both ");
                                                            addLunchDinner(days);
                                                        }
                                                    }

                                                });

                                    }

                                }

                            });


                }

            }
        });

    }

    @Override
    public void onItemClick(int position, String dayText) {


        String[] arr = daysInMonthArray(selectedDate).toArray(new String[0]);
        startDate.setText(dayText + "  " + monthYearFromDate(selectedDate));
        int i1 = (Arrays.asList(arr).indexOf(dayText));
        index1 = i1;   //starting booking date index on click
        index7=i1+6;  //last booking date index for 7 days
        index15=i1+14; //last booking date index for 15 days
        index30=i1+30;  //last booking date index for 30 days
        System.out.println(index1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.now();
            int currentdate=date.getDayOfMonth();
            String currentDate=Integer.toString(currentdate);
            indexofcurrentdate = (Arrays.asList(arr).indexOf(currentDate));
            System.out.println("index"+indexofcurrentdate+monthYearFromDate(LocalDate.now()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if(monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now()))) {
                if (!dayText.equals("") && index1 > indexofcurrentdate) {
                    if (sevendaypaln.isChecked()) {
                        if (cbdinner.isChecked()) {
                            System.out.println("dinner 7");
                            System.out.println(monthYearFromDate(selectedDate));
                            checkandAddMeal(7,"dinner");

                            System.out.println("kkk...");

                        } else if (cblunch.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch 7");
                            checkandAddMeal(7,"lunch");
                        } else if (cblunchdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch dinner 7");
                            checkthenaddLunchDinner(7);
                        } else {
                            Toast.makeText(getContext(), "Please choose a meal type", Toast.LENGTH_SHORT).show();
                        }
                    } else if (fifteendayplan.isChecked()) {
                        if (cbdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("dinner 15");
                            checkandAddMeal(15,"dinner");
                        }

                        else if (cblunch.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch 15");
                            checkandAddMeal(15,"lunch");
                        } else if (cblunchdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch dinner 15");
                            checkthenaddLunchDinner(15);

                        } else {
                            Toast.makeText(getContext(), "Please choose a meal type", Toast.LENGTH_SHORT).show();
                        }
                    } else if (thirtydayplan.isChecked()) {
                        if (cbdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("dinner 30");
                            checkandAddMeal(30,"dinner");
                        } else if (cblunch.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch 30");
                            checkandAddMeal(30,"lunch");
                        } else if (cblunchdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch dinner 30");
                            checkthenaddLunchDinner(30);
                        } else {
                            Toast.makeText(getContext(), "Please choose a meal type", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Please choose a meal plan", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Please select appropriate incoming date", Toast.LENGTH_SHORT).show();
                }
            }
            else if (!dayText.equals("")){

                if (sevendaypaln.isChecked()) {
                    if (cbdinner.isChecked()) {
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("dinner 7");

                        checkandAddMeal(7,"dinner");

                    } else if (cblunch.isChecked()) {
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("lunch 7");
                        checkandAddMeal(7,"lunch");
                    }
                    else if (cblunchdinner.isChecked()) {
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("lunch dinner 7");
                        checkthenaddLunchDinner(7);
                    } else {
                        Toast.makeText(getContext(), "Please choose a meal type", Toast.LENGTH_SHORT).show();
                    }
                } else if (fifteendayplan.isChecked()) {
                    if (cbdinner.isChecked()) {
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("dinner 15");
                        checkandAddMeal(15,"dinner");
                    } else if (cblunch.isChecked()) {
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("lunch 15");
                        checkandAddMeal(15,"lunch");
                    } else if (cblunchdinner.isChecked()) {
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("lunch dinner 15");
                        checkthenaddLunchDinner(15);
                    } else {
                        Toast.makeText(getContext(), "Please choose a meal type", Toast.LENGTH_SHORT).show();
                    }
                } else if (thirtydayplan.isChecked()) {
                    if (cbdinner.isChecked()) {
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("dinner 30");
                        checkandAddMeal(30,"dinner");
                    } else if (cblunch.isChecked()) {
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("lunch 30");
                        checkandAddMeal(30,"lunch");

                    } else if (cblunchdinner.isChecked()) {
                        checkthenaddLunchDinner(30);
                        System.out.println(monthYearFromDate(selectedDate));
                        System.out.println("lunch dinner 30");
                    } else {
                        Toast.makeText(getContext(), "Please choose a meal type", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Please choose a meal plan", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getContext(), "Please select appropriate incoming date", Toast.LENGTH_SHORT).show();

            }
        }

    }

}