package com.naruto.trango.checkIn_checkOut;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;

import java.util.ArrayList;
import java.util.List;

public class CheckListDataAdapter extends RecyclerView.Adapter<CheckListDataAdapter.ViewHolder>{

    //private CheckInListDaa[] listData;
    private List<CheckInListDaa> mfeedItemList;
    private Context mContext;
    private View prevView;

    public CheckListDataAdapter(Context context, ArrayList<CheckInListDaa> feedItemList) {
        //this.listData = listData;
        mContext=context;
        mfeedItemList=feedItemList;
    }

    @NonNull
    @Override
    public CheckListDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.checkin_sigle_item, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListDataAdapter.ViewHolder holder, int position) {

        CheckInListDaa checkInListDaa=mfeedItemList.get(position);

        holder.staion.setText(checkInListDaa.getStation());
        holder.atime.setText(checkInListDaa.getaTime());
        holder.dTime.setText(checkInListDaa.getdTime());
        holder.station_details.setText(checkInListDaa.getStation_details());
        holder.full_timetable.setText(checkInListDaa.getFull_timetable());
        holder.statondot.setImageResource(checkInListDaa.getImgId());

        holder.staion.setOnClickListener(view -> {
            holder.statondot.setVisibility(View.VISIBLE);
            holder.station_details.setVisibility(View.VISIBLE);
            holder.rvCheckinDetails.setVisibility(View.VISIBLE);
            holder.staion.setTypeface(Typeface.DEFAULT_BOLD);
            prevView = view;
        });

    }

    @Override
    public int getItemCount() {
        return mfeedItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView staion,atime,dTime,station_details,full_timetable;
        private ImageView statondot;
        private RelativeLayout rvCheckinDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            staion = itemView.findViewById(R.id.station_tv);
            atime = itemView.findViewById(R.id.atime_tv);
            dTime = itemView.findViewById(R.id.dtime_tv);
            statondot = itemView.findViewById(R.id.station_dot);
            station_details = itemView.findViewById(R.id.station_details);
            full_timetable = itemView.findViewById(R.id.fulltimetable_tv);
            rvCheckinDetails = itemView.findViewById(R.id.rv_checkin_details);
        }
    }

}
