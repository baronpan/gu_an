package com.example.applifecycle;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by j1gsaw on 2017/11/6.
 */

public class MyClassLoader extends ClassLoader {
    public void setBaseClassLoader(ClassLoader base) {
        mBase = base;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Log.d("Test", "MyClassLoader findClass " + name);

        Class<?> cClassLoader = Class.forName("ClassLoader");
        try {
            Method m_findClass = cClassLoader.getDeclaredMethod("findClass", String.class);
            return (Class<?>)m_findClass.invoke(mBase, name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Log.d("Test", "MyClassLoader loadClass " + name);
        return mBase.loadClass(name);
    }

    public ClassLoader mBase;
}
