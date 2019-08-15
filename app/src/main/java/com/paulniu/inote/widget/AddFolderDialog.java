package com.paulniu.inote.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.widgets.BaseDialog;
import com.paulniu.inote.R;
import com.paulniu.inote.callback.AddFolderDialogListener;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 17:46
 * Desc: 添加文件夹弹窗
 * Version:
 */
public class AddFolderDialog extends BaseDialog implements View.OnClickListener, TextWatcher {

    private View mRoot;
    private EditText etAddFolderDialogName;
    private TextView tvAddFolderDialogCancel;
    private TextView tvAddFolderDialogConfirm;

    private AddFolderDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        mRoot = inflater.inflate(R.layout.dialog_add_folder, container, false);
        etAddFolderDialogName = mRoot.findViewById(R.id.etAddFolderDialogName);
        tvAddFolderDialogCancel = mRoot.findViewById(R.id.tvAddFolderDialogCancel);
        tvAddFolderDialogConfirm = mRoot.findViewById(R.id.tvAddFolderDialogConfirm);

        ListenerUtility.setOnClickListener(this, tvAddFolderDialogCancel, tvAddFolderDialogConfirm);
        ListenerUtility.addTextChangeListener(etAddFolderDialogName, this);
        return mRoot;
    }

    /////////////////////////////////////////////////////////////必须实现的方法/////////////////////////////////////////////////////////////////////
    public void setAddFolderDialogListener(AddFolderDialogListener listener) {
        this.listener = listener;
    }
    /////////////////////////////////////////////////////////////必须实现的方法////////////////////////////////////////////////////////////////////

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!BaseUtility.isEmpty(s)) {
            tvAddFolderDialogConfirm.setEnabled(true);
        } else {
            tvAddFolderDialogConfirm.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddFolderDialogCancel:
                if (listener != null) {
                    listener.onAddFolderCancel();
                }
                break;
            case R.id.tvAddFolderDialogConfirm:
                if (listener != null && !BaseUtility.isEmpty(BaseUtility.getText(etAddFolderDialogName))) {
                    listener.onAddFolderConfirm(BaseUtility.getText(etAddFolderDialogName));
                }
                break;
        }
        dismiss();
    }

}
