package com.example.appall.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.appall.Models.User;
import com.example.appall.R;
import com.example.appall.Utils.Utils;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private EditText email, pass;
    private Switch remember;
    private Button btnEnter;
    private Realm realm;
    private User user;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        realm = Realm.getDefaultInstance();

        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        buildUI();
        setCredencialsIfExists();
        setToolbar();
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email = email.getText().toString();
                String _pass = pass.getText().toString();
                if (login(_email,_pass)) {
                    if (getUserRealm(_email, _pass)) {
                        saveOnPreferences(user.getId());
                        goToMain();
                    } else {
                        Toast.makeText(getApplicationContext(),"Usuario o Contraseña Incorrectos",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_item_login);
    }

    private boolean getUserRealm(String pEmail, String pPass) {

        user = realm.where(User.class).equalTo("email",pEmail).equalTo("password",pPass).findFirst();

        return user != null;
    }

    private boolean getUserById(int id){
        user = realm.where(User.class).equalTo("id",id).findFirst();

        return user != null;
    }

    private void setCredencialsIfExists() {
        if (Utils.getStatus(preferences).equals("A")){
            if (Utils.getIdUser(preferences) > 0){
                if (getUserById(Utils.getIdUser(preferences))){
                    email.setText(this.user.getEmail());
                    pass.setText(this.user.getPassword());
                }
            }
        }
    }

    private void goToMain() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("id",user.getId());
        startActivity(intent);
    }

    private void saveOnPreferences(int id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userId",id);
        if (remember.isChecked())
            editor.putString("status","A");
        else
            editor.putString("status","I");

        editor.apply();
    }

    private void buildUI(){
        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editPass);
        btnEnter = findViewById(R.id.btnEnter);
        remember = findViewById(R.id.sw1);
        toolbar = findViewById(R.id.toolbar);
    }

    private boolean login(String email, String pass) {
        if (!isValidEmail(email)) {
            Toast.makeText(this,"Email is not valid, please try again",Toast.LENGTH_SHORT).show();
            return false;
        } else if(!isValidPassword(pass)) {
            Toast.makeText(this,"Password is not valid, 4 characters or more, please try again",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String pass) {
        return pass.length() >= 4;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item_login,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_new_user:
                Intent intent = new Intent(LoginActivity.this, SingInActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
