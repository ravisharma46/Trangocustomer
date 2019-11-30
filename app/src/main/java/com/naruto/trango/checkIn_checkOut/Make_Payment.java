package com.naruto.trango.checkIn_checkOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.naruto.trango.R;
import com.naruto.trango.homepage.MainHomepage;

public class Make_Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make__payment);
    }

    public void callScanHomepageIntent(View view) {
        startActivity(new Intent(this, Checkout.class));
        finish();
    }

    public void callMainHomePageIntent(View view) {
        Intent intent = new Intent(this, MainHomepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
