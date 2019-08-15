package com.paulniu.inote.callback;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 18:13
 * Desc: 添加文件夹弹窗按钮点击事件接口
 * Version:
 */
public interface AddFolderDialogListener {
    void onAddFolderConfirm(String folderName);
    void onAddFolderCancel();
}
