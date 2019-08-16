package com.paulniu.inote.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.paulniu.inote.R;
import com.paulniu.inote.data.MemoModel;

/**
 * Coder: niupuyue
 * Date: 2019/8/16
 * Time: 9:52
 * Desc: 编辑备忘录页面
 * Version:
 */
public class EditMemoActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_INT_MEMOID = "memoId";

    public static Intent getIntent(Context context, MemoModel memoModel) {
        Intent intent = new Intent(context, EditMemoActivity.class);
        intent.putExtra(EXTRA_INT_MEMOID, memoModel.getMemoId());
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_memo;
    }

    @Override
    public void initViewById() {

    }

    @Override
    public void onClick(View v) {

    }
}
