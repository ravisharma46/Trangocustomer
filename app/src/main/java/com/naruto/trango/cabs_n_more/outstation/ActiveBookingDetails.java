package com.naruto.trango.cabs_n_more.outstation;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.naruto.trango.R;

public class ActiveBookingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_bookings_details);

        Window window = getWindow();
        window.setStatusBarColor(0);

        clickLister();
    }

    private void clickLister() {

        findViewById(R.id.back_btn).setOnClickListener(v -> onBackPressed());
    }
}