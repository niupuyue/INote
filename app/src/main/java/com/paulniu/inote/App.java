package com.paulniu.inote;

import android.app.Application;
import android.content.Context;

import com.niupuyue.mylibrary.utils.LibraryConstants;
//import com.squareup.leakcanary.LeakCanary;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 15:41
 * Desc:
 * Version:
 */
public class App extends Application {

    private static Context context;
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        application = this;
        LibraryConstants.setContext(this);

    }

}
