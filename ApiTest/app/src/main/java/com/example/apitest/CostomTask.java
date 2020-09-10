package com.example.apitest;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CostomTask extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {
        try{

            String str;
            //Api 접근
            //아이피를 넣어야되네
            URL url=new URL("http://14.55.237.125:8080/api/login/rain444");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.i("유효","성공");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestMethod("GET");

            //유효한 응답이 있는지
            if(conn.getResponseCode()==200){
                Log.i("유효","연결성공");
                //그럼 inputStreamReader로 읽기
                InputStream responseBody=conn.getInputStream();
                InputStreamReader responseBodyReader=new InputStreamReader(responseBody,"UTF-8");

                //api읽기
                JSONObject jsonObject=new JSONObject();
                JsonReader jsonReader=new JsonReader(responseBodyReader);
                jsonReader.beginObject();
                while (jsonReader.hasNext()){
                    String key=jsonReader.nextName();
                    if(key.equals("data")){

                        //String va=jsonReader.nextString();
                        Log.i("유효","찾음");

                        break;
                    }else {
                        jsonReader.skipValue();
                        Log.i("유효","못찾음");
                    }
                }
            }else {
                Log.i("유효","실패");
            }

        }catch (Exception e){
            Log.i("유효","실패");

        }


        return null;
    }


}
