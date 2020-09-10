package com.example.apitest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {

    @GET("/api/login/rain444")//{user_id}
    Call<DataDto>get_data();//@Path("user_id") String user_id
}
