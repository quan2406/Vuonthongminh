package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class fragment_thongso extends Fragment {
    TextView tas1,tdad1,tnd1,tdakk1,textView3,txapi,temp;
    ImageView home,nutnhan,gear,dubao;
    String a;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    View view;
    private BroadcastReceiver networkChangeReceiver;
    private boolean isConnected = false;
    CardView nhietdo1,doamdat1,anhsang1,doamkk1;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_thongso,container,false);
        check11();
        anhxa();
        GetCurrent();
        InitDataFirebase();
        checkNetworkConnection(fragment_thongso.this.getContext());
        nhietdo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nhietdo1 = new Intent(getActivity(),LineChart_nhietdo.class);
                startActivity(nhietdo1);
            }
        });
        doamkk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doamkk1 = new Intent(getActivity(),Linechart_doam.class);
                startActivity(doamkk1);
            }
        });
        doamdat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  doamdat1 = new Intent(getActivity(),Linechart_doamdat.class);
                startActivity(doamdat1);
            }
        });
        anhsang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  anhsang1 = new Intent(getActivity(),Linechart_anhsang.class);
                startActivity(anhsang1);
            }
        });
        return view;
    }
    private void anhxa(){
        tas1 = (TextView) view.findViewById(R.id.tas1);
        txapi = (TextView) view.findViewById(R.id.txapi);
        temp = (TextView) view.findViewById(R.id.temp);
        tdad1 = (TextView) view.findViewById(R.id.tdad1);
        tdakk1 = (TextView) view.findViewById(R.id.tdakk1);
        tnd1 = (TextView) view.findViewById(R.id.tnd1);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        dubao = (ImageView) view.findViewById(R.id.dubao);
        nhietdo1 = (CardView) view.findViewById(R.id.nhietdo1);
        doamdat1 = (CardView) view.findViewById(R.id.doamdat1);
        anhsang1 = (CardView) view.findViewById(R.id.anhsang1);
        doamkk1 = (CardView) view.findViewById(R.id.doamkk1);
    }
    private void InitDataFirebase(){
        final DatabaseReference DULIEUCAMBIEN = database.getReference("DULIEUCAMBIEN"); // ket noi den truong da tao tren firebase
        DULIEUCAMBIEN.addValueEventListener(new ValueEventListener() { // goi truong gia tri theo cap nhat thay doi database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String value =snapshot.getValue(String.class);
                if(value != null)
                {
                    char[] vl = new char[9];
                    value.getChars(0,2,vl,0);
                    String str = String.copyValueOf(vl) + "\u2103";
                    tnd1.setText(str);

                    value.getChars(2,4,vl,0);
                    String str1 = String.copyValueOf(vl) + "%";
                    tdakk1.setText(str1);

                    value.getChars(4,6,vl,0);
                    String str2 = String.copyValueOf(vl) + "%";
                    tdad1.setText(str2);

                    value.getChars(6,8,vl,0);
                    String str3= String.copyValueOf(vl) + "lx";
                    tas1.setText(str3);

                    value.getChars(8,9,vl,0);
                    String str4= String.copyValueOf(vl);
                    char s1 = str4.charAt(0);
                    if(s1 == '1' )
                    {
                        dubao.setImageResource(R.drawable.rain);
                    }else {
                        dubao.setImageResource(R.drawable.suncloud);

                    }
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        checkNetworkConnection(requireContext());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String errorMessage = "Mất kết nối với Firebase";
                Log.d("Error", errorMessage);
                if (progressDialog != null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
                if (error.getCode() == DatabaseError.NETWORK_ERROR) {
                    errorMessage = "Không thể kết nối đến Firebase, vui lòng kiểm tra kết nối Internet của bạn";
                }
                showAlertDialog1("Lỗi", errorMessage, "Đóng");
            }
        });
    }
    private void GetCurrent(){

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://api.weatherapi.com/v1/current.json?key=b421cb51418a45ef9f1150452232203&q=Hanoi&aqi=no";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    JSONObject jsonObjectcurrent= jsonObject.getJSONObject("current");
                    String nhietdo = jsonObjectcurrent.getString("temp_c");
                    Double a = Double.valueOf(nhietdo);
                    String Nhietdo = String.valueOf(a.intValue());
                    temp.setText(Nhietdo + "°C");

                    JSONObject jsonObjectLocation= jsonObject.getJSONObject("location");
                    String name = jsonObjectLocation.getString("name");
                    String vn = jsonObjectLocation.getString("country");
                    txapi.setText(name+", "+vn);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(stringRequest);
    }
   private void check11()
   {
       progressDialog = new ProgressDialog(getActivity());
       progressDialog.setTitle("Loading...");
       progressDialog.setMessage("Please wait.");
       progressDialog.setCancelable(false);
       progressDialog.show();

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
        if (!isConnected) {
            progressDialog.dismiss();
            showAlertDialog1("", "Mất kết nối.","Đóng");
        }

    }
}
