package com.paulniu.inote.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.inote.MainActivity;
import com.paulniu.inote.R;
import com.paulniu.inote.utils.BussinessUtils;
import com.paulniu.inote.widget.SplashVideoView;

/**
 * Coder: niupuyue
 * Date: 2019/9/19
 * Time: 15:50
 * Desc: 欢迎页面
 * 1.采用QQ哪种播放视频的方式显示
 * 2.视频播放的过程中右上角显示跳过按钮
 * 3.视频播放完成之后显示开启记录之旅按钮
 * 4.点击按钮之后跳转到下一个页面
 * Version:v0.0.1
 */
public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private SplashVideoView splashVideoView;
    private TextView tvSplashActivityJump;
    private TextView tvSplashActivityStartTravel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViewById() {
        splashVideoView = findViewById(R.id.splashVideoView);
        tvSplashActivityJump = findViewById(R.id.tvSplashActivityJump);
        tvSplashActivityStartTravel = findViewById(R.id.tvSplashActivityStartTravel);
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnClickListener(this, tvSplashActivityJump, tvSplashActivityStartTravel);
        // 播放完成之后的监听
        if (null != splashVideoView) {
            splashVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 播放完成之后，设置"跳过"按钮不可见，设置"开启记录之旅"按钮可见
                    if (null != tvSplashActivityJump) {
                        BaseUtility.resetVisibility(tvSplashActivityJump, View.GONE);
                    }
                    if (null != tvSplashActivityStartTravel) {
                        BaseUtility.resetVisibility(tvSplashActivityStartTravel, true);
                    }
                }
            });
        }
    }

    @Override
    public void initDataAfterListener() {
        super.initDataAfterListener();
        if (null != splashVideoView) {
            splashVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sport));
            splashVideoView.start();
            // 开始播放之后将"跳过按钮设置为可见"
            if (null != tvSplashActivityJump) {
                BaseUtility.resetVisibility(tvSplashActivityJump, true);
            }
            if (null != tvSplashActivityStartTravel) {
                BaseUtility.resetVisibility(tvSplashActivityStartTravel, View.GONE);
            }
        }
    }

    private void startTravel() {
        BussinessUtils.showedSplash();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSplashActivityStartTravel:
            case R.id.tvSplashActivityJump:
                startTravel();
                break;
        }
    }
}
