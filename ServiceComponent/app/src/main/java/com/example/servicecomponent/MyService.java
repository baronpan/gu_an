package com.example.servicecomponent;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class MyService extends Service {

    public MyService() {

    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    private final IBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TEST", "MyService onBind.");
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("TEST", "MyService onCreate.");
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TEST", "MyService onStartCommand.");

        // start foreground
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("This is Foreground Service")
                .setContentText("Foregrounded")
                .setContentIntent(pendingIntent)
                .setTicker("Ticker")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        startForeground(111, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TEST", "MyService onDestroy.");
    }

    public void testToCall() {
        Log.d("TEST", "MyService testToCall.");
    }
}
