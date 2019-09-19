package com.paulniu.inote.data.standard;

import com.paulniu.inote.data.BaseDataProvider;

/**
 * Coder: niupuyue
 * Date: 2019/9/19
 * Time: 17:12
 * Desc: 闪屏页面广告model
 * Version:
 */
public class SplashModel extends BaseDataProvider {

    /**
     * 网路闪屏页面图片地址
     */
    public String image = "";

    /**
     * 本地保存的图片地址
     */
    public String localImage = "";

    /**
     * 点击闪屏页面的跳转页面
     */
    public String url = "";

    /**
     * 活动开始时间
     */
    public String start = "";

    /**
     * 活动的结束时间
     */
    public String expiry = "";

}
