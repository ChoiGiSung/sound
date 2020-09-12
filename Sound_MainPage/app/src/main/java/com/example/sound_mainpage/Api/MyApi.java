package com.example.sound_mainpage.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyApi {
    @GET("/api/login/{user_id}/{user_pwd}")//{user_id}
    Call<DataDto> login(@Path("user_id") String user_id,@Path("user_pwd") String user_pwd);

    @GET("/api/data/{user_id}")
    Call<DataDto> getData(@Path("user_id")String user_id);

    @PUT("/api/userday/{user_id}")
    Call<Void> updateDay(@Path("user_id")String user_id,@Body UpdateUserDay updateUserDay);

    @PUT("/api/usersetting/{user_id}")
    Call<Void> updateSetting(@Path("user_id")String user_id,@Body Setting setting);

}
