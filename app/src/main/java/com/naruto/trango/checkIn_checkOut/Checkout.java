package com.naruto.trango.checkIn_checkOut;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.naruto.trango.R;
import com.naruto.trango.homepage.MainHomepage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.models.ChirpError;
import io.chirp.connect.interfaces.ConnectEventListener;

public class Checkout extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    //Chirp Global variables
    String CHIRP_APP_KEY = "2f7fFfEba47eeD56b6aec03C8";
    String CHIRP_APP_SECRET = "A7EA91FA7B4126dad3F8593d32Da5add8ccEAbbb3daEC9e0bE";
    String CHIRP_APP_CONFIG = "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";

    ChirpConnect chirp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_home_page);

        setToolbar();

        ImageView backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(view -> onBackPressed());

        //chirp initialisation using global variables
        chirp = new ChirpConnect(this, CHIRP_APP_KEY, CHIRP_APP_SECRET);
        ChirpError error = chirp.setConfig(CHIRP_APP_CONFIG);
        if (error.getCode() == 0) {
            Log.v("ChirpSDK: ", "Configured ChirpSDK");
        } else {
            Log.e("ChirpError: ", error.getMessage());
        }

    }

    private void setToolbar() {

        Window window = getWindow();
        window.setStatusBarColor(0);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.dots_3);
        toolbar.setOverflowIcon(drawable);

        // Setting the logo text - mekPark
        //TextView makLogoTitle = findViewById(R.id.mek_logo_title);

        String text = "<b><font color=#da1f26>mek</font><font color=#000000>Park</font></b>";
        String final_text = "&#160 " + text;
        // makLogoTitle.setText(Html.fromHtml(final_text));

    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan,menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.history:
                startActivity(new Intent(Checkout.this, ScanHistory.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    ConnectEventListener chirpEventListener = new ConnectEventListener() {
        @Override
        public void onSent(@NotNull byte[] bytes, int i) {
           // showDialogBox(new String(bytes));
        }

        @Override
        public void onSending(@NotNull byte[] bytes, int i) {
            Log.v("onSending", new String(bytes));
        }

        @Override
        public void onReceived(@Nullable byte[] bytes, int i) {
            if (bytes != null) {
                String identifier = new String(bytes);
                Log.v("ChirpSDK: ", "Received " + identifier);
            } else {
                Log.e("ChirpError: ", "Decode failed");
            }
        }

        @Override
        public void onReceiving(int i) {
            Log.v("onReceiving", i+ " - ");
        }

        @Override
        public void onStateChanged(int i, int i1) {

        }

        @Override
        public void onSystemVolumeChanged(float v, float v1) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        // Start ChirpSDK sender and receiver, if no arguments are passed both sender and receiver are started
        ChirpError error = chirp.start(true, true);
        if (error.getCode() > 0) {
            Log.e("ChirpError: ", error.getMessage());
        } else {
            Log.v("ChirpSDK: ", "Started ChirpSDK");
        }
        chirp.setListener(chirpEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirp.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chirp.stop();
        try {
            chirp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callMainHomePageIntent(View view) {

        Intent intent = new Intent(this, MainHomepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void sendQR(View view) {
        String identifier = "Aman";
        byte[] payload = identifier.getBytes(Charset.forName("UTF-8"));

        ChirpError error = chirp.send(payload);
        if (error.getCode() > 0) {
            Log.e("ChirpError: ", error.getMessage());
        } else {
            Log.v("ChirpSDK: ", "Sent " + identifier);
        }
    }


    public void showDialogBox(String qrCode) {
        new AlertDialog.Builder(this)
                .setTitle("QR Code Received")
                .setMessage("ChirpSDK: Received " + qrCode)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", (dialog, which) -> {
                    // Continue
                    dialog.dismiss();
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}

