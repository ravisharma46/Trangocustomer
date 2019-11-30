package com.naruto.trango;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.naruto.trango.on_boarding.OnBoardingActivity;

public class SplashActivity extends AppCompatActivity {

//    private LoginSessionManager mSession;
    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mSession=new LoginSessionManager(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        final ImageView backgroundOne = findViewById(R.id.splash_background1);
        final ImageView backgroundTwo = findViewById(R.id.splash_background2);
        final ImageView roadOne = findViewById(R.id.splash_road1);
        final ImageView roadTwo = findViewById(R.id.splash_road2);

        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            final float progress1 = (float) animation.getAnimatedValue();
            final float width1 = backgroundOne.getWidth();
            final float translationX1 = width1 * progress1;
            backgroundOne.setTranslationX(translationX1);
            backgroundTwo.setTranslationX(translationX1 - width1);
            final float progress2 = (float) animation.getAnimatedValue();
            final float width2 = roadOne.getWidth();
            final float translationX2 = width2 * progress2;
            roadOne.setTranslationX(translationX2);
            roadTwo.setTranslationX(translationX2 - width2);
        });
        animator.start();

        Handler handler = new Handler();

        handler.postDelayed(() -> {


//            if (!mSession.isLoggedIn()) {
//                mSession.checkLogin();
                Intent intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
                startActivity(intent);

//            }
//            else{
//                Intent intent = new Intent(SplashActivity.this, NavActivity.class);
//                startActivity(intent);
//            }

            finish();
        }, SPLASH_DISPLAY_LENGTH);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}