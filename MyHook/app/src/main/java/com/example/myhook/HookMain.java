package com.example.myhook;

import android.app.PendingIntent;
import android.view.textclassifier.TextClassification;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.setIntField;

public class HookMain implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        //XposedBridge.log("Loaded app: " + lpparam.packageName);

        if (lpparam.packageName.equals("com.example.myhook")) {
            findAndHookMethod("android.telephony.SmsManager", lpparam.classLoader, "sendTextMessage",
                    String.class,
                    String.class,
                    String.class,
                    PendingIntent.class,
                    PendingIntent.class,
                    new XC_MethodHook() {
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("before send");
                            Object[] args = param.args;
                            XposedBridge.log(args[0] + ": " + args[2]);
                        }

                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("after send");
                        }
                    });

            findAndHookConstructor("com.example.myhook.TestClass", lpparam.classLoader, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("TestClass constructor hooked.");
                }
            });


            findAndHookMethod("com.example.myhook.TestClass", lpparam.classLoader, "setA", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    Object ins = param.thisObject;
                    setIntField(ins, "a", 222);
                    return null;
                }
            });
        }
    }
}
