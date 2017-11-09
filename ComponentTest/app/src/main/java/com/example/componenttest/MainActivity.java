package com.example.componenttest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenBaiduClick(View view) {
        Uri url = Uri.parse("http://baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, url);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void OnCallClick(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:111"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void PermActivityClick(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.example.permissiontest", "com.example.permissiontest.PermActivity");
        startActivity(intent);
    }

    public void PermReceiverClick(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.example.permissiontest", "com.example.permissiontest.PermReceiver");
        sendBroadcast(intent);
    }

    public void DynamicReceiverClick(View view) {
        Intent intent = new Intent("com.example.permissiontest.action1");
        sendBroadcast(intent, "com.example.permissiontest.Permission3");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void DangerActivityClick(View view) {
        if (checkSelfPermission("com.example.permissiontest.Permission4") != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{"com.example.permissiontest.Permission4"}, 0);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 0: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setClassName("com.example.permissiontest", "com.example.permissiontest.DangerActivity");

                    startActivity(intent);

                }

            }
        }
    }
}
