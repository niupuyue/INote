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
        // 初始化leakCanary
//        initLeakCanary(this);
    }

    public void initLeakCanary(Application app) {
//        if (LeakCanary.isInAnalyzerProcess(app)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(app);
    }
}
