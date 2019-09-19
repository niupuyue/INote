package com.paulniu.inote.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.paulniu.inote.App;

/**
 * Coder: niupuyue
 * Date: 2019/9/19
 * Time: 16:26
 * Desc: SharedPerference存储
 * Version:
 */
public class SharedUtils {

    private static SharedPreferences mySharedPreferences = null;

    public static SharedPreferences getSharedPreferencesInstance() {
        if (mySharedPreferences == null && null != App.getContext()) {
            mySharedPreferences = App.getContext().getSharedPreferences("app_info", Activity.MODE_PRIVATE);
        }

        return mySharedPreferences;
    }


    /**
     * 设置最后查看欢迎页的versionName
     */
    public static void setShowSpalshVersion(String versionName){
        try {
            SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
            editor.putString(SHARED_LAST_SPLASH_VERSION_NAME,versionName).apply();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 获取最后一次查看欢迎页面的versionName
     */
    public static String getShowSplashVersion(){
        String versionName = "";
        try {
            versionName = getSharedPreferencesInstance().getString(SHARED_LAST_SPLASH_VERSION_NAME,"");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return versionName;
    }

    /**
     * 最后一次查看欢迎页的versionName
     */
    private static final String SHARED_LAST_SPLASH_VERSION_NAME = "info_last_spalsh_version_name";

}
