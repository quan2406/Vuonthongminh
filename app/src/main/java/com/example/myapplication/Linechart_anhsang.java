package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Linechart_anhsang extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String da;
    String dataListas[];
    ImageView backas;
    LineChart lineChartas;
    List<String> timeListas = new ArrayList<>();
    List<String> DataListas = new ArrayList<>();
    SharedPreferences sharedPrefas;
    int indexx = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart_anhsang);
        final DatabaseReference DULIEUCAMBIEN = database.getReference("DULIEUCAMBIEN"); // ket noi den truong da tao tren firebase
        lineChartas = findViewById(R.id.lineChartas);

        backas = findViewById(R.id.backas);
        //   sharedPref1as = getSharedPreferences("data_key", Context.MODE_PRIVATE);
        //  sharedPref2as = getSharedPreferences("time_key", Context.MODE_PRIVATE);
        initData();
        DULIEUCAMBIEN.addValueEventListener(new ValueEventListener() { // goi truong gia tri theo cap nhat thay doi database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String value =snapshot.getValue(String.class);
                char[] vl = new char[2];
                value.getChars(6,8,vl,0);
                String str = String.copyValueOf(vl) ;
             //   float number = Float.parseFloat(trimmedValue); // Chuyển đổi thành số

            //    da = str;
               // Float tempp = Float.parseFloat(da);

                Log.d("AAA","Giá trị  str: "+ str );
                Add(str,Time());
                Log.d("AAA","Giá trị  ddad: "+ DataListas );
                Log.d("AAA","Giá trị  Time "+ timeListas );
                if(DataListas.size() > 7 && timeListas.size() > 7)
                {
                    removeFirst();
                }
                Vebieudo(DataListas,timeListas);
                saveData();
             /*   String dataListString = TextUtils.join(",", DataListas);
                String timeListString = TextUtils.join(",", timeListas);

                SharedPreferences.Editor editor = sharedPrefas.edit();
                editor.putString("Datalistas", dataListString);
                editor.putString("timelistas", timeListString);
                editor.apply();
                String dataListasString = sharedPrefas.getString("Datalistas", "");
                String timeListasString = sharedPrefas.getString("timelistas", "");

                DataListas = new ArrayList<>(Arrays.asList(dataListasString.split(",")));
                timeListas = new ArrayList<>(Arrays.asList(timeListasString.split(",")));
                Vebieudo(DataListas,timeListas);

                //  saveDataToLocalStorage();
              //  readDataToLocalStorage();


                // lưu các thay đổi vào SharedPreferences

                 SharedPreferences.Editor editor = sharedPrefas.edit();
                editor.putString("Datalistas", String.valueOf(DataListas));

                editor.putString("timelistas", String.valueOf(timeListas));

                editor.commit();
                editor.apply();


             String dataListasString = sharedPrefas.getString("Datalistas", "");
                String timeListasString = sharedPrefas.getString("timelistas", "");
                DataListas = new ArrayList<>(Arrays.asList(dataListasString.split(",")));
                timeListas = new ArrayList<>(Arrays.asList(timeListasString.split(",")));


                // String savedDataList = sharedPref1as.getString("data_key", "");
               // Log.d("Saved DataListas", savedDataList);
              //  String savedDataList2 = sharedPref2as.getString("time_key", "");
             //   Log.d("timeListas ", savedDataList2);


               XAxis xAxis = lineChartas.getXAxis();
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
                lineChartas.getXAxis().setValueFormatter(formatter);
                lineChartas.setData(lineData); // Cập nhật dữ liệu lên biểu đồ
                lineChartas.notifyDataSetChanged(); // Thông báo cho biểu đồ rằng dữ liệu đã thay đổi
                lineChartas.invalidate(); // Cập nhật lại biểu đồ

  */
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        backas.setOnClickListener(new View.OnClickListener() {
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
        lineChartas.getDescription().setEnabled(false);
        lineChartas.setDrawGridBackground(false);
        lineChartas.setBackgroundColor(Color.WHITE);
        lineChartas.setDrawBorders(true);
        lineChartas.setBorderColor(Color.BLACK);
        lineChartas.setBorderWidth(1f);
        lineChartas.setTouchEnabled(true);
        lineChartas.setDragEnabled(true);
        lineChartas.setScaleEnabled(true);
        lineChartas.setPinchZoom(true);
        LineDataSet dataSet = new LineDataSet(entries, " Light ");
        LineData lineData = new LineData(dataSet);
        XAxis xAxis = lineChartas.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(time22));
        dataSet.setLineWidth(3f);
        xAxis.setGranularity(1f);// cai nay
        xAxis.setSpaceMin(0.3f);
        xAxis.setSpaceMax(0.3f);
        dataSet.setCircleRadius(9f);
        dataSet.setColor(Color.RED);
        dataSet.setCircleColor(Color.BLUE);
        lineData.setDrawValues(false);
        lineChartas.setData(lineData); // Cập nhật dữ liệu lên biểu đồ
        lineChartas.notifyDataSetChanged(); // Thông báo cho biểu đồ rằng dữ liệu đã thay đổi
        lineChartas.invalidate(); // Cập nhật lại biểu đồ
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
        if (DataListas.size() > 0) {
            String lastElement = DataListas.get(DataListas.size() - 1);
            if (lastElement.equals(dataValue)) {
                // If the last element's temperature value is the same as the new value,
                // do not add the new data point and return from the function.
                return;
            }
        }
        // Create a new data point and add it to the list.
        //   DataList newDataPoint = new DataList(dataValue, timeTitle);
        DataListas.add(dataValue);
        timeListas.add(timeTitle);
    }
    private void removeFirst( )
    {
        DataListas.remove(0);
        timeListas.remove(0);
    }
  //  private void saveDataToLocalStorage() {
        // SharedPreferences.Editor editor = sharedPref.edit();
        //  Gson gson1 = new Gson();
       // Gson gson2 = new Gson();
       //  String dataListJson = gson1.toJson(DataListas);
       //  String timeListJson = gson2.toJson(timeListas);

      //  sharedPref1as.edit().putString("DataList",dataListJson).apply();
       // sharedPref2as.edit().putString("timeList",timeListJson).apply();
        //sharedPref1as.edit().putString("DataListas", String.valueOf(DataListas)).apply();
       // sharedPref1as.edit().putString("timeListas", String.valueOf(DataListas)).apply();

       /*  Gson gson = new Gson();
        String dataListJson = gson.toJson(DataListas);
        String timeListJson = gson.toJson(timeListas);

        SharedPreferences.Editor editor = sharedPrefas.edit();
        SharedPreferences.Editor editor2 = sharedPrefas.edit();

        editor.putString("data_key", dataListJson);
        editor2.putString("time_key", timeListJson);
        editor.apply();
        editor2.apply();



        // Lưu trữ mảng ArrayList vào SharedPreferences


   //     SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
       // SharedPreferences sharedPreferences1 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

       SharedPreferences.Editor editor1 = sharedPref1as.edit();
        SharedPreferences.Editor editor2 = sharedPref2as.edit();

        Set<String> mySet2 = new HashSet<>(timeListas);
        Set<String> mySet1 = new HashSet<>(DataListas);
        editor1.putStringSet("DataListas", mySet1);
        editor2.putStringSet("timeListas", mySet2);
        editor1.apply();
        editor2.apply();


        SharedPreferences.Editor editor = sharedPrefas.edit();
        Set<String> dataSet = new HashSet<>(DataListas);
        editor.putStringSet("data_key", dataSet);
        Set<String> timeSet = new HashSet<>(timeListas);
        editor.putStringSet("time_key", timeSet);
        editor.apply();

        SharedPreferences.Editor editor = sharedPref1as.edit();
        editor.putString("data_key", TextUtils.join(",", DataListas));
        editor.apply();

        SharedPreferences.Editor editor2 = sharedPref2as.edit();
        editor2.putString("time_key", TextUtils.join(",", timeListas));
        editor2.apply();*/

    //}

//    private void readDataToLocalStorage(){
/// Đọc chuỗi JSON từ SharedPreferences
     /*   String timeJsonArray = sharedPrefas.getString("time_key", "");
        String dataJsonArray = sharedPrefas.getString("data_key", "");

// Chuyển đổi chuỗi JSON thành List<String>
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> timeListas = new Gson().fromJson(timeJsonArray, type);
        List<String> dataListas = new Gson().fromJson(dataJsonArray, type);



        Set<String> savedDataSet = sharedPrefas.getStringSet("data_key", null);
        if (savedDataSet != null) {
            DataListas = new ArrayList<>(savedDataSet);
        }
        Set<String> savedTimeSet = sharedPrefas.getStringSet("time_key", null);
        if (savedTimeSet != null) {
            timeListas = new ArrayList<>(savedTimeSet);
        }

        String savedDataList = sharedPref1as.getString("data_key", "");
        String[] dataList = savedDataList.split(",");
        DataListas = new ArrayList<>(Arrays.asList(dataList));

        String savedTimeList = sharedPref2as.getString("time_key", "");
        String[] timeList = savedTimeList.split(",");
        timeListas = new ArrayList<>(Arrays.asList(timeList));*/


   // }

    private void initData() {
        sharedPrefas = getSharedPreferences("myPrefsas", Context.MODE_PRIVATE);

        if (sharedPrefas.contains("tempDataas")) {
            String savedData = sharedPrefas.getString("tempDataas", "");
            DataListas = new ArrayList<>(Arrays.asList(savedData.split(",")));
        }
        if (sharedPrefas.contains("timeTitleas")) {
            String savedTime = sharedPrefas.getString("timeTitleas", "");
            timeListas = new ArrayList<>(Arrays.asList(savedTime.split(",")));
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPrefas.edit();
        editor.putString("tempDataas", TextUtils.join(",", DataListas));
        editor.putString("timeTitleas", TextUtils.join(",", timeListas));
        editor.apply();
    }

}