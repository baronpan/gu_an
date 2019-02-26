package com.example.xreftest;

import android.os.AsyncTask;
import android.os.Handler;

public class SubTestClass extends TestClass {
    public SubTestClass() {
        mHandler = new TestHandler();
        mThread = new TestThread();
        mTask = new TestTask();
    }

    @Override
    protected void subcall() {
        super.subcall();
        mHandler.sendEmptyMessage(0);
    }

    public Handler getHandler() {
        return (Handler)this.mHandler;
    }

    public void startThread() {
        mThread.start();
    }

    public void startTask() {
        mTask.execute();
    }

    private TestHandler mHandler;
    private Thread mThread;
    private AsyncTask mTask;
}
