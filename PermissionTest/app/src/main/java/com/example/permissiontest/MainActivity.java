package com.example.permissiontest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter("com.example.permissiontest.action1");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        getApplicationContext().registerReceiver(mReceiver, intentFilter,
                "com.example.permissiontest.Permission3", null);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TEST", "Dynamic Receiver start.");
        }
    };

    public void NoPermActivityClick(View view) {
        Intent intent = new Intent(this, NoPermActivity.class);
        startActivity(intent);
    }

    public void PermActivityClick(View view) {
        Intent intent = new Intent(this, PermActivity.class);
        startActivity(intent);
    }

    public void PermReceiverClick(View view) {
        Intent intent = new Intent(this, PermReceiver.class);
        sendBroadcast(intent);
    }

    public void DynamicReceiverClick(View view) {
        Intent intent = new Intent("com.example.permissiontest.action1");
        sendBroadcast(intent);
    }

    public void GrantUriPermClick(View view) {
        Intent intent = new Intent();

        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TEST", "result: " + resultCode);
        if (data != null) {
            Log.d("TEST", data.getDataString());
        } else {
            Log.d("TEST", "data is null");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void DangerActivityClick(View view) {
        Intent intent = new Intent(this, DangerActivity.class);
        startActivity(intent);
    }
}
