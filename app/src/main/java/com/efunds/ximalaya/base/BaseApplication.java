package com.efunds.ximalaya.base;

import android.app.Application;

import com.efunds.ximalaya.utils.LogUtils;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.init(this.getPackageName(),false);
    }
}
