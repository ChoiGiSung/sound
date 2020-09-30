package com.example.sound_mainpage.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    CheckBox autosave;

    //자동 로그인
    private boolean saveLoginData;
    private SharedPreferences appData;
    private String id;
    private String pwd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        autosave=findViewById(R.id.autosave);
        join_user=findViewById(R.id.join_user);
        btn_login=findViewById(R.id.btn_login);
        edit_id=findViewById(R.id.edit_id);
        edit_pw=findViewById(R.id.edit_pw);
        btn_login.setOnClickListener(btnListener);
        join_user.setOnClickListener(btnListener);

        checkLogin();

    }

    //로그인 검증 메소드
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
                            superUser.setUser_setting(findUser.getData().get(0).getUser_seting());
                            Log.i("수퍼유조",superUser.getUser_id());
                            Log.i("수퍼유조",superUser.getUser_setting());


                            save(); //로그인 정보 저장
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


    //로그인 유지 확인 메소드
    private void checkLogin(){
        //설정값 불러오기
        appData=getSharedPreferences("appData",MODE_PRIVATE);
        load();

        //이전에 로그인 정보를 저장시킨 기록이 있다면
        if(saveLoginData){
            edit_id.setText(id);
            edit_pw.setText(pwd);
            autosave.setChecked(saveLoginData);
        }
    }


    //설정값을 저장하는 함수
    private void save(){
        //SharedPreferences 객체만으로 저장 불가능 Editor사용
        SharedPreferences.Editor editor=appData.edit();
        //에디터 객체 .put타입 (저장시킬 이름 ,저장시킬 값)
        //저장시킬 이름이 이미 존재하면 덮어 씌움
        editor.putBoolean("SAVE_LOGIN_DATA",autosave.isChecked());
        editor.putString("ID",edit_id.getText().toString().trim());
        editor.putString("PWD",edit_pw.getText().toString().trim());

        //apply, commit을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    //설정값을 불러오는 함수
    private void load(){
        //SharedPreferences객체 .get타입 (저장된 이름, 기본 값)
        //저장된 이름이 이미 존재하지 않을 시 기본 값

        saveLoginData=appData.getBoolean("SAVE_LOGIN_DATA",false);
        id=appData.getString("ID","");
        pwd=appData.getString("PWD","");
    }
}
