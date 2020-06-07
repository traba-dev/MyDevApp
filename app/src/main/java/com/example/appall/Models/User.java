package com.example.appall.Models;

import com.example.appall.APP.MyApp;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private String lastName;
    @Required
    private String phoneNumber;
    @Required
    private String email;
    @Required
    private String password;

    private RealmList<Notificacion> notifications;

    public User(){}

    public User(String name, String lastName, String phoneNumber, String email, String password) {
        this.id = MyApp.UserId.incrementAndGet();
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.notifications = new RealmList<Notificacion>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public RealmList<Notificacion> getNotifications() {
        return notifications;
    }

}
