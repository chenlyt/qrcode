package com.cl.qrcode.activity;

import android.app.Application;
public class BaseApplication extends Application {
    private static BaseApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static BaseApplication getApp() {
        return app;
    }
}