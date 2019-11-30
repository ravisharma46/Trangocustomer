package com.naruto.trango.cabs_n_more.outstation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;

import java.util.List;

public class OutActiveBookingAdapter extends RecyclerView.Adapter<OutActiveBookingAdapter.ActiveRidesViewHolder> {

    private Context mCtx;
    private List<VehicleOutSation> mActiveList;

    public OutActiveBookingAdapter(Context mCtx, List<VehicleOutSation> mActiveList) {
        this.mCtx = mCtx;
        this.mActiveList = mActiveList;
    }

    @NonNull
    @Override
    public ActiveRidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActiveRidesViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.recycler_view_active_rides,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveRidesViewHolder holder, int position) {

        VehicleOutSation ride = mActiveList.get(position);


        holder.wholeView.setOnClickListener(v -> mCtx.startActivity(new Intent(mCtx, ActiveBookingDetails.class)));

    }

    @Override
    public int getItemCount() {
        return mActiveList.size();
    }

    public class ActiveRidesViewHolder extends RecyclerView.ViewHolder {

        CardView wholeView;

        public ActiveRidesViewHolder(View itemView) {
            super(itemView);

            wholeView = itemView.findViewById(R.id.card_view);
        }
    }
}