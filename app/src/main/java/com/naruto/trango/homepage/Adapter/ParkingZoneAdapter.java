package com.naruto.trango.homepage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.naruto.trango.R;
import com.naruto.trango.homepage.SimpleClasses.ParkingZone;

import java.util.List;

public class ParkingZoneAdapter extends RecyclerView.Adapter<ParkingZoneAdapter.ParkingZoneViewHolder> {

    List<ParkingZone> mParkingZoneList;
    private Context context;

    public ParkingZoneAdapter(List<ParkingZone> mParkingZoneList, Context context) {
//        this.mCtx = mCtx;
        this.context = context;
        this.mParkingZoneList = mParkingZoneList;
    }

    @Override
    public ParkingZoneAdapter.ParkingZoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.parking_zone_single_layout,parent,false);
        return new ParkingZoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingZoneAdapter.ParkingZoneViewHolder holder, int position) {

        ParkingZone zone = mParkingZoneList.get(position);

        int parking_no = position+1;

        Glide.with(context)
                .load(zone.getImage_id())
                .apply(new RequestOptions().centerCrop()
                        .fitCenter())
                .into(holder.iv_icon);

        holder.tv_title.setText(""+parking_no+"."+zone.getTitle());

    }

    @Override
    public int getItemCount() {
        return mParkingZoneList.size();
    }

    public class ParkingZoneViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        TextView tv_title;

        public ParkingZoneViewHolder(View itemView) {
            super(itemView);

            iv_icon  = itemView.findViewById(R.id.icon);
            tv_title = itemView.findViewById(R.id.title);
        }
    }
}
