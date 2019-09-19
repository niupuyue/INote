package com.paulniu.inote.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.paulniu.inote.App;
import com.paulniu.inote.Constants;

/**
 * Coder: niupuyue
 * Date: 2019/9/19
 * Time: 16:23
 * Desc: 业务逻辑工具类--提供可能会再多个地方使用的方法
 * Version:
 */
public class BussinessUtils {

    /**
     * 已经查看过欢迎页
     */
    public static void showedSplash() {
        try {
            String versionName = App.getContext().getPackageManager().getPackageInfo(App.getContext().getPackageName(), 0).versionName;
            SharedUtils.setShowSpalshVersion(versionName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 是否需要查看欢迎页
     */
    public static boolean isShowedSplash(){
        boolean isShow = false;
        try {
            // 查看最后一次查看欢迎的version
            String versionName = SharedUtils.getShowSplashVersion();
            // 如果versionname为null，新用户，需要查看
            if (BaseUtility.isEmpty(versionName)){
                isShow = true;
            }else {
                // 获取当前App的版本
                String currentVersion  = App.getContext().getPackageManager().getPackageInfo(App.getContext().getPackageName(),0).versionName;
                if (BaseUtility.equalsIgnoreCase(currentVersion,versionName)){
                    // 最后一次查看的版本和当前的版本一致，不需要查看欢迎页
                    isShow = false;
                }else {
                    // 判断当前版本是否需要查看欢迎页
                    ApplicationInfo appInfo = App.getContext().getPackageManager().getApplicationInfo(App.getContext().getPackageName(), PackageManager.GET_META_DATA);
                    isShow = appInfo.metaData.getBoolean(Constants.META_IS_NEED_SHOW_SPLASH);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return isShow;
    }

}
