package com.example.sound_mainpage.SoundTest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sound_mainpage.R;

public class ResultActivity extends AppCompatActivity {
    TextView result_txt;
    ImageView result_img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        result_txt=findViewById(R.id.result_txt);
        result_img = findViewById(R.id.result_img);

        Intent intent=getIntent();
        int result=intent.getIntExtra("answer",0);

        if(result>=10){
            result_img.setImageResource(R.mipmap.smile);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"훌륭합니다");
        }else if (result>=7){
            result_img.setImageResource(R.mipmap.expressionless);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"괜찮네요");
        }else if (result>=3){
            result_img.setImageResource(R.mipmap.samsung);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"좀 안들려요");
        }else if (result>=1){
            result_img.setImageResource(R.mipmap.sad);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"귀건강이 심각해요");
        }

    }
}
