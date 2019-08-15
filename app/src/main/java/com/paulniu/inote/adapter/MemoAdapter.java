package com.paulniu.inote.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 19:24
 * Desc:
 * Version:
 */
public class MemoAdapter extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView memoTitle;
        public TextView memoContent;
        public TextView memoDate;
        public TextView memoFolderName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
