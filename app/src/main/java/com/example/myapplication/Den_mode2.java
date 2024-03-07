package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
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

public class Den_mode2 extends AppCompatActivity {

    private ProgressBar mProgressBarden;
    String timee11;
    String formattedTime;
    EditText timeoutdenn;
    private int progr = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button denn;
    int giay;
    int giays,time1;
    String second;
    TextView tden;
    ImageView back3;
    Handler handler;
    DatabaseReference timeoutBulb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_den_mode2);
        InitDataFireBase();
        anhxa();
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        handler = new Handler();
        timeoutdenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePick();

            }
        });
        denn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDelay();
                String text = timeoutdenn.getText().toString().trim(); // Lấy giá trị của EditText

                if (TextUtils.isEmpty( text)) { // Nếu EditText rỗng
                    showAlertDialog("Lỗi","Không được để thời gian trống", "Đóng");
                }


            }
        });


    }
    private void InitDataFireBase(){
        timeoutBulb = database.getReference("timeoutBulb");
    }
    private void anhxa()
    {
        denn = (Button)findViewById(R.id.denn) ;
        back3 = (ImageView)findViewById(R.id.back3);
        timeoutdenn = (EditText)findViewById(R.id.timeoutden);
        mProgressBarden = findViewById(R.id.progressBarden);
        tden = findViewById(R.id.textView40001);
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(Den_mode2.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        final int[] hours = {0};
                        final int[] minutes = {0};
                        minutes[0] = minute;
                        hours[0] = hourOfDay;
                        // Hiển thị TimePickerDialog lần 2 để chọn giây
                        int totalSeconds = hours[0] * 3600 + minutes[0] * 60 ;
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
                        int selectedTime1 = (int) selectedTime.getTimeInMillis();
                        time1 = selectedTime1;
                        giay = diffInSeconds;
                        giays = diffInSeconds;
                        //  timeoutdenn.setText(formattedTime);
                        // Hiển thị kết quả
                        // TextView textView = findViewById(R.id.textView2);
                        //     textView.setText(String.valueOf(diffInSeconds));
                        check();

                    }
                }, 0, 0, true);
                timePickerDialog.show();
    }
    private void ProgressDelay(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progr <= giay) {

                    mProgressBarden.setProgress(giay);
                    mProgressBarden.setMax(giays);
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    formattedTime = formatter.format(new Date(giay * 1000));
                    giay --;
                    second = Integer.toString(giays);

                    if (giay == 0)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mProgressBarden.setMin(0);
                        }

                    }

                    if(giays > 0)
                    {
                        timeoutdenn.setText(formattedTime);
                        tden.setText(formattedTime);
                    }
                    handler.postDelayed(this, 1000);


                } else {
                    handler.removeCallbacks(this);
                }
            }
        }, 1000);
        timeoutBulb.setValue(second);

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