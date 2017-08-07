package com.dzy.commemlib.utils;

import android.util.Log;

/**
 * Created by dzysg on 2017/4/2 0002.
 */

public class LogUtils {


    public static void e(String tag,String msg){
        Log.e(tag,msg);
    }

    public static void e(String tag,String msg,Throwable t){
        Log.e(tag,msg,t);
    }

    public static void d(String tag,String msg){
        Log.d(tag,msg);
    }

    public static void i(String tag,String msg){
        Log.i(tag,msg);
    }
}
