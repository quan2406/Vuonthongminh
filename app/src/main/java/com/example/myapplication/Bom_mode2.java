package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class Bom_mode2 extends AppCompatActivity {
    SeekBar seekBar;
    private ProgressBar mProgressBar;
    private CountDownTimer mCountDownTimer;
    EditText timeoutbom;
    private int progr = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference pumpSpeed,timeoutPump;
    Button bomm;
    int giay;
    ImageView back2;
    int giays;
    TextView t1;
    Handler handler;

    String second;
    private int countDown = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bom_mode2);
        anhxa();
        InitDataFirebase();
        seekBar.setMax(3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(1);
        }
        back2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
          });
        bomm.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                ClickSeeBar();
                ProgressDelay();
              }
          });

        timeoutbom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePick();
            }
        });


    }
    private void InitDataFirebase(){
        pumpSpeed = database.getReference("pumpSpeed");
        timeoutPump = database.getReference("timeoutPump");
    }
    private void anhxa()
    {
        bomm  = (Button)findViewById(R.id.bomm);
        back2 = (ImageView)findViewById(R.id.back2);
        timeoutbom = (EditText)findViewById(R.id.timeoutbom);
        mProgressBar = findViewById(R.id.progressBar);
        t1 = findViewById(R.id.textView4000);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        handler = new Handler();
    }
    private void check()
    {
        if(giays < 0)
        {
            showAlertDialog("Lỗi","Thời gian không hợp lệ", "Đóng");
        }
        else {
            showAlertDialog("","Thiết lập thành công", "Đóng");

        }
    }
    private void TimePick(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(Bom_mode2.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final int[] hours = {0};
                final int[] minutes = {0};

                hours[0] = hourOfDay;

                minutes[0] = minute;


                // Lấy thời gian hiện tại
                Calendar currentTime = Calendar.getInstance();
                long currentMillis = currentTime.getTimeInMillis();

                // Tạo Calendar object với giờ phút giây được chọn
                Calendar selectedTime = Calendar.getInstance();
                selectedTime.set(Calendar.HOUR_OF_DAY, hours[0]);
                selectedTime.set(Calendar.MINUTE, minutes[0]);


                // Tính khoảng thời gian giữa hai thời điểm tính theo mili giây
                long diffInMillis = selectedTime.getTimeInMillis() - currentMillis;

                // Chuyển đổi sang giây
                int diffInSeconds = (int) (diffInMillis / 1000);
                giay = diffInSeconds;
                giays = diffInSeconds;

                // Hiển thị kết quả
                // TextView textView = findViewById(R.id.textView2);
                //     textView.setText(String.valueOf(diffInSeconds));
                check();
            }
        }, 0, 0, true);
        timePickerDialog.show();
    }
    private void ClickSeeBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("AAA","Giá trị : "+ progress);

                if (progress == 1 ) {
                    pumpSpeed.setValue("1");
                }else if (progress == 2 ) {
                    pumpSpeed.setValue("2");
                }
                else {
                    pumpSpeed.setValue("3");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void ProgressDelay(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progr <= giay) {
                    int giayy = giay /100;
                    mProgressBar.setProgress(giay);
                    mProgressBar.setMax(giays);
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String formattedTime = formatter.format(new Date(giay * 1000));
                    t1.setText(formattedTime);
                    timeoutbom.setText(formattedTime);

                    giay --;//progr

                    second = Integer.toString(giays);

                    if (giay == 0)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mProgressBar.setMin(0);
                        }

                    }
                    handler.postDelayed(this, 1000);

                } else {
                    handler.removeCallbacks(this);
                }
            }
        }, 1000);
        timeoutPump.setValue(second);

    }
    private void showAlertDialog(String tieude,String noidung, String buttonn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tieude);
        builder.setCancelable(false);
        builder.setMessage(noidung);
        builder.setPositiveButton(buttonn, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
