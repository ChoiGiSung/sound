package com.example.apitest;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private  static BroadcastReceiver broadcastReceiver=null;
    private  static IntentFilter filter=new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("서비스","dsa");

        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action=intent.getAction();
                Log.i("리스버",action);


                if(action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)){
                    final int state=intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE,BluetoothAdapter.ERROR);

                    switch (state){
                        case BluetoothAdapter.STATE_DISCONNECTED:
                            Toast.makeText(getApplicationContext(),"꺼짐",Toast.LENGTH_SHORT).show();
                            Log.i("리스버","꺼짐");
                            break;
                        case BluetoothAdapter.STATE_CONNECTED:
                            Toast.makeText(getApplicationContext(),"켜짐",Toast.LENGTH_SHORT).show();
                            Log.i("리스버","켜짐");
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Toast.makeText(getApplicationContext(),"꺼짐",Toast.LENGTH_SHORT).show();
                            Log.i("리스버","꺼짐");
                            break;
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Toast.makeText(getApplicationContext(),"켜짐",Toast.LENGTH_SHORT).show();
                            Log.i("리스버","켜짐");
                            break;
                    }
                }

            }
        };
        registerReceiver(broadcastReceiver,filter);
    }
}
