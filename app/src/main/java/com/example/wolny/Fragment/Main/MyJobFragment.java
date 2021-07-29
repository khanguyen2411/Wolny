package com.example.wolny.Fragment.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wolny.Adapter.Main.MyJobs.ViewPagerAdapter;
import com.example.wolny.R;
import com.google.android.material.tabs.TabLayout;


public class MyJobFragment extends Fragment {

    View mView;
    TabLayout tabLayout;
    ViewPager2 viewPager;

    public MyJobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_my_job, container, false);

        tabLayout = mView.findViewById(R.id.tabLayout);
        viewPager = mView.findViewById(R.id.viewpager);


        tabLayout.addTab(tabLayout.newTab().setText("As freelancer"));
        tabLayout.addTab(tabLayout.newTab().setText("As employer"));

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(adapter);

        viewPager.setUserInputEnabled(false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return mView;
    }
}