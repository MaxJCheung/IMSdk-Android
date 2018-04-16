package com.tencent.qcloud.presentation;

import android.app.Application;
import android.content.Context;



/**
 * 全局Application
 */
public class ImApplication2  {

    private static Context context;
    private static ImApplication2 instance;

    public static ImApplication2 getInstance() {
        if(instance == null)
        {
            instance = new ImApplication2();
        }
        return instance;
    }

    public void init(Application app) {
        this.context = app.getApplicationContext();
    }


    public static Context getContext() {
        return context;
    }


}
