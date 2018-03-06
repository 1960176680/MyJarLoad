package com.zhouwenguang.hz.myjarload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
                 String jarPath = sdcard + "/Jar/dynamic_dex.jar";
                 String tmpPath = getApplicationContext().getDir("Jar", 0).getAbsolutePath();
                 DexClassLoader cl = new DexClassLoader(jarPath, jarPath
                         , null, this.getClass().getClassLoader());
                Class<?> libProviderCls = null;
                 try {
                     libProviderCls = cl.loadClass("com.example.RoutePlanManager");
                     Constructor<?> localConstructor = libProviderCls.getConstructor(new Class[] {});
                     Object obj = localConstructor.newInstance(new Object[] {});
                     Method mMethodWrite = libProviderCls.getDeclaredMethod("get");
                     mMethodWrite.setAccessible(true);
                     String str = (String) mMethodWrite.invoke(obj);
                     Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         });




    }

}
