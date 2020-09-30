package com.example.sound_mainpage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.sound_mainpage.Api.ApiDto.DataDto;
import com.example.sound_mainpage.Api.ApiService;
import com.example.sound_mainpage.SoundCollection.Sound_collection;
import com.example.sound_mainpage.SoundCollection.list_item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyService extends Service {
    int count=0;
    long startTime,EndTime;
    String startTime_s,EndTime_s=null;
    ArrayList<String> Arl =new ArrayList<>();
    //유저이름 받아오기
    SuperUser superUser=SuperUser.getSuperUser();

    String userid=superUser.getUser_id();

    //실험==============================
    //알림창 소리관련
    AudioManager audio;

    //배열
    private ArrayList<list_item> list_itemArrayList=new ArrayList<>();
    //실험==============================


    //블루투스연결 필터
    private  static IntentFilter BlueFilter=new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
    //이어플러그 필터
    private  static IntentFilter intentFilter=new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    private  static BroadcastReceiver broadcastReceiver=null;


    //실험===========================================
    private  static IntentFilter intentFilter0=new IntentFilter("action0");
    private  static IntentFilter intentFilter1=new IntentFilter("action1");
    private  static IntentFilter intentFilter2=new IntentFilter("action2");


    //최대 볼륨과 현재 볼륨 지정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private  void volumSet(){
        audio= (AudioManager) getApplicationContext().getSystemService(AUDIO_SERVICE);// 매니저들은 get으로 가져옴

    }

    private void setArray(){
        list_itemArrayList.clear();
        SuperUser superUser=SuperUser.getSuperUser();
        String result = superUser.getUser_setting();
        Log.i("수퍼값",result+""+superUser.getUser_setting());
        if (!result.equals("0")) {
            String[] result_split = result.split("/"); //db값을 하나의 문자열로 받아서 자른다
            int i=0;
            for (String a : result_split) {
                String[] result2 = a.split(",");
                list_itemArrayList.add(new list_item(result2[0], Integer.parseInt(result2[1])));
                Log.i("링크값",i+"::"+list_itemArrayList.get(i).getBtn_name()+":"+list_itemArrayList.get(i).getSeek_bar());
                i++;
            }
        }
    }
    //실험===========================================
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //실험===========================================
    @RequiresApi(api = Build.VERSION_CODES.O)
    //실험===========================================
    @Override
    public void onCreate() {

        //서비스에서 가장 먼저 호출됨 (최초 한번만)
        super.onCreate();

        Log.i("Test온크리에디읕",userid);
        try{
            returnApi(); //onStartCommand가 먼저 실행되기 때문에 예외처리해줌
        }catch (Exception e){
            e.printStackTrace();
        }

        //실험==============================
        volumSet();
        setArray();
        //실험==============================

        broadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


               // 실험===============================

                try{
                    if(intent.getAction().equals("action0")){
                        Toast.makeText(getApplicationContext(), "notification Button Clicked", Toast.LENGTH_LONG).show();
                        int seek_bar =list_itemArrayList.get(0).getSeek_bar();
                        String btn_name = list_itemArrayList.get(0).getBtn_name();
                        Log.i("링크값",seek_bar+btn_name);
                        audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                                seek_bar,0);
                        setArray();
                        startMyOwnForeground();
                    }else if(intent.getAction().equals("action1")){
                        Toast.makeText(getApplicationContext(), "notification Button Clicked1", Toast.LENGTH_LONG).show();
                        int seek_bar =list_itemArrayList.get(1).getSeek_bar();
                        String btn_name = list_itemArrayList.get(1).getBtn_name();
                        Log.i("링크값",seek_bar+btn_name);
                        audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                                seek_bar,0);
                        setArray();
                        startMyOwnForeground();
                    }else if(intent.getAction().equals("action2")){
                        Toast.makeText(getApplicationContext(), "notification Button Clicked2", Toast.LENGTH_LONG).show();
                        int seek_bar =list_itemArrayList.get(2).getSeek_bar();
                        String btn_name = list_itemArrayList.get(2).getBtn_name();
                        Log.i("링크값",seek_bar+btn_name);
                        audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                                seek_bar,0);
                        setArray();
                        startMyOwnForeground();
                    }
                    registerReceiver(broadcastReceiver,intentFilter0);
                    registerReceiver(broadcastReceiver,intentFilter1);
                    registerReceiver(broadcastReceiver,intentFilter2);
                }catch (Exception e){
                    e.printStackTrace();
                }


                //실험===============================






                Log.i("Test온크리에디읕","서비스의 리시버");
                //블루투스 연결 엑션
                final String action=intent.getAction();
                //이어폰 연결 flag //연결은 false 비연결은 true
                boolean isEarphoneOn=(intent.getIntExtra("state",0)>0)?true:false;


                //블루투스의 상태
                final int state=intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE,BluetoothAdapter.ERROR);


                if(isEarphoneOn ||state==BluetoothAdapter.STATE_CONNECTED){
                    Log.e("이어폰log","Earphone is plugged");
                    Toast.makeText(getApplicationContext(),"연결",Toast.LENGTH_SHORT).show();

                     startTime =SystemClock.elapsedRealtime();
                    startTime_s =new SimpleDateFormat("ddHHmm").format(new Date());
                    // 연결시작 분
                    Log.e("이어폰log",isEarphoneOn+"");                                                    //블루투스는 상태이름 해석하듯이
                }else if ((isEarphoneOn==false||state==BluetoothAdapter.STATE_DISCONNECTED)&& startTime != 0 ){    //이어폰을 꽃을때는 true로 되서 if문에 걸리고 뺄때는 뺀상태인 false여야하고 실행시작시간이 0이아니여야한다
                    Log.e("이어폰log","Earphone is unplugged");
                    Toast.makeText(getApplicationContext(),"연결 해제",Toast.LENGTH_SHORT).show();
                    Log.e("이어폰log",isEarphoneOn+"");
                    //연결 끊은 시각
                    EndTime =SystemClock.elapsedRealtime();
                   EndTime_s =new SimpleDateFormat("ddHHmm").format(new Date()); //날짜계산을 위한거
                    count +=(int) ((EndTime-startTime)/1000/60)%60;
                    Toast.makeText(getApplicationContext(),count+"분",Toast.LENGTH_SHORT).show();

                    addTime(startTime_s,EndTime_s,count); //어레이리스트에 값 넣기 db 전송을 위하여
                 //   Toast.makeText(getApplicationContext(), Arl.get(Arl.size() - 1), Toast.LENGTH_SHORT).show();
                        for(String a : Arl){
                            Log.i("어레이",a);
                        }

                    //sendMsgToActivity(); //엑티비티로 메시지 보내기
                    sendApiupdateDay();

                    startTime=0;//이래야 평소에는 endtime을 저장하지 않는다
                }
            }
        };

        registerReceiver(broadcastReceiver,intentFilter);
        registerReceiver(broadcastReceiver,BlueFilter);
        //전까지 헤드폰 연결 비연결

    }

    private void sendApiupdateDay(){
        if (Arl.size() == 7) {
            new ApiService().execute("updateDay", userid, Arl.get(0), Arl.get(1), Arl.get(2),
                    Arl.get(3), Arl.get(4), Arl.get(5), Arl.get(6), "usetime");
        }
        }



        //백그라운드에서 동작하기 위해  startForeground(2, notification);을 썻었는데
        //오레오 버전 이후 부터는 채널 값을 만들어서 줘야 백그라운드에서 실행이 가능하다
        //그래서 밑에 채널을 만들어주는 함수를 작성
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        //채널에서 사용하기 위한 PendingIntent ,(addAction에서 사용함)
        Intent intent=new Intent(this,Sound_collection.class);
        intent.putExtra("userid",userid);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);


        //알림바에 나오는 모습
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.icondonkey)
                .setContentTitle("당신과 나의 귀건강")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .addAction(R.drawable.ic_launcher_foreground,"사용자 버튼 리스트",pendingIntent)
                .setAutoCancel(true)
                .build();

        Log.i("등록","1");

        //실험======================================================

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.reciver_sample);

        try {
            Intent intent0=new Intent("action0");
            Intent intent1=new Intent("action1");
            Intent intent2=new Intent("action2");

            PendingIntent snoo0=PendingIntent.getBroadcast(this,
                    0,
                    intent0,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent snoo1=PendingIntent.getBroadcast(this,
                    0,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent snoo2=PendingIntent.getBroadcast(this,
                    0,
                    intent2,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            //뷰 바꿔치기

            if (list_itemArrayList.size()>0){
                remoteViews.setOnClickPendingIntent(R.id.btn_smaple0,snoo0);
                remoteViews.setTextViewText(R.id.btn_smaple0,list_itemArrayList.get(0).getBtn_name());

            }
            else {
                remoteViews.setTextViewText(R.id.btn_smaple0,"설정해주세요!");
                remoteViews.setTextViewText(R.id.btn_smaple1,"설정해주세요!");
                remoteViews.setTextViewText(R.id.btn_smaple2,"설정해주세요!");

            }
            if (list_itemArrayList.size()>1){
                remoteViews.setOnClickPendingIntent(R.id.btn_smaple1,snoo1);
                remoteViews.setTextViewText(R.id.btn_smaple1,list_itemArrayList.get(1).getBtn_name());
            }else {
                remoteViews.setTextViewText(R.id.btn_smaple1,"설정해주세요!");
                remoteViews.setTextViewText(R.id.btn_smaple2,"설정해주세요!");

            }

            if (list_itemArrayList.size()>2){
                remoteViews.setOnClickPendingIntent(R.id.btn_smaple2,snoo2);
                remoteViews.setTextViewText(R.id.btn_smaple2,list_itemArrayList.get(2).getBtn_name());
            }else {
                Log.i("설정실","해주세요");
            }



        }catch (Exception e){
            e.printStackTrace();
        }finally {

            notification.contentView=remoteViews;
            nm.notify(1,notification);

        }







        //===========================================================
        startForeground(2, notification);
    }

    //서비스가 호출 될 떄마다 실행
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startMyOwnForeground(); //오레오 버전 이상은 채널 값을 만들어서 넣어줘야함
            }
            else{
                startForeground(1, new Notification()); //오레오 버전 이하는 원래 하던데로 백그라운드 실행이 가능함
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        try{//백그라운드에서 앱을꺼버리면 userid를 받을 수 없어서 오류가 남

            this.userid = intent.getStringExtra("userid");
            Log.i("Test 유저아이디",userid);
            //returnDB(); //값 얻어오기 이게 oncreate에서 되면 좋겠지만 onstart에서만 id값을 얻을 수 있다 하지만 start에 올려놔서 죽이지 못하게 해놨다
            returnApi();
        }catch (Exception e){

        }

        goActivityIntent_useTime();//서비스가 돌떄마다 메인으로 Arl 값을 전달 -> 그래프를 그리기 위해서
        return START_REDELIVER_INTENT ;

    }

    //액티비티에 메시지 보내기
    public void goActivityIntent_useTime(){
        Intent intent = new Intent("custom-event-name");
        intent.putStringArrayListExtra("usetime",Arl);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);  //이거는 main으로 가는거
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //서비스가 종료될때 실행
        Log.d("Test","서비스의 onDestory");
    }

    //어레이리스트에 사용시간을 넣기
    public void addTime(String day_start,String day_end,int count){ //int형으로도 전달
        String day_start_v =day_start.substring(0,2);
        String day_end_v =day_end.substring(0,2);
        Log.i("꼽은날",day_start_v);

        //start_int 랑 endint가 문제
        if(day_start_v.equals(day_end_v)){ //뺄때와 꼽을때의 날짜만 같으면 set을 해버림 -> 그럼 뺄때와 꼽을때가 같은 날짜이니 전날도 확인해 보자
            //같은 날이면 이용시간만 더하기 꼽을때와 뺄때가 날짜가 같으면 그냥 더하기
            String addValue= day_start_v+String.format("%04d",count);//4자리수로 바꾸기 시간으로 바꾸는 것이 아닌 110분을 0110으로 바꾸는 것
            if(!(Arl.get(Arl.size()-1).equals("0"))){
                //전날 값이 있고
                if(Arl.get(Arl.size()-1).substring(0,2).equals(day_start_v)){//전날과 같은 날이면
                    //뺄때와 꼽을때의 날짜가 같고 전날의 날짜가 같으면  그리고 0도 아니어야해
                    count = Integer.parseInt(Arl.get(Arl.size()-1).substring(2,6))+count;//전날과 count를 더하지 그리고 넣자
                    addValue= day_start_v+String.format("%04d",count);
                    Arl.set(Arl.size()-1,addValue); //set으로 update
                }else if(!(Arl.get(Arl.size()-1).substring(0,2).equals(day_start_v))){
                    //근대 만약 뺄때와 꼽을떄의 날짜가 같은데 맨 마지막이 0이아니고 삭제하고 add 이거나 다음 날이면
                    Arl.remove(0);
                    Arl.add(addValue);
                }
            }else if(Arl.size() ==7 && Arl.get(Arl.size()-1).equals("0")){
                //근대 만약 뺄때와 꼽을떄의 날짜가 같은데 맨 마지막이 0이면 삭제하고 add
                Arl.remove(0);
                Arl.add(addValue);
            }
        }else if(!day_start_v.equals(day_end_v) && Arl.size() >=7){
            //시작날짜와 종료날짜가 다르고 7크거나 같으면 하나 삭제하고 삽입
            //꼽을때 날짜와 뺄떄 날짜가 달라라
            this.count=0;
            Arl.remove(0);// 가장 먼저의 날짜 삭제하고 삽입

            //근데 이제 0이아니면  계산해서 == 전날의 데이터가 있다
            if(!Arl.get(Arl.size()-1).equals("0")){
                int getArray =Integer.parseInt(Arl.get(Arl.size()-1).substring(2,6)); //전날의 시간과 분을 얻음
                int plus_yesterday = 2360-Integer.parseInt(day_start.substring(2,6)); //전날 시간을 더하기 위해 24시에서 전날시간 빼기
                int Hour=Integer.parseInt(String.format("%04d",plus_yesterday).substring(0,2))*60; // 그 사용량의 앞 2 자리는 시간이므로 분으로 환산
                plus_yesterday=Hour+Integer.parseInt(String.format("%04d",plus_yesterday).substring(2,4)); //뒤의 2 자리는 분이므로 둘이 합산
                int add = getArray+plus_yesterday; //이제 string 에 날짜만 붙여서 넣자
                String usetime = day_start_v+String.format("%04d",add);
                Arl.set(Arl.size()-1,usetime);//전날데이터에 +
                Log.i("값들어가는지",Arl.get(Arl.size()-1));
                //여기까지가 전날 설정이고 이제 현재를 넣어줘야함


                //현재
                int endHour=Integer.parseInt(day_end.substring(2,4));//0000
                int min=Integer.parseInt(day_end.substring(4,6));
                int plus_today=endHour*60+min;
                                   //앞 2자리
                String endTime= day_end_v+String.format("%04d",plus_today);//0000
                Arl.add(endTime); //새로운 날짜와 시간 넣기
                Log.i("값들어가는지2",Arl.get(Arl.size()-1));
            }else {
                //꼽을때와 뺄때가 다른 날짜인데 앞에 데이터가 없어 그럼  어제와 오늘을 넣자
                int add = 2360-Integer.parseInt(day_start.substring(2,6)); //어제의 시작날짜를 빼서 24시 이전에 사용량을 구한다
                int Hour=Integer.parseInt(String.format("%04d",add).substring(0,2))*60; // 그 사용량의 앞 2 자리는 시간이므로 분으로 환산
                add=Hour+Integer.parseInt(String.format("%04d",add).substring(2,4)); //뒤의 2 자리는 분이므로 둘이 합산
                String usetime = day_start_v+String.format("%04d",add);
                Arl.set(Arl.size()-1,usetime);//전날데이터에 +

                String endTime= day_end_v+String.format("%04d",Integer.parseInt(day_end.substring(2,6)));
                Arl.add(endTime); //새로운 날짜와 시간 넣기
            }
        }
    }


    //db에서 day값 받아서 arry에 넣기
    private void returnApi(){
        Arl.clear(); //startcomm은 자주 호출 되므로
        Log.i("서비스영역","초기화");
        try{


            //db로 보내기
            String result=null;
            Log.i("서비스영역",userid);
            DataDto dataDto=new ApiService().execute("getUserData",userid).get(); //api로 값 받기
            DataDto.UserDto userDto=dataDto.getData().get(0);
            Log.i("서비스영역",userDto.getDay_1());
            Arl.add(userDto.getDay_1());
            Arl.add(userDto.getDay_2());
            Arl.add(userDto.getDay_3());
            Arl.add(userDto.getDay_4());
            Arl.add(userDto.getDay_5());
            Arl.add(userDto.getDay_6());
            Arl.add(userDto.getDay_7());
        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
