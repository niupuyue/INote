package com.paulniu.inote.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.niupuyue.mylibrary.base.BaseActivity;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.inote.R;
import com.paulniu.inote.db.NoteDaoSource;
import com.paulniu.inote.db.entity.Note;
import com.paulniu.inote.db.entity.NoteFolder;
import com.paulniu.inote.widget.RichTextEditor;
import com.paulniu.library.GeneralDialog;
import com.paulniu.library.callbacks.IBaseDialogClickCallback;

import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/9/18
 * Time: 18:19
 * Desc: 新建备忘录
 * Version:
 */
public class NewMemoActivity extends BaseActivity implements View.OnClickListener {

    private static final String EXTRA_INT_FOLDERID = "folderId";
    private static final String EXTRA_STRING_FOLDERNAME = "folderName";

    private ImageView back;
    private TextView title;
    private TextView save;
    private EditText et_new_title;
    private RichTextEditor et_new_content;

    private Note memoModel = new Note();
    private long folderId;
    private String folderName;

    public static Intent getInstance(Context context, NoteFolder folderModel) {
        Intent intent = new Intent(context, NewMemoActivity.class);
        intent.putExtra(EXTRA_INT_FOLDERID, folderModel.id);
        intent.putExtra(EXTRA_STRING_FOLDERNAME, folderModel.folderName);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_memo;
    }

    @Override
    public void initViewById() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        save = findViewById(R.id.save);
        et_new_title = findViewById(R.id.et_new_title);
        et_new_content = findViewById(R.id.et_new_content);
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnClickListener(this, back, save);
    }

    @Override
    public void initDataAfterListener() {
        folderId = getIntent().getLongExtra(EXTRA_INT_FOLDERID, -1);
        folderName = getIntent().getStringExtra(EXTRA_STRING_FOLDERNAME);
        BaseUtility.setText(title, getString(R.string.NewMemoActivity_add_new_memo));

    }

    private String getEditData() {
        StringBuilder content = new StringBuilder();
        try {
            List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
            for (RichTextEditor.EditData itemData : editList) {
                if (itemData.inputStr != null) {
                    content.append(itemData.inputStr);
                } else if (itemData.imagePath != null) {
                    content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!BaseUtility.isEmpty(BaseUtility.getText(et_new_title)) || !BaseUtility.isEmpty(getEditData())) {
            GeneralDialog.dialogWithTwoBtn(NewMemoActivity.this, getString(R.string.NewMemoActivity_tips), getString(R.string.NewMemoActivity_close_without_save), new IBaseDialogClickCallback() {
                @Override
                public void onClickPositive() {
                    finish();
                }

                @Override
                public void onClickNegative() {
                    // 什么也不用做
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if (BaseUtility.isEmpty(BaseUtility.getText(et_new_title))) {
                    Toast.makeText(NewMemoActivity.this, getString(R.string.NewMemoActivity_remark_empty_title), Toast.LENGTH_SHORT).show();
                    return;
                }
                String title = BaseUtility.getText(et_new_title);
                String content = getEditData();
                memoModel.noteTitle = title;
                memoModel.noteContent = content;
                memoModel.updateTime = System.currentTimeMillis();
                memoModel.folderId = folderId;
                memoModel.createTime = memoModel.updateTime;
                try {
                    NoteDaoSource.addOrUpdate(memoModel);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                // 保存成功
                Toast.makeText(NewMemoActivity.this, getString(R.string.NewMemoActivity_remark_save_success), Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
