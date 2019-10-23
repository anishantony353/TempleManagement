package com.saarit.temple_management.templemanagement.util;

import android.util.Log;

public class Utility {

    private static boolean isLogEnabled = true;


    public static void log(String TAG,String msg){
        if(isLogEnabled){
            Log.i(TAG,msg);
        }
    }
}
