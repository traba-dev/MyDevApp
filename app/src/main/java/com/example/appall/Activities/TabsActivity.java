package com.example.appall.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.appall.Adapters.TabsAdapter;
import com.example.appall.Fragments.DoneFragment;
import com.example.appall.Fragments.InWorkFragment;
import com.example.appall.Fragments.ToDoFragment;

import com.example.appall.Interfaces.OnChangeToDo;
import com.example.appall.Interfaces.OnChangeToDone;
import com.example.appall.Interfaces.OnChangeToWork;
import com.example.appall.R;
import com.google.android.material.tabs.TabLayout;

public class TabsActivity extends AppCompatActivity implements OnChangeToWork, OnChangeToDone, OnChangeToDo {

    private final static int TODO_FRAGMENT = 0;
    private final static int INWORK_FRAGMENT = 1;
    private final static int DONE_FRAGMENT = 2;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        setToolbar();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("To Do"));
        tabLayout.addTab(tabLayout.newTab().setText("In Work"));
        tabLayout.addTab(tabLayout.newTab().setText("Done"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //inflate icon menu
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onChangeStatusFromToDoToInWork(int i) {
        try {
            InWorkFragment inWorkFragment = (InWorkFragment) getSupportFragmentManager().getFragments().get(INWORK_FRAGMENT);
            inWorkFragment.onChangeAdapter(i);
            viewPager.setCurrentItem(INWORK_FRAGMENT);
        } catch (Exception e) {

        }

    }

    @Override
    public void onChangeStatusFromInWorkToDo(int i) {
        try {
            ToDoFragment toDoFragment = (ToDoFragment) getSupportFragmentManager().getFragments().get(TODO_FRAGMENT);
            toDoFragment.onChangeAdapter(i);
            viewPager.setCurrentItem(TODO_FRAGMENT);
        } catch (Exception e) {

        }
    }

    @Override
    public void onChangeStatusFromInWorkToDone(int i) {
        try {
            DoneFragment doneFragment = (DoneFragment) getSupportFragmentManager().getFragments().get(DONE_FRAGMENT);
            doneFragment.onChangeAdapter(i);
            viewPager.setCurrentItem(DONE_FRAGMENT);
        } catch (Exception e) {

        }

    }
    @Override
    public void onChangeStatusFromDoneToDo(int i) {
        try {
            ToDoFragment toDoFragment = (ToDoFragment) getSupportFragmentManager().getFragments().get(TODO_FRAGMENT);
            toDoFragment.onChangeAdapter(i);
            viewPager.setCurrentItem(TODO_FRAGMENT);
        } catch (Exception e) {

        }

    }

    @Override
    public void onChangeStatusFromDoneToInWork(int i) {
        try {
            InWorkFragment inWorkFragment = (InWorkFragment) getSupportFragmentManager().getFragments().get(INWORK_FRAGMENT);
            inWorkFragment.onChangeAdapter(i);
            viewPager.setCurrentItem(INWORK_FRAGMENT);
        } catch (Exception e){

        }

    }
}
