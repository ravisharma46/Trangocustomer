package com.naruto.trango.cabs_n_more.outstation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;

import java.util.ArrayList;
import java.util.List;

public class ActiveOutStationBookings extends Fragment {

    private final String TAG = "ActiveBookings";
    private View mRootView;

    private MyOutStationBookings mActivity;

    List<VehicleOutSation> mActiveRidesList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (MyOutStationBookings) getActivity();
    }

    public ActiveOutStationBookings() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG,"onCreateView");
        mRootView = inflater.inflate(R.layout.fragment_outstation_active_bookings, container, false);

        mActiveRidesList = new ArrayList<>();

        mActiveRidesList.add(new VehicleOutSation(1,"Mini","Indica, Micra, Ritz",
                50,"4+1", R.mipmap.mini, "Location",
                "Drop Point",12345678749L,12345678749L, 50 ));

        mActiveRidesList.add(new VehicleOutSation(1,"Mini","Indica, Micra, Ritz",
                50,"4+1",R.mipmap.mini, "Location",
                "Drop Point",12345678749L,12345678749L, 50 ));

        mActiveRidesList.add(new VehicleOutSation(1,"Mini","Indica, Micra, Ritz",
                50,"4+1",R.mipmap.mini, "Location",
                "Drop Point",12345678749L,12345678749L, 50 ));

        RecyclerView recyclerView = mRootView.findViewById(R.id.recycler_view_active_rides);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        OutActiveBookingAdapter adapter = new OutActiveBookingAdapter(getActivity(),mActiveRidesList);
        recyclerView.setAdapter(adapter);

        return mRootView;
    }

}