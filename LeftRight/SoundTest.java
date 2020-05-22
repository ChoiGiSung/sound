package com.example.leftright_sound;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SoundTest extends AppCompatActivity {
    Button btn_left,btn_right,tv1;
    //TextView tv1;

    final String [] result=new String [10];// 정답
    final String [] playResult=new String [10];//사용자 응답


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_test);

        btn_left=findViewById(R.id.btn_left);
        btn_right=findViewById(R.id.btn_right);
        tv1=findViewById(R.id.tv1);
        dialog_notice();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playResult[9] !=null) {
                    int age = result_sound(result, playResult);
                    String s = Integer.toString(age);
                    tv1.setText(s+"개 맞췄다!");
                }
            }
        });

    }


    //램덤사운드 만들기
    public void sountTest(){
        int i=get_intent();
        //소리나게 하기위한 soundpool
        SoundPool soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        int sound=0;

        //사용자가 고른사운드 설정
        switch (i){
            case 1:
                sound=soundPool.load(this,R.raw.sound1,1);
                break;
            case 2:
                sound=soundPool.load(this,R.raw.sound10000,1);
                break;
            case 3:
                sound=soundPool.load(this,R.raw.sound12000,1);
                break;
            case 4:
                sound=soundPool.load(this,R.raw.sound_14100,1);
                break;
            case 5:
                sound=soundPool.load(this,R.raw.sound_14900,1);
                break;
            case 6:
                sound=soundPool.load(this,R.raw.sound_15800,1);
                break;
            case 7:
                sound=soundPool.load(this,R.raw.sound_16746,1);
                break;
            case 8:
                sound=soundPool.load(this,R.raw.sound_17742,1);
                break;
        }
        //램덤 소리 발생
        for(int j=0;j<10;j++){
            SystemClock.sleep(5000);
            int ran= (int) Math.round(Math.random());
            final int jj=j;//플레이어의 답 담기위한 변수
            //좌 우 소리 발생
            switch (ran){
                case 0:
                    soundPool.play(sound,1,0,0,1,1);
                   result[j]="l";
                   break;
                case 1:
                    //우측실행
                    soundPool.play(sound,0,1,0,1,1);
                result[j]="r"; //정답 담기
                    break;
            }
            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playResult[jj]="l";//플레이어의 답 담기
                    Toast.makeText(getApplicationContext(),"왼쪽"+Integer.toString(jj+1)+"/"+"10",Toast.LENGTH_SHORT).show();
                }
            });
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playResult[jj]="r";
                    Toast.makeText(getApplicationContext(),"오른쪽"+Integer.toString(jj+1)+"/"+"10",Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    public int get_intent(){
        Intent intent=getIntent();
        int i=intent.getIntExtra("chec",1);
        return i;
    }

    //채점하기
    public int result_sound(String[] result,String[] player){
        int answer=0;
        for(int i=0;i<10;i++){
            if(result[i].equals(player[i])){
                answer++;
            }
        }
        return answer;
    }
    //안내 다이어 로그
    private void dialog_notice(){
        AlertDialog.Builder dig=new AlertDialog.Builder(SoundTest.this);
        dig.setTitle("안내");
        View dialogView =View.inflate(SoundTest.this,R.layout.dialog_view2,null);
        dig.setView(dialogView);



        dig.setPositiveButton("테스트 시작!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView text_dialog2=findViewById(R.id.text_dialog2);

                //쓰레드 객체 생성
                newRunnable nr=new newRunnable();
                final Thread t=new Thread(nr);
                //쓰레드 실행
                t.start();
                //채점하기

            }
        });
        dig.show();
    }

    //쓰레드 //소리와 버튼을 동시에 누룰 수 있게 하기 위해
    class newRunnable implements Runnable{
        //이너 클래스로 한 이유는  get_intent(); 의 값이 필요해서
        @Override
        public void run() {
                    sountTest();

        }
    }
}
