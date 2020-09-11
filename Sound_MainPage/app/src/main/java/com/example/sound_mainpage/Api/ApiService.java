package com.example.sound_mainpage.Api;

import android.os.AsyncTask;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        DataDto login = login(strings[0], strings[1]);


        return login;
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
    public static DataDto getData(String user_id){
        initApi(url);

        Call<DataDto> getCall=myApi.getData(user_id);
        getCall.enqueue(new Callback<DataDto>() {
            @Override
            public void onResponse(Call<DataDto> call, Response<DataDto> response) {
                dataDto =response.body();
                Log.i("서비스",dataDto.getData().get(0).getUser_seting()+"L"+dataDto.getData().get(0).getDay_1());
            }

            @Override
            public void onFailure(Call<DataDto> call, Throwable t) {

            }
        });
        return dataDto;
    }

     public static  DataDto login(String user_id,String user_pwd){
        initApi(url);
        Log.i("api영역","로그인 호출");
        Call<DataDto> getCall=myApi.login(user_id,user_pwd);
        getCall.enqueue(new Callback<DataDto>() {
            @Override
            public void onResponse(Call<DataDto> call, Response<DataDto> response) {
                dataDto=response.body();
                Log.i("api영역",dataDto.getData().get(0).getUser_id());
            }

            @Override
            public void onFailure(Call<DataDto> call, Throwable t) {
            }
        });

        return dataDto;
    }


}

