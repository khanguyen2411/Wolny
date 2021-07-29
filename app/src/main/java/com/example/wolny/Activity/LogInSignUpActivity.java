package com.example.wolny.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wolny.Adapter.LogInSignUp.ViewPagerAdapter;
import com.example.wolny.R;
import com.example.wolny.Utils.PageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LogInSignUpActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_sign_up);

        init();
        ivBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        // init adapter for viewpager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager2.setAdapter(adapter);

        //add tab for tab layout
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.addTab(tabLayout.newTab().setText("Log in"));

        viewPager2.setPageTransformer(new PageTransformer());

        //change tab when swipe viewpager
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        //change fragment when select a tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void init() {
        viewPager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
        ivBack = findViewById(R.id.ivBack);
    }

}