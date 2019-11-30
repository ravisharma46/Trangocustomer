package com.naruto.trango.homepage.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.naruto.trango.Listners.ParkingBookingListener;
import com.naruto.trango.R;
import com.naruto.trango.commonfiles.CommonVariablesFunctions;
import com.naruto.trango.homepage.SimpleClasses.ParkingLocation;

import java.util.List;

public class ParkingLocationsAdapter extends RecyclerView.Adapter<ParkingLocationsAdapter
        .ParkingLocationViewHolder> {

    private final String TAG = "ParkingLocationAdapter";

    Context mCtx;
    List<ParkingLocation> mParkingLocation;
    ParkingBookingListener listener;

    public ParkingLocationsAdapter(Context mCtx, List<ParkingLocation> mParkingLocation, ParkingBookingListener listener) {
        this.mCtx = mCtx;
        this.mParkingLocation = mParkingLocation;
        this.listener = listener;

    }

    @Override
    public ParkingLocationsAdapter.ParkingLocationViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_view_parking_locations,parent,false);
        return new ParkingLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParkingLocationsAdapter.ParkingLocationViewHolder holder, int position) {

        final ParkingLocation parkingLocation = mParkingLocation.get(position);

        holder.location.setText(parkingLocation.getLocation());
        // Think about distance
        int parkingType = parkingLocation.getParkingType();
        String sParkingType = null;
        switch (parkingType){
            case 1: sParkingType = "Paid Parking"; break;
            case 2: sParkingType = "Free Parking"; break;
            case 3: sParkingType = "Garage Parking"; break;
        }

        holder.parking_type.setText(sParkingType);
        holder.distance.setText(""+parkingLocation.getDistanceFromMyLocation()+" km");


        //To do about vancant no. of parking
        holder.navigate.setOnClickListener((View.OnClickListener) v -> {

            //LatLng destination = parkingLocation.getLatLng();

            LatLng destination = new LatLng(Double.parseDouble(parkingLocation.getLatitude()),
                    Double.parseDouble(parkingLocation.getLongitude()));


            double latitude  = destination.latitude;
            double longitude = destination.longitude;

            CommonVariablesFunctions.sendNavigateIntent(mCtx,latitude,longitude);


        });

        // Book now button is hidden
        if(parkingType == 1 || parkingType == 3)
            holder.book_now.setVisibility(View.VISIBLE);
        else
            holder.book_now.setVisibility(View.GONE);


        holder.book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.BookNow(parkingLocation);
            }
        });


        // Setting fonts
        Typeface typefaceGBR = Typeface.createFromAsset(mCtx.getAssets(),"fonts/gotham_book_regular.otf");
        Typeface typefaceMM  = Typeface.createFromAsset(mCtx.getAssets(),"fonts/montserrat_medium.otf");
        Typeface typefaceMSB = Typeface.createFromAsset(mCtx.getAssets(),"fonts/montserrat_semi_bold.otf");

        holder.location.setTypeface(typefaceMM);
        holder.distance.setTypeface(typefaceMSB);
        holder.parking_type.setTypeface(typefaceMM);
        holder.vacant_bike_parking.setTypeface(typefaceMM);
        holder.vacant_car_parking.setTypeface(typefaceMM);
        holder.book_now.setTypeface(typefaceGBR);

        holder.textd.setTypeface(typefaceMM);
        holder.textv.setTypeface(typefaceMM);

        holder.vacant_car_parking.setText(""+parkingLocation.getInitialCarVacancy());
        holder.vacant_bike_parking.setText(""+parkingLocation.getInitialBikeVacancy());
    }


    @Override
    public int getItemCount() {
        return mParkingLocation.size();
    }

    public class ParkingLocationViewHolder extends RecyclerView.ViewHolder {

        TextView location, distance, parking_type;
        TextView vacant_bike_parking, vacant_car_parking;
        FloatingActionButton navigate;
        TextView book_now,textd,textv;


        public ParkingLocationViewHolder(View itemView) {
            super(itemView);

            location     = itemView.findViewById(R.id.location);
            distance     = itemView.findViewById(R.id.distance);
            parking_type = itemView.findViewById(R.id.parking_type);

            vacant_bike_parking   = itemView.findViewById(R.id.vacant_bike_parking);
            vacant_car_parking    = itemView.findViewById(R.id.vacant_car_parking);

            navigate     = itemView.findViewById(R.id.navigate);
            book_now     = itemView.findViewById(R.id.book_now);

            textd = itemView.findViewById(R.id.text_distance);
            textv = itemView.findViewById(R.id.textv);
        }

    }
}
