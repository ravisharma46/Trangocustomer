package com.naruto.trango.cabs_n_more.outstation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.naruto.trango.R;
import com.naruto.trango.commonfiles.CommonVariablesFunctions;
import com.naruto.trango.commonfiles.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.naruto.trango.commonfiles.CommonVariablesFunctions.TIME_FORMAT;

public class OutstationHomepageActivity extends AppCompatActivity implements AddressDialog.AddressDialogListener {

    private final  String TAG = getClass().getSimpleName();

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private ProgressDialog mProgressDialog;
    private LatLng mLatlngSelected;

    private List<OutStationLocation> mOutStationList;
    private List<KeyValueClass> mOutStationSpinnerItem;

    private Calendar mCalender;

    private long mDepartureUnixTime = 0;
    private long mReturnUnixTime    = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outstation_cab_home_page);

        mProgressDialog = new ProgressDialog(OutstationHomepageActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);

        mOutStationList = new ArrayList<>();

        mOutStationSpinnerItem = new ArrayList<>();
        mOutStationSpinnerItem.add(new KeyValueClass(0,"Select Destination"));

        setToolbar();
        clickListener();

        fetchAllOutStationLocation();
        
    }

    private void setToolbar() {

        Window window = getWindow();
        window.setStatusBarColor(0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.dots_3);
        toolbar.setOverflowIcon(drawable);

    }

    private void clickListener() {

        findViewById(R.id.back_btn).setOnClickListener(v -> onBackPressed());

        final TextView tv_pickup    = findViewById(R.id.pickup_point);
        final Spinner spinnerDropLocation = findViewById(R.id.drop_location_spinner);

        tv_pickup.setOnClickListener(v -> getLocationPermissionAndOpenAddressDialog());

        findViewById(R.id.departure_time).setOnClickListener(v -> showDateTimePicker((TextView) v));

        findViewById(R.id.return_time).setOnClickListener(v -> showDateTimePicker((TextView) v));

        findViewById(R.id.find_cheapest_cabs).setOnClickListener(v -> {

            String pickupLocation = tv_pickup.getText().toString().trim();

            int pos = spinnerDropLocation.getSelectedItemPosition();

//                pickupLocation = "New Location";
//                pos = 5;
//                mDepartureUnixTime = 1550869144;


            if(pickupLocation.equals("")){
                Toast.makeText(OutstationHomepageActivity.this,"Pickup location is empty",Toast.LENGTH_SHORT).show();
                return;
            }

            if(pos == 0){
                Toast.makeText(OutstationHomepageActivity.this,"Select drop location",Toast.LENGTH_SHORT).show();
                return;
            }

            if(mDepartureUnixTime == 0){
                Toast.makeText(OutstationHomepageActivity.this,"Departure time required",Toast.LENGTH_SHORT).show();
                return;
            }


            Log.e(TAG," departure unix "+mDepartureUnixTime);
            Log.e(TAG," return unix "+mReturnUnixTime);

            // To find the selected location

            int outStationSelectedId = mOutStationSpinnerItem.get(pos).getKey();

            OutStationLocation selectedLocation = null;
            for(int i=0;i<mOutStationList.size();i++){

                OutStationLocation location = mOutStationList.get(i);

                if(location.getId() == outStationSelectedId){
                    selectedLocation = location;
                    break;
                }

            }

            Intent i = new Intent(OutstationHomepageActivity.this, VehicleOptionPage.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedLocation",selectedLocation);
            bundle.putLong("departure_unix",mDepartureUnixTime);
            bundle.putLong("return_unix",mReturnUnixTime);

            if (selectedLocation != null) {
                selectedLocation.setPickupLocation(pickupLocation);
            }

            i.putExtra("bundle",bundle);

            startActivity(i);

        });
    }

    private void fetchAllOutStationLocation() {

        Log.e(TAG,"called : fetchAllOutStationLocation");
        mProgressDialog.show();

        String SEND_URL = CommonVariablesFunctions.BASE_URL + "fetch_all_outstation_from_delhi.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_URL, response -> {
            Log.e(TAG, response);
            mProgressDialog.dismiss();
            try {
                JSONArray jsonArray = new JSONArray(response);
                int rc = jsonArray.getJSONObject(0).getInt("rc");
                if (rc <= 0) {

                    String mess = jsonArray.getJSONObject(0).getString("mess");
                    Toast.makeText(OutstationHomepageActivity.this, mess, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, mess);
                    return;
                }

                mOutStationList.clear();

                mOutStationSpinnerItem.clear();
                mOutStationSpinnerItem.add(new KeyValueClass(0, "Select Destination"));

                for (int i = 1; i < jsonArray.length(); i++) {

                    int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));

                    String dropLocation = jsonArray.getJSONObject(i).getString("drop_location");
                    String dropState = jsonArray.getJSONObject(i).getString("drop_state");

                    int distance = Integer.parseInt(jsonArray.getJSONObject(i).getString("distance"));

                    int micro = Integer.parseInt(jsonArray.getJSONObject(i).getString("micro"));
                    int mini = Integer.parseInt(jsonArray.getJSONObject(i).getString("mini"));
                    int sedan = Integer.parseInt(jsonArray.getJSONObject(i).getString("sedan"));
                    int suv = Integer.parseInt(jsonArray.getJSONObject(i).getString("suv"));
                    int traveller = Integer.parseInt(jsonArray.getJSONObject(i).getString("traveller"));
                    int luxury = Integer.parseInt(jsonArray.getJSONObject(i).getString("luxury"));

                    mOutStationList.add(new OutStationLocation(id, dropLocation, dropState, distance, micro, mini,
                            sedan, suv, traveller, luxury));

                    mOutStationSpinnerItem.add(new KeyValueClass(id, dropLocation));

                }

                Log.e(TAG, "size " + mOutStationList.size());

                KeyValueAdapter adapter = new KeyValueAdapter(OutstationHomepageActivity.this,
                        R.layout.spinner_layout_custom, mOutStationSpinnerItem);

                Spinner spinnerDropLocation = findViewById(R.id.drop_location_spinner);
                spinnerDropLocation.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            mProgressDialog.dismiss();
            Log.e(TAG,error.toString());
            Toast.makeText(OutstationHomepageActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(CommonVariablesFunctions.RETRY_SECONDS*1000, CommonVariablesFunctions.NO_OF_RETRY,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(OutstationHomepageActivity.this).addToRequestQueue(stringRequest);

    }

    private void getLocationPermissionAndOpenAddressDialog() {

        Log.e(TAG, "getLocationPermission");

        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(OutstationHomepageActivity.this,
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(OutstationHomepageActivity.this,
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                new AddressDialog().show(getSupportFragmentManager(), null);

            } else {
                ActivityCompat.requestPermissions(OutstationHomepageActivity.this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(OutstationHomepageActivity.this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.outstation_home_page_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_my_rides :

            case R.id.menu_offers:

            case R.id.menu_support:

            case R.id.menu_faq:

                return true;

            case R.id.menu_my_bookings:
                startActivity(new Intent(OutstationHomepageActivity.this,MyOutStationBookings.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getAddressViaListener(LatLng selectedLatlng, String completeAddress) {

        mLatlngSelected = selectedLatlng;

        TextView tv_pickup = findViewById(R.id.pickup_point);
        tv_pickup.setText(completeAddress);

    }

    public void showDateTimePicker(final TextView textView) {

        final Calendar currentDate = Calendar.getInstance();
        mCalender = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(OutstationHomepageActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
            mCalender.set(year, monthOfYear, dayOfMonth);

            @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(OutstationHomepageActivity.this, (view1, hourOfDay, minute) -> {
                mCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalender.set(Calendar.MINUTE, minute);

                long unixInMilli = mCalender.getTimeInMillis();

                SimpleDateFormat datePattern = new SimpleDateFormat("EEE, d MMM", Locale.getDefault());
                String formattedDate = datePattern.format(mCalender.getTime());

                SimpleDateFormat timePattern = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
                String formattedTime ;

                if (DateUtils.isToday(unixInMilli))
                    formattedTime = CommonVariablesFunctions.TODAY + ", " +timePattern.format(mCalender.getTime());
                else if(isTomorrow(mCalender.getTime()))
                    formattedTime = CommonVariablesFunctions.TOMMARROW + ", " + timePattern.format(mCalender.getTime());
                else
                    formattedTime = timePattern.format(mCalender.getTime());

                textView.setText(formattedDate+"\n"+formattedTime);
                textView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

                if(textView == findViewById(R.id.departure_time))
                    mDepartureUnixTime = unixInMilli/1000L;
                if(textView == findViewById(R.id.return_time))
                    mReturnUnixTime = unixInMilli/1000L;

            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false);

            timePickerDialog.show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    public static boolean isTomorrow(Date d) {
        return DateUtils.isToday(d.getTime() - DateUtils.DAY_IN_MILLIS);
    }
}