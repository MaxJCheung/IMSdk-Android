package com.tencent.qcloud.timchat;

import android.app.Application;
import android.content.Context;


/**
 * 全局Application
 */
public class ImApplication {


    private static ImApplication instance;
    private static Context context;
    private Application app;

    public static ImApplication getInstance() {
        if(instance == null)
        {
            instance = new ImApplication();
        }
        return instance;
    }

    public void init(Application app) {
        this.context = app.getApplicationContext();
        this.app = app;
        init();
    }


    public void init() {


    }

    public static Context getContext() {
        return context;
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }
}
