package com.example.myhook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager mgr = SmsManager.getDefault();
                mgr.sendTextMessage("111", "111", "aaa", null, null);
            }
        });

        TestClass tc = new TestClass();
        Log.d("MyHook", "Field a is " + tc.getA());
        tc.psetA();
        Log.d("MyHook", "Set field, now a is " + tc.getA());

        releaseSubstrate();
        openMemmap();
        //startHook();
        openMemmap();
    }

    public void releaseSubstrate() {
        String libname = "libsubstrate.so";
        try {
            InputStream istream = this.getAssets().open(libname);
            String libpath = this.getFilesDir().getAbsolutePath() + File.separator + libname;
            File file = new File(libpath);
            FileOutputStream fstream = new FileOutputStream(file);
            int len = -1;
            byte[] buffer = new byte[2048];
            while ((len = istream.read(buffer)) != -1) {
                fstream.write(buffer, 0, len);
            }
            fstream.flush();
            istream.close();
            fstream.close();
            Log.d("MyHook", libname + " released to " + libpath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native void openMemmap();

    public native void startHook();
}
