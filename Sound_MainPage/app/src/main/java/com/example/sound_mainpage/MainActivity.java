package com.example.sound_mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectListener());

    }
    class ItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.mode:
                    Intent intent= new Intent(getApplicationContext(),Mode_Activity.class);
                    startActivity(intent);
                    break;
                case R.id.home:
                    Intent intent2= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.user:
                    Intent intent3= new Intent(getApplicationContext(),User_Activity.class);
                    startActivity(intent3);
                    break;
            }

            return true;
        }
    }

}
