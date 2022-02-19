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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements IMain.ISetQuote {

    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    @Override
    protected void onStart() {
        super.onStart();
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("status", "online");
        databaseReference.child("Users").child(uid).child("status").setValue("online");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);

        viewPager2 = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        viewPager2.setAdapter(adapter);

        viewPager2.setUserInputEnabled(false);
        viewPager2.setPageTransformer(null);

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

    @Override
    protected void onDestroy() {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("status", "offline");
        databaseReference.child("Users").child(uid).child("status").setValue("offline");
        super.onDestroy();
    }
}