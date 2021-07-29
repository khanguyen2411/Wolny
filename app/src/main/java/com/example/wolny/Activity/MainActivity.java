package com.example.wolny.Activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wolny.Adapter.Main.ViewPagerAdapter;
import com.example.wolny.R;
import com.example.wolny.Utils.PageTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);

        viewPager2 = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        viewPager2.setAdapter(adapter);

        viewPager2.setUserInputEnabled(false);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuMyJobs:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.menuBrowse:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.menuPostJob:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.menuMessage:
                        viewPager2.setCurrentItem(3);
                        break;
                    case R.id.menuUser:
                        viewPager2.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }

}