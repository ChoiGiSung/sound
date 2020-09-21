package com.example.sound_mainpage.SoundTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sound_mainpage.MainActivity;
import com.example.sound_mainpage.R;
import com.example.sound_mainpage.SuperUser;

public class ResultActivity extends AppCompatActivity {
    TextView result_txt,eyeUrl;
    ImageView result_img;
    Button goHomeBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        result_txt=findViewById(R.id.result_txt);
        result_img = findViewById(R.id.result_img);
        goHomeBtn=findViewById(R.id.goHome);



        Intent intent=getIntent();
        int result=intent.getIntExtra("answer",0);



        if(result>=10){
            result_img.setImageResource(R.mipmap.happy);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"훌륭합니다\n");

        }else if (result>=7){
            result_img.setImageResource(R.mipmap.good);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"괜찮네요\n");
        }else if (result>=3){
            result_img.setImageResource(R.mipmap.soso);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"좀 안들려요\n");
        }else if (result>=0){
            result_img.setImageResource(R.mipmap.soso);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"귀건강이 심각해요\n");
        }



        //홈으로 가기
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //수퍼유저 객체
                SuperUser superUser=SuperUser.getSuperUser();
                String user_id = superUser.getUser_id();
                Toast.makeText(getApplicationContext(),superUser.getUser_id(),Toast.LENGTH_SHORT).show();

                Intent goHome=new Intent(getApplicationContext(), MainActivity.class);
                goHome.putExtra("userid",user_id);
                startActivity(goHome);
            }
        });

    }
}
