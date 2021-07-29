package com.example.wolny.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wolny.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);

        findViewById(R.id.ivLogo).startAnimation(animation);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    startActivity(new Intent(getBaseContext(), OnBoardingActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                }
                finish();
            }
        }, 1500);
    }
}