package com.naruto.trango.checkIn_checkOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.naruto.trango.R;

public class RoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
    }

    public void getTripDetails(View view) {
        Intent intent = new Intent(this, CheckIn.class);
        startActivity(intent);
    }
}
