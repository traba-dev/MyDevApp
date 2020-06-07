package com.example.appall.Models;

import com.example.appall.APP.MyApp;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Notificacion extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String title;
    @Required
    private String description;
    @Required
    private String status;
    private Date create_ad;

    public Notificacion(){}

    public Notificacion(String title, String description, String status) {
        this.id = MyApp.NotId.incrementAndGet();
        this.title = title;
        this.description = description;
        this.create_ad = new Date();
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreate_ad() {
        return create_ad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
