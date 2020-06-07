package com.example.appall.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appall.Fragments.MapFragment;
import com.example.appall.Fragments.NotifyFragment;
import com.example.appall.Fragments.ProfileFragment;
import com.example.appall.Models.User;
import com.example.appall.R;
import com.example.appall.Utils.Utils;
import com.google.android.material.navigation.NavigationView;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private ImageView imageViewNavigationView;
    private NavigationView navigationView;
    private Realm realm;
    private SharedPreferences preferences;
    private TextView txtName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        setToolbar();
        initializeElement();
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        if (Utils.getIdUser(preferences) > 0)
                getUser(Utils.getIdUser(preferences));
        else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        if (savedInstanceState == null) {
            setDefaultFragment();
        }
    }

    private void initializeElement() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        txtName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtName);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(this);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //inflate icon menu
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setDefaultFragment() {
        MenuItem item =navigationView.getMenu().getItem(0);
        NotifyFragment fragment = new NotifyFragment();
        changeFragment(fragment,item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        boolean fragmentTransaction = false;
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.item_exit:
                logout();
            break;
            case R.id.item_exit_clear:
                RemovePreferences();
                logout();
                break;
            case R.id.item_profile:
                fragment = new ProfileFragment();
                fragmentTransaction = true;
                break;
            case R.id.item_not:
                fragment = new NotifyFragment();
                fragmentTransaction = true;
                break;
            case R.id.item_my_kanban:
                Intent intent = new Intent(MainActivity.this, TabsActivity.class);
                startActivity(intent);
                break;
            case R.id.item_navigation:
                fragment = new MapFragment();
                fragmentTransaction = true;
                break;
            default:
                break;
        }

        if (fragmentTransaction) {
            changeFragment(fragment,item);
            drawerLayout.closeDrawers();
        }
        return fragmentTransaction;
    }
    private void getUser(int id) {
        user = realm.where(User.class).equalTo("id",id).findFirst();
        txtName.setText(user.getName());
    }
    private void logout() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void RemovePreferences(){
        preferences.edit().clear().apply();
    }
    private void changeFragment(Fragment fragment, MenuItem item) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
