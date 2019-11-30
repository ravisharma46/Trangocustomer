package com.naruto.trango.cabs_n_more.outstation;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.naruto.trango.R;

public class MyOutStationBookings extends AppCompatActivity {

    private final String TAG =  this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_outstation_bookings);

        Window window = getWindow();
        window.setStatusBarColor(0);

        replaceFragment(new ActiveOutStationBookings());
        setUpTavLayout();

    }

    private void setUpTavLayout() {

        findViewById(R.id.back_btn).setOnClickListener(v -> onBackPressed());

        final TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("Removed"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tabLayout.getSelectedTabPosition()){

                    case 0:
                        replaceFragment(new ActiveOutStationBookings());
                        break;

                    case 1:
                        replaceFragment(new RemovedOutStationBookings());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    public void replaceFragment(Fragment fragment) {

        Log.e(TAG, "replaceFragment");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.commit();
    }
}