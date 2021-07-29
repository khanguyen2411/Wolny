package com.example.wolny.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.wolny.Adapter.OnBoarding.ViewPagerAdapter;
import com.example.wolny.R;

import me.relex.circleindicator.CircleIndicator2;
import me.relex.circleindicator.CircleIndicator3;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    Button btnFacebook, btnEmailUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_onboarding);

        init();

        ViewPagerAdapter mAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(mAdapter);

        CircleIndicator3 indicator = findViewById(R.id.circleIndicator);
        indicator.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        setTextColor("#0184f7");
                        break;
                    case 1:
                        setTextColor("#22b3a4");
                        break;
                    case 2:
                        setTextColor("#ed7b1b");
                        break;
                }
            }
        });

        btnEmailUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LogInSignUpActivity.class));
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void init(){
        btnFacebook = findViewById(R.id.btnFacebook);
        btnEmailUsername = findViewById(R.id.btnEmailUsername);
        viewPager2 = findViewById(R.id.viewpager);
    }

    void setTextColor(String colorString){
        int color = Color.parseColor(colorString);
        btnFacebook.setTextColor(color);
        btnEmailUsername.setTextColor(color);
    }
}