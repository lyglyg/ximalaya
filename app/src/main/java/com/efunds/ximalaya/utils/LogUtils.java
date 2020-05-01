package com.efunds.ximalaya.utils;

import android.util.Log;

/**
 * log工具类
 */
public class LogUtils {
    private static String sTag = "LogUtils";
    public static boolean sIsRealease = false;

    /**
     * log初始化方法
     * @param tag
     * @param isRealease
     */
    public static void init(String tag,boolean isRealease){
        LogUtils.sTag = tag;
        sIsRealease = isRealease;
    }

    public static void v(String tag ,String content){
        if(!sIsRealease){
            Log.v("["+sTag+"]"+tag,content);
        }
    }
    public static void d(String tag,String content){
        if(!sIsRealease){
            Log.d("["+sTag+"]"+tag,content);
        }
    }
    public static void i(String tag,String content){
        if(!sIsRealease){
            Log.i("["+sTag+"]"+tag,content);
        }
    }

    public static void w(String tag,String content){
        if(!sIsRealease){
            Log.w("["+sTag+"]"+tag,content);
        }
    }

    public static void e(String tag,String content){
        if(!sIsRealease){
            Log.e("["+sTag+"]"+tag,content);
        }
    }
}
