package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class fragment_chedo3 extends Fragment {
    Switch swmode1, swmode2, swmode3;
    boolean sw1,sw2,sw3;
    EditText edit1nd,edit1da,edit1as,edit2nd,edit2da,edit2as;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String formattedValue1,formattedValue2,formattedValue3,formattedValue4,formattedValue5,formattedValue6;
    DecimalFormat decimalFormat;
    private List<EditText> mEditTextList;
    int v1,v2,v3,v4,v5,v6;
    private boolean mSwitchEnabled = false;
    String value1,value2,value3,value4,value5,value6;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vieww = inflater.inflate(R.layout.activity_chedo3,container,false);
        checkNetworkConnection(fragment_chedo3.this.getContext());

        final DatabaseReference CHEDO = database.getReference("CHEDO");
        final DatabaseReference threshodLight = database.getReference("thresholdLight");
        final DatabaseReference thresholdSoilMosture = database.getReference("thresholdSoilMosture");
        final DatabaseReference thresholdTemp = database.getReference("thresholdTemp");
        decimalFormat  = new DecimalFormat("00");
        swmode1 = (Switch) vieww.findViewById(R.id.swmode1);
        swmode2 = (Switch) vieww.findViewById(R.id.swmode2);
        swmode3 = (Switch) vieww.findViewById(R.id.swmode3);
        edit1nd = (EditText) vieww.findViewById(R.id.edit1nd);
        edit1da = (EditText) vieww.findViewById(R.id.edit1da);
        edit1as = (EditText) vieww.findViewById(R.id.edit1as);
        edit2nd = (EditText) vieww.findViewById(R.id.edit2nd);
        edit2da = (EditText) vieww.findViewById(R.id.edit2da);
        edit2as = (EditText) vieww.findViewById(R.id.edit2as);
        mEditTextList = new ArrayList<>();
        mEditTextList.add(edit1nd);
        mEditTextList.add(edit1da);
        mEditTextList.add(edit1as);
        mEditTextList.add(edit2nd);
        mEditTextList.add(edit2da);
        mEditTextList.add(edit2as);

        CHEDO.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value =snapshot.getValue(String.class);
                if(value.equals("1")){
                    swmode1.setChecked(true);
                    swmode2.setChecked(false);
                    swmode3.setChecked(false);
                }
                else if(value.equals("2")){
                    swmode1.setChecked(false);
                    swmode2.setChecked(true);
                    swmode3.setChecked(false);
                }
                else {
                    swmode1.setChecked(false);
                    swmode2.setChecked(false);
                    swmode3.setChecked(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        swmode1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    CHEDO.setValue("1");
                }
                else{
                };
            }
        });
        swmode2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    CHEDO.setValue("2");
                }
                else{};
            }
        });

        for (EditText editText : mEditTextList) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Do nothing
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s)) {
                        mSwitchEnabled = false;
                        swmode3.setChecked(false);
                      //  swmode3.setEnabled(false);
                    } else if (allEditTextsFilled()) {
                        mSwitchEnabled = true;
                    //    swmode3.setEnabled(true);
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                    // Do nothing
                }
            });
        }
        swmode3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    value1 = (edit1nd.getText().toString());
                    value2 =(edit1da.getText().toString());
                    value3 = (edit1as.getText().toString());
                    value4 = (edit2nd.getText().toString());
                    value5 = (edit2da.getText().toString());
                    value6 = (edit2as.getText().toString());
                    if (value1 != null && !value1.isEmpty() ||value2 != null && !value2.isEmpty()
                            ||value3 != null && !value3.isEmpty() ||value4 != null && !value4.isEmpty()
                            ||value5 != null && !value5.isEmpty() || value6 != null && !value6.isEmpty()
                      ) {
                        // Kiểm tra chuỗi đầu vào không rỗng hoặc null
                        try {
                            // Chuyển đổi chuỗi thành số nguyên
                            v1 = Integer.parseInt(value1);
                            v2 = Integer.parseInt(value2);
                            v3 = Integer.parseInt(value3);
                            v4 = Integer.parseInt(value4);
                            v5 = Integer.parseInt(value5);
                            v6 = Integer.parseInt(value6);

                                formattedValue1 = decimalFormat.format(v1);
                                formattedValue2 = decimalFormat.format(v2);
                                formattedValue3 = decimalFormat.format(v3);
                                formattedValue4 = decimalFormat.format(v4);
                                formattedValue5 = decimalFormat.format(v5);
                                formattedValue6 = decimalFormat.format(v6);

                            thresholdTemp.setValue(formattedValue1 + formattedValue4);
                            thresholdSoilMosture.setValue(formattedValue2 + formattedValue5);
                            threshodLight.setValue(formattedValue3 + formattedValue6 );


                        } catch (NumberFormatException e) {
                            // Xử lý ngoại lệ nếu chuỗi không phải là một số hợp lệ
                            e.printStackTrace();
                            showAlertDialog1("Lỗi","Không được để trống","Đóng");
                        }
                    } else {
                        // Xử lý trường hợp chuỗi đầu vào rỗng hoặc null
                        // ...
                        showAlertDialog1("Lỗi","Không được để trống","Đóng");
                           }






                    if (!mSwitchEnabled) {


                        swmode3.setChecked(false);
                    } else {

                        swmode3.setChecked(isChecked);
                        CHEDO.setValue("3");
                    }

                }

            }
        });
        return vieww;
    }
    private boolean allEditTextsFilled() {
        for (EditText editText : mEditTextList) {
            if (TextUtils.isEmpty(editText.getText())) {
                return false;

            }


        }
        return true;
    }
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Lỗi");
        builder.setMessage("Các thông số không được để trống");
        builder.setPositiveButton("Đóng", null);

        AlertDialog dialog = builder.create();
        dialog.show();
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
     //   boolean isWifi = activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
      //  boolean isMobile = activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

        if (!isConnected) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            showAlertDialog1("", "Mất kết nối.","Đóng");        }

    }

}
