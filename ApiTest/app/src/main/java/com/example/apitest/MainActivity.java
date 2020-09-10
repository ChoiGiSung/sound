package com.example.apitest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView text;
    private String url="http://14.55.237.125:8080";
    private MyApi myApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.btn1);
        text=findViewById(R.id.text);

        initMyApi(url);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    String result=new CostomTask().execute().get();
//                    text.setText(result);
//                } catch (Exception e) { }

                Call<DataDto> getCall = myApi.get_data();
                getCall.enqueue(new Callback<DataDto>() {
                    @Override
                    public void onResponse(Call<DataDto> call, Response<DataDto> response) {
                        DataDto data=response.body();
                        String result=data.getData().get(0).getUser_id();
                        text.setText(result);
                    }

                    @Override
                    public void onFailure(Call<DataDto> call, Throwable t) {

                    }
                });

            }
        });


    }
    private void initMyApi(String url){
        //retorfit 객체를 생성하고 이 객체를 이용해서 api service를 create 해준다
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(MyApi.class);
    }
}
