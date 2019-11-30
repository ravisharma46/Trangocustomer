package com.naruto.trango.cabs_n_more.outstation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;

import java.util.List;

public class OutRemovedBookingsAdapter extends RecyclerView.Adapter<OutRemovedBookingsAdapter.ActiveRidesViewHolder> {

    private Context mCtx;
    private List<VehicleOutSation> mRemovedList;

    public OutRemovedBookingsAdapter(Context mCtx, List<VehicleOutSation> mRemovedList) {
        this.mCtx = mCtx;
        this.mRemovedList = mRemovedList;
    }

    @NonNull
    @Override
    public ActiveRidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActiveRidesViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.recycler_view_removed_rides,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveRidesViewHolder holder, int position) {

        VehicleOutSation ride = mRemovedList.get(position);

    }

    @Override
    public int getItemCount() {
        return mRemovedList.size();
    }

    public class ActiveRidesViewHolder extends RecyclerView.ViewHolder {

        CardView wholeView;

        public ActiveRidesViewHolder(View itemView) {
            super(itemView);

            wholeView = itemView.findViewById(R.id.card_view);
        }
    }
}