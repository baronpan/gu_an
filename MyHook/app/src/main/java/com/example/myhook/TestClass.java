package com.example.myhook;

import android.util.Log;

public class TestClass {
    public TestClass() {
        Log.d("MyHook", "TestClass Constructor");
        setA();
    }

    private int a;

    public int getA() { return this.a; }
    private void setA() { this.a = 111; }
    public void psetA() { this.setA(); }
}
