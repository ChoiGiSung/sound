package com.example.sound_mainpage.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sound_mainpage.Api.ApiService;
import com.example.sound_mainpage.Api.ApiDto.DataDto;
import com.example.sound_mainpage.R;

public class JoinActivity extends AppCompatActivity {
    EditText joinID,joinPWD;
    Button btnJoin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_page);
        init();
        btnJoin.setOnClickListener(listener);


    }
    private void init(){
        joinID=findViewById(R.id.join_id);
        joinPWD=findViewById(R.id.join_pwd);
        btnJoin=findViewById(R.id.btn_join);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String joinId = joinID.getText().toString();
            String joinPwd = joinPWD.getText().toString();

            try {
                if(joinId !=null && joinPwd!=null){
                    DataDto resultDto = new ApiService().execute("join", joinId, joinPwd).get();
                    String result=resultDto.getJoinresult();
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                    if(result.equals("이미있는회원")){
                        Toast.makeText(getApplicationContext(),"이미 존재하는 아이디",Toast.LENGTH_SHORT).show();
                        joinID.setText("");
                        joinPWD.setText("");
                    }else if(result.equals("회원가입완료")){
                        joinID.setText("");
                        joinPWD.setText("");
                        Toast.makeText(getApplicationContext(),"회원가입 되었습니다",Toast.LENGTH_SHORT);
                        finish();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"아이디나 비번을 입력하세요",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
