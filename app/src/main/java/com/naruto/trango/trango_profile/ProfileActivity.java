package com.naruto.trango.trango_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.naruto.trango.R;
import com.naruto.trango.local_transport.LocalsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Button change_passWord, bt_done;
    private TextView name, mobile, email, name_1, update_pic;
    private CircleImageView imageView;
    // private LoginSessionManager msessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        change_passWord = findViewById(R.id.btChange_pass);
        bt_done = findViewById(R.id.bt_done);

        imageView = findViewById(R.id.image_icon);
        name = findViewById(R.id.tvname);
        name_1 = findViewById(R.id.tvname1);
        mobile = findViewById(R.id.tvmobile);
        email = findViewById(R.id.tvemail);
        update_pic = findViewById(R.id.tvupdatepic);

        try{
            Toolbar toolbar = findViewById(R.id.profile_toolbar_widget);
            toolbar.setTitle("Profile");
            toolbar.setNavigationOnClickListener(this::onNavigationUpClick);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        change_passWord.setOnClickListener(view -> changePassword());

        bt_done.setOnClickListener(view -> onBackPressed());

    }

    private void onNavigationUpClick(View view) {
        onBackPressed();
    }

    public void changePassword() {

        final AlertDialog alertDialog;

        LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
        final View v = inflater.inflate(R.layout.dialog_change_password, null);


        alertDialog = new AlertDialog.Builder(ProfileActivity.this, android.
                R.style.Theme_DeviceDefault_Light_Dialog_MinWidth).create();

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        alertDialog.setView(v);

        final EditText et_current_pass, et_new_pass, et_confirm_pass;
        final TextView password_error;
        TextView done, cancel;

        et_current_pass = v.findViewById(R.id.current_password);
        et_new_pass = v.findViewById(R.id.new_password);
        et_confirm_pass = v.findViewById(R.id.confirm_password);


        password_error = v.findViewById(R.id.tv_password_error);

        done = v.findViewById(R.id.done);
        cancel = v.findViewById(R.id.cancel);

        done.setOnClickListener(v1 -> {

            String currentPass, newPass, confirmPass;

            currentPass = et_current_pass.getText().toString().trim();
            newPass = et_new_pass.getText().toString().trim();
            confirmPass = et_confirm_pass.getText().toString().trim();


            if (currentPass.equals("") || newPass.equals("") ||
                    confirmPass.equals("")) {
                password_error.setVisibility(View.VISIBLE);
                password_error.setText("All field are required");
                return;
            }


            //if old password is wrong
//            if(!currentPass.equals(msessionManager.getUserDetailsFromSP()
//                    .get(KEY_PASSWORD))){
//                password_error.setVisibility(View.VISIBLE);
//                password_error.setText("*Current password is wrong");
//                et_new_pass.setText("");
//                et_confirm_pass.setText("");
//
//                return;
//            }


            //if new password is shorter than six character
            if (newPass.length() < 6) {
                password_error.setVisibility(View.VISIBLE);
                password_error.setText("*New password must be of at least six characters");
                et_confirm_pass.setText("");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                password_error.setVisibility(View.VISIBLE);
                password_error.setText("*New password and confirm password do not match");
                et_confirm_pass.setText("");
                return;
            }

            password_error.setVisibility(View.GONE);

            //Toast.makeText(EditProfile.this,"new Pass "+newPass,Toast.LENGTH_SHORT).show();

            //et_password.setText(newPass);
            //sendNewPasswordToDataBase(newPass);
            alertDialog.dismiss();

        });

        cancel.setOnClickListener(v12 -> alertDialog.dismiss());
        alertDialog.show();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void callMyTripsIntent(View view) {
        Intent intent = new Intent(this, MyTripsActivity.class);
        startActivity(intent);
    }

//    public void getCurrentLocation(View view) {
//
//        Location location = null;
//        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            if (locationManager != null) {
//                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            }
//        } else {
//            Snackbar.make(view, "Please enable location permission from settings", BaseTransientBottomBar.LENGTH_SHORT).setAction("OK", null).show();
//            return;
//        }
//
//        String finalAddress = "city", lat="", lon="";
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses = null;
//        try {
//            if (location != null) {
//                lat = String.valueOf(location.getLatitude());
//                lon = String.valueOf(location.getLongitude());
//                Log.e("latlong", lat+" - "+lon);
//                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            }
//            if (addresses.size() > 0) {
////                StringBuilder sb = new StringBuilder();
////                Address address = addresses.get(0);
////                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
////                        sb.append(address.getAddressLine(i)).append(", ");
////                    }
////                    sb.append(address.getLocality());
////                    finalAddress = sb.toString();
//                address.setText(addresses.get(0).getAddressLine(0));
//                }
//            Log.e("Latlon: ", lat+" - "+lon);
//                tvLat.setText(lat);
//                tvLon.setText(lon);
//            } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}