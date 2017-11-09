package com.example.lifecycle;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final static String SAVE_STRING = "Save TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            Log.d("TEST", savedInstanceState.getString(SAVE_STRING));
        }

        Log.d("TEST", "onCreate.");
        Log.d("TEST", "MainActivity: Task " + getTaskId());
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("TEST", "onStart.");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TEST", "onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("TEST", "onPause.");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("TEST", SAVE_STRING);

        outState.putString("SAVE_TEXTVIEW", ((TextView)findViewById(R.id.textview)).getText().toString());
        Log.d("TEST", "onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("TEST", "onStop.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("TEST", "onRestart.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("TEST", "onDestroy.");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        Log.d("TEST", "onRestoreInstanceState");
    }

    public void NewActivityClick(View view) {
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }

    public void CallClick(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://111"));
        startActivityForResult(intent, 1);
    }

    public void FinishClick(View view) {
        this.finish();
    }

    public void StartItselfClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS);
        startActivity(intent);
    }

    public void StartSingleTopClick(View view) {
        Intent intent = new Intent(this, SingleTopActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Log.d("TEST", "Return from Call");
        }
    }

    public void NewRecentTaskClick(View view) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void FinishAndRemoveRecentClick(View view) {
        finishAndRemoveTask();
    }
}
