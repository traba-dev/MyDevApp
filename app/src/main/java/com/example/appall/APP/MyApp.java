package com.example.appall.APP;

import android.app.Application;
import android.os.SystemClock;

import com.example.appall.Models.Notificacion;
import com.example.appall.Models.User;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApp extends Application {

    public static AtomicInteger UserId = new AtomicInteger();
    public static AtomicInteger NotId = new AtomicInteger();

    @Override
    public void onCreate() {
        Realm.init(getApplicationContext());
        SystemClock.sleep(10000);
        setupRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        UserId = getIdByTable(realm, User.class);
        NotId = getIdByTable(realm, Notificacion.class);
        realm.close();
        super.onCreate();
    }

    private void setupRealmConfig() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()): new AtomicInteger();
    }
}
