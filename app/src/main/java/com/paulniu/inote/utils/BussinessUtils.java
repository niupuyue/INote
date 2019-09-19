package com.paulniu.inote.utils;

import com.paulniu.inote.App;

/**
 * Coder: niupuyue
 * Date: 2019/9/19
 * Time: 16:23
 * Desc: 业务逻辑工具类--提供可能会再多个地方使用的方法
 * Version:
 */
public class BussinessUtils {

    /**
     * 已经查看过引导页
     */
    public static void showedSplash() {
        try {
            String versionName = App.getContext().getPackageManager().getPackageInfo(App.getContext().getPackageName(), 0).versionName;
            SharedUtils.setShowSpalshVersion(versionName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
