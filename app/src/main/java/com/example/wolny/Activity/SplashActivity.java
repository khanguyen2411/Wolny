package com.example.wolny.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wolny.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        findViewById(R.id.ivLogo).startAnimation(animation);

        Intent intent;

//        if(!isOnline()){
//            Toast.makeText(this, "Please check the connection", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(this, LogInSignUpActivity.class));
//            finish();
//        }

        if (currentUser == null) {
            intent = new Intent(getBaseContext(), OnBoardingActivity.class);
        } else {
            intent = new Intent(getBaseContext(), MainActivity.class);
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isOnline()){
                    Toast.makeText(getBaseContext(), "Please check the connection", Toast.LENGTH_LONG).show();
                }
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;

    }

}