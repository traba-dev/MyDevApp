package com.example.appall.Utils;


import android.content.SharedPreferences;

public class Utils {

//    public static String getEmailUserShared(SharedPreferences preferences) {
//        return preferences.getString("email","");
//    }
//    public static String getPassUserShared(SharedPreferences presPreferences) {
//        return presPreferences.getString("pass","");
//    }

    public static int getIdUser(SharedPreferences preferences) {
        return preferences.getInt("userId",0);
    }
    public static String getStatus(SharedPreferences preferences){
        return preferences.getString("status","I");
    }
}
