package com.example.sound_mainpage;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import static android.content.Context.AUDIO_SERVICE;

public class MyListAdapter extends BaseAdapter {
    //배열 혹은 ArrayList에 item들을 담아서 순서대로 화면에 표시해준다
    Context context;
    ArrayList<list_item> list_items;

    //소리관련
    AudioManager audio;
    SoundPool  soundPool=new SoundPool(1,AudioManager.STREAM_MUSIC,0);

    @Override
    public void notifyDataSetChanged() {
        //데이터 바뀜
        super.notifyDataSetChanged();
    }


    //getView에서 쓸 textView를 선언해준다
    Button btn;
    SeekBar seek;

    //생성자 생성
    public MyListAdapter(Context context, ArrayList<list_item> list_items) {
        this.context = context;
        this.list_items = list_items;
        Log.i("컨텍스트",context+"받음");

    }
    //삭제하기
    public void removeself(int position){
        list_items.remove(position);
    }

    @Override
    public int getCount() {
        return this.list_items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView=
                    LayoutInflater.from(context).inflate(R.layout.list_item,null);
                    btn=convertView.findViewById(R.id.list_btn);
                    seek=convertView.findViewById(R.id.list_seek);

                     //소리 설정
                    volumSet();
           // Log.i("시크바",Integer.toString(list_items.get(position).getSeek_bar()));

        }
        //이게 있어야 눌림
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context,"삭제",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        btn.setText(list_items.get(position).getBtn_name());
        seek.setProgress(list_items.get(position).getSeek_bar());


        //버튼이 눌리면 그 소리로 설정
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //소리지정
                int nowVolume= 0;
                nowVolume=list_items.get(position).getSeek_bar();//해당 시크바의 소리를 가져와

               // Log.i("버튼 소리23",position+"");
              //  Log.i("버튼 소리",list_items.get(position).getSeek_bar()/6+"");

                //현재의 소리값 가져와서 버튼 누를시 소리 설정하기
                audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                        nowVolume,0);
                //소리도 듣기
                final int sound = soundPool.load(context, R.raw.ping, 1);
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(sound,1,1,0,0,1);
                    }
                });
               // Log.i("뭐라고",position+"");
            }
        });

        //소리관련
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //드래그 하면 발생

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //   탭하는 순간 onStartTrackingTouch() 발생
                //늘거나 주는 사운드소리
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //드래그 놓으면 발생
                //최종 사운드 소리
                //현재의 사운드 나오게
                int progress =seekBar.getProgress();
                audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress,0);
                final int sound = soundPool.load(context, R.raw.ping, 1);
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(sound,1,1,0,0,1);
                    }
                });

                list_items.get(position).setSeek_bar(progress);//시크바가 바뀌면 item값도 바꾸기
            }
        });

        return convertView;

    }
    //최대 볼륨과 현재 볼륨 지정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private  void volumSet(){
        audio= (AudioManager) context.getSystemService(AUDIO_SERVICE);// 매니저들은 get으로 가져옴
        int max=audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //음악타입의 맥스볼륨가져오기
        int CurrentVolum= audio.getStreamVolume(AudioManager.STREAM_MUSIC); //음악타입의 현재 볼륨

        //시크바의 소리 조정하기
        seek.setMax(max);
        seek.setProgress(CurrentVolum);

    }

}
