package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LineChart_nhietdo extends AppCompatActivity {
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     String ab;

     ImageView backnd;
    List<String> timeList =new ArrayList<>();
    List<String> DataList  =new ArrayList<>();
    Float tempp;
     int indexx = 0;
    // Khai báo biến SharedPreferences
    SharedPreferences sharedPrefnd;
     LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_nhietdo);
        final DatabaseReference DULIEUCAMBIEN = database.getReference("DULIEUCAMBIEN"); // ket noi den truong da tao tren firebase
        lineChart = findViewById(R.id.lineChart);
        backnd = findViewById(R.id.backnd);
        initData();

        //    timeList =
     //   DataList = new ArrayList<>();
        //   timeList.add(timeString);


        DULIEUCAMBIEN.addValueEventListener(new ValueEventListener() { // goi truong gia tri theo cap nhat thay doi database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value =snapshot.getValue(String.class);
                char[] vl = new char[2];
                value.getChars(0,2,vl,0);
                String str = String.copyValueOf(vl) ;
             //   ab = str;
               // dataList = new String[10];
               // dataList[indexx] = ab;
               // DataList.add(ab);
                Log.d("AAA","Giá trị  str: "+ str );
                Add(str,Time());
                Log.d("AAA","Giá trị  ddad: "+ DataList );
                Log.d("AAA","Giá trị  Time "+ timeList );
                if(DataList.size() > 7 && timeList.size() > 7)
                {
                    removeFirst();
                }

                Vebieudo(DataList,timeList);
                saveData();
        /*
                XAxis xAxis = lineChart.getXAxis();
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
                dataSet.setLineWidth(3f);
                xAxis.setGranularity(1f);
                xAxis.setSpaceMin(0.3f);
                xAxis.setSpaceMax(0.3f);
                dataSet.setCircleRadius(9f);
                dataSet.setColor(Color.RED);
                dataSet.setCircleColor(Color.BLUE);
                lineData.setDrawValues(false);
                // Cập nhật biểu đồ
                entries.add(new Entry( indexx,tempp));
                indexx++;
                IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(timeList);
                lineChart.getXAxis().setValueFormatter(formatter);

                if(DataList.size() > 7)
                {
                    DataList.remove(0);

                }
                lineChart.setData(lineData); // Cập nhật dữ liệu lên biểu đồ
                lineChart.notifyDataSetChanged(); // Thông báo cho biểu đồ rằng dữ liệu đã thay đổi
                lineChart.invalidate(); // Cập nhật lại biểu đồ
                // vẽ lại
*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        backnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawBorders(true);
        lineChart.setBorderColor(Color.BLACK);
        lineChart.setBorderWidth(1f);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        LineDataSet dataSet = new LineDataSet(entries, " Temperature ");
        LineData lineData = new LineData(dataSet);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(time22));
        dataSet.setLineWidth(3f);
        xAxis.setGranularity(1f);// cai nay
        xAxis.setSpaceMin(0.3f);
        xAxis.setSpaceMax(0.3f);
        dataSet.setCircleRadius(9f);
        dataSet.setColor(Color.RED);
        dataSet.setCircleColor(Color.BLUE);
        lineData.setDrawValues(false);
        lineChart.setData(lineData); // Cập nhật dữ liệu lên biểu đồ
        lineChart.notifyDataSetChanged(); // Thông báo cho biểu đồ rằng dữ liệu đã thay đổi
        lineChart.invalidate(); // Cập nhật lại biểu đồ
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
      if (DataList.size() > 0) {
          String lastElement = DataList.get(DataList.size() - 1);
          if (lastElement.equals(dataValue)) {
              // If the last element's temperature value is the same as the new value,
              // do not add the new data point and return from the function.
              return;
          }
      }
      // Create a new data point and add it to the list.
   //   DataList newDataPoint = new DataList(dataValue, timeTitle);
      DataList.add(dataValue);
      timeList.add(timeTitle);
  }
  private void removeFirst( )
  {
          DataList.remove(0);
          timeList.remove(0);
  }
    private void initData() {
        sharedPrefnd = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        if (sharedPrefnd.contains("tempData")) {
            String savedData = sharedPrefnd.getString("tempData", "");
            DataList = new ArrayList<>(Arrays.asList(savedData.split(",")));
        }
        if (sharedPrefnd.contains("timeTitle")) {
            String savedTime = sharedPrefnd.getString("timeTitle", "");
            timeList = new ArrayList<>(Arrays.asList(savedTime.split(",")));
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPrefnd.edit();
        editor.putString("tempData", TextUtils.join(",", DataList));
        editor.putString("timeTitle", TextUtils.join(",", timeList));
        editor.apply();
    }
}