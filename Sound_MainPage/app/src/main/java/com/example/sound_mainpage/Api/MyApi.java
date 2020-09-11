package com.example.sound_mainpage.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyApi {
    @GET("/api/login/{user_id}/{user_pwd}")//{user_id}
    Call<DataDto> login(@Path("user_id") String user_id,@Path("user_pwd") String user_pwd);

    @GET("/api/seating/{user_id}")
    Call<DataDto> getData(@Path("user_id")String user_id);
}
