package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Setting_chedo_adapter extends BaseAdapter  {
    Context contextt;
    String namechedo[];

    LayoutInflater inflater;
    public Setting_chedo_adapter(Context ctxx, String[] chedo ){
        this.contextt = ctxx;
        this.namechedo = chedo;
        inflater = LayoutInflater.from(ctxx);

    }
    @Override
    public int getCount() {
        return namechedo.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_setting_chedo_list,null);
        TextView txtview = (TextView) convertView.findViewById(R.id.txt22);
        txtview.setText(namechedo[position]);
        return convertView;

    }
}
