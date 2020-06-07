package com.example.appall.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appall.Adapters.MyAD;
import com.example.appall.Interfaces.OnChangeToDo;
import com.example.appall.Interfaces.OnChangeToWork;
import com.example.appall.Models.Notificacion;
import com.example.appall.Models.User;
import com.example.appall.R;
import com.example.appall.Utils.Utils;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment extends Fragment {

    private int id;
    private ListView listView;
    private Realm realm;
    private RealmResults<Notificacion> notifications;
    private SharedPreferences preferences;
    private MyAD Adapter;
    private User user;
    private OnChangeToDo IChangeStatus;

    public ToDoFragment() {
        // Required empty public constructor
    }

    // Eventos para enlazar el listener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChangeToWork) {
            IChangeStatus = (OnChangeToDo) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ToDoFragment");
        }
    }
    // Eventos para desenlazar el listener
    @Override
    public void onDetach() {
        super.onDetach();
        IChangeStatus = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_to_do, container, false);
        // Inflate the layout for this fragment
        preferences = inflater.getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        getUserRealm(Utils.getIdUser(preferences));
        getListOfNotifications();

        listView = (ListView) v.findViewById(R.id.listViewTodo);
        Adapter = new MyAD(getContext(),R.layout.adapter_kanban,notifications);
        listView.setAdapter(Adapter);

        registerForContextMenu(listView);
        return v;
    }

    private void getUserRealm(int id){
        user = realm.where(User.class).equalTo("id",id).findFirst();
    }
    public void getListOfNotifications(){

       this.notifications = user.getNotifications().where().equalTo("status","T").findAll();

    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_kanban_todo,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.item_delete:
                if (this.notifications.get(info.position) != null)
                    removeNotify(this.notifications.get(info.position));
                Adapter.notifyDataSetChanged();
                return false;
            case  R.id.item_change:
                if (this.notifications.get(info.position) != null){
                    changeAction(this.notifications.get(info.position));
                    getListOfNotifications();
                    IChangeStatus.onChangeStatusFromToDoToInWork(0);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void changeAction(Notificacion not) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(not).setStatus("I");
        realm.commitTransaction();
        Adapter.notifyDataSetChanged();
    }

    private void removeNotify(Notificacion not) {
        realm.beginTransaction();
        not.deleteFromRealm();
        realm.commitTransaction();
    }

    public void onChangeAdapter(int i) {
        getListOfNotifications();
        Adapter.notifyDataSetChanged();
    }
}
