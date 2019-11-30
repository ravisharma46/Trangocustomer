package com.naruto.trango.transit;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naruto.trango.R;
import com.naruto.trango.checkIn_checkOut.CheckIn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainMap extends AppCompatActivity implements OnMapReadyCallback {

    private final String TAG = getClass().getSimpleName();
    private GoogleMap mMap;
    TextView tvCongestion;
    TextView tvBusNum, tvBusNum2, tvBusNum3, tvBusNum4;

    private LocationRequest mLocationRequest;

    private LatLng mOrigin = null;
    private BottomSheetBehavior mBottomSheetBehavior;
    private Marker mMarkerOrigin;

    private FusedLocationProviderClient mFusedLocationClient;
    private int ZOOM_VALUE = 15;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int GPS_REQUEST = 100;

    private List<Transporter> mTransporterList;
    private List<Marker> mParkingLocationMarkerList;


    TextView mMvBtn;
    CardView mMenuMyVehicleLayout;

    private LinearLayout mZigzagLayout;
    private ImageView mZigzagOpenBtn;
    private ImageView mZigzafCloseBtn, ivMapType, ivMapType2, ivMapType3, ivMapType4;
    private TextView mParkingCount;
    private TextView mParkingRadius;

    private LinearLayout mParkingLocationOpenButton;
    private ImageView mParkingLocationCloseButton;
    private LinearLayout mParkingLocationLayout;

    private int mParkingFilterType = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        init();

        new GpsUtils(this).turnGPSOn(isGPSEnable -> {
            if(isGPSEnable) {
                initMap();
                clickListener();

            }

        });

    }

    private void init() {

        Log.e(TAG,"called : init");

        mTransporterList = new ArrayList<>();

        mParkingLocationMarkerList = new ArrayList<>();

        mMvBtn               = findViewById(R.id.menu_ns);
        mMenuMyVehicleLayout = findViewById(R.id.menu_ns_layout);

        mZigzagLayout   = findViewById(R.id.zigzag_layout);
        mZigzagOpenBtn  = findViewById(R.id.zigzag_open_button);
        mZigzafCloseBtn = findViewById(R.id.zigzag_close_btn);
        mParkingCount   = findViewById(R.id.parking_count);
        mParkingRadius  = findViewById(R.id.parking_radius);

        mParkingLocationOpenButton  = findViewById(R.id.open_parking_locaton);
        mParkingLocationCloseButton = findViewById(R.id.close_parking_location);
        mParkingLocationLayout      = findViewById(R.id.parking_location_layout);

    }

    private void clickListener() {
        Log.e(TAG,"called : clickListener");

        CardView menu_layout = findViewById(R.id.menu_ns_layout);

        mMvBtn.setOnClickListener(view -> {

            if(menu_layout.getVisibility() == View.VISIBLE)
                menu_layout.setVisibility(View.GONE);
            else
                menu_layout.setVisibility(View.VISIBLE);

        });

        findViewById(R.id.bus).setOnClickListener(view -> filterParkingLocation(1));

        findViewById(R.id.train).setOnClickListener(view -> filterParkingLocation(2));

        findViewById(R.id.metro).setOnClickListener(view -> filterParkingLocation(3));

        findViewById(R.id.all).setOnClickListener(view -> filterParkingLocation(0));

        /* ----------------- Zigzag layout ------------------------------*/

        mZigzagOpenBtn.setOnClickListener(v -> {
            Log.e(TAG,"Zigzag opened");
            mZigzagLayout.setVisibility(View.VISIBLE);

        });

        mZigzafCloseBtn.setOnClickListener(v -> {
            mZigzagLayout.setVisibility(View.GONE);
            mParkingLocationLayout.setVisibility(View.GONE);
        });

        mParkingLocationOpenButton.setOnClickListener(v -> {
            Log.e(TAG,"Parking location opened");
            mMenuMyVehicleLayout.setVisibility(View.GONE);
            mParkingLocationLayout.setVisibility(View.VISIBLE);
        });

        mParkingLocationCloseButton.setOnClickListener(v -> mParkingLocationLayout.setVisibility(View.GONE));

    }

    private void initMap(){

        Log.e(TAG,"called : initMap");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"called : onPause");
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.e(TAG,"called : onMapReady");

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        final View bottom_sheet = findViewById(R.id.bottom_sheet_maps_activity);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        // Initially bottom sheet is hidden
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000*1000);
        mLocationRequest.setFastestInterval(1000*1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainMap.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mMap.setMyLocationEnabled(false);

            } else {

                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(true);
        }

        findViewById(R.id.gps).setOnClickListener(view -> {
            Log.e(TAG,"clicked");
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mOrigin, ZOOM_VALUE));
        });

        /*--------------------- onMarkerClick ----------------------------------*/

        mMap.setOnMarkerClickListener(this::clickingOnMarker);

        mMap.setOnMapClickListener(latLng -> {

            mZigzagLayout.setVisibility(View.GONE);
            mParkingLocationLayout.setVisibility(View.GONE);

            mMenuMyVehicleLayout.setVisibility(View.GONE);

            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            findViewById(R.id.bottom_sheet_header).setVisibility(View.GONE);
            findViewById(R.id.bottom_sheet_main_content).setVisibility(View.GONE);
        });

        ImageView expandBottomSheetBtn = findViewById(R.id.expand_bottom_sheet_btn);

        expandBottomSheetBtn.setOnClickListener(v -> {

            findViewById(R.id.bottom_sheet_main_content).setVisibility(View.VISIBLE);
            findViewById(R.id.bottom_sheet_header).setVisibility(View.GONE);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        ImageView bottomSheetBackbtn = findViewById(R.id.bottomSheet_back_btn);
        bottomSheetBackbtn.setOnClickListener(v -> {
            Log.e(TAG,"Bottom Sheet Back button is clicekd");

            findViewById(R.id.bottom_sheet_header).setVisibility(View.VISIBLE);
            findViewById(R.id.bottom_sheet_main_content).setVisibility(View.GONE);
        });

    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {

                Location location = locationList.get(locationList.size() - 1);
                Log.e(TAG,"lat="+location.getLatitude()+"logi="+location.getLongitude());
                mOrigin = new LatLng(location.getLatitude(), location.getLongitude());
                if (mMarkerOrigin != null) {
                    mMarkerOrigin.remove();
                }

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                Drawable drawableOrigin = getDrawable(R.drawable.my_location);
                Bitmap bitmapOrigin = drawableToBitmap(drawableOrigin);

                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(latLng);
                markerOptions.title("MyLocation");
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmapOrigin));


                mMarkerOrigin = mMap.addMarker(markerOptions);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_VALUE));

                fetchTranportLocation();

            }
        }
    };

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MainMap.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainMap.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(MainMap.this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MainMap.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION );
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainMap.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(MainMap.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    mMap.setMyLocationEnabled(true);
                }

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Toast.makeText(MainMap.this, "permission denied", Toast.LENGTH_LONG).show();
                Log.e("MapFrag", "permission denied");

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                initMap();
            }
        }
    }

    private void fetchTranportLocation(){

        Log.e(TAG,"called : fetchLocation");

        if(mTransporterList.size()>0)
            mTransporterList.clear();

        mTransporterList.add(new Transporter(1,1,"28.486152","77.076842","Ganpat Sectoe 18 (footover bridge)"));
        mTransporterList.add(new Transporter(2,1,"28.486244","77.077073","Genpact NH 8 / Fob"));
        mTransporterList.add(new Transporter(3,1,"28.482692","77.074707","IFFCO Chowk (service lane)"));
        mTransporterList.add(new Transporter(9,1,"23.285420","77.396010","Bhopal Bus Stop"));
        mTransporterList.add(new Transporter(10,1,"23.269811","77.376949","Koh-E-Fiza Bus Stop"));
        mTransporterList.add(new Transporter(11,1,"23.271211","77.375908","VIP guest house"));

        mTransporterList.add(new Transporter(4,2,"28.479558","77.079953","M G road Metro Station"));
        mTransporterList.add(new Transporter(5,2,"28.472093","77.072559","IFFCO Chowk"));
        mTransporterList.add(new Transporter(6,2,"28.491742","77.088296","Belvedere Tower"));

        mTransporterList.add(new Transporter(7,3,"28.489261","77.011346","Gurgaon (old railway station)"));
        mTransporterList.add(new Transporter(8,3,"28.536190","77.048519","Bijwasan Railway Station (Noor)"));

        calDistanceOfAllMarkers();

    }

    private void calDistanceOfAllMarkers() {
        Log.e(TAG, "calculate distanceOfAllMarkers");

        for(int i=0;i<mTransporterList.size();i++) {

            float[] results = new float[3];

            double latitude  = Double.parseDouble(mTransporterList.get(i).getLatitude());
            double longitude = Double.parseDouble(mTransporterList.get(i).getLongitude());

            LatLng parkingLocation = new LatLng(latitude,longitude);

            //LatLng parkingLocation = mTransporterList.get(i).getLatLng();

            if(mOrigin == null){
                Log.e(TAG,"origin is null");
                return;
            }

            Location.distanceBetween(mOrigin.latitude, mOrigin.longitude, parkingLocation.latitude, parkingLocation.longitude, results);

            float distance = results[0] / 1000f;
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String distanceinKm = decimalFormat.format(distance);

            Log.e(TAG,"distance="+distanceinKm);

            mTransporterList.get(i).setDisanceFromMyLocation(distanceinKm);
        }

        addParkingLocationsToMap();
    }

    private void addParkingLocationsToMap() {

        Log.e(TAG, "addParkingSpots");

        for (int i = 0; i < mTransporterList.size(); i++) {

            Transporter parkingSpot = mTransporterList.get(i);

            Drawable drawable;
            int parkingType = parkingSpot.getType();

            switch (parkingType) {
                case 1:
                    drawable = getDrawable(R.drawable.bus);
                    break;
                case 2:
                    drawable = getDrawable(R.drawable.train);
                    break;
                case 3:
                    drawable = getDrawable(R.drawable.metro);
                    break;
                default:
                    drawable = getDrawable(R.drawable.source);
            }

            Bitmap bitmap = drawableToBitmap(drawable);

            double latitude  = Double.parseDouble(parkingSpot.getLatitude());
            double longitude = Double.parseDouble(parkingSpot.getLongitude());

            LatLng markerLatlng = new LatLng(latitude,longitude);
            MarkerOptions markerOptions = new MarkerOptions().position(markerLatlng)
                    .title(parkingSpot.getTitle())
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap));

            Marker marker = mMap.addMarker(markerOptions);
            mParkingLocationMarkerList.add(marker);
        }

        RecyclerView recyclerViewParkingLocations = findViewById(R.id.recycler_view_parking_locations);
        recyclerViewParkingLocations.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainMap.this);
        recyclerViewParkingLocations.setLayoutManager(linearLayoutManager);
        TransporterAdapter adapter = new TransporterAdapter(MainMap.this,mTransporterList);
        recyclerViewParkingLocations.setAdapter(adapter);

        mParkingCount.setText(String.valueOf(mTransporterList.size()));
        //mParkingRadius.setText(String.valueOf(mRadiusSelected)+" km");

    }

    private Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        //Log.e(TAG,"width="+drawable.getIntrinsicWidth()+"");

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private boolean clickingOnMarker(Marker marker) {

        Log.e(TAG,marker.getId() + " is clicked");
        marker.showInfoWindow();

        mZigzagLayout.setVisibility(View.GONE);
        mParkingLocationLayout.setVisibility(View.GONE);
        mMenuMyVehicleLayout.setVisibility(View.GONE);

        findViewById(R.id.bottom_sheet_header).setVisibility(View.VISIBLE);
        findViewById(R.id.bottom_sheet_main_content).setVisibility(View.GONE);

        // This is now working (direct comparision dont works)
        String s1 = marker.getPosition().toString();
        String s2 = mMarkerOrigin.getPosition().toString();

        // Origin is clicked - do nothing and return
        if(s1.equals(s2)) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return true;
        }

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        TextView tv_title,tv_desc, tv_distance1,tv_landmark;
        TextView tv_title2,tv_desc2,tv_distance2;
        ImageView iv_image,iv_image2;
        Button btn_navigate1, btn_advanceBook;
        Button btn_navigate2, btn_bookNow;
        LinearLayout parkingSlotsLayout;

        // On header Bottom sheet
        iv_image        = findViewById(R.id.location_image);
        tv_title        = findViewById(R.id.location_title);
        tv_desc         = findViewById(R.id.description);
        tv_distance1    = findViewById(R.id.botton_sheeet_header_distance);
        btn_navigate1   = findViewById(R.id.navigate1);
        btn_advanceBook = findViewById(R.id.advance_book);

        // On MainContent Bottom sheet
        btn_navigate2       = findViewById(R.id.navigate2);
//        btn_bookNow         = findViewById(R.id.booknow);

        iv_image2           = findViewById(R.id.bottom_sheet_main_content_image_location);
        tv_title2           = findViewById(R.id.bottom_sheet_main_content_title);
        tv_desc2            = findViewById(R.id.bottom_sheet_main_content_description);
        tv_distance2        = findViewById(R.id.bottom_sheet_main_content_distance);
        tv_landmark         = findViewById(R.id.bottom_sheet_main_content_landmark);
        parkingSlotsLayout  = findViewById(R.id.bottom_sheet_main_content_parking_slot_ll);

        TextView tv_OpenHrs      = findViewById(R.id.bottom_sheet_main_content_opening_hrs);
        tvBusNum = findViewById(R.id.bus_num);
        tvCongestion = findViewById(R.id.tv_congestion);
        ivMapType = findViewById(R.id.iv_map_type);
        tvBusNum2 = findViewById(R.id.bus_num2);
        ivMapType2 = findViewById(R.id.iv_map_type2);
        tvBusNum3 = findViewById(R.id.bus_num3);
        ivMapType3 = findViewById(R.id.iv_map_type3);
        tvBusNum4 = findViewById(R.id.bus_num4);
        ivMapType4 = findViewById(R.id.iv_map_type4);
//        TextView tv_carFare      = findViewById(R.id.car_parking_fare);
//        TextView tv_carCapacity  = findViewById(R.id.car_parking_capacity);
//        TextView tv_bikeVacancy  = findViewById(R.id.bike_parking_vacancy);
//        TextView tv_carVacancy   = findViewById(R.id.car_parking_vacancy);

        String busCongestionUrl = "http://10.34.5.77:5000/api/v1/resources/congestion_level?tag=D";
        new JsonTask().execute(busCongestionUrl+tvBusNum.getText().toString());

        String s3 = marker.getPosition().toString();
        int markerId = 0;

        for(int i=0;i<mTransporterList.size();i++){

            LatLng temp = new LatLng(Double.parseDouble(mTransporterList.get(i).getLatitude()),
                    Double.parseDouble(mTransporterList.get(i).getLongitude()));
            String s4 = temp.toString();

            if( s3.equals(s4)) {
                markerId = i;
                break;
            }

        }

        Log.e(TAG,"Marker id "+markerId);

        final Transporter parkingLocation = mTransporterList.get(markerId);

        LatLng clickedLatLng = new LatLng(Double.parseDouble(parkingLocation.getLatitude()),
                Double.parseDouble(parkingLocation.getLongitude()));

        String title    = parkingLocation.getTitle();
        //String desc     = parkingLocation.getDesc();

        //String imagePath = parkingLocation.getImagePath();

        int parkingType = parkingLocation.getType();
        //String landmark = parkingLocation.getLandmark();
        String distance  = parkingLocation.getDisanceFromMyLocation();

        if(parkingType == 2){
            btn_advanceBook.setVisibility(View.GONE);
//            btn_bookNow.setVisibility(View.GONE);

            ivMapType.setImageResource(R.drawable.train);
            tvBusNum.setText("T001");
            ivMapType2.setImageResource(R.drawable.train);
            tvBusNum2.setText("T002");
            ivMapType3.setImageResource(R.drawable.train);
            tvBusNum3.setText("T003");
            ivMapType4.setImageResource(R.drawable.train);
            tvBusNum4.setText("T004");

        }
        else if (parkingType == 1){
            btn_advanceBook.setVisibility(View.VISIBLE);
//            btn_bookNow.setVisibility(View.VISIBLE);

            ivMapType.setImageResource(R.drawable.bus);
            tvBusNum.setText("673");
            ivMapType2.setImageResource(R.drawable.bus);
            tvBusNum2.setText("333");
            ivMapType3.setImageResource(R.drawable.bus);
            tvBusNum3.setText("319");
            ivMapType4.setImageResource(R.drawable.bus);
            tvBusNum4.setText("273");

        } else if (parkingType == 3) {
            btn_advanceBook.setVisibility(View.VISIBLE);

            ivMapType.setImageResource(R.drawable.metro);
            tvBusNum.setText("001");
            ivMapType2.setImageResource(R.drawable.metro);
            tvBusNum2.setText("002");
            ivMapType3.setImageResource(R.drawable.metro);
            tvBusNum3.setText("003");
            ivMapType4.setImageResource(R.drawable.metro);
            tvBusNum4.setText("004");
        }

//        LatLng latLng=null;
//
//        mMap.moveCamera(clickedLatLng,ZOOM_VALUE,latLng);

//        btn_navigate1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToDestination(parkingLocation);
//            }
//        });
//
//        btn_advanceBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG,"Advance book is clicked");
//                openBookinPage(parkingLocation);
//            }
//        });

//        btn_navigate2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToDestination(parkingLocation);
//            }
//        });
//
//        btn_bookNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG,"book Now is clicked");
//                openBookinPage(parkingLocation);
//
//            }
//        });

//        Glide.with(MapsActivity.this).load(imagePath).into(iv_image);
//        Glide.with(MapsActivity.this).load(imagePath).into(iv_image2);

        //iv_image.setImageResource(imageId);

//        tv_title.setText(title);
//        tv_desc.setText(desc);

        //iv_image2.setImageResource(imageId);
        tv_title2.setText(title);
//        tv_desc2.setText(desc);
//        tv_landmark.setText(landmark);
        tv_distance1.setText(""+distance+" km");
        tv_distance2.setText(""+distance+" km");

//        tv_OpenHrs.setText(parkingLocation.getOpeningHrs());
//        tv_bikeFare.setText(String.valueOf(parkingLocation.getFareForBike()));
//        tv_carFare.setText(String.valueOf(parkingLocation.getFareForCar()));
//        tv_bikeCapacity.setText(String.valueOf(parkingLocation.getBikeCapacity()));
//        tv_carCapacity.setText(String.valueOf(parkingLocation.getCarCapacity()));
//
//        tv_carVacancy.setText(""+parkingLocation.getInitialCarVacancy());
//        tv_bikeVacancy.setText(""+parkingLocation.getInitialBikeVacancy());

        return true;
    }

    public void callCheckInIntent(View view) {
        Intent intent = new Intent(this, CheckIn.class);
        startActivity(intent);
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine())!=null) {
                    buffer.append(line).append("\n");
                }
                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection!=null) {
                    connection.disconnect();
                }
                try {
                    if (reader!=null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvCongestion.setText(s);
        }
    }

//    private void fetchCongestion(String busNum) throws IOException {
////        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        String busCongestionUrl = "http://10.34.5.77:5000/api/v1/resources/congestion_level?tag=D";
//        Log.e("url", busCongestionUrl+busNum);
//
//        URL url = new URL(busCongestionUrl+busNum);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            readStream(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            urlConnection.disconnect();
//        }
//
////        StringRequest stringRequest = new StringRequest(Request.Method.GET, busCongestionUrl+busNum, response -> {
////            try {
////                JSONObject jsonObject = new JSONObject(response);
////                String heavy = jsonObject.getString("heavy");
////                String insane = jsonObject.getString("insane");
////                String light = jsonObject.getString("light");
////                String normal = jsonObject.getString("normal");
////
////                if (heavy.equals("1")) {
////                    tvCongestion.setText("Heavy");
////                    tvCongestion.setTextColor(getColor(R.color.light_yellow));
////                } else if (insane.equals("1")) {
////                    tvCongestion.setText("Insane");
////                    tvCongestion.setTextColor(getColor(R.color.red_dark));
////                } else if (light.equals("1")) {
////                    tvCongestion.setText("Low");
////                    tvCongestion.setTextColor(getColor(R.color.green));
////                } else if (normal.equals("1")) {
////                    tvCongestion.setText("Moderate");
////                    tvCongestion.setTextColor(getColor(R.color.yellow));
////                }
////            } catch (JSONException e) {
////                Log.e("error", "error");
////                e.printStackTrace();
////            }
////        }, Throwable::printStackTrace) {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                return super.getParams();
////            }
////
////            @Override
////            public Map<String, String> getHeaders() throws AuthFailureError {
////                return super.getHeaders();
////            }
////        };
////
////        requestQueue.add(stringRequest);
//    }

    private void filterParkingLocation(int filterValue) {

        Log.e(TAG,"filteredParkingLocation");

        mMap.clear();
        mParkingLocationMarkerList.clear();

        // Adding origin marker
        Drawable drawableOrigin = getDrawable(R.drawable.my_location);
        Bitmap bitmapOrigin = drawableToBitmap(drawableOrigin);

        MarkerOptions markerOptionsOrigin = new MarkerOptions().position(mOrigin)
                .title("My Location")
                .icon(BitmapDescriptorFactory.fromBitmap(bitmapOrigin));

        mMarkerOrigin = mMap.addMarker(markerOptionsOrigin);

        List<Transporter> filteredParkingLocatons = new ArrayList<>();

        switch(filterValue)
        {
            case 1:
                for(int i=0;i<mTransporterList.size();i++)
                {
                    Transporter parkingLocation = mTransporterList.get(i);
                    if(parkingLocation.getType() == 1)
                        filteredParkingLocatons.add(parkingLocation);
                }
                break;

            case 2:
                for(int i=0;i<mTransporterList.size();i++)
                {
                    Transporter parkingLocation = mTransporterList.get(i);
                    if(parkingLocation.getType() == 2)
                        filteredParkingLocatons.add(parkingLocation);
                }
                break;

            case 3:
                for(int i=0;i<mTransporterList.size();i++)
                {
                    Transporter parkingLocation = mTransporterList.get(i);
                    if(parkingLocation.getType()== 3)
                        filteredParkingLocatons.add(parkingLocation);
                }
                break;

            default:
                filteredParkingLocatons.addAll(mTransporterList);
                break;

        }

        for(int i=0;i<filteredParkingLocatons.size();i++)
        {
            Transporter filteredLocation = filteredParkingLocatons.get(i);

            Drawable drawable = null;
            int parkingType = filteredLocation.getType();

            switch (parkingType) {
                case 1:
                    drawable = getDrawable(R.drawable.bus);
                    break;
                case 2:
                    drawable = getDrawable(R.drawable.train);
                    break;
                case 3:
                    drawable = getDrawable(R.drawable.metro);
                    break;
            }

            Bitmap bitmap = drawableToBitmap(drawable);

            double latitude  = Double.parseDouble( filteredLocation.getLatitude());
            double longitude = Double.parseDouble(filteredLocation.getLongitude());

            LatLng latLng = new LatLng(latitude, longitude);

            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                    .title(filteredLocation.getTitle())
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap));

            Marker marker = mMap.addMarker(markerOptions);
            mParkingLocationMarkerList.add(marker);
        }

        RecyclerView recyclerViewParkingLocations = findViewById(R.id.recycler_view_parking_locations);
        recyclerViewParkingLocations.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainMap.this);
        recyclerViewParkingLocations.setLayoutManager(linearLayoutManager);
        TransporterAdapter adapter = new TransporterAdapter(MainMap.this,filteredParkingLocatons);
        recyclerViewParkingLocations.setAdapter(adapter);

        // Parling count and radius selected
        mParkingCount.setText(String.valueOf(filteredParkingLocatons.size()));
        //mParkingRadius.setText(String.valueOf(mRadiusSelected)+" km");

    }

}
