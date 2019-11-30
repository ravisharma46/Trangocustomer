package com.naruto.trango.homepage.SimpleClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.naruto.trango.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateAdapterViewHolder> {

    private Context mCtx;
    private Calendar mCalendar;
    private Date mDate;
    private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    private String mDateSelected;

    public DateAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public DateAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_view_calender,parent,false);
        return new DateAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAdapterViewHolder holder, final int position) {

        mCalendar     = Calendar.getInstance();
        mDate         = mCalendar.getTime();
        mDateSelected = df.format(mDate);

        if(position!=0)
            mCalendar.add(Calendar.DATE,position);

        int day             = mCalendar.get(Calendar.DAY_OF_WEEK);
        int dateToSet       = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month           = mCalendar.get(Calendar.MONTH);

        String dayToSet     = getDay(day);
        String monthToSet   = getMonth(month);


        holder.tv_day.setText(dayToSet);
        holder.tv_date.setText(String.valueOf(dateToSet));
        holder.tv_month.setText(monthToSet);

        Log.e("dateSelected",mDateSelected);

        holder.ll_dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE,position);
                Date date = calendar.getTime();
                mDateSelected = df.format(date);

                Log.e("dateSelected",mDateSelected);
            }
        });


    }



    @Override
    public int getItemCount() {
        return 10;
    }
    public class DateAdapterViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_dateLayout;
        TextView tv_day,tv_date,tv_month;

        public DateAdapterViewHolder(View itemView) {
            super(itemView);

            ll_dateLayout = itemView.findViewById(R.id.date_layout);
            tv_day        = itemView.findViewById(R.id.day);
            tv_date       = itemView.findViewById(R.id.date);
            tv_month      = itemView.findViewById(R.id.month);
        }
    }

    private String getDay(int day)
    {
        switch (day){
            case Calendar.SUNDAY    : return "Sun";
            case Calendar.MONDAY    : return "Mon";
            case Calendar.TUESDAY   : return "Tue";
            case Calendar.WEDNESDAY : return "Wed";
            case Calendar.THURSDAY  : return "Thu";
            case Calendar.FRIDAY    : return "Fri";
            case Calendar.SATURDAY  : return "Sat";
        }
        return "N/A";
    }

    private String getMonth(int month) {

        switch (month){
            case Calendar.JANUARY   : return "Jan";
            case Calendar.FEBRUARY  : return "Feb";
            case Calendar.MARCH     : return "Mar";
            case Calendar.APRIL     : return "Apr";
            case Calendar.MAY       : return "May";
            case Calendar.JUNE      : return "Jun";
            case Calendar.JULY      : return "Jul";
            case Calendar.AUGUST    : return "Aug";
            case Calendar.SEPTEMBER : return "Sep";
            case Calendar.OCTOBER   : return "Oct";
            case Calendar.NOVEMBER  : return "Nov";
            case Calendar.DECEMBER  : return "Dec";
        }
        return "N/A";

    }

}
