package com.example.appall.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appall.Adapters.MyAD;
import com.example.appall.Models.Notificacion;
import com.example.appall.Models.User;
import com.example.appall.R;
import com.example.appall.Utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragment extends Fragment {

    public Realm realm;
    public ListView listView;
    public RealmList<Notificacion> notifications;
    public MyAD myAD;
    public User user;
    private FloatingActionButton fap;
    private EditText title;
    private EditText description;
    private SharedPreferences preferences;

    public NotifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notify, container, false);
        preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        getUser();

        getListOfNotifications();

        title = (EditText) v.findViewById(R.id.txtTitleNotify);
        description = (EditText) v.findViewById(R.id.txtDescriptionNotify);

        listView = (ListView) v.findViewById(R.id.listViewNotify);
        myAD = new MyAD(getContext(),R.layout.adapter_notify,notifications);
        listView.setAdapter(myAD);

        fap = v.findViewById(R.id.fapNotify);
        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog().show();
            }
        });

        registerForContextMenu(listView);
        return v;
    }

    private void getUser() {
        if (Utils.getIdUser(preferences) > 0){
            this.user = realm.where(User.class).equalTo("id",Utils.getIdUser(preferences)).findFirst();
        }
    }

    public void getListOfNotifications(){
        notifications = user.getNotifications();
    }

    private void createNotification(String pTitle, String pDescription){
        Notificacion _notify = new Notificacion(pTitle,pDescription,"T");
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(_notify);
        this.user.getNotifications().add(_notify);
        realm.commitTransaction();
    }

    private void deleteTest(){
        realm.beginTransaction();
        user.getNotifications().deleteLastFromRealm();
        realm.commitTransaction();
    }

    public void setUserId(User pUser) {
        this.user = pUser;
    }


    private Dialog showAlertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View v =inflater.inflate(R.layout.alert_dialog_notify,null);

        alertDialog.setView(v).setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                title = v.findViewById(R.id.txtTitleNotify);
                description = v.findViewById(R.id.txtDescriptionNotify);

                String _title = title.getText().toString();
                String _description = description.getText().toString();
                if (!TextUtils.isEmpty(_title) && !TextUtils.isEmpty(_description)){
                    createNotification(_title,_description);
                    title.setText("");
                    description.setText("");
                    myAD.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(),"Los campos son requeridos",Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return alertDialog.create();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_item_not, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.item_delete_not){
            if (this.notifications.get(info.position) != null) {
                deleteNotify(this.notifications.get(info.position));
                myAD.notifyDataSetChanged();
            }
        }

        return super.onContextItemSelected(item);
    }

    private void deleteNotify(Notificacion notify) {
        realm.beginTransaction();
        notify.deleteFromRealm();
        realm.commitTransaction();
    }
}
