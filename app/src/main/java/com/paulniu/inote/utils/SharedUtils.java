package com.paulniu.inote.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.niupuyue.mylibrary.utils.SpanStringUtility;
import com.paulniu.inote.App;
import com.paulniu.inote.data.standard.SplashModel;

/**
 * Coder: niupuyue
 * Date: 2019/9/19
 * Time: 16:26
 * Desc: SharedPerference存储
 * Version:
 */
public class SharedUtils {

    private static SharedPreferences mySharedPreferences = null;

    private static SharedPreferences getSharedPreferencesInstance() {
        if (mySharedPreferences == null && null != App.getContext()) {
            mySharedPreferences = App.getContext().getSharedPreferences("app_info", Activity.MODE_PRIVATE);
        }

        return mySharedPreferences;
    }


    /**
     * 设置最后查看欢迎页的versionName
     */
    public static void setShowSpalshVersion(String versionName) {
        try {
            SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
            editor.putString(SHARED_LAST_SPLASH_VERSION_NAME, versionName).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取最后一次查看欢迎页面的versionName
     */
    public static String getShowSplashVersion() {
        String versionName = "";
        try {
            versionName = getSharedPreferencesInstance().getString(SHARED_LAST_SPLASH_VERSION_NAME, "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return versionName;
    }

    /**
     * 设置广告ad对象
     */
    public static void setSplashAdModel(SplashModel splashAdModel) {
        try {
            SharedPreferences.Editor editor = getSharedPreferencesInstance().edit();
            editor.putString(SHARED_SPLASH_AD_MODEL, SpanStringUtility.serializeToString(splashAdModel)).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取广告ad对象
     */
    public static SplashModel getSplashAdModel() {
        SplashModel splashModel = null;
        try {
            splashModel = (SplashModel) SpanStringUtility.deSerializationToObject(getSharedPreferencesInstance().getString(SHARED_SPLASH_AD_MODEL, ""));
            // TODO 没有数据 暂时先写一个假数据
            splashModel = new SplashModel();
            splashModel.image = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=173843264,1010023015&fm=26&gp=0.jpg";
            splashModel.localImage = "";
            splashModel.url = "http://www.paulniu.com";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return splashModel;
    }

    /**
     * 最后一次查看欢迎页的versionName
     */
    private static final String SHARED_LAST_SPLASH_VERSION_NAME = "info_last_spalsh_version_name";

    /**
     * 广告ad对象
     */
    public static final String SHARED_SPLASH_AD_MODEL = "info_splash_ad_model";

}
