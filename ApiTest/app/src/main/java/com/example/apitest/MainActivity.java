package com.example.apitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class MainActivity extends AppCompatActivity {
    Button button;
    TextView text;
    BootstrapButton BootstrapButton;
    VideoView yotube;

    //마이크
    private MediaRecorder recorder = null;
    private static String fileName = "/dev/null";
    private double mEMA = 0.0;
    private double EMA_FILTER = 1.0;
    Handler handler = new Handler() ;


    private String url = "http://14.55.237.125:8080";
    private MyApi myApi;
    private static BroadcastReceiver broadcastReceiver = null;
    private static IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
//    PlayerView pv;
//    SimpleExoPlayer player;
//    Uri videoUri=Uri.parse("https://youtu.be/sU06MD6NoDs?t=110");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn1);
        BootstrapButton = findViewById(R.id.ss);
        text = findViewById(R.id.text);
//        yotube=findViewById(R.id.yotube);
        Log.i("d메인d", "dsa");

        //권한얻기
         checkDangerousPermissions();

//

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
                pollTask.run();
            }
        });
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }


        int db = (int) getAmplitudeEMA();


    }

    private Runnable pollTask = new Runnable() {
        @Override
        public void run() {
            double amplitude = getAmplitude();
            text.setText("Amplitude: " + amplitude);

            handler.postDelayed(pollTask, 500);
        }
    };

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            Log.e("녹음 시작", "dd");
        } catch (IOException e) {
            Log.e("녹음 시작", "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }


    //진폭얻기 계산
    public double getAmplitude() {
        if (recorder != null) {
            return 20 * Math.log10(recorder.getMaxAmplitude() / 16.0);
        } else {
            return 0;
        }
    }

    //데시벨 계산
    public double getAmplitudeEMA() {
        double amp = getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) + mEMA;
        return mEMA;
    }

    //권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.RECORD_AUDIO
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }

        }
    }
}


















//        broadcastReceiver=new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                final String action=intent.getAction();
//                Log.i("리스버",action);
//
//
//                    final int state=intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE,BluetoothAdapter.ERROR);
//
//                    switch (state){
//                        case BluetoothAdapter.STATE_DISCONNECTED:
//                            Toast.makeText(getApplicationContext(),"꺼짐",Toast.LENGTH_SHORT).show();
//                            Log.i("리스버","꺼짐");
//                            break;
//                        case BluetoothAdapter.STATE_CONNECTED:
//                            Toast.makeText(getApplicationContext(),"켜짐",Toast.LENGTH_SHORT).show();
//                            Log.i("리스버","켜짐");
//                            break;
//                        case BluetoothAdapter.STATE_TURNING_OFF:
//                            Toast.makeText(getApplicationContext(),"꺼짐",Toast.LENGTH_SHORT).show();
//                            Log.i("리스버","꺼짐");
//                            break;
//                        case BluetoothAdapter.STATE_TURNING_ON:
//                            Toast.makeText(getApplicationContext(),"켜짐",Toast.LENGTH_SHORT).show();
//                            Log.i("리스버","켜짐");
//                            break;
//                    }
//                }
//
//        };
//        registerReceiver(broadcastReceiver,filter);






//        Resources res=getResources();
//        int id_video=res.getIdentifier("test","raw",getPackageName());
//

//        initMyApi(url);
//        //비디오 관련
//        Uri videoUri=Uri.parse("android.resource://com.example.test/"+id_video);
//        yotube.setVideoURI(videoUri);
//        yotube.start();
//



        //TypefaceProvider.registerDefaultIconSets();

//        Intent intent=new Intent(getApplicationContext(), service.class);
//       // startService(intent);
//        BootstrapButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"dd",Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                try {
////                    String result=new CostomTask().execute().get();
////                    text.setText(result);
////                } catch (Exception e) { }
//
//                Call<DataDto> getCall = myApi.get_data();
//                getCall.enqueue(new Callback<DataDto>() {
//                    @Override
//                    public void onResponse(Call<DataDto> call, Response<DataDto> response) {
//                        DataDto data=response.body();
//                        String result=data.getData().get(0).getUser_id();
//                        text.setText(result);
//                        BootstrapButton.setText("ddd");
//                        Toast.makeText(getApplicationContext(),"dd",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<DataDto> call, Throwable t) {
//
//                    }
//                });
//
//            }
//        });



//
//    private void initMyApi(String url){
//        //retorfit 객체를 생성하고 이 객체를 이용해서 api service를 create 해준다
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        myApi = retrofit.create(MyApi.class);
//    }

