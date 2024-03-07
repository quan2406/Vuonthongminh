package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
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

public class Quat_mode2 extends AppCompatActivity {
    private ProgressBar mProgressBarfan;
    EditText timeoutfan;
    private int progr = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    int giay;
    int giays;
    String second;
    Handler handler;
    TextView tfan;
    ImageView back6;
    Button fan;
    DatabaseReference timeoutFan;
    private int countDown = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quat_mode2);
         anhxa();
        InitDataFirebase();
        back6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDelay();
                String text = timeoutfan.getText().toString().trim(); // Lấy giá trị của EditText
                if (TextUtils.isEmpty( text)) { // Nếu EditText rỗng
                    showAlertDialog("Lỗi","Không được để thời gian trống", "Đóng");
                }
                timeoutFan.setValue(second);

            }
        });
        timeoutfan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePick();
            }

        });
    }
    private void InitDataFirebase(){

        timeoutFan = database.getReference("timeoutFan");
    }
    private void anhxa()
    {
        fan = (Button)findViewById(R.id.fan) ;
        back6 = (ImageView)findViewById(R.id.back6);
        timeoutfan = (EditText)findViewById(R.id.timeoutfan);
        mProgressBarfan = findViewById(R.id.progressBarFan);
        tfan = findViewById(R.id.textView40003);
        handler = new Handler();
    }
    public void check()
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(Quat_mode2.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                final int[] hours = {0};
                final int[] minutes = {0};
                hours[0] = hourOfDay;
                minutes[0] = minute;

                // Hiển thị TimePickerDialog lần 2 để chọn giây
                int totalSeconds = hours[0] * 3600 + minutes[0] * 60 ;
                // Lấy thời gian hiện tại
                Calendar currentTime = Calendar.getInstance();
                long currentMillis = currentTime.getTimeInMillis();
                // Tạo Calendar object với giờ phút giây được chọn
                Calendar selectedTime = Calendar.getInstance();
                selectedTime.set(Calendar.HOUR_OF_DAY, hours[0]);
                selectedTime.set(Calendar.MINUTE, minutes[0]);
                long diffInMillis = selectedTime.getTimeInMillis() - currentMillis;
                int diffInSeconds = (int) (diffInMillis / 1000);
                giay = diffInSeconds;
                giays = diffInSeconds;

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
                    int giayy = giay /100;

                    mProgressBarfan.setProgress(giay);
                    mProgressBarfan.setMax(giays);
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String formattedTime = formatter.format(new Date(giay * 1000));


                    if(giays > 0)
                    {
                        tfan.setText(formattedTime);
                        timeoutfan.setText(formattedTime);
                    }
                    //  timeoutdenn.setText(formattedTime);

                    giay --;
                    second = Integer.toString(giays);
                    if (giay == 0)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mProgressBarfan.setMin(0);
                        }
                    }
                    handler.postDelayed(this, 1000);
                } else {
                    handler.removeCallbacks(this);
                }
            }
        }, 1000);
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