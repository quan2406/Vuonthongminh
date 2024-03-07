package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Linechart_doam extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String da;
    ImageView backda;
    LineChart lineChartda;
    List<String> timeListda = new ArrayList<>();
    List<String> DataListda = new ArrayList<>();
    SharedPreferences sharedPrefda;


    int indexx = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart_doam);
        final DatabaseReference DULIEUCAMBIEN = database.getReference("DULIEUCAMBIEN"); // ket noi den truong da tao tren firebase
        lineChartda = findViewById(R.id.lineChartda);
        backda = findViewById(R.id.backda);
       initData();

        DULIEUCAMBIEN.addValueEventListener(new ValueEventListener() { // goi truong gia tri theo cap nhat thay doi database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value =snapshot.getValue(String.class);
                char[] vl = new char[2];
                value.getChars(2,4,vl,0);
                String str = String.copyValueOf(vl) ;
                da = str;
                Log.d("AAA","Giá trị  str: "+ str );
                Add(str,Time());
                Log.d("AAA","Giá trị  ddad: "+ DataListda );
                Log.d("AAA","Giá trị  Time "+ timeListda );
                if(DataListda.size() > 7 && timeListda.size() > 7)
                {
                    removeFirst();
                }
                Vebieudo(DataListda,timeListda);
                saveData();

              //  Float tempp = Float.parseFloat(da);
             //   dataListda = new String[10];
            //    dataListda[indexx] = str;
            //    indexx++;
       /*         XAxis xAxis = lineChartda.getXAxis();
                Calendar calendar = Calendar.getInstance();
                // Lấy thời gian hiện tại của hệ thống
                Date currentTime = calendar.getTime();
                // Định dạng thời gian thành chuỗi theo định dạng "dd/MM HH:mm"
                SimpleDateFormat format = new SimpleDateFormat("E HH:mm", Locale.getDefault());
                String timeString = format.format(currentTime);
                // Thêm chuỗi thời gian vào danh sách
                timeList.add(timeString);
                LineDataSet dataSet = new LineDataSet(entries, " Temperature ");
                LineData lineData = new LineData(dataSet);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(timeList));
                entries.add(new Entry( indexx,tempp));
                dataSet.setLineWidth(3f);
                xAxis.setGranularity(1f);
                xAxis.setSpaceMin(0.5f);
                xAxis.setSpaceMax(0.5f);
                dataSet.setCircleRadius(9f);
                dataSet.setColor(Color.RED);
                dataSet.setCircleColor(Color.BLUE);
                lineData.setDrawValues(false);
                // Cập nhật biểu đồ
                IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(timeList);
                lineChartda.getXAxis().setValueFormatter(formatter);
                lineChartda.setData(lineData); // Cập nhật dữ liệu lên biểu đồ
                lineChartda.notifyDataSetChanged(); // Thông báo cho biểu đồ rằng dữ liệu đã thay đổi
                lineChartda.invalidate(); // Cập nhật lại biểu đồ

        */
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        backda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent lda =   new Intent(Linechart_doam.this, fragment_thongso.class);
              //  startActivity(lda);
                finish();
            }
        });


    }
    private void Vebieudo(List<String> data, List<String> time22 )
    {
        List<Entry>  entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            float temp = Float.parseFloat(data.get(i));
            //  float time = Float.parseFloat(time22.get(i));
            entries.add(new Entry(i, temp));
        }
        // Customize chart appearance
        lineChartda.getDescription().setEnabled(false);
        lineChartda.setDrawGridBackground(false);
        lineChartda.setBackgroundColor(Color.WHITE);
        lineChartda.setDrawBorders(true);
        lineChartda.setBorderColor(Color.BLACK);
        lineChartda.setBorderWidth(1f);
        lineChartda.setTouchEnabled(true);
        lineChartda.setDragEnabled(true);
        lineChartda.setScaleEnabled(true);
        lineChartda.setPinchZoom(true);
        LineDataSet dataSet = new LineDataSet(entries, " Humidity ");
        LineData lineData = new LineData(dataSet);
        XAxis xAxis = lineChartda.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(time22));
        dataSet.setLineWidth(3f);
        xAxis.setGranularity(1f);// cai nay
        xAxis.setSpaceMin(0.3f);
        xAxis.setSpaceMax(0.3f);
        dataSet.setCircleRadius(9f);
        dataSet.setColor(Color.RED);
        dataSet.setCircleColor(Color.BLUE);
        lineData.setDrawValues(false);
        lineChartda.setData(lineData); // Cập nhật dữ liệu lên biểu đồ
        lineChartda.notifyDataSetChanged(); // Thông báo cho biểu đồ rằng dữ liệu đã thay đổi
        lineChartda.invalidate(); // Cập nhật lại biểu đồ
    }

    private String Time(){
        Calendar calendar = Calendar.getInstance();
        // Lấy thời gian hiện tại của hệ thống
        Date currentTime = calendar.getTime();
        // Định dạng thời gian thành chuỗi theo định dạng "dd/MM HH:mm"
        SimpleDateFormat format = new SimpleDateFormat("E HH:mm", Locale.getDefault());
        String timeString = format.format(currentTime);
        // Thêm chuỗi thời gian vào danh sách
        return timeString;
    }
    private void Add(String dataValue, String timeTitle )
    {
   /*   if(DataList.size() > 0)
      {
          String lastElement = DataList.get(DataList.size() - 1);
          if(lastElement != dataValue )
          {
              DataList.add(dataValue);
              timeList.add(timeTitle);
          }
      }
      else
      {
          DataList.add(dataValue);
          timeList.add(timeTitle);
    } */
        if (DataListda.size() > 0) {
            String lastElement = DataListda.get(DataListda.size() - 1);
            if (lastElement.equals(dataValue)) {
                // If the last element's temperature value is the same as the new value,
                // do not add the new data point and return from the function.
                return;
            }
        }
        // Create a new data point and add it to the list.
        //   DataList newDataPoint = new DataList(dataValue, timeTitle);
        DataListda.add(dataValue);
        timeListda.add(timeTitle);
    }
    private void removeFirst( )
    {
        DataListda.remove(0);
        timeListda.remove(0);
    }
    private void initData() {
        sharedPrefda = getSharedPreferences("myPrefsda", Context.MODE_PRIVATE);
        if (sharedPrefda.contains("tempDatada")) {
            String savedData = sharedPrefda.getString("tempDatada", "");
            DataListda = new ArrayList<>(Arrays.asList(savedData.split(",")));
        }
        if (sharedPrefda.contains("timeTitleda")) {
            String savedTime = sharedPrefda.getString("timeTitleda", "");
            timeListda = new ArrayList<>(Arrays.asList(savedTime.split(",")));
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPrefda.edit();
        editor.putString("tempDatada", TextUtils.join(",", DataListda));
        editor.putString("timeTitleda", TextUtils.join(",", timeListda));
        editor.apply();
    }
}