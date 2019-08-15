package com.paulniu.inote.callback;

import android.view.View;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 17:10
 * Desc: 文件夹点击事件
 * Version:
 */
public interface FolderItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
