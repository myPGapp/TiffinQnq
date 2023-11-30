package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.Map;

public class CustomCalendar extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    protected TextView monthYearText,textView,startDate,endDate;
    RecyclerView calendarRecyclerVIew;
    LocalDate selectedDate;
    TextView monthyearTV;
    FirebaseAuth auth;
    Button btnhome,btnpay;
    int dinneroverlapthis,dinneroverlapnext,dinneroverlapthis1,dinneroverlapnext2,bookingfallinthismonth,bookingfallintwomonths,
            alreadybookedthis,alreadybookednext,addmealthis1,addmealthis2,addmealnext1,addmealnext2,a,fieldSizeDinner,fieldSizeLunch;
    String meal_this1,meal_this2,meal_next1,meal_next2;
    CheckBox cblunch,cbdinner,cblunchdinner,sevendaypaln,fifteendayplan,thirtydayplan,cbcustom;
    String userID,stDate,enDate,meal;
    FirebaseFirestore fstore;
    int indexofcurrentdate;
    EditText edtdaysNo;
    TextView dinnerab,lunchab,lunchdinnerab,holiday,dinnerbooked,lunchbooked;
    int cssd;
    int index1,index7,index15,index30;
    String my,mynext,mynextnext;
    ProgressBar progressBar;
    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_calendar);
        cbdinner=findViewById(R.id.cbdinner);
        cblunch=findViewById(R.id.cblunch);
        cbcustom=findViewById(R.id.cbcustom);
        textView=findViewById(R.id.textView);
        monthyearTV=findViewById(R.id.monthYearTV);
        btnhome=findViewById(R.id.btnhome);
        dinnerab=findViewById(R.id.dinnerab);
        lunchab=findViewById(R.id.lunchab);
        btnpay=findViewById(R.id.btnpay);
        lunchdinnerab=findViewById(R.id.lunchdinnerab);
        holiday=findViewById(R.id.holiday);
        dinnerbooked=findViewById(R.id.dinnerbooked);
        lunchbooked=findViewById(R.id.lunchbooked);

//        startDate=findViewById(R.id.startDate);
//        endDate=findViewById(R.id.endDate);
        sevendaypaln=findViewById(R.id.sevendayplan);
        fifteendayplan=findViewById(R.id.fifteendayplan);
        thirtydayplan=findViewById(R.id.thirtydayplan);
        cblunchdinner=findViewById(R.id.cblunchdinner);
        //edtdaysNo=findViewById(R.id.edtdaysNo);
        initWidgets();
//        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        selectedDate=LocalDate.now();
        setMonthView();
        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
//        holiday.setBackgroundColor(Color.RED);
//        dinnerbooked.setBackgroundColor(R.color.dark_gray);
//        lunchbooked.setBackgroundColor(R.color.green);
       lunchdinnerab.setBackgroundResource(R.color.blue);
       dinnerbooked.setBackgroundResource(R.color.dinner_booked);
//        dinnerab.setBackgroundColor(R.color.gray);
        lunchab.setBackgroundColor(Color.CYAN);

//        startDate.setText("");
//        endDate.setText("");
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


        cbcustom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sevendaypaln.setEnabled(false);
                    fifteendayplan.setEnabled(false);
                    thirtydayplan.setEnabled(false);
                    if(cbdinner.isChecked()){
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(CustomCalendar.this);
                        final EditText edittext = new EditText(CustomCalendar.this);
                        edittext.setText("5");
//                        Integer.parseInt(edittext.getText().toString());
                        alert.setMessage("You are booking DINNER !!");
                        alert.setTitle("Please enter Number of days of booking and click/choose start date");

                        alert.setView(edittext);

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                a = Integer.parseInt(edittext.getText().toString());
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });
                        alert.show();

                    }else if(cblunch.isChecked()){
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(CustomCalendar.this);
                        final EditText edittext = new EditText(CustomCalendar.this);
                        edittext.setText("5");
//                        Integer.parseInt(edittext.getText().toString());
                        alert.setMessage("You are booking LUNCH !!");
                        alert.setTitle("Please enter Number of days of booking and click/choose start date");

                        alert.setView(edittext);

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                a = Integer.parseInt(edittext.getText().toString());
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });
                        alert.show();

                    }else if(cblunchdinner.isChecked()){
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(CustomCalendar.this);
                        final EditText edittext = new EditText(CustomCalendar.this);
                        edittext.setText("5");
//                        Integer.parseInt(edittext.getText().toString());
                        alert.setMessage("You are booking LUNCH+DINNER !!");
                        alert.setTitle("Please enter Number of days of booking and click/choose start date");

                        alert.setView(edittext);

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                a = Integer.parseInt(edittext.getText().toString());
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });
                        alert.show();

                    }

                }else{
                    sevendaypaln.setEnabled(true);
                    fifteendayplan.setEnabled(true);
                    thirtydayplan.setEnabled(true);
                }
            }
        });
        sevendaypaln.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fifteendayplan.setEnabled(false);
                    thirtydayplan.setEnabled(false);
                    cbcustom.setEnabled(false);
                }else{
                    fifteendayplan.setEnabled(true);
                    thirtydayplan.setEnabled(true);
                    cbcustom.setEnabled(true);
                }

            }
        });
        fifteendayplan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sevendaypaln.setEnabled(false);
                    thirtydayplan.setEnabled(false);
                    cbcustom.setEnabled(false);
                }else{
                    sevendaypaln.setEnabled(true);
                    thirtydayplan.setEnabled(true);
                    cbcustom.setEnabled(true);
                }

            }
        });
        thirtydayplan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sevendaypaln.setEnabled(false);
                    fifteendayplan.setEnabled(false);
                    cbcustom.setEnabled(false);
                }else{
                    sevendaypaln.setEnabled(true);
                    fifteendayplan.setEnabled(true);
                    cbcustom.setEnabled(true);
                }

            }
        });
        //cbmealtype(cbdinner,cblunch,cblunchdinner,meal);

        cbdinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!cbcustom.isChecked()){
                    if(b){
                        AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                        alert.setTitle(" Please choose starting date");
                        alert.setMessage("You are booking DINNER !!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alert.show();
                        cblunch.setEnabled(false);
                        cblunchdinner.setEnabled(false);

                    }else {
                        cblunch.setEnabled(true);
                        cblunchdinner.setEnabled(true);
                    }
                }else{
                    if(b){
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(CustomCalendar.this);
                        final EditText edittext = new EditText(CustomCalendar.this);
                        edittext.setText("5");
//                        Integer.parseInt(edittext.getText().toString());
                        alert.setMessage("You are booking DINNER !!");
                        alert.setTitle("Please enter Number of days of booking and click/choose start date");

                        alert.setView(edittext);

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                a = Integer.parseInt(edittext.getText().toString());
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });
                        alert.show();
                    /*
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                alert.setTitle(" Please choose starting date");
                alert.setMessage("You are booking DINNER !!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                    alert.show();
                    */
                        cblunch.setEnabled(false);
                        cblunchdinner.setEnabled(false);

                    }else{
                        cblunch.setEnabled(true);
                        cblunchdinner.setEnabled(true);
                    }
                }
            }
        });
        cblunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!cbcustom.isChecked()){
                    if(b){
                        AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                        alert.setTitle(" Please choose starting date");
                        alert.setMessage("You are booking LUNCH !!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
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
                }else{
                    if(b){
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(CustomCalendar.this);
                        final EditText edittext = new EditText(CustomCalendar.this);
                        edittext.setText("8");
//                        Integer.parseInt(edittext.getText().toString());
                        alert.setMessage("You are booking LUNCH !!");
                        alert.setTitle("Please enter Number of days of booking and click/choose start date");

                        alert.setView(edittext);

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                a = Integer.parseInt(edittext.getText().toString());
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });
                        alert.show();
                    /*
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                alert.setTitle(" Please choose starting date");
                alert.setMessage("You are booking DINNER !!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                    alert.show();
                    */
                        cbdinner.setEnabled(false);
                        cblunchdinner.setEnabled(false);

                    }else{
                        cbdinner.setEnabled(true);
                        cblunchdinner.setEnabled(true);
                    }
                }
//

            }
        });
        cblunchdinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!cbcustom.isChecked()){
                    if(b){
                        AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                        alert.setTitle(" Please choose starting date");
                        alert.setMessage("You are booking LUNCH+DINNER !!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alert.show();
                        cblunch.setEnabled(false);
                        cbdinner.setEnabled(false);

                    }else {
                        cblunch.setEnabled(true);
                        cbdinner.setEnabled(true);
                    }
                }else{
                    if(b){
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(CustomCalendar.this);
                        final EditText edittext = new EditText(CustomCalendar.this);
                        edittext.setText("8");
//                        Integer.parseInt(edittext.getText().toString());
                        alert.setMessage("You are booking LUNCH+DINNER !!");
                        alert.setTitle("Please enter Number of days of booking and click/choose start date");

                        alert.setView(edittext);

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //What ever you want to do with the value
                                a = Integer.parseInt(edittext.getText().toString());
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });
                        alert.show();
                    /*
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                alert.setTitle(" Please choose starting date");
                alert.setMessage("You are booking DINNER !!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                    alert.show();
                    */
                        cblunch.setEnabled(false);
                        cbdinner.setEnabled(false);

                    }else{
                        cblunch.setEnabled(true);
                        cbdinner.setEnabled(true);
                    }
                }
//

            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(intent);
                finish();

            }
        });

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
        ArrayList<String>daysInMonth=daysInMonthArray(selectedDate);
        CalendarAdapter calendarAdapter=new CalendarAdapter(daysInMonth,this,my,mycd,index1,indexofcurrentdate,cblunch,cbdinner,cblunchdinner,sevendaypaln,fifteendayplan,thirtydayplan);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),7);
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
        calendarRecyclerVIew=findViewById(R.id.calendarRecyclerView);
        monthYearText=findViewById(R.id.monthYearTV);
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
    public void addMeal(int days, String mealtype){

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
                                            System.out.println("aaa..."+daysInMonthArray(selectedDate).get(i));
                                            continue;
                                        }
                                    }
                                    cssd = commondays.size(); //holidays falling in booking range

                                    String getdays = daysInMonthArray(selectedDate).get(i);
                                    if(getdays.equals("")){
                                        break;
                                    }
                                    daysthismonth.add(getdays);
                                    System.out.println(daysthismonth); //days this month array

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

                                        AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                        alert.setTitle("Congratulations");
                                        alert.setMessage("You have successfully booked "+mealtype+" for "+days+" days")
                                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                        startActivity(intent);
                                                        finish();
                                                        //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                        alert.show();

                                        //Toast.makeText(CustomCalendar.this, mealtype+  " date added", Toast.LENGTH_SHORT).show();

                                        System.out.println("bbb...");
                                        break;
                                    }


                                }else{
                                    System.out.println("ccc... ="+daysthismonth.size());
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
                            int k=0;
                            while(k<days+counterdashnextmonth-validdayscounter+sizecommondaysnextmonth){

                                if(daysInMonthArray(selectedDate).get(k).equals("")){
                                    k++;
                                    counterdashnextmonth++;
                                    System.out.println("ddd..."+commondaysnextmonth);
                                    continue;
                                }

                                System.out.println("eee..."+daysInMonthArray(selectedDate).get(k));
                                nextmonthdays.add(daysInMonthArray(selectedDate).get(k));
                                System.out.println("fff..."+daysInMonthArray(selectedDate).get(k));
                                for(int p=0;p<holidaysnext.size();p++)
                                    if(daysInMonthArray(selectedDate).get(k).equals(holidaysnext.get(p))){
                                        commondaysnextmonth.add(holidaysnext.get(p));
                                        k++;
                                        System.out.println("fff..."+k);
                                        continue;

                                    }
                                sizecommondaysnextmonth=commondaysnextmonth.size();
                                System.out.println("jjj..."+sizecommondaysnextmonth);
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
                                System.out.println("kkk..."+k);

                                k++;
                                System.out.println("lll..."+nextmonthdays.size()+".."+daysthismonth.size());
                                if(nextmonthdays.size()==days-daysthismonth.size()){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked "+mealtype+" for "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
                                    System.out.println(" mmm..."+k);
                                    break;
                                }
                                int nextmonthdaysize=nextmonthdays.size();
                                System.out.println("nnn..."+nextmonthdaysize);
                            }
                            System.out.println("ooo..."+counterdashnextmonth);
                            int nextmonthdaysize=nextmonthdays.size();
//                            System.out.println("next month size..."+nextmonthdaysize);
                        }else if (holidays ==null & holidaysnext ==null){

                            System.out.println(" null...+null...");
                            ArrayList<String>thismonthdays=new ArrayList<>();
                            ArrayList<String>nextmonthdays=new ArrayList<>();

                            for (int i = index1; i <daysInMonthArray(selectedDate).size(); i++) {
                                String getdays = daysInMonthArray(selectedDate).get(i);
                                if (getdays.equals("")) {
                                    i++;
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
                                int d = thismonthdays.size();
                                if(d==days){

                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked "+mealtype+" for "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();

                                    break;
                                }


                            }
                            System.out.println("ppp..."+thismonthdays.size());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            int counterdashnext=0;
                            int i=0;
                            while(i<days-thismonthdays.size()+counterdashnext){
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

                                System.out.println("qqq..."+counterdashnext);
                                if(thismonthdays.size()+nextmonthdays.size()==days) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked "+mealtype+" for "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
                                    break;
                                }
                            }


                        } else if (holidays ==null & holidaysnext !=null){
                            System.out.println("   null...+not null...");

                            ArrayList<String>thismonthdays=new ArrayList<>();
                            int i = index1;
                            while ( i <daysInMonthArray(selectedDate).size()) {
                                String getdays = daysInMonthArray(selectedDate).get(i);

                                if(getdays.equals("")){
                                    break;
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

                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked "+mealtype+" for "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();

                                    break;
                                }
                                i++;

                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                selectedDate=selectedDate.plusMonths(1);
                            }
                            ArrayList<String>nextmonthdays=new ArrayList<>();
                            ArrayList<String> commondaysnextmonth=new ArrayList<>();
                            int counterdashnextmonth=0;
                            int sizecommondaysnextmonth=0;
                            int k=0;
                            while(k<days+counterdashnextmonth-thismonthdays.size()+sizecommondaysnextmonth){
                                if(daysInMonthArray(selectedDate).get(k).equals("")){
                                    k++;
                                    counterdashnextmonth++;
                                    continue;
                                }
                                System.out.println("rrrss..."+counterdashnextmonth);

                                System.out.println("rrr..."+daysInMonthArray(selectedDate).get(k));
                                //nextmonthdays.add(daysInMonthArray(selectedDate).get(k));
                                System.out.println("sss..."+daysInMonthArray(selectedDate).get(k));
                                for(int p=0;p<holidaysnext.size();p++) {
                                    if (daysInMonthArray(selectedDate).get(k).equals(holidaysnext.get(p))) {
                                        commondaysnextmonth.add(holidaysnext.get(p));
                                        k++;
                                        System.out.println("sssa'..." + commondaysnextmonth.size());

                                        continue;

                                    }
                                }
                                nextmonthdays.add(daysInMonthArray(selectedDate).get(k));
                                System.out.println("sssaa!"+nextmonthdays.size());
                                System.out.println("sssa "+daysInMonthArray(selectedDate).get(k));
                                sizecommondaysnextmonth=commondaysnextmonth.size();
                                //System.out.println("Size of holidays next month in this range"+sizecommondaysnextmonth);
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
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked "+mealtype+" for "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
                                    break;
                                }
                                k++;


                            }

                            //}

                            System.out.println("ttt..."+counterdashnextmonth);
                            int nextmonthdaysize=nextmonthdays.size();
                            System.out.println("uuu..."+nextmonthdaysize);



                        }else {
                            System.out.println("  not null...+null...");

                            ArrayList<String> commondays = new ArrayList<>();
                            ArrayList<String>thismonthdays=new ArrayList<>();
                            ArrayList<String>nextmonthdays=new ArrayList<>();

                            int i = index1, j,validdayscounter=0;
                            while (i <daysInMonthArray(selectedDate).size() ) {
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
                                    System.out.println("vvv..."+validdayscounter);
                                    break;

                                }


                                i++;
                                validdayscounter++;
                                thismonthdays.size();
                                if(thismonthdays.size()==days){
                                    //Toast.makeText(CustomCalendar.this, mealtype+   " date added", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked "+mealtype+" for "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
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
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked "+mealtype+" for "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
                                    break;
                                }

                            } System.out.println("www..."+counterdashnext);

                        }

                    }

                }

            }
        });

    }
    public void checkandAddMeal(int days,String mealtype)
    {
        System.out.println("item..."+userID);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = firestore.collection("users").document(userID);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (!document.get("name").equals(""))
                        {
                            fstore.collection("holidays").document("Ml4ziSIN1dW84IWC0YYD").
                                    get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(Task<DocumentSnapshot> task) {
                                            DocumentSnapshot documentSnapshot=task.getResult();
                                            if(task.isSuccessful()){

                                                ArrayList<String> holidays = (ArrayList<String>) documentSnapshot.get(my);
                                                ArrayList<String>holidaysnext=(ArrayList<String>) documentSnapshot.get(mynext);
                                                fstore.collection("users").document(userID).collection("events").document(mealtype).get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(Task<DocumentSnapshot> task) {
                                                                DocumentSnapshot documentSnapshot=task.getResult();
                                                                if(documentSnapshot.exists()) {
                                                                    ArrayList<String> bookeddaysthismonth = (ArrayList<String>) documentSnapshot.get(my);
                                                                    ArrayList<String> bookeddaysnextmonth = (ArrayList<String>) documentSnapshot.get(mynext);
                                                                    int i = index1;
                                                                    int counter = 0;
                                                                    int holidaysthiscounter = 0;
                                                                    while (i < daysInMonthArray(selectedDate).size()) {
                                                                        if (bookeddaysthismonth != null) {
                                                                            if (bookeddaysthismonth.contains(daysInMonthArray(selectedDate).get(i))) {

                                                                                //Toast.makeText(CustomCalendar.this, "already booked", Toast.LENGTH_SHORT).show();
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
                                                                                    System.out.println("xxx..." + counter);
                                                                                    System.out.println("yyy..." + daysInMonthArray(selectedDate).get(index1 + counter));
                                                                                    System.out.println("add meal this month1");
                                                                                    meal_this1=monthYearFromDate(selectedDate);
                                                                                    addmealthis1=3;
                                                                                    //addMeal(days, mealtype);
                                                                                    break;

                                                                                }

                                                                            }

                                                                            i++;

                                                                            if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                                System.out.println("zzz..." + i);
                                                                                System.out.println("ab..." + counter);
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
                                                                                    System.out.println("ac..." + counter);
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
                                                                                System.out.println("ad..." + i);
                                                                                System.out.println("ae..." + counter);
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                    System.out.println("af..." + holidaysthiscounter); //no of holidays in this range this month
                                                                    System.out.println("ag..." + counter);
                                                                    if (counter < days + holidaysthiscounter) {
                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                            selectedDate = selectedDate.plusMonths(1);
                                                                        }
                                                                        int counternext = 0;
                                                                        int counterdashnext = 0;
                                                                        int holidayscounternext = 0;
                                                                        while (daysInMonthArray(selectedDate).get(counterdashnext).equals("")) {
                                                                            System.out.println("ah..." + counterdashnext);
                                                                            counterdashnext++;
                                                                        }

                                                                        for (int l = counterdashnext; l < counterdashnext + days - counter + holidaysthiscounter; l++) {
                                                                            System.out.println("ai..." + counterdashnext + "aj..." + counter + "ak..." + holidaysthiscounter);
                                                                            System.out.println("al..." + daysInMonthArray(selectedDate).get(l));
                                                                            if (holidaysnext != null) {
                                                                                if (holidaysnext.contains(daysInMonthArray(selectedDate).get(l))) {
                                                                                    holidayscounternext++;
                                                                                    System.out.println("am..." + holidayscounternext);
                                                                                }
                                                                            }
                                                                        }
                                                                        System.out.println("......."+holidayscounternext);
                                                                        while (counternext < days  - counter + holidaysthiscounter+holidayscounternext) {
                                                                            if (daysInMonthArray(selectedDate).get(counternext).equals("")) {
                                                                                counternext++;
                                                                                continue;

                                                                            }

                                                                            counternext++;
                                                                        }
                                                                        System.out.println("an..." + counternext);
                                                                        System.out.println("ao..." + counterdashnext);
                                                                        for (int k = counterdashnext; k < counterdashnext + counternext+ holidayscounternext ; k++) {
                                                                            System.out.println("ap..." + k);

                                                                            if (bookeddaysnextmonth != null) {
                                                                                System.out.println("aq..."+daysInMonthArray(selectedDate).get(k));

                                                                                if (bookeddaysnextmonth.contains(daysInMonthArray(selectedDate).get(k))) {
                                                                                    System.out.println("ar..." + k);
                                                                                    System.out.println("already booked");
                                                                                    alreadybookednext = 2;
                                                                                    break;
                                                                                }else if (k ==counterdashnext+counternext-1) {
                                                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                                        selectedDate = selectedDate.minusMonths(1);
                                                                                    }
                                                                                    System.out.println("as..."+k);
                                                                                    meal_next1=monthYearFromDate(selectedDate);
                                                                                    System.out.println(meal_next1);
                                                                                    addmealnext1=5;
                                                                                    System.out.println(addmealnext1);
                                                                                    break;
                                                                                }
                                                                            }else if (k ==counterdashnext+counternext-1) {
                                                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                                    selectedDate = selectedDate.minusMonths(1);
                                                                                }
                                                                                System.out.println("at..."+k);
                                                                                meal_next1=monthYearFromDate(selectedDate);
                                                                                addmealnext2=6;
                                                                                System.out.println(addmealnext2);
                                                                                break;
                                                                            }

                                                                        }

                                                                    }
                                                                    if(alreadybookedthis==1||alreadybookednext==2){
                                                                        System.out.println(".."+alreadybookedthis+".."+alreadybookednext);

                                                                        AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                                                        alert.setTitle("OOps !!");
                                                                        alert.setMessage("You already have booked days in this date range, Plz check")
                                                                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                                        Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                                                        startActivity(intent);
                                                                                        finish();
                                                                                        //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                                                        alert.show();
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
                                                                }


                                                            }
                                                        });
                                            }

                                        }
                                    });

                        }
                        else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                            alert.setTitle(" Please update Profile with address");
                            alert.setMessage("Profile update required before booking").setPositiveButton("Update profile now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (document.exists()) {
                                       String phone= document.getString("phoneNo");
                                        //Toast.makeText(CustomCalendar.this, phone, Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(CustomCalendar.this, OtpUpdateProfile.class);
                                        intent.putExtra("phone",phone);
                                        startActivity(intent);
                                        finish();
                                    }
//                                    Intent intent=new Intent(CustomCalendar.this, OtpUpdateProfile.class);
//                            startActivity(intent);
                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("book later", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(CustomCalendar.this, MainActivity.class);
                            startActivity(intent);
                                    finish();
                                }
                            });
                            alert.show();
//
//                            Intent intent=new Intent(CustomCalendar.this, OtpUpdateProfile.class);
//                            startActivity(intent);
//                            Toast.makeText(CustomCalendar.this, "Now plz proceed for booking", Toast.LENGTH_SHORT).show();
                       }
                    }
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

                                        //Toast.makeText(CustomCalendar.this, "dinner lunch"+  " date added", Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                        alert.setTitle("Congratulations");
                                        alert.setMessage("You have successfully booked for lunch and dinner for  "+days+" days")
                                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                        startActivity(intent);
                                                        finish();
                                                        //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                        alert.show();
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
                                    // Toast.makeText(CustomCalendar.this, "dinner lunch"+  " date added", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked for lunch and dinner for  "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
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
                                String getdays = daysInMonthArray(selectedDate).get(i);
                                if (getdays.equals("")) {
                                    i++;
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
                                int d = thismonthdays.size();
                                if(d==days){
                                    // Toast.makeText(CustomCalendar.this, "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked for lunch and dinner for  "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();

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
                                    // Toast.makeText(CustomCalendar.this, "dinner lunch" + " date added", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked for lunch and dinner for  "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
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
                                    break;
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
                                    //Toast.makeText(CustomCalendar.this, "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked for lunch and dinner for  "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();

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
                                    //Toast.makeText(CustomCalendar.this, "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked for lunch and dinner for  "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
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
                                    //Toast.makeText(CustomCalendar.this, "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked for lunch and dinner for  "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
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
                                    //Toast.makeText(CustomCalendar.this, "dinner lunch"+   " date added", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                    alert.setTitle("Congratulations");
                                    alert.setMessage("You have successfully booked for lunch and dinner for  "+days+" days")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                    startActivity(intent);
                                                    finish();
                                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                    alert.show();
                                    break;
                                }

                            } System.out.println("count is..."+counterdashnext);

                        }

                    }

                }

            }
        });
    }
    public void checkthenaddLunchDinner(int days)
    {
        DocumentReference docIdRef = fstore.collection("users").document(userID);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (!document.get("name").equals("")){
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
                                                                    System.out.println("checkthenaddLunchDinner  booked days this month" + bookeddaysthismonth);
                                                                    System.out.println("checkthenaddLunchDinner  booked days next month" + bookeddaysnextmonth);
                                                                    int i = index1;
                                                                    int counter = 0;
                                                                    int holidaysthiscounter = 0;
                                                                    while (i < daysInMonthArray(selectedDate).size()) {
                                                                        System.out.println(daysInMonthArray(selectedDate).get(i));
                                                                        if(counter-holidaysthiscounter==days){

                                                                            System.out.println("baa....checkthenaddLunchDinner ."+counter+",,"+holidaysthiscounter);
                                                                            System.out.println(daysInMonthArray(selectedDate).get(i));
                                                                            break;
                                                                        }
                                                                        if (bookeddaysthismonth != null) {
                                                                            if (bookeddaysthismonth.contains(daysInMonthArray(selectedDate).get(i))) {
                                                                                dinneroverlapthis = 1;
                                                                                System.out.println(daysInMonthArray(selectedDate).get(i));
                                                                                break;
                                                                            } else if (!daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                                counter++;
                                                                                if (holidays != null) {
                                                                                    if (holidays.contains(daysInMonthArray(selectedDate).get(i))) {
                                                                                        holidaysthiscounter++;
                                                                                        System.out.println("bab....checkthenaddLunchDinner ."+holidaysthiscounter);

                                                                                    }
                                                                                }

                                                                            }

                                                                            i++;

                                                                            if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                                System.out.println("bac....checkthenaddLunchDinner ." + i);
                                                                                System.out.println("bad....checkthenaddLunchDinner .." + counter);
                                                                                break;
                                                                            }

                                                                        } else {
                                                                            if (!daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                                counter++;
                                                                                if (holidays != null) {
                                                                                    if (holidays.contains(daysInMonthArray(selectedDate).get(i))) {
                                                                                        holidaysthiscounter++;
                                                                                        System.out.println("bad....checkthenaddLunchDinner ..."+holidaysthiscounter);
                                                                                    }
                                                                                }

                                                                            }

                                                                            i++;

                                                                            if (daysInMonthArray(selectedDate).get(i).equals("")) {
                                                                                System.out.println("bae....checkthenaddLunchDinner ." + i);
                                                                                System.out.println("baf....checkthenaddLunchDinner ..." + counter);
                                                                                break;
                                                                            }
                                                                        }

                                                                    }
                                                                    System.out.println("bag....checkthenaddLunchDinner ...." + holidaysthiscounter); //no of holidays in this range this month
                                                                    System.out.println("bah....checkthenaddLunchDinner ...." + counter);
                                                                    if (counter < days + holidaysthiscounter) {
                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                            selectedDate = selectedDate.plusMonths(1);   //go to next month
                                                                        }
                                                                        int counternext = 0;
                                                                        int counterdashnext = 0;
                                                                        int holidayscounternext = 0;
                                                                        while (daysInMonthArray(selectedDate).get(counterdashnext).equals("")) {
                                                                            System.out.println("bai....checkthenaddLunchDinner ....." + counterdashnext);
                                                                            counterdashnext++;
                                                                        }

                                                                        for (int l = counterdashnext; l < counterdashnext + days - counter + holidaysthiscounter; l++) {
                                                                            System.out.println("baj....checkthenaddLunchDinner .." + counterdashnext + "bak....checkthenaddLunchDinner .." + counter + " bal....checkthenaddLunchDinner ." + holidaysthiscounter);
                                                                            System.out.println("bal....checkthenaddLunchDinner ." + daysInMonthArray(selectedDate).get(l));
                                                                            if (holidaysnext != null) {
                                                                                if (holidaysnext.contains(daysInMonthArray(selectedDate).get(l))) {
                                                                                    holidayscounternext++;
                                                                                    System.out.println("bam....checkthenaddLunchDinner ." + holidayscounternext);
                                                                                }
                                                                            }else {
                                                                                System.out.println("ban....checkthenaddLunchDinner ."+daysInMonthArray(selectedDate).get(l));

                                                                            }
                                                                        }
                                                                        while (counternext < days - 1 - counter + holidaysthiscounter) {
                                                                            if (daysInMonthArray(selectedDate).get(counternext).equals("")) {
                                                                                counternext++;
                                                                                System.out.println("bao....checkthenaddLunchDinner ...." + counternext);
                                                                                continue;

                                                                            }

                                                                            counternext++;
                                                                        }
                                                                        System.out.println("bap....checkthenaddLunchDinner ..." + counternext);

                                                                        System.out.println("baq....checkthenaddLunchDinner ." + counterdashnext);
                                                                        int counter1 = 1;

                                                                        for (int k = 0; k < counterdashnext + counternext + holidayscounternext; k++) {
                                                                            System.out.println("bar....checkthenaddLunchDinner ." + daysInMonthArray(selectedDate).get(k));

                                                                        }
                                                                        for (int k = 0; k < counterdashnext + counternext + holidayscounternext+1; k++) {

                                                                            System.out.println("baas....checkthenaddLunchDinner ....." + daysInMonthArray(selectedDate).get(k));

                                                                            if (k > counterdashnext - 1) {
                                                                                System.out.println("bat....checkthenaddLunchDinner .." + counter1);
                                                                                counter1++;
                                                                            }
                                                                            if (bookeddaysnextmonth != null) {
                                                                                System.out.println("bau....checkthenaddLunchDinner ." + daysInMonthArray(selectedDate).get(k));
                                                                                if (bookeddaysnextmonth.contains(daysInMonthArray(selectedDate).get(k))) {
                                                                                    dinneroverlapnext = 2;
                                                                                    System.out.println("bav....checkthenaddLunchDinner .");

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
                                                                    System.out.println("baw....checkthenaddLunchDinner ."+monthYearFromDate(selectedDate));
                                                                    fstore.collection("users").document(userID).collection("events").document("lunch").get().
                                                                            addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(Task<DocumentSnapshot> task) {
                                                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                                                    if (documentSnapshot.exists()) {
                                                                                        ArrayList<String> bookeddaysthismonth = (ArrayList<String>) documentSnapshot.get(my);
                                                                                        ArrayList<String> bookeddaysnextmonth = (ArrayList<String>) documentSnapshot.get(mynext);
                                                                                        System.out.println("bax....checkthenaddLunchDinner ...." + bookeddaysthismonth);
                                                                                        System.out.println("bay....checkthenaddLunchDinner ..." + bookeddaysnextmonth);
                                                                                        int i = index1;
                                                                                        int counter = 0;
                                                                                        int holidaysthiscounter = 0;
                                                                                        while (i < daysInMonthArray(selectedDate).size()) {
                                                                                            System.out.println(daysInMonthArray(selectedDate).get(i));
                                                                                            System.out.println("...."+(counter - holidaysthiscounter));
                                                                                            if (counter - holidaysthiscounter+1 == days) {
                                                                                                bookingfallinthismonth = 7;
                                                                                                System.out.println("baz....checkthenaddLunchDinner ..." + counter + ",," + holidaysthiscounter);
                                                                                                System.out.println(daysInMonthArray(selectedDate).get(i));
                                                                                                break;
                                                                                            }
                                                                                            if (bookeddaysthismonth != null) {
                                                                                                if (bookeddaysthismonth.contains(daysInMonthArray(selectedDate).get(i))) {
                                                                                                    dinneroverlapthis1 = 3;
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
                                                                                                    System.out.println("baza....checkthenaddLunchDinner ..." + i);
                                                                                                    System.out.println("bazb....checkthenaddLunchDinner ...." + counter);
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
                                                                                                    System.out.println("bazc....checkthenaddLunchDinner ...." + i);
                                                                                                    System.out.println("bazd....checkthenaddLunchDinner ....." + counter);
                                                                                                    break;
                                                                                                }
                                                                                            }

                                                                                        }
                                                                                        System.out.println("baze....checkthenaddLunchDinner ....." + holidaysthiscounter); //no of holidays in this range this month
                                                                                        System.out.println("bazf....checkthenaddLunchDinner ...." + counter);
                                                                                        if (counter < days + holidaysthiscounter-1) {
                                                                                            bookingfallintwomonths = 8;
                                                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                                                selectedDate = selectedDate.plusMonths(1);   //go to next month
                                                                                            }
                                                                                            int counternext = 0;
                                                                                            int counterdashnext = 0;
                                                                                            int holidayscounternext = 0;
                                                                                            while (daysInMonthArray(selectedDate).get(counterdashnext).equals("")) {
                                                                                                System.out.println("bazg....checkthenaddLunchDinner ...." + counterdashnext);
                                                                                                counterdashnext++;
                                                                                            }

                                                                                            for (int l = counterdashnext; l < counterdashnext + days - counter + holidaysthiscounter; l++) {
                                                                                                System.out.println("bazg....checkthenaddLunchDinner ....." + counterdashnext + "bazh....checkthenaddLunchDinner ..." + counter + " bazi....checkthenaddLunchDinner ..." + holidaysthiscounter);
                                                                                                System.out.println("bazj....checkthenaddLunchDinner ..." + daysInMonthArray(selectedDate).get(l));
                                                                                                if (holidaysnext != null) {
                                                                                                    if (holidaysnext.contains(daysInMonthArray(selectedDate).get(l))) {
                                                                                                        holidayscounternext++;
                                                                                                        System.out.println("bazk....checkthenaddLunchDinner ....." + holidayscounternext);
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                            while (counternext < days - 1 - counter + holidaysthiscounter) {
                                                                                                if (daysInMonthArray(selectedDate).get(counternext).equals("")) {
                                                                                                    counternext++;
                                                                                                    System.out.println("bazl....checkthenaddLunchDinner ...." + counternext);
                                                                                                    continue;

                                                                                                }

                                                                                                counternext++;
                                                                                            }
                                                                                            System.out.println("bazm....checkthenaddLunchDinner ....." + counternext);

                                                                                            System.out.println("bazn....checkthenaddLunchDinner ......" + counterdashnext);
                                                                                            int counter1 = 1;

                                                                                            for (int k = 0; k < counterdashnext + counternext + holidayscounternext; k++) {
                                                                                                System.out.println("bazo....checkthenaddLunchDinner .........." + daysInMonthArray(selectedDate).get(k));

                                                                                            }
                                                                                            for (int k = 0; k < counterdashnext + counternext + holidayscounternext + 1; k++) {

                                                                                                System.out.println("bazp....checkthenaddLunchDinner ........." + daysInMonthArray(selectedDate).get(k));

                                                                                                if (k > counterdashnext - 1) {
                                                                                                    System.out.println("bazq....checkthenaddLunchDinner ... .." + counter1);
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

                                                                                    System.out.println(dinneroverlapthis + ".." + dinneroverlapnext + ".." + dinneroverlapthis1 + ".." + dinneroverlapnext2);
                                                                                    if (dinneroverlapthis == 1 || dinneroverlapnext == 2 || dinneroverlapnext2 == 4 || dinneroverlapthis1 == 3) {
                                                                                        AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                                                                        alert.setTitle("OOps !!");
                                                                                        alert.setMessage("You already have booked days in this date range, Plz check")
                                                                                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                                                        Intent intent = new Intent(getApplicationContext(), CustomCalendar.class);
                                                                                                        startActivity(intent);
                                                                                                        finish();
                                                                                                        //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                });
//                                               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                           @Override
//                                           public void onClick(DialogInterface dialogInterface, int i) {
//
//                                           }
//                                       });
                                                                                        alert.show();
                                                                                        // Toast.makeText(CustomCalendar.this, "already bookedddd" +dinneroverlapthis+"..."+dinneroverlapnext, Toast.LENGTH_SHORT).show();

                                                                                    } else if (bookingfallintwomonths == 8) {
                                                                                        System.out.println("...kkk");
                                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                                                                            if (!monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now()))) {


                                                                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                                                    selectedDate = selectedDate.minusMonths(1);   //go to next month
                                                                                                }


                                                                                                System.out.println(monthYearFromDate(selectedDate));
                                                                                                System.out.println("bazr....checkthenaddLunchDinner ... ");
                                                                                                addLunchDinner(days);
                                                                                            }
                                                                                        }
                                                                                    }else {
                                                                                        System.out.println("bazs....checkthenaddLunchDinner ... ");

                                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                                            if (!monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now()))) {

                                                                                                System.out.println(monthYearFromDate(selectedDate));
//                                                                    System.out.println("bazs....checkthenaddLunchDinner ... ");
                                                                                                addLunchDinner(days);
                                                                                            }else{
                                                                                                System.out.println("bazt....checkthenaddLunchDinner ... ");
                                                                                                addLunchDinner(days);
                                                                                            }
                                                                                        }


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

                        else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                            alert.setTitle(" Please update Profile with address");
                            alert.setMessage("Profile update required before booking").setPositiveButton("Update profile now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DocumentReference docIdRef = fstore.collection("users").document(userID);
                                    docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    String phone= document.getString("phoneNo");
                                                    //Toast.makeText(CustomCalendar.this, phone, Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(CustomCalendar.this, OtpUpdateProfile.class);
                                                    intent.putExtra("phone",phone);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }
                                        }
                                    });


//                                    Intent intent=new Intent(CustomCalendar.this, OtpUpdateProfile.class);
//                                    startActivity(intent);
                                    //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("book later", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(CustomCalendar.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            alert.show();
//
//                            Intent intent=new Intent(CustomCalendar.this, OtpUpdateProfile.class);
//                            startActivity(intent);
//                            Toast.makeText(CustomCalendar.this, "Now plz proceed for booking", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });



    }

    @Override

    public void onItemClick(int position, String dayText) {


    /*
    {


//      System.out.println(day-1+"   ..ok..");
        //a=Integer.parseInt(edtdaysNo.getText().toString());
        String[] arr = daysInMonthArray(selectedDate).toArray(new String[0]);
        //startDate.setText(dayText + "  " + monthYearFromDate(selectedDate));
        int i1 = (Arrays.asList(arr).indexOf(dayText));
        index1 = i1;   //starting booking date index on click
        index7 = i1 + 6;  //last booking date index for 7 days
        index15 = i1 + 14; //last booking date index for 15 days
        index30 = i1 + 30;  //last booking date index for 30 days
        System.out.println(index1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.now();
            int currentdate = date.getDayOfMonth();
            String currentDate = Integer.toString(currentdate);
            indexofcurrentdate = (Arrays.asList(arr).indexOf(currentDate));
            System.out.println("index" + indexofcurrentdate + monthYearFromDate(LocalDate.now()));
        }
        if (!cbcustom.isChecked()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                if (monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now()))) {
                    if (!dayText.equals("") && index1 > indexofcurrentdate) {


                        if (sevendaypaln.isChecked()) {
                            if (cbdinner.isChecked()) {
                                System.out.println("dinner 7");
                                System.out.println(monthYearFromDate(selectedDate));
                                checkandAddMeal(7, "dinner");

                                System.out.println("kkk...");

                            } else if (cblunch.isChecked()) {
                                System.out.println(monthYearFromDate(selectedDate));
                                System.out.println("lunch 7");
                                checkandAddMeal(7, "lunch");
                            } else if (cblunchdinner.isChecked()) {
                                System.out.println(monthYearFromDate(selectedDate));
                                System.out.println("lunch dinner 7");
                                checkthenaddLunchDinner(7);
                            } else {
                                Toast.makeText(this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                            }
                        } else if (fifteendayplan.isChecked()) {
                            if (cbdinner.isChecked()) {
                                System.out.println(monthYearFromDate(selectedDate));
                                System.out.println("dinner 15");
                                checkandAddMeal(15, "dinner");
                            } else if (cblunch.isChecked()) {
                                System.out.println(monthYearFromDate(selectedDate));
                                System.out.println("lunch 15");
                                checkandAddMeal(15, "lunch");
                            } else if (cblunchdinner.isChecked()) {
                                System.out.println(monthYearFromDate(selectedDate));
                                System.out.println("lunch dinner 15");
                                checkthenaddLunchDinner(15);

                            } else {
                                Toast.makeText(this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                            }
                        } else if (thirtydayplan.isChecked()) {
                            if (cbdinner.isChecked()) {
                                System.out.println(monthYearFromDate(selectedDate));
                                System.out.println("dinner 30");
                                checkandAddMeal(30, "dinner");
                            } else if (cblunch.isChecked()) {
                                System.out.println(monthYearFromDate(selectedDate));
                                System.out.println("lunch 30");
                                checkandAddMeal(30, "lunch");
                            } else if (cblunchdinner.isChecked()) {
                                System.out.println(monthYearFromDate(selectedDate));
                                System.out.println("lunch dinner 30");
                                checkthenaddLunchDinner(30);
                            } else {
                                Toast.makeText(this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Please choose a meal plan", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Please select appropriate incoming date", Toast.LENGTH_SHORT).show();
                    }
                } else if (!dayText.equals("")) {

                    if (sevendaypaln.isChecked()) {
                        if (cbdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("dinner 7");

                            checkandAddMeal(7, "dinner");

                        } else if (cblunch.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch 7");
                            checkandAddMeal(7, "lunch");
                        } else if (cblunchdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch dinner 7");
                            checkthenaddLunchDinner(7);
                        } else {
                            Toast.makeText(this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                        }
                    } else if (fifteendayplan.isChecked()) {
                        if (cbdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("dinner 15");
                            checkandAddMeal(15, "dinner");
                        } else if (cblunch.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch 15");
                            checkandAddMeal(15, "lunch");
                        } else if (cblunchdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch dinner 15");
                            checkthenaddLunchDinner(15);
                        } else {
                            Toast.makeText(this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                        }
                    } else if (thirtydayplan.isChecked()) {
                        if (cbdinner.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("dinner 30");
                            checkandAddMeal(30, "dinner");
                        } else if (cblunch.isChecked()) {
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch 30");
                            checkandAddMeal(30, "lunch");

                        } else if (cblunchdinner.isChecked()) {
                            checkthenaddLunchDinner(30);
                            System.out.println(monthYearFromDate(selectedDate));
                            System.out.println("lunch dinner 30");
                        } else {
                            Toast.makeText(this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Please choose a meal plan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please select appropriate incoming date", Toast.LENGTH_SHORT).show();

                }
            }
        }else
        {
            DocumentReference documentReference=fstore.collection("users").document(userID).collection("events").document("dinner");
            documentReference  .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot !=null){
                            Map<String, Object> map=documentSnapshot.getData();
                            fieldSizeDinner=map.size();
                            System.out.println("....++"+fieldSizeDinner);
                            DocumentReference documentReference1=fstore.collection("users").document(userID).collection("events").document("lunch");
                            documentReference1  .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        DocumentSnapshot documentSnapshot=task.getResult();
                                        if(documentSnapshot !=null){
                                            Map<String, Object> map=documentSnapshot.getData();
                                            fieldSizeLunch=map.size();
                                            System.out.println("....++"+fieldSizeDinner);
                                            System.out.println("ff.."+fieldSizeLunch+"d.."+fieldSizeDinner);
                                            if(fieldSizeDinner<2 && fieldSizeLunch<2){
                                                if(a<7){
                                                    //Toast.makeText(CustomCalendar.this, "First time minimum 7 day booking allowed", Toast.LENGTH_SHORT).show();
                                                    AlertDialog.Builder alert = new AlertDialog.Builder(CustomCalendar.this);
                                                    alert.setTitle(" First time Booking");
                                                    alert.setMessage("First time booking, minimum 7 days required !!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            //Toast.makeText(CustomCalendar.this, "Thanks", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                        }
                                                    });
                                                    alert.show();
                                                } else{
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                                        if (monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now()))) {
                                                            if (!dayText.equals("") && index1 > indexofcurrentdate) {
                                                                if (cbdinner.isChecked()) {
                                                                    System.out.println("dinner 7");
                                                                    System.out.println(monthYearFromDate(selectedDate));

                                                                    checkandAddMeal(a, "dinner");

                                                                    System.out.println("kkk...");

                                                                } else if (cblunch.isChecked()) {
                                                                    System.out.println(monthYearFromDate(selectedDate));
                                                                    System.out.println("lunch 7");
                                                                    checkandAddMeal(a, "lunch");
                                                                } else if (cblunchdinner.isChecked()) {
                                                                    System.out.println(monthYearFromDate(selectedDate));
                                                                    System.out.println("lunch dinner 7");
                                                                    checkthenaddLunchDinner(a);
                                                                } else {
                                                                    Toast.makeText(CustomCalendar.this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(CustomCalendar.this, "Please select appropriate incoming date", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else if (!dayText.equals("")) {


                                                            if (cbdinner.isChecked()) {
                                                                System.out.println(monthYearFromDate(selectedDate));
                                                                System.out.println("dinner 7");

                                                                checkandAddMeal(a, "dinner");

                                                            } else if (cblunch.isChecked()) {
                                                                System.out.println(monthYearFromDate(selectedDate));
                                                                System.out.println("lunch 7");
                                                                checkandAddMeal(a, "lunch");
                                                            } else if (cblunchdinner.isChecked()) {
                                                                System.out.println(monthYearFromDate(selectedDate));
                                                                System.out.println("lunch dinner 7");
                                                                checkthenaddLunchDinner(a);
                                                            } else {
                                                                Toast.makeText(CustomCalendar.this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(CustomCalendar.this, "Please select appropriate incoming date", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                }
                                            } else{
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                                    if (monthYearFromDate(selectedDate).equals(monthYearFromDate(LocalDate.now()))) {
                                                        if (!dayText.equals("") && index1 > indexofcurrentdate) {
                                                            if (cbdinner.isChecked()) {
                                                                System.out.println("dinner 7");
                                                                System.out.println(monthYearFromDate(selectedDate));

                                                                checkandAddMeal(a, "dinner");

                                                                System.out.println("kkk...");

                                                            } else if (cblunch.isChecked()) {
                                                                System.out.println(monthYearFromDate(selectedDate));
                                                                System.out.println("lunch 7");
                                                                checkandAddMeal(a, "lunch");
                                                            } else if (cblunchdinner.isChecked()) {
                                                                System.out.println(monthYearFromDate(selectedDate));
                                                                System.out.println("lunch dinner 7");
                                                                checkthenaddLunchDinner(a);
                                                            } else {
                                                                Toast.makeText(CustomCalendar.this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(CustomCalendar.this, "Please select appropriate incoming date", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else if (!dayText.equals("")) {


                                                        if (cbdinner.isChecked()) {
                                                            System.out.println(monthYearFromDate(selectedDate));
                                                            System.out.println("dinner 7");

                                                            checkandAddMeal(a, "dinner");

                                                        } else if (cblunch.isChecked()) {
                                                            System.out.println(monthYearFromDate(selectedDate));
                                                            System.out.println("lunch 7");
                                                            checkandAddMeal(a, "lunch");
                                                        } else if (cblunchdinner.isChecked()) {
                                                            System.out.println(monthYearFromDate(selectedDate));
                                                            System.out.println("lunch dinner 7");
                                                            checkthenaddLunchDinner(a);
                                                        } else {
                                                            Toast.makeText(CustomCalendar.this, "Please choose a meal type", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(CustomCalendar.this, "Please select appropriate incoming date", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            }

                                        }

                                    }

                                }
                            });

                        }

                    }

                }
            });




        }

    }
    */
    }
}

