package com.example.sound_mainpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.sound_mainpage.SoundChoice.sound_choice;
import com.example.sound_mainpage.SoundCollection.Sound_collection;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView topOne,topTwo,TopThree;
    ImageButton menu_top;
    ArrayList<String> Arl=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //바텀 네비

        final Intent intent = getIntent(); //로그인 창에서 오는 id 받기
        final String userid=intent.getStringExtra("userid");

        Log.i("메인 아이디",userid);
        //서비스 실행 시키키
        Intent service_intent=new Intent(
                getApplicationContext(),//현재 제어권자
                MyService.class//이동할 컴포넌트
        );
        service_intent.putExtra("userid",userid);
        startService(service_intent);

        menu_top=findViewById(R.id.menu_top);
        topOne=findViewById(R.id.topOne);
        topTwo=findViewById(R.id.topTwo);
        topOne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1 = new Intent(getApplicationContext(),Sound_useTime.class);
                intent1.putStringArrayListExtra("usetime",Arl);
                startActivity(intent1);
                return false;
            }
        });
        topTwo.findViewById(R.id.topTwo);
        topTwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1 = new Intent(getApplicationContext(), Sound_collection.class);
               intent1.putExtra("userid",userid);
                startActivity(intent1);
                return false;
            }
        });
        TopThree=findViewById(R.id.TopThree);
        TopThree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1 = new Intent(getApplicationContext(), sound_choice.class);
                startActivity(intent1);
                return false;
            }
        });

        // 위에 메뉴 버튼 클릭 시 드로우 레이아웃 작동
        menu_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer);
                drawer.openDrawer(Gravity.LEFT);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //로컬브로드 캐스트에서 값을 받기
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessamgeReceiver,new IntentFilter("custom-event-name")
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessamgeReceiver);
    }

    //리시버를 이용하여 서비스 값 받기
    //바로 실행된다
    private BroadcastReceiver mMessamgeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try{
                Arl = intent.getStringArrayListExtra("usetime");
                for(String a : Arl){

                    Log.i("어레이MainActivity",a);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

//    @Override
//    public void startActivityForResult(Intent intent, int requestCode) {
//       Log.i("스타트엑티비티포 리절트","ㅇㅇ");
//        intent= new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"));
//        super.startActivityForResult(intent, requestCode);
//    }

    }



