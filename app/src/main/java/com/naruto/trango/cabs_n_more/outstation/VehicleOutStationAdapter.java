package com.naruto.trango.cabs_n_more.outstation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;

import java.util.List;

public class VehicleOutStationAdapter extends RecyclerView.Adapter<VehicleOutStationAdapter.OutStationViewHolder> {

    private Context mCtx;
    private List<VehicleOutSation> mVehicleList;

    public VehicleOutStationAdapter(Context mCtx, List<VehicleOutSation> mVehicleList) {
        this.mCtx = mCtx;
        this.mVehicleList = mVehicleList;
    }

    @NonNull
    @Override
    public OutStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycle_view_vehicle_option,parent,false);
        return new OutStationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OutStationViewHolder holder, int position) {

        final VehicleOutSation vehicle = mVehicleList.get(position);

        holder.tv_name.setText(vehicle.getName());

        holder.tv_name2.setText(vehicle.getName());
        holder.tv_sample.setText("( "+vehicle.getSampleVehicles()+" )");

        int fare = vehicle.getFarePerKm();
        int dis = vehicle.getDistance();

        int totalFare = fare*dis;

        if(vehicle.getReturnUnix() == 0)
            holder.tv_total_fare.setText("\u20B9" +" "+totalFare);
        else {
            holder.tv_total_fare.setText("\u20B9" + " " + 2 * totalFare);
            holder.tv_add_charegs.setVisibility(View.VISIBLE);
        }

        holder.tv_person.setText(vehicle.getPerson());
        holder.tv_fare_per_km.setText("\u20B9"+" "+fare+".0/km");

        holder.iv_vehicle.setImageResource(vehicle.getImage());

        holder.wholeView.setOnClickListener(v -> {
            Intent i = new Intent(mCtx,ReviewOrder.class);
            i.putExtra("info",vehicle);
            mCtx.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return mVehicleList.size();
    }

    public class OutStationViewHolder extends RecyclerView.ViewHolder {

        CardView wholeView;

        TextView tv_name, tv_sample, tv_total_fare;
        TextView tv_name2, tv_person, tv_fare_per_km;
        ImageView iv_vehicle;

        TextView tv_add_charegs;

        public OutStationViewHolder(View itemView) {
            super(itemView);

            wholeView      = itemView.findViewById(R.id.cardView);

            tv_name        = itemView.findViewById(R.id.name);
            tv_name2       = itemView.findViewById(R.id.name2);

            tv_sample      = itemView.findViewById(R.id.sample);
            tv_total_fare  = itemView.findViewById(R.id.total_fare);
            tv_person      = itemView.findViewById(R.id.person);
            tv_fare_per_km = itemView.findViewById(R.id.fare_per_km);

            iv_vehicle     = itemView.findViewById(R.id.image_vehicle);

            tv_add_charegs = itemView.findViewById(R.id.text_add_charges);

        }
    }
}