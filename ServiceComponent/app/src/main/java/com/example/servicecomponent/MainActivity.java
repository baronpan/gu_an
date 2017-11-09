package com.example.servicecomponent;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Message message = Message.obtain();
        message.what = 1;
        message.obj = null;
        mHandler.sendMessage(message);
    }

    private final TestHandler mHandler = new TestHandler();

    class TestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public void startServiceClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MyService.class);
        startService(intent);
    }

    public void stopServiceClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MyService.class);
        stopService(intent);
    }


    public void startForegroundClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MyService.class);
        startService(intent);
    }

    private boolean mBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            MyService.MyBinder binder = (MyService.MyBinder)service;
            binder.getService().testToCall();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("TEST", "onServiceDisconnected");
            mBound = false;
        }
    };

    public void boundClick(View view) {
        Intent intent = new Intent(this, MyService.class);
        if (!mBound) {
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            ((Button)view.findViewById(R.id.button4)).setText("Unbound Service");
        } else {
            unbindService(mConnection);
            ((Button)view.findViewById(R.id.button4)).setText("Bound Service");
            mBound = false;
        }
    }

    private ServiceConnection mAIDLConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                IMyAidlInterface.Stub.asInterface(service).basicTypes(0,0,true,1,1,"aa");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("TEST", "onServiceDisconnected");
            mBound = false;
        }
    };

    public void AIDLServiceClick(View view) {
        Intent intent = new Intent(this, MyAIDLService.class);
        bindService(intent, mAIDLConnection, Context.BIND_AUTO_CREATE);
    }
}
