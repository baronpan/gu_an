package com.example.datastorage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //internalStorage();
        //externalStorage();
        //sharedPrefs();
        //testDB();
        //testKeystore();
        testWebview();
    }

    public void internalStorage() {
        File internaldir = getFilesDir();

        Log.d("Test", internaldir.getAbsolutePath());

        File internalcache = getCacheDir();
        Log.d("Test", internalcache.getAbsolutePath());

        try {
            FileOutputStream outputStream = openFileOutput("Test", Context.MODE_PRIVATE);
            outputStream.write("TestTest".getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File.createTempFile("test001", "test002");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    public void externalStorage() {
        File externaldir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        Log.d("Test", externaldir.getAbsolutePath());

        File externalcache = getExternalCacheDir();
        Log.d("Test", externalcache.getAbsolutePath());



        requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);


    }

    public void recursedir(File path) {
        File[] list = path.listFiles();

        for (File file: list) {
            if (file.isDirectory()) {
                Log.d("Test", "Dir: " + file);
                recursedir(file);
            } else {
                Log.d("Test", "File: " + file);
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File extstoragedir = Environment.getExternalStorageDirectory();
            Log.d("Test", extstoragedir.getAbsolutePath());
            recursedir(extstoragedir);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void sharedPrefs() {
        SharedPreferences sharedPref = getSharedPreferences("Test", Context.MODE_PRIVATE);

        String value = sharedPref.getString("TestKey", "Default");
        Log.d("Test", value);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TestKey", "TestValue");
        editor.commit();

        value = sharedPref.getString("TestKey", "Default");
        Log.d("Test", value);
    }

    public void testDB() {
        ContentValues values = new ContentValues();
        values.put("passvalue", "value1");
        Uri result = getContentResolver().insert(Uri.parse("content://com.example.datastorage.TestProvider/Passcode"), values);
        if (result != null) {
            Log.d("Test", "insert success: " + result.toString());
        }

        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.datastorage.TestProvider/Passcode"),
                new String[] {"_ID", "passvalue"}, " _ID <= 4 ", null, "_ID DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Log.d("Test", cursor.getString(0) + ": " + cursor.getString(1));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NewApi")
    public void testKeystore() {
        try {
            Log.d("Test", "Default type: " + KeyStore.getDefaultType());

            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            Enumeration<String> aliases = ks.aliases();
            for (Enumeration<String> e = aliases; e.hasMoreElements();)
                Log.d("Test", e.nextElement());

            final KeyGenerator kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            kg.init(new KeyGenParameterSpec.Builder("key1", KeyProperties.PURPOSE_ENCRYPT|KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            SecretKey sk = kg.generateKey();


            ks.load(null);
            KeyStore.Entry ke = ks.getEntry("key1", null);
            if (ke instanceof KeyStore.SecretKeyEntry) {
                Log.d("Test", "Found secret key");
            }

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, ((KeyStore.SecretKeyEntry)ke).getSecretKey() );
            byte[] iv = cipher.getIV();
            Log.d("Test", Base64.encodeToString(iv, Base64.DEFAULT));
            byte[] enc_bytes = cipher.doFinal("This is plaintext.".getBytes("UTF-8"));
            Log.d("Test", Base64.encodeToString(enc_bytes, Base64.DEFAULT));

            cipher.init(Cipher.DECRYPT_MODE, ((KeyStore.SecretKeyEntry)ke).getSecretKey(), new IvParameterSpec(iv));
            byte[] dec_bytes = cipher.doFinal(enc_bytes);
            Log.d("Test", new String(dec_bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testWebview() {
        final WebView webview = (WebView)findViewById(R.id.Webview);
        Button button = (Button)findViewById(R.id.button2);
        final TextView textview = (TextView)findViewById(R.id.editText3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Test", "load: " + textview.getText().toString());
                WebSettings websettings = webview.getSettings();
                websettings.setJavaScriptEnabled(true);
                webview.addJavascriptInterface(new JSInterface(), "testobject");
                webview.loadUrl(textview.getText().toString());
            }
        });
    }
}
