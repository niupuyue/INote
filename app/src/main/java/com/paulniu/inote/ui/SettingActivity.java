package com.paulniu.inote.ui;

import android.view.View;
import android.widget.ImageView;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.inote.R;

/**
 * Coder: niupuyue
 * Date: 2019/9/23
 * Time: 11:40
 * Desc: 设置页面
 * Version:v0.0.1
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViewById() {
        back = findViewById(R.id.back);
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnClickListener(this, back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
