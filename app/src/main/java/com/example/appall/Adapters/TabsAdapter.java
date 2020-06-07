package com.example.appall.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appall.Fragments.DoneFragment;
import com.example.appall.Fragments.InWorkFragment;
import com.example.appall.Fragments.ToDoFragment;

public class TabsAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public TabsAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm,numberOfTabs);
        this.numberOfTabs = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ToDoFragment();
            case 1:
                return new InWorkFragment();
            case 2:
                return new DoneFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
