package com.example.soundcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.GpsStatus;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    AudioManager audio;
    SeekBar study_bar,exercise_bar;
    Button study,exercise;
    SoundPool soundPool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //초기화
        init();
        //볼륨 조정
        volumSet();

        study_bar.setOnSeekBarChangeListener(listener);
        exercise_bar.setOnSeekBarChangeListener(listener);
        study.setOnClickListener(listener_btn);
        exercise.setOnClickListener(listener_btn);

    }

    SeekBar.OnSeekBarChangeListener listener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //드래그 하면 발생
            //현재의 사운드 나오게

            audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                    progress,0);
            final int sound = soundPool.load(MainActivity.this, R.raw.dding, 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(sound,1,1,0,1,1);
                }
            });


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
         //   탭하는 순간 onStartTrackingTouch() 발생
            //늘거나 주는 사운드소리
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        //드래그 놓으면 발생
        //최종 사운드 소리
        }
    };

    View.OnClickListener listener_btn=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //클릭하면 그 시크바의 소리 가져오기
            int nowVolume= 0;
            switch (v.getId()){
                case R.id.study:
                    nowVolume=study_bar.getProgress();
                    break;
                case R.id.exercise:
                    nowVolume=exercise_bar.getProgress();
                    break;
            }
            //현재의 소리값 가져와서 버튼 누를시 소리 설정하기
            audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                    nowVolume,0);
            //소리도 듣기
            final int sound = soundPool.load(MainActivity.this, R.raw.dding, 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(sound,1,1,0,1,1);
                }
            });
        }
    };

    //초기화
    private void init(){
        study_bar=findViewById(R.id.study_bar);
        exercise_bar=findViewById(R.id.exercise_bar);
        study=findViewById(R.id.study);
        exercise=findViewById(R.id.exercise);

        //소리 객체 만들기
        soundPool=new SoundPool(1,AudioManager.STREAM_MUSIC,0);
    }
    //최대 볼륨과 현재 볼륨 지정
    private  void volumSet(){
         audio= (AudioManager) getSystemService(AUDIO_SERVICE);// 매니저들은 get으로 가져옴
        int max=audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //음악타입의 맥스볼륨가져오기
        int CurrentVolum= audio.getStreamVolume(AudioManager.STREAM_MUSIC); //음악타입의 현재 볼륨

        //시크바의 소리 조정하기
        study_bar.setMax(max);
        study_bar.setProgress(CurrentVolum);

        exercise_bar.setMax(max);
        exercise_bar.setProgress(CurrentVolum);


    }

}
