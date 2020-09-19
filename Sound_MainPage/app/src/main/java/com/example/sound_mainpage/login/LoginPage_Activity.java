package com.example.sound_mainpage.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sound_mainpage.Api.ApiDto.DataDto;
import com.example.sound_mainpage.Api.ApiService;
import com.example.sound_mainpage.MainActivity;
import com.example.sound_mainpage.R;
import com.example.sound_mainpage.SuperUser;

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
                       // DataDto findUser = apiService.login(loginid, loginpw);
                        DataDto findUser=null;
                        if (!loginid.equals("") && !loginpw.equals("")){
                            findUser =new ApiService().execute("login",loginid,loginpw).get(); //api통신
                        }else {
                            Toast.makeText(LoginPage_Activity.this,"아이디 또는 비밀번호를 확인",Toast.LENGTH_LONG).show();
                        }
                        //Log.i("로그인",findUser.getData().get(0).getUser_id());
                         Log.i("로그인","ㅇㅏ디");
                        if(findUser!=null){
                            Toast.makeText(LoginPage_Activity.this,"로그인",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginPage_Activity.this, MainActivity.class);
                            intent.putExtra("userid",loginid);

                            SuperUser superUser=SuperUser.getSuperUser();
                            superUser.setUser_id(loginid);
                            Log.i("수퍼유조",superUser.getUser_id());

                            startActivity(intent);
                            finish();
                        }else if(findUser==null){
                            Toast.makeText(LoginPage_Activity.this,"아이디 또는 비밀번호를 확인",Toast.LENGTH_SHORT).show();
                            edit_id.setText("");
                            edit_pw.setText("");
                        }
                    }catch (Exception e){e.printStackTrace(); }
                    break;

                case R.id.join_user:
                    Intent intent =new Intent(LoginPage_Activity.this, JoinActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };

//    View.OnClickListener btnListener=new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.btn_login: //로그인 버튼 눌렀을 경우
//                    String loginid=edit_id.getText().toString();
//                    String loginpw=edit_pw.getText().toString();
//                    try{
//                        String result = new CustomTask().execute(loginid,loginpw,"login").get();
//                        result=result.trim();
//                    //    Toast.makeText(LoginPage_Activity.this,result,Toast.LENGTH_SHORT).show();
//                        if(result.equals("true")){
//                            Toast.makeText(LoginPage_Activity.this,"로그인",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginPage_Activity.this,MainActivity.class);
//                            intent.putExtra("userid",loginid);
//                            startActivity(intent);
//                            finish();
//                        }else if(result.equals(false)){
//                            Toast.makeText(LoginPage_Activity.this,"아이디 또는 비밀번호가 틀렸음",Toast.LENGTH_SHORT).show();
//                            edit_id.setText("");
//                            edit_pw.setText("");
//                        }else if(result.equals("noId")){
//                            Toast.makeText(LoginPage_Activity.this,"존재하지 않는 아이디",Toast.LENGTH_SHORT).show();
//                            edit_id.setText("");
//                            edit_pw.setText("");
//                        }
//                    }catch (Exception e){e.printStackTrace(); }
//                    break;
//
//                case R.id.join_user:
//                    Intent intent =new Intent(LoginPage_Activity.this,JoinActivity.class);
//                    startActivity(intent);
//                    break;
//
//            }
//        }
//    };

}
