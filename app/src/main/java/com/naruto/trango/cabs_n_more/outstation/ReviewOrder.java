package com.naruto.trango.cabs_n_more.outstation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.naruto.trango.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReviewOrder extends AppCompatActivity {

    private final  String TAG = getClass().getSimpleName();

    VehicleOutSation mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        Window window = getWindow();
        window.setStatusBarColor(0);

        mInfo = (VehicleOutSation) getIntent().getSerializableExtra("info");
        Log.e(TAG, "origin "+mInfo.getOrigin());

        setValues();

    }

    @SuppressLint("SetTextI18n")
    private void setValues() {

        findViewById(R.id.back_btn).setOnClickListener(v -> onBackPressed());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, d MMM", Locale.getDefault());
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        ImageView iv_vehicle    = findViewById(R.id.image_vehicle);

        TextView tv_name        = findViewById(R.id.name);
        TextView tv_name2       = findViewById(R.id.name2);
        TextView tv_sample      = findViewById(R.id.sample);
        TextView tv_person      = findViewById(R.id.person);

        TextView tv_from        = findViewById(R.id.from);
        TextView tv_to          = findViewById(R.id.to);
        TextView tv_d_date      = findViewById(R.id.departure_date);
        TextView tv_d_time      = findViewById(R.id.departure_time);

        TextView tv_distance    = findViewById(R.id.distance);
        TextView tv_total_fare  = findViewById(R.id.total_fare);


        iv_vehicle.setImageResource(mInfo.getImage());
        tv_name.setText(mInfo.getName());
        tv_name2.setText(mInfo.getName());

        tv_sample.setText("( " +mInfo.getSampleVehicles()+" )");
        tv_person.setText(mInfo.getPerson());


        String formattedDepartureDate = dateFormatter.format(mInfo.getDepartureUnix()*1000L);
        tv_d_date.setText(formattedDepartureDate);

        String formattedTime = timeFormatter.format(mInfo.getDepartureUnix()*1000L);
        tv_d_time.setText(formattedTime);


        tv_from.setText(mInfo.getOrigin());
        tv_to.setText(mInfo.getDestination());


        int distance = mInfo.getDistance();
        int fare     = mInfo.getFarePerKm();

        int totalFare = distance*fare;

        tv_distance.setText(distance+" km");

        if(mInfo.getReturnUnix() == 0){
            tv_total_fare.setText("\u20B9 "+totalFare);
        }

        else {
            tv_total_fare.setText("\u20B9 "+2*totalFare);
            findViewById(R.id.text_add_charges).setVisibility(View.VISIBLE);
        }

    }
}