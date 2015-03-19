package com.example.lexy.materialtest.materialtest;

import android.app.Application;
import android.content.Context;


public class MyApp extends Application {

    public static final String API_KEY = "a94ac164a19a3e2c8c2c7b406d36866b746e7130";
    private static MyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static  MyApp getInstance () {

        return sInstance;

    }

    public static Context getAppContext () {

        return sInstance.getApplicationContext();

    }

}
