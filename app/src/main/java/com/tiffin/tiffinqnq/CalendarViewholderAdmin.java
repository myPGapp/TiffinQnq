package com.tiffin.tiffinqnq;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tiffin.tiffinqnq.R;

public class CalendarViewholderAdmin extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView dayOfMonth;
    TextView dinner,lunch,celldaytext;
    CheckBox cblunch,cbdinner,cblunchdinner,sevendaypaln,fifteendayplan,thirtydayplan;
    String my,mycd,userID;
    int index1,indexofcurrentdate;
    public  final AdapterCustomerView.OnItemListener onItemListener;
    public CalendarViewholderAdmin(View itemView,AdapterCustomerView.OnItemListener onItemListener,CheckBox cblunch,CheckBox cbdinner,CheckBox cblunchdinner,CheckBox sevendaypaln, CheckBox fifteendayplan, CheckBox thirtydayplan, String my,String mycd,String userID,int index1,int indexofcurrentdate) {
        super(itemView);
        dayOfMonth=itemView.findViewById(R.id.cellDayText);
        dinner=itemView.findViewById(R.id.dinner);
        lunch=itemView.findViewById(R.id.lunch);
        celldaytext=itemView.findViewById(R.id.cellDayText);
        this.onItemListener=onItemListener;
        this.cblunch=cblunch;
        this.cbdinner=cbdinner;
        this.cblunchdinner=cblunchdinner;
        this.sevendaypaln=sevendaypaln;
        this.fifteendayplan=fifteendayplan;
        this.thirtydayplan=thirtydayplan;
        this.my=my;
        this.mycd=mycd;
        this.userID=userID;
        this.index1=index1;
        this.indexofcurrentdate=indexofcurrentdate;
        itemView.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
//        if(dayOfMonth.getText().toString().equals("17")){
//            itemView.setVisibility(View.INVISIBLE);
//}
        if (!dayOfMonth.getText().equals("")) {
            if (my.equals(mycd) && index1 >= indexofcurrentdate) {

                if (cbdinner.isChecked()) {
                    //dinner.setBackgroundColor(Color.RED);
                    dinner.setBackgroundResource(R.drawable.round_grey);
                    //dayOfMonth.setBackgroundColor(Color.RED);
                } else if (cblunch.isChecked()) {
                    lunch.setBackgroundResource(R.drawable.round_green);
                } else if (cblunchdinner.isChecked()) {
                    dinner.setBackgroundResource(R.drawable.round_grey);
                    lunch.setBackgroundResource(R.drawable.round_green);
                } else {
                }
            } else if (my.equals(mycd) && index1 < indexofcurrentdate){

            } else if(!my.equals(mycd)){
                if (cbdinner.isChecked()) {
                    //dinner.setBackgroundColor(Color.RED);
                    dinner.setBackgroundResource(R.drawable.round_grey);
                } else if (cblunch.isChecked()) {
                    lunch.setBackgroundResource(R.drawable.round_green);
                } else if (cblunchdinner.isChecked()) {
                    dinner.setBackgroundResource(R.drawable.round_grey);
                    lunch.setBackgroundResource(R.drawable.round_green);
                }

            }
        }
        else{
        }
    }
}


