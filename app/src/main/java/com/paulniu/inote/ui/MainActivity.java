package com.paulniu.inote.ui;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.inote.R;
import com.paulniu.inote.adapter.FolderAdapter;
import com.paulniu.inote.callback.AddFolderDialogListener;
import com.paulniu.inote.callback.FolderItemClickListener;
import com.paulniu.inote.db.FolderDaoSource;
import com.paulniu.inote.db.entity.NoteFolder;
import com.paulniu.inote.widget.AddFolderDialog;
import com.paulniu.library.GeneralDialog;
import com.paulniu.library.callbacks.IBaseDialogClickCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, FolderItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static MainActivity mainActivity;

    private RecyclerView recyclerView;
    private TextView tvMainActivityCreateFolder;
    private FolderAdapter adapter;
    private List<NoteFolder> folderModelList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;

    private Handler mHandler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViewById() {
        recyclerView = findViewById(R.id.recyclerView);
        tvMainActivityCreateFolder = findViewById(R.id.tvMainActivityCreateFolder);
        swipeRefresh = findViewById(R.id.swipeRefresh);
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnClickListener(this, tvMainActivityCreateFolder);
        ListenerUtility.setOnRefreshListener(this, swipeRefresh);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void initDataAfterListener() {
        mainActivity = this;
        adapter = new FolderAdapter();
        adapter.setFolderClickListener(this);
        folderModelList = FolderDaoSource.getAllFolder();
        adapter.setFolderModels(folderModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (!BaseUtility.isEmpty(folderModelList)) {
            startActivity(MemoForFolderActivity.getIntent(this, folderModelList.get(position)));
        }
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        if (!BaseUtility.isEmpty(folderModelList)) {
            GeneralDialog.dialogWithTwoBtn(MainActivity.this, getString(R.string.MainActivity_tips), getString(R.string.MainActivity_delete_folder), new IBaseDialogClickCallback() {
                @Override
                public void onClickPositive() {
                    // 删除该文件夹
                    deleteFolder(folderModelList.get(position));
                }

                @Override
                public void onClickNegative() {

                }
            });
        }
    }

    public void showAddFolderDialog() {
        AddFolderDialog dialog = new AddFolderDialog();
        dialog.setAddFolderDialogListener(new AddFolderDialogListener() {
            @Override
            public void onAddFolderConfirm(String folderName) {
                addFolder(folderName);
            }

            @Override
            public void onAddFolderCancel() {

            }
        });
        dialog.show(getSupportFragmentManager(), this.getClass().getName());
    }

    /**
     * 添加文件夹
     *
     * @param folderName
     */
    private void addFolder(String folderName) {
        try {
            NoteFolder folder = new NoteFolder();
            folder.createTime = System.currentTimeMillis();
            folder.updateTime = folder.createTime;
            folder.folderName = folderName;
            FolderDaoSource.addOrUpdate(folder);
        } catch (Exception ex) {
            ex.printStackTrace();
            CustomToastUtility.makeTextError(getString(R.string.MainActivity_remarks_add_folder_fail));
        }
        CustomToastUtility.makeTextSucess(getString(R.string.MainActivity_remarks_add_folder_success));
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }

    /**
     * 删除文件夹
     */
    private void deleteFolder(NoteFolder folderModel) {

        try {
            FolderDaoSource.delete(folderModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            CustomToastUtility.makeTextError(getString(R.string.MainActivity_remarks_delete_folder_fail));
        }
        CustomToastUtility.makeTextSucess(getString(R.string.MainActivity_remarks_delete_folder_success));
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (null != mHandler) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 清空原有数据
                    folderModelList.clear();
                    folderModelList = FolderDaoSource.getAllFolder();
                    adapter.setFolderModels(folderModelList);
                    adapter.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                }
            }, 2000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMainActivityCreateFolder:
                // 显示弹窗dialog
                showAddFolderDialog();
                break;
        }
    }

}
