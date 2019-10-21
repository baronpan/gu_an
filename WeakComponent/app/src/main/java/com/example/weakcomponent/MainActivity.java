package com.example.weakcomponent;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter("android.intent.action.USER_PRESENT");
        //IntentFilter filter = new IntentFilter("com.example.weakcomponent.intent");
        Context ctx = getApplicationContext();

        ctx.registerReceiver(new DynamicReceiver(), filter);

        //IntentFilter filter2 = new IntentFilter("com.example.weakcomponent.intent");
        ctx.registerReceiver(new DynamicPermReceiver(), filter, "com.example.weakcomponent.perm", null);

        Intent intent = new Intent();
        intent.setAction("com.example.weakcomponent.intent");
        ctx.sendBroadcast(intent, "com.example.weakcomponent.perm");
        //ctx.sendBroadcast(intent);

        //Intent intent2 = new Intent();
        //intent.setAction("android.intent.action.BOOT_COMPLETED");
        //ctx.sendBroadcast(intent);

        this.mClipManager = (ClipboardManager)this.getSystemService(CLIPBOARD_SERVICE);
        this.mClipManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                Log.d("Test", "ClipText: " + mClipManager.getPrimaryClip().getItemAt(0).getText().toString());
            }
        });
    }

    private ClipboardManager mClipManager;
}
