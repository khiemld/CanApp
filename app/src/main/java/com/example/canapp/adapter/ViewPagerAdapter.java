package com.example.canapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.canapp.HomeActivity;
import com.example.canapp.My_Profile;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeActivity();
            case 1:
                return new HomeActivity();
            case 2:
                return new HomeActivity();
            case 3:
                return new My_Profile();
            default:
                return new HomeActivity();
        }
    }



    @Override
    public int getCount() {
        return 4;
    }
}
