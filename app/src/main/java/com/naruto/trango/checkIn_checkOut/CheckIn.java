package com.naruto.trango.checkIn_checkOut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.naruto.trango.R;

import java.util.ArrayList;

public class CheckIn extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CheckListDataAdapter mAdapter;
    private Context context;

    Integer[] stationdot={R.drawable.asset_78xhdpi};
    String[] station = {"Indrpuram"};
    String[] atime = {"1:40pm"};
    String[] dtime = {"1:20pm"};
    String[] station_deatils={"station details"};
    String[] full_timetable={"Full TimeTabe"};

    private ArrayList<CheckInListDaa> feedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        mRecyclerView=findViewById(R.id.checkin_RV);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //adding data to arraylist
        feedItemList = new ArrayList<>();

        for(int i=0;i<16;i++) {
            for (int j = 0; j< atime.length; j++) {
                CheckInListDaa getterSetter = new CheckInListDaa(stationdot[j],station[j], atime[j], dtime[j],station_deatils[j],full_timetable[j]);
                feedItemList.add(getterSetter);
            }
        }
        //recyclerview adapter
        mAdapter = new CheckListDataAdapter(CheckIn.this,feedItemList);

        //set adpater for recyclerview
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_menu, menu);
        return true;
    }

    public void callScanCheckoutIntent(View view) {
        Intent intent = new Intent(this, Scan_Checkout.class);
        startActivity(intent);
    }
}
