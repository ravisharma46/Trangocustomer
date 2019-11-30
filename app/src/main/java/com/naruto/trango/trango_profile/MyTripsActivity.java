package com.naruto.trango.trango_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.naruto.trango.R;

public class MyTripsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
    }

    public void callRedeemIntent(View view) {
        Intent intent = new Intent(this, RedeemActivity.class);
        startActivity(intent);
    }
}
