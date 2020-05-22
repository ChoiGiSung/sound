package com.example.leftright_sound;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.concurrent.ThreadPoolExecutor;

import javax.xml.transform.Templates;

public class MainActivity extends AppCompatActivity {
    SoundPool soundPool;

    SoundManager soundManager;
    Button but1,but2,but3;
    boolean play;
    int playSoundId;
    CheckBox ch1,ch2,ch3,ch4,ch5,ch6,ch7,ch8,ch9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        //볼륨컨트롤러 미디어 볼룸이로만 나오게
//        setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//        //값 범위 1~15
//        AudioManager audioManager=null;
//        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//
//        //음량 늘이는 소스
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
//                (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)+1),0);
//
//        //음량 줄이는 소스
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
//                (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)-1),0);



//        int [] btnId={R.id.btn1,R.id.btn2,R.id.btn3};
//        Button buttonCase[]=new Button[btnId.length];
//
//        //롤리팝 이상 버전일 경우
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            soundPool=new SoundPool.Builder().build();
//        }else{
//            soundPool=new SoundPool(1,AudioManager.STREAM_MUSIC,0);
//        }
//
//        soundManager=new SoundManager(this,soundPool);
//        //소리 넣기
//        soundManager.addSound(0,R.raw.sound1);
//        soundManager.addSound(1,R.raw.sound10000);
//        soundManager.addSound(2,R.raw.sound12000);

        //안내창
        dialog_notice();


    }
    private void init(){
        ch1=findViewById(R.id.ch1);
        ch2=findViewById(R.id.ch2);
        ch3=findViewById(R.id.ch3);
        ch4=findViewById(R.id.ch4);
        ch5=findViewById(R.id.ch5);
        ch6=findViewById(R.id.ch6);
        ch7=findViewById(R.id.ch7);
        ch8=findViewById(R.id.ch8);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn1:
                sound_load(R.raw.sound1);
                break;
            case R.id.btn2:
                sound_load(R.raw.sound10000);
            case R.id.btn3:
                sound_load(R.raw.sound12000);
                break;
            case R.id.btn4:
                sound_load(R.raw.sound_14100);
                break;
            case R.id.btn5:
                sound_load(R.raw.sound_14900);
                break;
            case R.id.btn6:
                sound_load(R.raw.sound_15800);
                break;
            case R.id.btn7:
                sound_load(R.raw.sound_16746);
                break;
            case R.id.btn8:
                sound_load(R.raw.sound_17742);
                break;
            case R.id.btn_Go:
                goSound();
                break;
        }
    }

    private void sound_load(int raw){
        soundPool=new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        final int s3=soundPool.load(this,raw,1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(s3,1,1,0,1,1);
            }
        });
    }

    //사운드 넘어가기
    public void goSound(){
        Intent intent=new Intent(getApplicationContext(),SoundTest.class);
        int i=0;
        if(ch1.isChecked()){
            i=1;
        }else if (ch2.isChecked()){
            i=2;
        }else if(ch3.isChecked()) {
            i = 3;
        }else if(ch4.isChecked()) {
            i = 4;
        }else if(ch5.isChecked()) {
            i = 5;
        }else if(ch6.isChecked()) {
            i = 6;
        }else if(ch7.isChecked()) {
            i = 7;
        }else if(ch8.isChecked()) {
            i = 8;
        }
        intent.putExtra("chec",i);
        startActivity(intent);
    }

    //안내 다이어 로그
    private void dialog_notice(){
        AlertDialog.Builder dig=new AlertDialog.Builder(MainActivity.this);
        dig.setTitle("안내");
        View dialogView =View.inflate(MainActivity.this,R.layout.dialog_view,null);
        dig.setView(dialogView);

        dig.setPositiveButton("알겠어요!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView text_dialog1=findViewById(R.id.text_dialog1);

            }
        });
        dig.show();
    }


}
