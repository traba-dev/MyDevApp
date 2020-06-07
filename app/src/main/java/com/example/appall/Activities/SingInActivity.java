package com.example.appall.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appall.Models.User;
import com.example.appall.R;
import com.example.appall.Utils.Utils;

import io.realm.Realm;

public class SingInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private EditText edName;
    private EditText edLastName;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPass;
    private EditText edConfPass;
    private Realm realm;
    private User user;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        realm = Realm.getDefaultInstance();
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        register = (Button) findViewById(R.id.btnRegister);
        edName = (EditText) findViewById(R.id.editName);
        edLastName = (EditText) findViewById(R.id.editLastName);
        edPhone = (EditText) findViewById(R.id.editPhone);
        edEmail = (EditText) findViewById(R.id.editEmail);
        edPass = (EditText) findViewById(R.id.editPass);
        edConfPass = (EditText) findViewById(R.id.editConfPass);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (!haveAnyFieldEmpty()){
            if (comparePassword()){
                createNewUser(edName.getText().toString(), edLastName.getText().toString(), edPhone.getText().toString(), edEmail.getText().toString(), edPass.getText().toString());
                Intent intent;

                if (sharedPreferences())
                    intent = new Intent(SingInActivity.this,MainActivity.class);
                else
                  intent = new Intent(SingInActivity.this, LoginActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else
                Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
        }
        else
          Toast.makeText(getApplicationContext(),"Todos los campos son Requeridos",Toast.LENGTH_SHORT).show();

    }

    public boolean sharedPreferences() {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userId",this.user.getId());
        editor.putString("status","I");
        editor.apply();

        if (Utils.getIdUser(preferences) > 0)
            return true;
        else
            return false;
    }

    private boolean haveAnyFieldEmpty(){
        if (!TextUtils.isEmpty(edName.getText().toString())
                && !TextUtils.isEmpty(edLastName.getText().toString())
                && !TextUtils.isEmpty(edPhone.getText().toString())
                && !TextUtils.isEmpty(edEmail.getText().toString())
                && !TextUtils.isEmpty(edPass.getText().toString())
                && !TextUtils.isEmpty(edConfPass.getText().toString())){
            return false;
        } else {
            return true;
        }
    }
    private boolean comparePassword() {
        return edPass.getText().toString().equals(edConfPass.getText().toString());
    }
    private void createNewUser(String name, String lastName, String phoneNumber, String email, String password) {
        user = new User(name,lastName,phoneNumber,email,password);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
}
