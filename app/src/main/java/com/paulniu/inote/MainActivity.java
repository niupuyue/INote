package com.paulniu.inote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.callbacks.ISimpleDialogButtonClickCallback;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.widgets.BaseDialog;
import com.niupuyue.mylibrary.widgets.SimpleDialog;
import com.paulniu.inote.adapter.FolderAdapter;
import com.paulniu.inote.callback.AddFolderDialogListener;
import com.paulniu.inote.callback.FolderItemClickListener;
import com.paulniu.inote.data.FolderModel;
import com.paulniu.inote.db.FolderDao;
import com.paulniu.inote.ui.MemoForFolderActivity;
import com.paulniu.inote.widget.AddFolderDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, FolderItemClickListener {

    private RecyclerView recyclerView;
    private TextView tvMainActivityCreateFolder;
    private FolderAdapter adapter;
    private List<FolderModel> folderModelList = new ArrayList<>();
    private FolderDao folderDao;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViewById() {
        recyclerView = findViewById(R.id.recyclerView);
        tvMainActivityCreateFolder = findViewById(R.id.tvMainActivityCreateFolder);
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnClickListener(this, tvMainActivityCreateFolder);
    }

    @Override
    public void initDataAfterListener() {
        folderDao = new FolderDao(this);
        adapter = new FolderAdapter();
        adapter.setFolderClickListener(this);
        folderModelList = folderDao.getAllFolders();
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
            SimpleDialog.showSimpleDialog(this, getString(R.string.MainActivity_delete_folder), new ISimpleDialogButtonClickCallback() {
                @Override
                public void onLeftButtonClick() {

                }

                @Override
                public void onRightButtonClick() {
                    // 删除该文件夹
                    deleteFolder(folderModelList.get(position));
                }

                @Override
                public void onCancel() {

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
        long insertCount = folderDao.insertFolder(folderName);
        if (insertCount > 0) {
            CustomToastUtility.makeTextSucess("插入成功！");
        } else {
            CustomToastUtility.makeTextError("插入失败！");
        }
    }

    /**
     * 删除文件夹
     */
    private void deleteFolder(FolderModel folderModel) {
        int ret = folderDao.deleteFolder(folderModel.getFolderId());
        if (ret > 0) {
            CustomToastUtility.makeTextSucess("删除成功！");
        } else {
            CustomToastUtility.makeTextError("删除失败！");
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
