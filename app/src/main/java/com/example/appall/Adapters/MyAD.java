package com.example.appall.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appall.Models.Notificacion;
import com.example.appall.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyAD extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Notificacion> notifications;

    public MyAD(Context context, int layout, List<Notificacion> notifications){
        this.context = context;
        this.layout = layout;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return this.notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return this.notifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.notifications.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        IViewHolder v;

        if (convertView == null){

            convertView = LayoutInflater.from(this.context).inflate(this.layout,null);

            v = new IViewHolder();

            v.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            v.txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
            v.txtCreatedNotify = (TextView) convertView.findViewById(R.id.txtCreatedNotify);

            convertView.setTag(v);

        }else{
            v = (IViewHolder) convertView.getTag();
        }

        v.txtTitle.setText(this.notifications.get(position).getTitle());
        v.txtDescription.setText(this.notifications.get(position).getDescription());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = this.notifications.get(position).getCreate_ad();
        String created_ad = df.format(date).toString();
        v.txtCreatedNotify.setText("Fecha: "+created_ad);

        return convertView;
    }

    static class IViewHolder{
        private TextView txtTitle;
        private TextView txtDescription;
        private TextView txtCreatedNotify;
    }
}
