package com.naruto.trango;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SOSActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        coordinatorLayout = findViewById(R.id.sos_coordinate_layout);
    }

    public void sendAlert(View view) {
        Snackbar.make(coordinatorLayout, "Alert Sent!", BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}
