package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_chucnang extends Fragment {
    boolean stateDen,  stateBom,stateQuat,stateMaihien;
    ImageView back;
    int icons[]={R.drawable.revolve,R.drawable.fan,R.drawable.lightbulb,R.drawable.canopy};
    String nameicons[]={"BƠM","QUẠT","ĐÈN","MÁI CHE"};
    String id[] = {"swbom","swquat","swden","swmaiche"};
    ProgressDialog progressDialog;


    ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chucnang,container,false);
        checkNetworkConnection(fragment_chucnang.this.getContext());

        listView = (ListView) view.findViewById(R.id.listview);
        listviewchucnangAdapter lvAdapter = new listviewchucnangAdapter(getActivity().getApplicationContext(),icons,nameicons,id );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = listView.getPositionForView(view);
                String selectedItem = (String) parent.getItemAtPosition(position);
                int a = position;
                if (a >= 0 && a < parent.getCount()) {
                    // xử lý sự kiện click trên item hợp lệ
                    if(a == 0){
                        Intent i2 = new Intent(getActivity(),Bom_mode2.class);
                        startActivity(i2);
                    }
                    else if (a == 1){
                        Intent i3 = new Intent(getActivity(),Quat_mode2.class);
                        startActivity(i3);
                    }
                    else if (a == 2){
                        Intent i4 = new Intent(getActivity(),Den_mode2.class);
                        startActivity(i4);
                    }

                }
            }
        });
        listView.setAdapter(lvAdapter);
        return view;
    }


    private void showAlertDialog1(String tieude,String noidung, String buttonn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(tieude);
        builder.setCancelable(false);
        builder.setMessage(noidung);
        builder.setPositiveButton(buttonn, null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void checkNetworkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
       // boolean isWifi = activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
       // boolean isMobile = activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

        if (!isConnected) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            showAlertDialog1("", "Mất kết nối.","Đóng");        }
        // if (isWifi) {


    }



}



