package com.example.xreftest;

import android.os.Handler;
import android.os.Message;

public class TestHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case 0:
                break;
            case 1:
                break;
        }
    }
}
