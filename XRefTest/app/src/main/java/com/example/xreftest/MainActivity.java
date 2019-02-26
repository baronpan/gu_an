package com.example.xreftest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestClass testcls = new TestClass();
        testcls.test();

        TestClass sub_testcls = (TestClass)new SubTestClass();
        sub_testcls.subcall();

        ITest icls = (ITest)new InterfaceClass();
        icls.testInterface();

        SubTestClass orig_subcls = (SubTestClass)sub_testcls;
        orig_subcls.getHandler().sendEmptyMessage(1);
        orig_subcls.startThread();
        orig_subcls.startTask();
    }
}
