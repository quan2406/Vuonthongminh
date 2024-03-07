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

public class Linechart_doamdat extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String dad;
    String dataListdad[];
    LineChart lineChartdad;
    List<String> timeListdad = new ArrayList<>();
    List<String> DataListdad = new ArrayList<>();
    SharedPreferences sharedPrefdad;
    ImageView backdad;
    int indexx = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart_doamdat);

        final DatabaseReference DULIEUCAMBIEN = database.getReference("DULIEUCAMBIEN"); // ket noi den truong da tao tren firebase
        lineChartdad = findViewById(R.id.lineChartdad);

        backdad = findViewById(R.id.backdad);


        initData();
        DULIEUCAMBIEN.addValueEventListener(new ValueEventListener() { // goi truong gia tri theo cap nhat thay doi database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value =snapshot.getValue(String.class);
                char[] vl = new char[2];
                value.getChars(4,6,vl,0);
                String str = String.copyValueOf(vl) ;
                dad = str;
              //  Float tempp = Float.parseFloat(dad);

                Log.d("AAA","Giá trị  str: "+ str );
                Add(str,Time());
                Log.d("AAA","Giá trị  ddad: "+ DataListdad );
                Log.d("AAA","Giá trị  Time "+ timeListdad );
                if(DataListdad.size() > 7 && timeListdad.size() > 7)
                {
                    removeFirst();
                }
                Vebieudo(DataListdad,timeListdad);
                saveData();
           /*     XAxis xAxis = lineChartdad.getXAxis();
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
                lineChartdad.getXAxis().setValueFormatter(formatter);
                lineChartdad.setData(lineData); // Cập nhật dữ liệu lên biểu đồ
                lineChartdad.notifyDataSetChanged(); // Thông báo cho biểu đồ rằng dữ liệu đã thay đổi
                lineChartdad.invalidate(); // Cập nhật lại biểu đồ

            */
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        backdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent ldad =   new Intent(Linechart_doamdat.this, fragment_thongso.class);
            //    startActivity(ldad);
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
        lineChartdad.getDescription().setEnabled(false);
        lineChartdad.setDrawGridBackground(false);
        lineChartdad.setBackgroundColor(Color.WHITE);
        lineChartdad.setDrawBorders(true);
        lineChartdad.setBorderColor(Color.BLACK);
        lineChartdad.setBorderWidth(1f);
        lineChartdad.setTouchEnabled(true);
        lineChartdad.setDragEnabled(true);
        lineChartdad.setScaleEnabled(true);
        lineChartdad.setPinchZoom(true);
        LineDataSet dataSet = new LineDataSet(entries, " Soil Mosture ");
        LineData lineData = new LineData(dataSet);
        XAxis xAxis = lineChartdad.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(time22));
        dataSet.setLineWidth(3f);
        xAxis.setGranularity(1f);// cai nay
        xAxis.setSpaceMin(0.3f);
        xAxis.setSpaceMax(0.3f);
        dataSet.setCircleRadius(9f);
        dataSet.setColor(Color.RED);
        dataSet.setCircleColor(Color.BLUE);
        lineData.setDrawValues(false);
        lineChartdad.setData(lineData); // Cập nhật dữ liệu lên biểu đồ
        lineChartdad.notifyDataSetChanged(); // Thông báo cho biểu đồ rằng dữ liệu đã thay đổi
        lineChartdad.invalidate(); // Cập nhật lại biểu đồ
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
        if (DataListdad.size() > 0) {
            String lastElement = DataListdad.get(DataListdad.size() - 1);
            if (lastElement.equals(dataValue)) {
                // If the last element's temperature value is the same as the new value,
                // do not add the new data point and return from the function.
                return;
            }
        }
        // Create a new data point and add it to the list.
        //   DataList newDataPoint = new DataList(dataValue, timeTitle);
        DataListdad.add(dataValue);
        timeListdad.add(timeTitle);
    }
    private void removeFirst( )
    {
        DataListdad.remove(0);
        timeListdad.remove(0);
    }
    private void initData() {
        sharedPrefdad = getSharedPreferences("myPrefsdad", Context.MODE_PRIVATE);
        if (sharedPrefdad.contains("tempDatadad")) {
            String savedData = sharedPrefdad.getString("tempDatadad", "");
            DataListdad = new ArrayList<>(Arrays.asList(savedData.split(",")));
        }
        if (sharedPrefdad.contains("timeTitledad")) {
            String savedTime = sharedPrefdad.getString("timeTitledad", "");
            timeListdad = new ArrayList<>(Arrays.asList(savedTime.split(",")));
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPrefdad.edit();
        editor.putString("tempDatadad", TextUtils.join(",", DataListdad));
        editor.putString("timeTitledad", TextUtils.join(",", timeListdad));
        editor.apply();
    }
}