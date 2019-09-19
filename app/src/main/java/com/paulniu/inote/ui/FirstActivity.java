package com.paulniu.inote.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.bumptech.glide.Glide;
import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.inote.MainActivity;
import com.paulniu.inote.R;
import com.paulniu.inote.data.standard.SplashModel;
import com.paulniu.inote.utils.BussinessUtils;
import com.paulniu.inote.utils.SharedUtils;

import java.lang.ref.WeakReference;

/**
 * Coder: niupuyue
 * Date: 2019/9/19
 * Time: 15:46
 * Desc: 进入App后的第一个页面
 * 1.添加一个倒计时
 * 2.添加广告图片，并且为广告图片添加点击事件，点击之后打开相应的网页
 * 3.点击跳过进入到首页中
 * Version:v0.0.1
 */
public class FirstActivity extends BaseActivity implements View.OnClickListener {

    // 跳转到下一个页面
    private static int HANDLER_MSG_NEXT = 0x100;
    // 展示广告
    private static int HANDLER_MSG_SHOW_AD = 0x101;
    // 倒计时
    private static int HANDLER_MSG_COUNT_DOWN = 0x102;
    // 点击广告渠道下一页
    private static int HANDLER_MSG_NEXT_CLICK_AD = 0x103;

    private ImageView ivFirstActivityAD;
    private TextView tvFirstActivityJump;

    private Handler mHandler = null;
    private Thread mCountDownThread;

    /**
     * 搜索人才库handler对象
     */
    class FirstActivityHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        // 构造方式初始化弱引用对象
        public FirstActivityHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        // 处理handler传递的message对象
        @Override
        public void handleMessage(Message msg) {
            if (mWeakReference == null || mWeakReference.get() == null || mWeakReference.get().isFinishing())
                return;
            if (msg.what == HANDLER_MSG_NEXT) {
                // 跳转到下一个页面
                stopCountDown();
                gotoMain();
            } else if (msg.what == HANDLER_MSG_SHOW_AD) {
                // 展示广告
                SplashModel splashModel = (SplashModel) msg.obj;
                Glide.with(FirstActivity.this).load(splashModel.url).into(ivFirstActivityAD);
                BaseUtility.resetVisibility(ivFirstActivityAD, true);
                startCountDown();
            } else if (msg.what == HANDLER_MSG_COUNT_DOWN) {
                // 倒计时
                BaseUtility.setText(tvFirstActivityJump, getString(R.string.FirstActivity_jump, msg.obj.toString()));
            } else if (msg.what == HANDLER_MSG_NEXT_CLICK_AD) {
                // 点击广告跳转到下一页

            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_first;
    }

    @Override
    public void initViewById() {

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ivFirstActivityAD = findViewById(R.id.ivFirstActivityAD);
        tvFirstActivityJump = findViewById(R.id.tvFirstActivityJump);
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnClickListener(this, tvFirstActivityJump);
    }

    @Override
    public void initDataAfterListener() {
        // 初始化handler对象
        mHandler = new FirstActivityHandler(this);
        toNext();
    }

    /**
     * 下一步需要执行的操作
     */
    private void toNext() {

        try {
            if (null != MainActivity.mainActivity) {
                // 应用已经打开，直接跳转到首页
                gotoMain();
            } else if (BussinessUtils.isShowedSplash()) {
                gotoSpalsh();
            } else if (isShowAd()) {
                // 展示广告
                showAd();
            } else {
                // 默认情况
                if (null != mHandler) {
                    mHandler.sendEmptyMessageDelayed(HANDLER_MSG_NEXT, 1000);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 跳转到首页
     */
    private void gotoMain() {
        try {
            Intent intent = null;
            intent = new Intent(FirstActivity.this, MainActivity.class);
            if (null != intent) {
                startActivity(intent);
                finish();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 跳转到欢迎页面
     */
    private void gotoSpalsh() {
        try {
            Intent intent = null;
            intent = new Intent(FirstActivity.this, SplashActivity.class);
            if (null != intent) {
                startActivity(intent);
                finish();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 判断是否需要展示广告
     *
     * @return
     */
    private boolean isShowAd() {
        boolean isShowAd = true;
        SplashModel splashModel = SharedUtils.getSplashAdModel();
        // 真正的逻辑
        if (null == splashModel || BaseUtility.isEmpty(splashModel.localImage)) {
            mHandler.sendEmptyMessageDelayed(HANDLER_MSG_NEXT, 1000);
            // 设置倒计时不可见
            BaseUtility.resetVisibility(tvFirstActivityJump, false);
            isShowAd = false;
        }
        // 如果当前时间大于结束时间，则不能展示
        if (null != splashModel && !BaseUtility.isEmpty(splashModel.expiry) && System.currentTimeMillis() > Long.valueOf(splashModel.expiry)) {
            isShowAd = false;
        }
        // 如果当前事件小于开始时间，则不能展示
        if (null != splashModel && !BaseUtility.isEmpty(splashModel.start) && System.currentTimeMillis() < Long.valueOf(splashModel.start)) {
            isShowAd = false;
        }
        return isShowAd;
    }

    /**
     * 展示广告
     */
    private void showAd() {
        try {
            SplashModel splashModel = SharedUtils.getSplashAdModel();
            Message message = new Message();
            message.what = HANDLER_MSG_SHOW_AD;
            message.obj = splashModel;
            mHandler.sendMessage(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 开始倒计时
     */
    private void startCountDown() {
        mCountDownThread = new Thread() {
            @Override
            public void run() {
                try {
                    Message msg;
                    for (int i = 3; i >= 0; i--) {
                        // 倒计时结束
                        if (i == 0) {
                            msg = Message.obtain();
                            msg.what = HANDLER_MSG_NEXT;
                            mHandler.sendMessageDelayed(msg, 500);
                        } else {
                            msg = Message.obtain();
                            msg.what = HANDLER_MSG_COUNT_DOWN;
                            msg.obj = i;
                            mHandler.sendMessage(msg);
                            // 如果最后一秒，停留的时间少点
                            if (i == 1) {
                                Thread.sleep(800);
                            } else {
                                Thread.sleep(1000);
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        mCountDownThread.start();
    }

    /**
     * 停止倒计时
     */
    private void stopCountDown() {
        if (null != mCountDownThread) {
            mCountDownThread.interrupt();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19){
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFirstActivityJump:
                // 跳转到首页
                gotoMain();
                break;
            case R.id.ivFirstActivityAD:
                // 点击广告页
                break;
        }
    }
}
