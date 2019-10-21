package com.example.weakcomponent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class BrowserActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("Test", "BrowserActivity start.");

        super.onCreate(savedInstanceState);
    }
}
