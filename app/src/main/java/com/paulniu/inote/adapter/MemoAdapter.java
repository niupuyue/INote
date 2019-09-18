package com.paulniu.inote.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.paulniu.inote.R;
import com.paulniu.inote.callback.FolderItemClickListener;
import com.paulniu.inote.data.MemoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 19:24
 * Desc:
 * Version:
 */
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private FolderItemClickListener listener;
    private List<MemoModel> memoModels = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_memo, parent, false);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemoModel model = memoModels.get(position);
        holder.itemView.setTag(position);
        BaseUtility.setText(holder.memoTitle, model.getTitle());
        BaseUtility.setText(holder.memoDate, model.getDate());
        BaseUtility.setText(holder.memoContent, model.getContent());
    }

    @Override
    public int getItemCount() {
        return BaseUtility.isEmpty(memoModels) ? 0 : BaseUtility.size(memoModels);
    }

    public void setFolderItemClickListener(FolderItemClickListener listener) {
        this.listener = listener;
    }

    public void setMemoModels(List<MemoModel> models) {
        this.memoModels = models;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView memoTitle;
        public TextView memoContent;
        public TextView memoDate;
        public TextView memoFolderName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memoTitle = itemView.findViewById(R.id.tv_recyclerview_item_memo_title);
            memoDate = itemView.findViewById(R.id.tv_recyclerview_item_memo_date);
            memoContent = itemView.findViewById(R.id.tv_recyclerview_item_memo_content);
            memoFolderName = itemView.findViewById(R.id.tv_recyclerview_item_memo_folder_name);
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick(v, (Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (listener != null) {
            listener.onItemLongClick(v, (Integer) v.getTag());
        }
        return true;
    }

}
