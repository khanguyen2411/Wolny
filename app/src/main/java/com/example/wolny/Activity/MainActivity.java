package com.example.wolny.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wolny.Adapter.Main.ViewPagerAdapter;
import com.example.wolny.IMain;
import com.example.wolny.Presenter.SetQuotePresenter;
import com.example.wolny.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements IMain.ISetQuote {

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

        SetQuotePresenter presenter = new SetQuotePresenter(this);

        presenter.insertQuote(this);
    }


    @Override
    public void setQuote(String author, String content) {

    }

}