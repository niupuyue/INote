package com.paulniu.inote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.paulniu.inote.R;
import com.paulniu.inote.callback.FolderItemClickListener;
import com.paulniu.inote.data.FolderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 16:56
 * Desc: 文件夹adapter
 * Version:
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<FolderModel> folderModels;
    private FolderItemClickListener listener;
    private Context mContext;

    public FolderAdapter() {
        folderModels = new ArrayList<>();
    }

    public void setFolderModels(List<FolderModel> folders) {
        this.folderModels = folders;
    }

    public void setFolderClickListener(FolderItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_folder, parent, false);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.ViewHolder holder, int position) {
        FolderModel model = folderModels.get(position);
        holder.itemView.setTag(position);
        BaseUtility.setText(holder.tv_recyclerview_item_folder_name, model.getFolderName());
        BaseUtility.setText(holder.tv_recyclerview_item_folder_nums, mContext.getString(R.string.MemoFolderActivity_counts,String.valueOf(model.getFolderNumbers())));
    }

    @Override
    public int getItemCount() {
        return BaseUtility.isEmpty(folderModels) ? 0 : BaseUtility.size(folderModels);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_recyclerview_item_folder_name;
        public TextView tv_recyclerview_item_folder_nums;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_recyclerview_item_folder_name = itemView.findViewById(R.id.tv_recyclerview_item_folder_name);
            tv_recyclerview_item_folder_nums = itemView.findViewById(R.id.tv_recyclerview_item_folder_nums);
        }
    }

    public void addData(int position, FolderModel folderModel) {
        if (BaseUtility.isEmpty(folderModels)) {
            folderModels = new ArrayList<>();
        }
        if (position < 0){
            position = BaseUtility.isEmpty(folderModels) ? 0 : BaseUtility.size(folderModels) - 1;
        }
        folderModels.add(position, folderModel);
        notifyItemChanged(position);
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
