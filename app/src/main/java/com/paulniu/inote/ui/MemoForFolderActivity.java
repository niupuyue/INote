package com.paulniu.inote.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.inote.R;
import com.paulniu.inote.data.MemoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 19:02
 * Desc: 文件夹中备忘录列表
 * Version:
 */
public class MemoForFolderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title;
    private RecyclerView recyclerview;
    private TextView tvMemoFolderActivityCounts;
    private ImageView ivMeomoFolderActivityAddmemo;

    private List<MemoModel> memoModelList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_memo_folder;
    }

    @Override
    public void initViewById() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        recyclerview = findViewById(R.id.recyclerview);
        tvMemoFolderActivityCounts = findViewById(R.id.tvMemoFolderActivityCounts);
        ivMeomoFolderActivityAddmemo = findViewById(R.id.ivMeomoFolderActivityAddmemo);
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnClickListener(this, back, ivMeomoFolderActivityAddmemo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ivMeomoFolderActivityAddmemo:
                // 新建一个备忘录
                break;
        }
    }
}
