package com.paulniu.inote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.TimeUtility;
import com.paulniu.inote.R;
import com.paulniu.inote.callback.FolderItemClickListener;
import com.paulniu.inote.db.entity.NoteFolder;
import com.paulniu.inote.db.entity.NoteFolderWithNoteCount;

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

    private List<NoteFolder> folderModels;
    private FolderItemClickListener listener;
    private Context mContext;

    public FolderAdapter() {
        folderModels = new ArrayList<>();
    }

    public void setFolderModels(List<NoteFolder> folders) {
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
        NoteFolder model = folderModels.get(position);
        holder.itemView.setTag(position);
        BaseUtility.setText(holder.tv_recyclerview_item_folder_name, model.folderName);
        if (model instanceof NoteFolderWithNoteCount) {
            NoteFolderWithNoteCount folderWithNoteCount = (NoteFolderWithNoteCount) model;
            BaseUtility.setText(holder.tv_recyclerview_item_folder_nums, mContext.getString(R.string.MemoFolderActivity_counts, String.valueOf(folderWithNoteCount.noteCount)));
            BaseUtility.setText(holder.tv_recyclerview_item_folder_time, TimeUtility.convertToString(model.updateTime == 0 ? model.createTime : model.updateTime));
        }
    }

    @Override
    public int getItemCount() {
        return BaseUtility.isEmpty(folderModels) ? 0 : BaseUtility.size(folderModels);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_recyclerview_item_folder_name;
        public TextView tv_recyclerview_item_folder_nums;
        public TextView tv_recyclerview_item_folder_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_recyclerview_item_folder_name = itemView.findViewById(R.id.tv_recyclerview_item_folder_name);
            tv_recyclerview_item_folder_nums = itemView.findViewById(R.id.tv_recyclerview_item_folder_nums);
            tv_recyclerview_item_folder_time = itemView.findViewById(R.id.tv_recyclerview_item_folder_time);
        }
    }

    public void addData(int position, NoteFolder folderModel) {
        if (BaseUtility.isEmpty(folderModels)) {
            folderModels = new ArrayList<>();
        }
        if (position < 0) {
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
