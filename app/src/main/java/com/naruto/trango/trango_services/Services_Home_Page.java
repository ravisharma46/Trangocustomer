package com.naruto.trango.trango_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.naruto.trango.R;
import com.naruto.trango.cabs_n_more.CabHomepageActivity;
import com.naruto.trango.local_transport.LocalsActivity;

public class Services_Home_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services__home__page);

    }

    public void callCAMIntent(View view) {
        Intent intent = new Intent(this, CabHomepageActivity.class);
        startActivity(intent);
    }

    public void callBusTransitIntent(View view) {
        Intent intent = new Intent(this, LocalsActivity.class);
        intent.putExtra("tab", "bus");
        startActivity(intent);
    }

    public void callTrainTransitIntent(View view) {
        Intent intent = new Intent(this, LocalsActivity.class);
        intent.putExtra("tab", "train");
        startActivity(intent);
    }

    public void callMetroTransitIntent(View view) {
        Intent intent = new Intent(this, LocalsActivity.class);
        intent.putExtra("tab", "metro");
        startActivity(intent);
    }
}


