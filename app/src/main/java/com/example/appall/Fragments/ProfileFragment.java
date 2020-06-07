package com.example.appall.Fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.appall.Models.User;
import com.example.appall.R;
import com.example.appall.Utils.Utils;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private EditText name, lastName, phone, email, pass;
    private User user;
    private SharedPreferences preferences;
    private Realm realm;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        realm = Realm.getDefaultInstance();
        preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        getUser();
        initialize(v);
        Button save = (Button) v.findViewById(R.id.btnRegisterProfile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFieldEmpty()){
                    updateUser();
                    Toast.makeText(getContext(),"Usuario actualizado Satisfactoriamente",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(),"Todos los campos son requeridos",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void getUser() {
        if (Utils.getIdUser(preferences) > 0){
            this.user = realm.where(User.class).equalTo("id",Utils.getIdUser(preferences)).findFirst();
        }
    }

    private void updateUser(){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this.user).setName(name.getText().toString());
        realm.copyToRealmOrUpdate(this.user).setLastName(lastName.getText().toString());
        realm.copyToRealmOrUpdate(this.user).setPhoneNumber(phone.getText().toString());
        realm.commitTransaction();
    }

    private void initialize(View v){
        name = (EditText) v.findViewById(R.id.editNameProfile);
        lastName = (EditText) v.findViewById(R.id.editLastNameProfile);
        phone = (EditText) v.findViewById(R.id.editPhoneProfile);
        email = (EditText) v.findViewById(R.id.editEmailProfile);
        pass = (EditText) v.findViewById(R.id.editPassProfile);

        if (this.user != null){
            name.setText(this.user.getName());
            lastName.setText(this.user.getLastName());
            phone.setText(this.user.getPhoneNumber());
            email.setText(this.user.getEmail());
            pass.setText(this.user.getPassword());
        }

        email.setEnabled(false);
        pass.setEnabled(false);
    }

    private boolean isFieldEmpty(){
        return TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(lastName.getText()) ||
                TextUtils.isEmpty(phone.getText()) || TextUtils.isEmpty(email.getText()) ||
                TextUtils.isEmpty(pass.getText());
    }
}
