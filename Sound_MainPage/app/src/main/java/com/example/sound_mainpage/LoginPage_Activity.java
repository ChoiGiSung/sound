package com.example.sound_mainpage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LoginPage_Activity extends AppCompatActivity {
    EditText edit_id,edit_pw;
    Button btn_login;
    TextView join_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        join_user=findViewById(R.id.join_user);
        btn_login=findViewById(R.id.btn_login);
        edit_id=findViewById(R.id.edit_id);
        edit_pw=findViewById(R.id.edit_pw);
        btn_login.setOnClickListener(btnListener);
        join_user.setOnClickListener(btnListener);

    }


    View.OnClickListener btnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login: //로그인 버튼 눌렀을 경우
                    String loginid=edit_id.getText().toString();
                    String loginpw=edit_pw.getText().toString();
                    try{
                        String result = new CustomTask().execute(loginid,loginpw,"login").get();
                        result=result.trim();
                    //    Toast.makeText(LoginPage_Activity.this,result,Toast.LENGTH_SHORT).show();
                        if(result.equals("true")){
                            Toast.makeText(LoginPage_Activity.this,"로그인",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginPage_Activity.this,MainActivity.class);
                            intent.putExtra("userid",loginid);
                            startActivity(intent);
                            finish();
                        }else if(result.equals(false)){
                            Toast.makeText(LoginPage_Activity.this,"아이디 또는 비밀번호가 틀렸음",Toast.LENGTH_SHORT).show();
                            edit_id.setText("");
                            edit_pw.setText("");
                        }else if(result.equals("noId")){
                            Toast.makeText(LoginPage_Activity.this,"존재하지 않는 아이디",Toast.LENGTH_SHORT).show();
                            edit_id.setText("");
                            edit_pw.setText("");
                        }
                    }catch (Exception e){e.printStackTrace(); }
                    break;

                case R.id.join_user:
                    Intent intent =new Intent(LoginPage_Activity.this,JoinActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };



}
