package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listviewchucnangAdapter extends BaseAdapter {
    Context context;
    private int count = 0;
    String nameicons[];

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    int h;
    String id[];
    int icons[];

    String abc= "0000";


    LayoutInflater inflater;
    //private Context mContext;

    private boolean mSwitch1State,mSwitch2State,mSwitch3State;
    public listviewchucnangAdapter(Context ctx, int[] icon, String[] iconname, String[] idd){
        this.context = ctx;
        this.nameicons = iconname;
        this.icons = icon;
        this.id = idd  ;
        inflater = LayoutInflater.from(ctx);


    }


    @Override
    public int getCount() {
        return nameicons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

//



    @Override
    public long getItemId(int position) {
        return 0;
    }
    public int getPositionForView(View view) {
        return (int) view.getTag();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//
        convertView = inflater.inflate(R.layout.activity_listviewchucnang, null);
        TextView txtview = (TextView) convertView.findViewById(R.id.txt1);
        ImageView iconImg = (ImageView) convertView.findViewById(R.id.den);
        Switch sw11 = (Switch) convertView.findViewById(R.id.i_tem_switch1);

        h = position;
        txtview.setText(nameicons[position]);
      iconImg.setImageResource(icons[position]);

        final DatabaseReference TINHIEUDONGCO = database.getReference("TINHIEUDONGCO");
        final DatabaseReference CHEDO = database.getReference("CHEDO");
        final DatabaseReference pumpSpeed = database.getReference("pumpSpeed");

        CHEDO.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if(value.equals("1"))
                {
                    sw11.setEnabled(false);
                }
                else if(value.equals("2"))
                {
                    sw11.setEnabled(true);
                }
                else {
                    sw11.setEnabled(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //  Intent intent = getIntent();
        //  boolean switch1State = intent.getBooleanExtra("switch1_state", false);
        TINHIEUDONGCO.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                abc = String.valueOf(value);
                boolean isOn = (value.charAt(position) == '1');
                sw11.setChecked(isOn);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sw11.setTag(position);
        iconImg.setTag(position);
        sw11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = (int) buttonView.getTag();
                char[] charArr = abc.toCharArray();

                if (isChecked) {

                    charArr[position] = '1';
                    if(charArr[0] == '1')
                    {
                        pumpSpeed.setValue("1");

                    }
                } else {
                    charArr[position] = '0';
                    if(charArr[0] == '0')
                    {
                        pumpSpeed.setValue("0");

                    }
                }
                abc = new String(charArr);
                database.getReference("TINHIEUDONGCO").setValue(abc);


            }

        });


        return convertView;

    }


}
