package com.example.datastorage;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JSInterface {
    @JavascriptInterface
    public void test() {
        Log.d("Test", "JS execute");
    }

    @JavascriptInterface
    public void printObject(Object obj) {
        if (obj != null) {
            Log.d("Test", obj.toString());
        }
    }

    @JavascriptInterface
    public void printString(String str) {
        Log.d("Test", str);
    }
}
