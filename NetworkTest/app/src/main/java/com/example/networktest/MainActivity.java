package com.example.networktest;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testSSL();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void testSSL() throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException {
        //URL url = new URL("https://192.168.2.1:4443/test.html");
        URL url = new URL("https://192.168.31.160:4443/test.html");

        HttpsURLConnection sconn = (HttpsURLConnection)url.openConnection();

        InputStream pemfile = getApplicationContext().getResources().openRawResource(R.raw.test);
        if (pemfile == null) {
            Log.d("Test", "pem empty.");
        }

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try {
            ca = cf.generateCertificate(pemfile);
            Log.d("Test", "ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            pemfile.close();
        }
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("ca", ca);
        sconn.setSSLSocketFactory(new WeakSSLSocketFactory(ks));
        sconn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        InputStream in = sconn.getInputStream();

        byte[] buffer = new byte[4096];
        int readbytes;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (-1 != (readbytes = in.read(buffer))) {
            out.write(buffer, 0, readbytes);
        }
        Log.d("Test", out.toString());
    }
}
