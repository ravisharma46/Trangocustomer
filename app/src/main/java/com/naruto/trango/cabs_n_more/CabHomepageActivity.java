package com.naruto.trango.cabs_n_more;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.naruto.trango.R;
import com.naruto.trango.cabs_n_more.outstation.MyOutStationBookings;
import com.naruto.trango.cabs_n_more.outstation.OutstationHomepageActivity;
import com.naruto.trango.commonfiles.CommonVariablesFunctions;

import java.util.Objects;

public class CabHomepageActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private final String TAG = this.getClass().getSimpleName();

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final float GPS_ZOOM = 18f;
    private Boolean mLocationPermissionsGranted = false;

    LatLng mSelectedLatlng;

    private CardView mcv_cab_option;
    private CardView mcv_cab_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camhomepage);

        setToolbar();
        getLocationPermission();

        clickListener();

        mcv_cab_option = findViewById(R.id.cab_option_card_view);
        mcv_cab_details = findViewById(R.id.cab_details_card_view);
    }

    private void clickListener() {

        findViewById(R.id.back_btn).setOnClickListener(v -> finish());

        ImageView iv_open = findViewById(R.id.open);
        ImageView iv_close = findViewById(R.id.close);

        iv_open.setOnClickListener(v -> mcv_cab_option.setVisibility(View.VISIBLE));

        iv_close.setOnClickListener(v -> hideCabDetails());

        findViewById(R.id.ola).setOnClickListener(v -> mcv_cab_details.setVisibility(View.VISIBLE));

        findViewById(R.id.uber).setOnClickListener(v -> mcv_cab_details.setVisibility(View.VISIBLE));

        findViewById(R.id.ola3).setOnClickListener(v -> mcv_cab_details.setVisibility(View.VISIBLE));

        findViewById(R.id.outstation).setOnClickListener(v -> startActivity(new Intent(CabHomepageActivity.this, OutstationHomepageActivity.class)));

    }

    private void initMap() {

        Log.e(TAG, "initMap");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.e(TAG, "onMapReady");

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (mLocationPermissionsGranted) {
            animateToCurrentLocation();
        }

    }

    /*------------------------------------ Supporting Function --------------------------------------*/

    private void setToolbar() {

        Window window = getWindow();
        window.setStatusBarColor(0);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.dots_3);
        toolbar.setOverflowIcon(drawable);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.outstation_home_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_my_rides:

            case R.id.menu_support:

            case R.id.menu_offers:

            case R.id.menu_faq:

                return true;

            case R.id.menu_my_bookings:
                startActivity(new Intent(CabHomepageActivity.this, MyOutStationBookings.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void getLocationPermission() {

        Log.e(TAG, "getLocationPermission");

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.e(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        Log.e(TAG, "onRequestPermissionsResult: permission failed");
                        return;
                    }
                }
                Log.e(TAG, "onRequestPermissionsResult: permission granted");
                mLocationPermissionsGranted = true;
                initMap();
            }
        }
    }

    private void animateToCurrentLocation() {

        mMap.clear();

        try {

            mSelectedLatlng = CommonVariablesFunctions.getDeviceLocation(this);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mSelectedLatlng, GPS_ZOOM));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {

        if (mcv_cab_details.getVisibility() == View.VISIBLE)
            mcv_cab_details.setVisibility(View.GONE);
        else
            super.onBackPressed();

    }

    private void hideCabDetails() {
        mcv_cab_option.setVisibility(View.GONE);
        mcv_cab_details.setVisibility(View.GONE);
    }
}