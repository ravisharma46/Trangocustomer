package com.naruto.trango.cabs_n_more.outstation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;

import java.util.ArrayList;
import java.util.List;

public class RemovedOutStationBookings extends Fragment {

    private final String TAG = "RemovedBookimgs";
    private View mRootView;

    List<VehicleOutSation> mRemovedRides;

    public RemovedOutStationBookings() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG,"onCreateView");
        mRootView = inflater.inflate(R.layout.fragment_outstation_removed_bookings, container, false);

        mRemovedRides = new ArrayList<>();

        mRemovedRides.add(new VehicleOutSation(1,"Mini","Indica, Micra, Ritz",
                50,"4+1", R.mipmap.mini, "Location",
                "Drop Point",12345678749L,12345678749L, 50 ));

        mRemovedRides.add(new VehicleOutSation(1,"Mini","Indica, Micra, Ritz",
                50,"4+1",R.mipmap.mini, "Location",
                "Drop Point",12345678749L,12345678749L, 50 ));

        mRemovedRides.add(new VehicleOutSation(1,"Mini","Indica, Micra, Ritz",
                50,"4+1",R.mipmap.mini, "Location",
                "Drop Point",12345678749L,12345678749L, 50 ));

        RecyclerView recyclerView = mRootView.findViewById(R.id.recycler_view_removed_rides);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        OutRemovedBookingsAdapter adapter = new OutRemovedBookingsAdapter(getActivity(),mRemovedRides);
        recyclerView.setAdapter(adapter);

        return mRootView ;
    }


}