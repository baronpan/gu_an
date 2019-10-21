package com.example.weakcomponent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ImplicitService extends Service {
    public ImplicitService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
