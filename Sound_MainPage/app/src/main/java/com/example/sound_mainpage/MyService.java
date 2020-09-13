package com.example.sound_mainpage;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.sound_mainpage.Api.ApiService;
import com.example.sound_mainpage.Api.ApiDto.DataDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyService extends Service {
    int count=0;
    long startTime,EndTime;
    String startTime_s,EndTime_s=null;
    ArrayList<String> Arl =new ArrayList<>();
    String userid=null;



    private  static IntentFilter intentFilter=new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    private  static BroadcastReceiver broadcastReceiver=null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {

        //서비스에서 가장 먼저 호출됨 (최초 한번만)
        super.onCreate();

        returnApi();
        Log.d("Test","서비스의 oncreate");

        broadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean isEarphoneOn=(intent.getIntExtra("state",0)>0)?true:false;

                if(isEarphoneOn){
                    Log.e("이어폰log","Earphone is plugged");
                    Toast.makeText(getApplicationContext(),"연결",Toast.LENGTH_SHORT).show();

                     startTime =SystemClock.elapsedRealtime();
                    startTime_s =new SimpleDateFormat("ddHHmm").format(new Date());
                    // 연결시작 분

                }else if (!isEarphoneOn && startTime != 0){
                    Log.e("이어폰log","Earphone is unplugged");
                    Toast.makeText(getApplicationContext(),"연결 해제",Toast.LENGTH_SHORT).show();

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
        //전까지 헤드폰 연결 비연결

    }

    private void sendApiupdateDay(){
        String result=null;
        // Log.i("제발",userid+Arl.get(0)+Arl.size());
        if (Arl.size() == 7) {
            new ApiService().execute("updateDay", userid, Arl.get(0), Arl.get(1), Arl.get(2),
                    Arl.get(3), Arl.get(4), Arl.get(5), Arl.get(6), "usetime");
        }
        }
//    private void sendMsgToActivity(){
//
//
//        //db로 보내기
//        String result=null;
//       // Log.i("제발",userid+Arl.get(0)+Arl.size());
//        try {
//            if (Arl.size() == 7) {
//                result = new CustomTask().execute(userid,Arl.get(0),Arl.get(1),Arl.get(2),
//                        Arl.get(3),Arl.get(4),Arl.get(5),Arl.get(6),"usetime").get();
//          //      Log.i("갔냐?","ㅇ");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        result=result.trim();
//
//       // Log.i("왔다",result);
//      //  Toast.makeText(getApplication(),result,Toast.LENGTH_SHORT).show();
//        if(result.equals("true")){
//            Toast.makeText(getApplication(),"값 넣기 성공",Toast.LENGTH_SHORT).show();
//        }else if(result.equals(false)){
//            Toast.makeText(getApplication(),"아이디 또는 비밀번호가 틀렸음",Toast.LENGTH_SHORT).show();
//        }else if(result.equals("noId")){
//            Toast.makeText(getApplication(),"존재하지 않는 아이디",Toast.LENGTH_SHORT).show();
//        }
//      //  Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//      }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //서비스가 호출 될 떄마다 실행
        Log.d("Test","서비스의 onStartCommand");

        startForeground(1, new Notification()); //강제 종료로도 죽이지 못하게

        try{//백그라운드에서 앱을꺼버리면 userid를 받을 수 없어서 오류가 남

            this.userid = intent.getStringExtra("userid");
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
