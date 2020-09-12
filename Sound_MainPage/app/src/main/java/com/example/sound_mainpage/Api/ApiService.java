package com.example.sound_mainpage.Api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class ApiService  extends AsyncTask<String,Void,DataDto> {

    private static  String url="http://14.55.237.125:8080";
    private static MyApi myApi;
    private static DataDto dataDto=null;

    public ApiService( ) {
    }


    @Override
    protected DataDto doInBackground(String... strings) {
        DataDto dataDto=null;
        if(strings[0].equals("login")){
            dataDto = login(strings[1], strings[2]); //로그인일경우 로그인 api호출
            Log.i("api영역","백글 로그인호출");
        }else if(strings[0].equals("getUserData")){
            dataDto=getUserData(strings[1]);//데이터를 얻고자 할때는 겟 데이터
            Log.i("api영역","겟데이터호출");

        }else if(strings[0].equals("updateDay")){
            UpdateUserDay updateUserDay=new UpdateUserDay();
            updateUserDay.setDay_1(strings[2]);updateUserDay.setDay_2(strings[3]);
            updateUserDay.setDay_3(strings[4]);updateUserDay.setDay_4(strings[5]);
            updateUserDay.setDay_5(strings[6]);updateUserDay.setDay_6(strings[7]);updateUserDay.setDay_7(strings[8]);
            updateUserDay(strings[1],updateUserDay);
        }else if(strings[0].equals("updateSeting")){
            //세팅업데이트
            Setting setting=new Setting();
            setting.setSetting(strings[2]);
            updateSetting(strings[1],setting);
            Log.i("api영역세팅업데이트",strings[1]+strings[2]);


        }
        return dataDto;
    }


    public static void initApi(String url){
        //retorfit 객체를 생성하고 이 객체를 이용해서 api service를(이 클래스 아님) create해 준다
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myApi = retrofit.create(MyApi.class);
        Log.i("api영역","초기화 호출");

    }

    //setting 업데이트
    public static void updateSetting(String user_id,Setting setting){
        initApi(url);
        Log.i("api세팅",setting.getSetting());
        Call<Void> getCall=myApi.updateSetting(user_id,setting);
        try {
            getCall.execute().body();
            Log.i("api영역 업데이트보냈음",user_id+setting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //day업데이트
    public static void updateUserDay(String user_id,UpdateUserDay updateUserDay){
        initApi(url);
        Log.i("api영역 업데이트7",updateUserDay.getDay_7());
        Call<Void> getCall=myApi.updateDay(user_id, updateUserDay);
        try {
            getCall.execute().body(); //업데이트인데 뭘 돌려주지?
            Log.i("api영역 업데이트보냈음",user_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataDto getUserData(String user_id){
        initApi(url);

        Call<DataDto> getCall=myApi.getData(user_id);
        try {
             dataDto = getCall.execute().body();//이건 동기 방식
            if (dataDto.getData()==null){ //아디나 비번이 틀려서 값이 널로오면
                Log.i("api영역","에러");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        getCall.enqueue(new Callback<DataDto>() { //이건 비동기 방싱
//            @Override
//            public void onResponse(Call<DataDto> call, Response<DataDto> response) {
//                dataDto =response.body();
//                Log.i("서비스",dataDto.getData().get(0).getUser_seting()+"L"+dataDto.getData().get(0).getDay_1());
//            }
//
//            @Override
//            public void onFailure(Call<DataDto> call, Throwable t) {
//
//            }
//        });
        return dataDto;
    }

     public static  DataDto login(String user_id,String user_pwd){
        initApi(url);
        Log.i("api영역","로그인 호출");
        Call<DataDto> getCall=myApi.login(user_id,user_pwd);
         try {
             dataDto=getCall.execute().body();
             if (dataDto.getData()==null){ //아디나 비번이 틀려서 값이 널로오면
                 Log.i("api영역","에러");
                 return null;
             }
             //Log.i("api영역",dataDto.getData().get(0).getUser_id());
             Log.i("api영역",dataDto.getData().toString());
         } catch (IOException e) {
             e.printStackTrace();
         }

        return dataDto;
    }


}

