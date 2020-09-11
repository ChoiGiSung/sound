package com.example.sound_mainpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Sound_useTime extends AppCompatActivity {
    ArrayList<String> Arl=null;
    LineChart lineChart;
    TextView usertime_Avg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_usetime);
        lineChart=findViewById(R.id.chart);
        usertime_Avg=findViewById(R.id.usertime_Avg);

        //사용 값을 메인 액티비티에서 받음
        Intent intent=getIntent();
        Arl=intent.getStringArrayListExtra("usetime");

        drawChart(); //달이 바뀌면 오류
        usertime_Avg.setText(Average(Arl));

    }

    public void drawChart(){
        try{
            ArrayList<Entry> values = new ArrayList<>();
            if(!Arl.isEmpty()){
                for(String index : Arl){
                    if(!index.equals("0")){
                        int day =Integer.parseInt(index.substring(0,2));
                        int usetime =Integer.parseInt(index.substring(2,6));//분이다
                        values.add(new Entry(day, usetime));
                    }else {
                        values.add(new Entry(0,0));
                    }
                }
            }
            //들어갈 데이터 이미지설정
            LineDataSet set1;
            set1 = new LineDataSet(values, "분/날짜") ;
            set1.setLineWidth(2);
            set1.setCircleRadius(6);
            set1.setColor(Color.BLUE); //차트의 선 색
            set1.setCircleColor(Color.BLUE); //차트 point 색


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            // create a data object with the data sets
            LineData data = new LineData(dataSets);
            // black lines and points

            //set data

            //오른쪽 y출 설정
            YAxis yAxisRight =lineChart.getAxisRight();
            yAxisRight.setDrawLabels(false);
            yAxisRight.setDrawAxisLine(false);
            yAxisRight.setDrawGridLines(false);
            //y축 제거

            //x축 설정
            XAxis xAxis =lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //아래로
            xAxis.setLabelCount(7,true); //x는 7개

            lineChart.setData(data);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String Average(ArrayList<String> arl){
        int sum=0;
        try{

            //평균 구하기
            for(int i=0;i<arl.size();i++){
                if(!arl.get(i).equals("0"))
                    sum+=Integer.parseInt(arl.get(i).substring(2,6));
                Log.i("수락",sum+"");
            }
            sum/=arl.size();
            //평균 분


        }catch (Exception e){
            e.printStackTrace();
        }
        int hour=sum/60;
        int mint=sum%60;
        String result="당신의 최근 7일 평균 이용 시간은 "+hour+"시간"+mint+"분"+"입니다.";
        return result;
    }




}
