package com.example.applifecycle;

import android.app.Application;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by j1gsaw on 2017/10/12.
 */

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        Log.d("Test", "MyApplication attachBaseContext called.");
        if (base != null) {
            Log.d("Test", "base is not null.");
        }

        //Thread.dumpStack();

        try {
            final Class<?> cActivityThread = Class.forName("android.app.ActivityThread");

            final Method m_currentActivityThread = cActivityThread.getDeclaredMethod("currentActivityThread");
            Object obj_currentActivityThread = m_currentActivityThread.invoke(null);

            Log.d("Test", "currentActivityThread is " + (obj_currentActivityThread == null ? "null":"not null"));

            final Method m_currentPackgeName = cActivityThread.getDeclaredMethod("currentPackageName");
            Log.d("Test", "currentPackageName is " + m_currentPackgeName.invoke(null));

            final Method m_currentProcessName = cActivityThread.getDeclaredMethod("currentProcessName");
            Log.d("Test", "currentProcessName is " + m_currentProcessName.invoke(null));

            final Method m_currentApplication = cActivityThread.getDeclaredMethod("currentApplication");
            Object obj_currentApplication = m_currentApplication.invoke(null);
            Log.d("Test", "currentApplication is " + (obj_currentApplication == null ? "null":"not null"));

            final Field m_Packages = cActivityThread.getDeclaredField("mPackages");
            m_Packages.setAccessible(true);
            Object obj_mPackages = m_Packages.get(obj_currentActivityThread);

            if (obj_mPackages != null) {
                Log.d("Test", "mPackages:");
                ArrayMap<String, WeakReference<Object>> obj_mPackages_raw = (ArrayMap<String, WeakReference<Object>>)(obj_mPackages);
                for (String i: obj_mPackages_raw.keySet()) {
                    Log.d("Test", "\t" + i);
                }

                final Class<?> cLoadedApk = Class.forName("android.app.LoadedApk");
                Object obj_LoadedApk = ((WeakReference<Object>)obj_mPackages_raw.valueAt(0)).get();
                Field m_ClassLoader = cLoadedApk.getDeclaredField("mClassLoader");
                m_ClassLoader.setAccessible(true);
                MyClassLoader mLoader = new MyClassLoader();
                mLoader.setBaseClassLoader((ClassLoader)m_ClassLoader.get(obj_LoadedApk));
                m_ClassLoader.set(obj_LoadedApk, mLoader);

                Field m_Application = cLoadedApk.getDeclaredField("mApplication");
                m_Application.setAccessible(true);
                Log.d("Test", "LoadedApk mApplication is " + (m_Application.get(obj_LoadedApk) == null ? "null":"not null"));
            } else {
                Log.d("Test", "mPackages is null");
            }

            final Field m_ResourcePackages = cActivityThread.getDeclaredField("mResourcePackages");
            m_ResourcePackages.setAccessible(true);
            Object obj_mResourcePackages = m_ResourcePackages.get(obj_currentActivityThread);
            if (obj_mResourcePackages != null) {
                Log.d("Test", "mResourcePackages:");
                ArrayMap<String, WeakReference<Object>> obj_mResourcePackages_raw = (ArrayMap<String, WeakReference<Object>>)(obj_mResourcePackages);
                for (String i: obj_mResourcePackages_raw.keySet()) {
                    Log.d("Test", "\t" + i);
                }
            } else {
                Log.d("Test", "mResourcePackages is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        Log.d("Test", "MyApplication onCreate called.");

        //Thread.dumpStack();

        super.onCreate();
    }
}
