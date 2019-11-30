package com.naruto.trango.on_boarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.naruto.trango.R;
import com.naruto.trango.login.LoginActivity;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    Button next1, next2, startbtn;
    private int state = 0;
    TextView skip;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        next1 = findViewById(R.id.nextbtn1);
        next2 = findViewById(R.id.nextbtn2);
        startbtn = findViewById(R.id.startbtnser);
        next2.setVisibility(View.GONE);
        startbtn.setVisibility(View.GONE);
        skip = findViewById(R.id.skiptv);

        viewPager = findViewById(R.id.viewpager);

        sliderDotspanel = findViewById(R.id.ll_dots);



        OnBoardingAdapter onBoardingAdapter = new OnBoardingAdapter(this);

        viewPager.setAdapter(onBoardingAdapter);
        next1.setOnClickListener(this::onClick);
        next2.setOnClickListener(this::onClick);
        startbtn.setOnClickListener(this::onClick);

        skip.setOnClickListener(view -> startApp());

        dotscount = onBoardingAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        next1.setVisibility(View.VISIBLE);
                        next2.setVisibility(View.GONE);
                        startbtn.setVisibility(View.GONE);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        next2.setVisibility(View.VISIBLE);
                        startbtn.setVisibility(View.GONE);
                        next1.setVisibility(View.GONE);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        startbtn.setVisibility(View.VISIBLE);
                        next2.setVisibility(View.GONE);
                        next1.setVisibility(View.GONE);
                        skip.setVisibility(View.GONE);
                        break;


                }

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dots));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nextbtn1:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                break;
            case R.id.nextbtn2:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 2, true);
                break;
            case R.id.startbtnser:
                if (state==0) {
                    startApp();
                    state=1;
                }
                break;

        }

    }

    private void startApp() {
        Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}