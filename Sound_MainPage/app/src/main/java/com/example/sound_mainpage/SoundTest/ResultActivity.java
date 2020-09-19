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
    TextView result_txt;
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
            result_img.setImageResource(R.mipmap.smile);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"훌륭합니다"+
                    "당신을 위한 귀 건강 음식! : ㅂㄱ제ㅐㅓㅏ지ㅏㅓㄹ이ㅏㄴㅇ리ㅏㅇㄹ니ㅏㅣㅏㄴㄹㅇ나ㅓㅣ;ㄹㅇ니ㅏ나ㅣ;" +
                    "ㄹㄴㅇ;ㅣㅓㄹㅇ나ㅣㅇㄴ리ㅏㅜㅇㄹ나ㅣㄹㄴ이ㅜㅏㄹㄴ위ㅏㄴ아ㅣㅜㄹㅇ나ㅟㄹㅇ니ㅜㅏㄹㄴ우ㅏ" +
                    "아ㅜㄴㄹ이ㅏㅜㅇㄴ라ㅣㅜㅇㄴ라ㅣㅜㄴㅇ라ㅟㅇㄴ라ㅣㅜㅇㄹ나ㅟ아ㅟㄴㅇ");
        }else if (result>=7){
            result_img.setImageResource(R.mipmap.expressionless);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"괜찮네요"+
                    "당신을 위한 귀 건강 음식! : ㅂㄱ제ㅐㅓㅏ지ㅏㅓㄹ이ㅏㄴㅇ리ㅏㅇㄹ니ㅏㅣㅏㄴㄹㅇ나ㅓㅣ;ㄹㅇ니ㅏ나ㅣ;" +
                    "ㄹㄴㅇ;ㅣㅓㄹㅇ나ㅣㅇㄴ리ㅏㅜㅇㄹ나ㅣㄹㄴ이ㅜㅏㄹㄴ위ㅏㄴ아ㅣㅜㄹㅇ나ㅟㄹㅇ니ㅜㅏㄹㄴ우ㅏ" +
                    "아ㅜㄴㄹ이ㅏㅜㅇㄴ라ㅣㅜㅇㄴ라ㅣㅜㄴㅇ라ㅟㅇㄴ라ㅣㅜㅇㄹ나ㅟ아ㅟㄴㅇ");
        }else if (result>=3){
            result_img.setImageResource(R.mipmap.samsung);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"좀 안들려요"+
                    "당신을 위한 귀 건강 음식! : ㅂㄱ제ㅐㅓㅏ지ㅏㅓㄹ이ㅏㄴㅇ리ㅏㅇㄹ니ㅏㅣㅏㄴㄹㅇ나ㅓㅣ;ㄹㅇ니ㅏ나ㅣ;" +
                    "ㄹㄴㅇ;ㅣㅓㄹㅇ나ㅣㅇㄴ리ㅏㅜㅇㄹ나ㅣㄹㄴ이ㅜㅏㄹㄴ위ㅏㄴ아ㅣㅜㄹㅇ나ㅟㄹㅇ니ㅜㅏㄹㄴ우ㅏ" +
                    "아ㅜㄴㄹ이ㅏㅜㅇㄴ라ㅣㅜㅇㄴ라ㅣㅜㄴㅇ라ㅟㅇㄴ라ㅣㅜㅇㄹ나ㅟ아ㅟㄴㅇ");
        }else if (result>=1){
            result_img.setImageResource(R.mipmap.sad);
            result_txt.setText(String.valueOf(result)+"개 맞힌 당신 \n"+"귀건강이 심각해요"+
                    "당신을 위한 귀 건강 음식! : ㅂㄱ제ㅐㅓㅏ지ㅏㅓㄹ이ㅏㄴㅇ리ㅏㅇㄹ니ㅏㅣㅏㄴㄹㅇ나ㅓㅣ;ㄹㅇ니ㅏ나ㅣ;" +
                    "ㄹㄴㅇ;ㅣㅓㄹㅇ나ㅣㅇㄴ리ㅏㅜㅇㄹ나ㅣㄹㄴ이ㅜㅏㄹㄴ위ㅏㄴ아ㅣㅜㄹㅇ나ㅟㄹㅇ니ㅜㅏㄹㄴ우ㅏ" +
                    "아ㅜㄴㄹ이ㅏㅜㅇㄴ라ㅣㅜㅇㄴ라ㅣㅜㄴㅇ라ㅟㅇㄴ라ㅣㅜㅇㄹ나ㅟ아ㅟㄴㅇ");
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
