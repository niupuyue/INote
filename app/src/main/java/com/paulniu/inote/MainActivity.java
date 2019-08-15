package com.paulniu.inote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.widgets.BaseDialog;
import com.paulniu.inote.adapter.FolderAdapter;
import com.paulniu.inote.callback.AddFolderDialogListener;
import com.paulniu.inote.callback.FolderItemClickListener;
import com.paulniu.inote.data.FolderModel;
import com.paulniu.inote.ui.MemoForFolderActivity;
import com.paulniu.inote.widget.AddFolderDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, FolderItemClickListener {

    private RecyclerView recyclerView;
    private TextView tvMainActivityCreateFolder;
    private FolderAdapter adapter;
    private List<FolderModel> folderModelList = new ArrayList<>();

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
        adapter = new FolderAdapter();
        adapter.setFolderClickListener(this);
        for (int i = 0; i < 50; i++) {
            FolderModel model = new FolderModel();
            model.setFolderId(i + 100);
            model.setFolderName("文件夹" + i);
            model.setFolderNumbers(11 + i);
            folderModelList.add(model);
        }
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
    public void onItemLongClick(View view, int position) {
        if (!BaseUtility.isEmpty(folderModelList)) {
            CustomToastUtility.makeTextSucess("长恩的是第" + position + "个文件夹");
        }
    }

    public void showAddFolderDialog() {
        AddFolderDialog dialog = new AddFolderDialog();
        dialog.setAddFolderDialogListener(new AddFolderDialogListener() {
            @Override
            public void onAddFolderConfirm(String folderName) {
                CustomToastUtility.makeTextSucess(folderName);
            }

            @Override
            public void onAddFolderCancel() {

            }
        });
        dialog.show(getSupportFragmentManager(), this.getClass().getName());
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
