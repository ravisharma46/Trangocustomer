package com.naruto.trango.cabs_n_more.outstation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VehicleOptionPage extends AppCompatActivity {

    private final  String TAG = getClass().getSimpleName();

    private OutStationLocation mSelectedLocation;
    private Long mDepartureUnix;
    private Long mReturnUnix;

    private List<VehicleOutSation> mVehhicleList;

    SimpleDateFormat mDateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_option);

        Window window = getWindow();
        window.setStatusBarColor(0);

        mVehhicleList  = new ArrayList<>();
        mDateFormatter = new SimpleDateFormat("EEE, d MMM hh:mm a", Locale.getDefault());

        getInfoFromIntent();
        setData();

        clickListener();
        setRecycleView();
    }



    private void getInfoFromIntent() {

        Bundle bundle     = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            mSelectedLocation = (OutStationLocation) bundle.getSerializable("selectedLocation");
        }
        if (bundle != null) {
            mDepartureUnix    = bundle.getLong("departure_unix");
            mReturnUnix       = bundle.getLong("return_unix");
        }

        Log.e(TAG, mSelectedLocation.getDropLocation() + " departure="+mDepartureUnix + " return="+mReturnUnix);
    }

    @SuppressLint("SetTextI18n")
    private void setData() {

        TextView tv_from       = findViewById(R.id.from);
        TextView tv_to         = findViewById(R.id.to);
        ImageView iv_arrow     = findViewById(R.id.arrow);
        TextView tv_departure  = findViewById(R.id.departure_time);
        TextView tv_return     = findViewById(R.id.return_time);

        tv_from.setText("New Delhi");
        tv_to.setText(mSelectedLocation.getDropLocation());

        if(mReturnUnix != 0){
            iv_arrow.setBackgroundResource(R.drawable.double_arrow);
        }
        else {
            iv_arrow.setBackgroundResource(R.drawable.right_arrow);
        }

        String formattedDepartureDate = mDateFormatter.format(mDepartureUnix*1000L);
        tv_departure.setText(formattedDepartureDate);

        if(mReturnUnix!=0){
            String formattedReturnDate = mDateFormatter.format(mReturnUnix*1000L);
            tv_return.setText(" -- "+formattedReturnDate);
        }

        else {
            tv_return.setText(" -- One way only");
        }
    }

    private void clickListener() {

        findViewById(R.id.back_btn).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.cross).setOnClickListener(v -> findViewById(R.id.ll_certified_driver).setVisibility(View.GONE));

        findViewById(R.id.edit).setOnClickListener(v -> onBackPressed());

    }

    private void setRecycleView() {

        mVehhicleList.add(new VehicleOutSation(1,"Mini","Indiaca, Micra, Ritz",
                mSelectedLocation.getPriceMini(),"4+1",R.mipmap.mini, mSelectedLocation.getPickupLocation(),
                mSelectedLocation.getDropLocation(),mDepartureUnix,mReturnUnix, mSelectedLocation.getDistance() ));

        mVehhicleList.add(new VehicleOutSation(2,"Micro","Redi Go, Alto, Nano",
                mSelectedLocation.getPriceMicro(),"4+1",R.mipmap.micro,mSelectedLocation.getPickupLocation(),
                mSelectedLocation.getDropLocation(),mDepartureUnix,mReturnUnix,mSelectedLocation.getDistance() ));

        mVehhicleList.add(new VehicleOutSation(3,"Sedan","Switft Desire, Honda City",
                mSelectedLocation.getPriceSedan(),"4+1", R.mipmap.sedan,mSelectedLocation.getPickupLocation(),
                mSelectedLocation.getDropLocation(),mDepartureUnix,mReturnUnix,mSelectedLocation.getDistance()  ));

        mVehhicleList.add(new VehicleOutSation(4,"Suv","Innova, Tavera, Sumo",
                mSelectedLocation.getPriceSuv(),"7+1",R.mipmap.suv,mSelectedLocation.getPickupLocation(),
                mSelectedLocation.getDropLocation(),mDepartureUnix,mReturnUnix,mSelectedLocation.getDistance()  ));

        mVehhicleList.add(new VehicleOutSation(5,"Traveller","Traveller Go, Traveller Rapid",
                mSelectedLocation.getPriceTraveller(),"15+1",R.mipmap.traveller,mSelectedLocation.getPickupLocation(),
                mSelectedLocation.getDropLocation(),mDepartureUnix,mReturnUnix,mSelectedLocation.getDistance()  ));

        mVehhicleList.add(new VehicleOutSation(6,"Luxury","Audi, Ferrari, Bmw",
                mSelectedLocation.getPriceLuxury(),"4+1",R.mipmap.luxury,mSelectedLocation.getPickupLocation(),
                mSelectedLocation.getDropLocation(),mDepartureUnix,mReturnUnix,mSelectedLocation.getDistance() ));

        RecyclerView recyclerView = findViewById(R.id.recycler_view_vehicle_option);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(VehicleOptionPage.this));

        VehicleOutStationAdapter adapter = new VehicleOutStationAdapter(VehicleOptionPage.this,mVehhicleList);

        recyclerView.setAdapter(adapter);
    }
}