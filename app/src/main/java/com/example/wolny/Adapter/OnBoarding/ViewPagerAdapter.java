package com.example.wolny.Adapter.OnBoarding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wolny.Fragment.OnBoarding.OnBoardingFragment1;
import com.example.wolny.Fragment.OnBoarding.OnBoardingFragment2;
import com.example.wolny.Fragment.OnBoarding.OnBoardingFragment3;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new OnBoardingFragment1();
            case 1:
                return new OnBoardingFragment2();
            case 2:
                return new OnBoardingFragment3();
        }
        return new OnBoardingFragment1();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
