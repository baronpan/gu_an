package com.example.weakcomponent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class IntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        Log.d("Test", "IntentActivity is run.");
    }
}
