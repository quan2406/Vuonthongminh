package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    TextView t1,t2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       anhxa();
       getTime();
       getDelay();



    }
    private void getTime()
    {
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String currentDay = new SimpleDateFormat("'Ngày' dd,' tháng' MM, yyyy", Locale.getDefault()).format(new Date());
        t1.setText(currentTime);
        t2.setText(currentDay);
    }
    private void anhxa(){
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
    }
    private void getDelay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HOMEwww.class));
                finish();
            }
        },1000);
    }
}