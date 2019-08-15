package com.paulniu.inote;

import android.app.Application;
import android.content.Context;

import com.niupuyue.mylibrary.utils.LibraryConstants;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 15:41
 * Desc:
 * Version:
 */
public class App extends Application {

    public static Application getApplication() {
        return getApplication();
    }

    public static Context getContext() {
        return getContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LibraryConstants.setContext(this);
    }
}
