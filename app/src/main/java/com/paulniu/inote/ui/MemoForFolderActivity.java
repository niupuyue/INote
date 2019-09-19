package com.paulniu.inote.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.inote.R;
import com.paulniu.inote.adapter.MemoAdapter;
import com.paulniu.inote.callback.FolderItemClickListener;
import com.paulniu.inote.data.FolderModel;
import com.paulniu.inote.data.MemoModel;
import com.paulniu.inote.db.FolderDao;
import com.paulniu.inote.db.MemoDao;
import com.paulniu.library.GeneralDialog;
import com.paulniu.library.callbacks.IBaseDialogClickCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 19:02
 * Desc: 文件夹中备忘录列表
 * Version:
 */
public class MemoForFolderActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_INT_FOLDERID = "folderId";
    private static final String EXTRA_OBJECT_FOLDER = "folder";

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
    private SwipeRefreshLayout swipeRefresh;

    private List<MemoModel> memoModelList = new ArrayList<>();
    private MemoAdapter adapter;
    private FolderModel folderModel;
    private FolderDao folderDao;
    private MemoDao memoDao;
    private Handler mHandler = new Handler();

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
        swipeRefresh = findViewById(R.id.swipeRefresh);
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnClickListener(this, back, ivMeomoFolderActivityAddmemo);
        ListenerUtility.setOnRefreshListener(this, swipeRefresh);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void initDataAfterListener() {
        memoDao = new MemoDao(this);
        folderDao = new FolderDao(this);
        // 根据folderid获取到folder对象
        folderModel = folderDao.getFolderById(getIntent().getIntExtra(EXTRA_INT_FOLDERID, -1));
        if (null != title) {
            BaseUtility.setText(title, folderModel.getFolderName());
            BaseUtility.setText(tvMemoFolderActivityCounts, getString(R.string.MemoFolderActivity_counts, String.valueOf(folderModel.getFolderNumbers())));
        }
        adapter = new MemoAdapter();
        adapter.setFolderItemClickListener(new FolderItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                if (!BaseUtility.isEmpty(memoModelList)) {
//                    startActivity(EditMemoActivity.getIntent(MemoForFolderActivity.this, memoModelList.get(position)));
//                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                if (position >= memoModelList.size()) {
                    return;
                }
                GeneralDialog.dialogWithTwoBtn(MemoForFolderActivity.this, getString(R.string.MemoFolderActivity_tips), getString(R.string.MemoFolderActivity_tips_is_delete_memo), new IBaseDialogClickCallback() {
                    @Override
                    public void onClickPositive() {
                        // 删除备忘录
                        int count = memoDao.deleteMemo(memoModelList.get(position).getMemoId());
                        if (count > 0) {
                            Toast.makeText(MemoForFolderActivity.this, getString(R.string.MemoFolderActivity_remarks_delete_memo_success), Toast.LENGTH_SHORT).show();
                            swipeRefresh.setRefreshing(true);
                            onRefresh();
                        }
                    }

                    @Override
                    public void onClickNegative() {

                    }
                });
            }
        });
        memoModelList = memoDao.getMemoByFolderId(folderModel.getFolderId());
        adapter.setMemoModels(memoModelList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerview.addItemDecoration(dividerItemDecoration);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        if (null != mHandler) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 清空原有数据
                    memoModelList.clear();
                    memoModelList = memoDao.getMemoByFolderId(folderModel.getFolderId());
                    adapter.setMemoModels(memoModelList);
                    adapter.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                }
            }, 2000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ivMeomoFolderActivityAddmemo:
                // 新建一个备忘录
                Intent intent = NewMemoActivity.getInstance(MemoForFolderActivity.this,folderModel);
                startActivity(intent);
                break;
        }
    }

}
