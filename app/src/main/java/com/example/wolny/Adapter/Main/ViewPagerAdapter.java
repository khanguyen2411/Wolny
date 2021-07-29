package com.example.wolny.Adapter.Main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wolny.Fragment.Main.BrowseFragment;
import com.example.wolny.Fragment.Main.MessageFragment;
import com.example.wolny.Fragment.Main.MyJobFragment;
import com.example.wolny.Fragment.Main.PostJobFragment;
import com.example.wolny.Fragment.Main.UserFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new BrowseFragment();
            case 2:
                return new PostJobFragment();
            case 3:
                return new MessageFragment();
            case 4:
                return new UserFragment();
            default:
                return new MyJobFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
