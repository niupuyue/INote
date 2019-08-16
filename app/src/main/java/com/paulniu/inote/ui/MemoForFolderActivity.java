package com.paulniu.inote.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.inote.R;
import com.paulniu.inote.adapter.MemoAdapter;
import com.paulniu.inote.callback.FolderItemClickListener;
import com.paulniu.inote.data.FolderModel;
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

    private static final String EXTRA_INT_FOLDERID = "folderId";

    public static Intent getIntent(Context context, FolderModel folderModel) {
        Intent intent = new Intent(context, MemoForFolderActivity.class);
        intent.putExtra(EXTRA_INT_FOLDERID, folderModel.getFolderId());
        return intent;
    }

    private ImageView back;
    private TextView title;
    private RecyclerView recyclerview;
    private TextView tvMemoFolderActivityCounts;
    private ImageView ivMeomoFolderActivityAddmemo;

    private List<MemoModel> memoModelList = new ArrayList<>();
    private MemoAdapter adapter;

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
    public void initDataAfterListener() {
        adapter = new MemoAdapter();
        adapter.setFolderItemClickListener(new FolderItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!BaseUtility.isEmpty(memoModelList)) {
                    startActivity(EditMemoActivity.getIntent(MemoForFolderActivity.this, memoModelList.get(position)));
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                CustomToastUtility.makeTextError("长摁了第" + position + "个备忘录");
            }
        });
        for (int i = 0; i < 50; i++) {
            MemoModel model = new MemoModel();
            model.setContent("内容" + i + 1);
            model.setDate("2019年08月15日21:00:43");
            model.setFolderName("文件夹" + i + 1);
            model.setTitle("标题" + i + 1);
            memoModelList.add(model);
        }
        adapter.setMemoModels(memoModelList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerview.addItemDecoration(dividerItemDecoration);
        recyclerview.setAdapter(adapter);
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
