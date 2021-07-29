package com.example.wolny.Adapter.LogInSignUp;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wolny.Fragment.LogInSignUp.LogInFragment;
import com.example.wolny.Fragment.LogInSignUp.SignUpFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SignUpFragment();
            case 1:
                return new LogInFragment();
        }
        return new SignUpFragment();
        
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
