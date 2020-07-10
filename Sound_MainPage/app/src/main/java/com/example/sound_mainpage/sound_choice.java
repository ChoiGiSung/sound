package com.example.sound_mainpage;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sound_choice extends AppCompatActivity {

    SoundPool soundPool;
    CheckBox c1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_choice_layout);

        Gallery gallery= findViewById(R.id.gallery);
        MyAdapter myAdapter =new MyAdapter(this);
        gallery.setAdapter(myAdapter);
        c1=findViewById(R.id.ch_choice);

    }

    public class MyAdapter extends BaseAdapter{
        Context context;
        Integer [] age_picture = {R.mipmap.baby,R.mipmap.teens,R.mipmap.twenties,
                R.mipmap.thirties,R.mipmap.fifteen,R.mipmap.sixties,R.mipmap.seventeen};
        String [] age_name ={"영유아","십대","이십대","삼십대","오십대","육십대","칠십대"};

        public MyAdapter(Context context){
            this.context=context;
        }

        @Override
        public int getCount() {
            return age_picture.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(age_picture[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(400,450));


            final  int pos =position;
            //갤러리에서 이미지가 클릭되면


            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    TextView txt_age = findViewById(R.id.txt_age);
                    txt_age.setText(age_name[pos]);


                    final Button button =findViewById(R.id.btn_choice);
                    final  Button goTest=findViewById(R.id.goTest);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (pos){
                                case 0:
                                    sound_load(R.raw.sound_17742);
                                    button.setText("십대");
                                    break;
                                case 1:
                                    sound_load(R.raw.sound_16746);
                                    break;
                                case 2:
                                    sound_load(R.raw.sound_15800);
                                    break;
                                case 3:
                                    sound_load(R.raw.sound_14900);
                                    break;
                                case 4:
                                    sound_load(R.raw.sound_14100);
                                    break;
                                case 5:
                                    sound_load(R.raw.sound12000);
                                    break;
                                case 6:
                                    sound_load(R.raw.sound10000);
                                    break;
                                case 7:
                                    sound_load(R.raw.sound1);
                                    break;
                            }
                        }
                    });

                    goTest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goSound(pos);
                        }
                    });


                     return false;
                }
            });

            return imageView;
        }

        private void sound_load(int raw){
            soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
            final int s3=soundPool.load(context,raw,1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(s3,1,1,0,1,1);
                }
            });
        }
        public void goSound(int position){
            Intent intent=new Intent(getApplicationContext(),SoundTest.class);
            int i=position;
            if(c1.isChecked()){
                intent.putExtra("chec",i);
                startActivity(intent);
            }

        }
    }
}
