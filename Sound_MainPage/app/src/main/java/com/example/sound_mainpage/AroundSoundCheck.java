package com.example.sound_mainpage;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class AroundSoundCheck extends AppCompatActivity {
    TextView dbText,dbList;
    int amplitude=0;
    String re[]={"120dB: 고통을 주는 소음,천둥",
            "110dB: 락 음악,자동차 경적 소리",
            "100dB: 헤어 드라이기, 모터사이클",
            "90dB: 화물 트럭, 전동 공구 소음",
            "80dB: 혼잡한 거리, 시계 알람소리",
            "70dB: 혼잡한 차도, 진공 청소기",
            "60dB: 일미터 앞 보통의 대화소 리",
            "50dB: 조용한 사무실, 조용한 거리",
            "40dB: 조용한 도서관, 공원",
            "30dB: 속삭이는 소리,조용한 방",
            "20dB: 모기 소리, 나뭇잎 스치는 소리",
            "10dB: 숨쉬는 소리, 매우 조용함"};

    //마이크
    private MediaRecorder recorder = null;
    //마이크 기본 저장위치가 필요하다
    private static String fileName = "/dev/null";
    //데시벨 기본 설정값
    private double mEMA = 0.0;
    private double EMA_FILTER = 1.0;
    Handler handler = new Handler() ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_sound);
        dbText=findViewById(R.id.dbText);
        dbList=findViewById(R.id.dbList);

        //권한 얻기
        checkDangerousPermissions();
        //진폭얻기
        startRecording();

        //쓰레드로 데시벨 계산해서  값표시
        pollTask.run();









    }












    //값 마다 색 다르게
    private void getColor(){


        if(amplitude>=70){
            if(amplitude>=120){
                setColor(0);
            }else if(amplitude>=110){
                setColor(1);
            }else if(amplitude>=100){
                setColor(2);
            }else if(amplitude>=90){
                setColor(3);
            }else if(amplitude>=80){
                setColor(4);
            }else if(amplitude>=70){
                setColor(5);
            }
        }else {
            if(amplitude>=60){
                setColor(6);
            }else if(amplitude>=50){
                setColor(7);
            }else if(amplitude>=40){
                setColor(8);
            }else if(amplitude>=30){
                setColor(9);
            }else if(amplitude>=20){
                setColor(10);
            }else if(amplitude>=10){
                setColor(11);
            }
        }


    }
    //색 정해주기
    private void setColor(int i){
        dbList.setText(re[i]);
        Log.i("색","변환");
        dbList.setTextColor(Color.RED);
    }


    //쓰레드로 값 얻기
    private Runnable pollTask = new Runnable() {
        @Override
        public void run() {
            amplitude = (int) getAmplitude();
            dbText.setText(amplitude+"DB");
            getColor();
            handler.postDelayed(pollTask, 500);

        }
    };

    //마이크로 진폭 얻기
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            Log.e("녹음 시작", "dd");
        } catch (IOException e) {
            Log.e("녹음 시작", "prepare() failed");
        }

        recorder.start();
    }

    //진폭얻기 계산
    public double getAmplitude() {
        if (recorder != null) {
            return 20 * Math.log10(recorder.getMaxAmplitude() / 16.0);
        } else {
            return 0;
        }
    }

    //데시벨 계산
    public double getAmplitudeEMA() {
        double amp = getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) + mEMA;
        return mEMA;
    }

    //액티비티 끝났을떄
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRecording();
    }

    //오디오 끝내기
    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    //권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.RECORD_AUDIO
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }

        }
    }
}
