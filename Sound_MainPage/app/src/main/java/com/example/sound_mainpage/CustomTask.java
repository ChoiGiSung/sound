package com.example.sound_mainpage;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

//jsp로 값 보내고 값 받기

public  class CustomTask extends AsyncTask<String,Void,String> {
    String sendMsg,receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        try{
            String str;
            URL url = new URL("http://14.55.238.20:8080/Sound_data/sound_data.jsp");//나의 jsp 페이지로
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");


            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            if(strings.length==3){//로그인 및 회원가입
                sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&type="+strings[2];
                Log.i("타입",strings[0]+strings[1]+strings[2]);
                osw.write(sendMsg);
                osw.flush();
            }else if(strings.length ==9){//길이는 9// 이거는 사용시간 넣기
                sendMsg = "id="+strings[0]+"&day_1="+strings[1]+"&day_2="+strings[2]+"&day_3="+strings[4]+"&day_4="+strings[4]+
                        "&day_5="+strings[5]+"&day_6="+strings[6]+"&day_7="+strings[7]+"&type="+strings[8];
                Log.i("타입",strings[0]+strings[1]+strings[2]);
                osw.write(sendMsg);
                osw.flush();
            }//나중에 넓히면 됨
            else if(strings.length==2){//이거는 사용시간 얻기 얻기
                sendMsg = "id="+strings[0]+"&type="+strings[1];
                Log.i("타입",strings[0]+strings[1]);
                osw.write(sendMsg);
                osw.flush();
            }
            else if(strings.length==4){
                //세팅값 넣기
                sendMsg="id="+strings[0]+"&seting="+strings[1]+"&type="+strings[2];
                Log.i("타입",strings[0]+strings[1]+strings[2]);
                osw.write(sendMsg);
                osw.flush();
            }  else if(strings.length==5){
                //세팅값 넣기
                sendMsg="id="+strings[0]+"&type="+strings[1];
                Log.i("타입",strings[0]+strings[1]);
                osw.write(sendMsg);
                osw.flush();
            }

            if(conn.getResponseCode() == conn.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine() )!= null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            }else {
                Log.i("통신 결과",conn.getResponseMessage()+"에러");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return receiveMsg;
    }
}