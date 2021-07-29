package com.example.wolny.Adapter.Main.MyJobs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wolny.Fragment.Main.MyJob.EmployerFragment;
import com.example.wolny.Fragment.Main.MyJob.FreelancerFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return new EmployerFragment();
        }
        return new FreelancerFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
