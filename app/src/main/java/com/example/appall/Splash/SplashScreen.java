package com.example.appall.Splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appall.Activities.LoginActivity;
import com.example.appall.Activities.MainActivity;
import com.example.appall.Models.User;
import com.example.appall.Utils.Utils;

import io.realm.Realm;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences preferences;
    private Realm realm;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        realm = Realm.getDefaultInstance();

        setCredentialsIfExists();

        finish();
    }

    private void goToActivity(Activity activity, Class pClass) {
        Intent intent = new Intent(activity,pClass);
        if (pClass == MainActivity.class)
                intent.putExtra("id", this.user.getId());
        startActivity(intent);
    }

    private boolean getUserById(int id){
        user = realm.where(User.class).equalTo("id",id).findFirst();

        return user != null;
    }

    private void  setCredentialsIfExists() {
        String _email = "";
        String _pass = "";
        if (Utils.getStatus(preferences).equals("A")){
            if (Utils.getIdUser(preferences) > 0){
                if (getUserById(Utils.getIdUser(preferences))){
                     _email = this.user.getEmail();
                     _pass = this.user.getPassword();
                }
            }
        }
        validUser(_email,_pass);
    }

    private void validUser(String _email, String _pass){
        if (getUserRealm(_email,_pass))
            goToActivity(SplashScreen.this,MainActivity.class);
        else
            goToActivity(SplashScreen.this, LoginActivity.class);
    }

    private boolean getUserRealm(String pEmail, String pPass) {

       user = realm.where(User.class).equalTo("email",pEmail).equalTo("password",pPass).findFirst();
        return user != null;
    }

}
