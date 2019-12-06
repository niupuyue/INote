package com.paulniu.inote.db.dao;

import android.text.TextUtils;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.paulniu.inote.db.entity.NoteFolder;
import com.paulniu.inote.db.entity.NoteFolderWithNoteCount;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-12-05
 * Time: 23:31
 * Desc: 文件夹操作
 * Version:
 */
@Dao
public abstract class FolderDao {

    /**
     * 更新文件夹
     *
     * @param folder
     */
    @Update
    public abstract void update(NoteFolder... folder);

    /**
     * 插入文件夹
     *
     * @param folder
     */
    @Insert
    public abstract void insert(NoteFolder... folder);

    /**
     * 删除文件夹
     *
     * @param folder
     */
    @Delete
    public abstract void delete(NoteFolder... folder);

    /**
     * 查询所有的文件夹
     *
     * @return
     */
    @Query("select NoteFolder.*,count(Note.id) as noteCount from NoteFolder left join Note on NoteFolder.id = Note.folderId group by NoteFolder.id order by NoteFolder.updateTime desc")
    public abstract List<NoteFolderWithNoteCount> getAllNoteFolders();

    /**
     * 根据folderId获取folder对象
     *
     * @param folderId
     * @return
     */
    @Query("select NoteFolder.*,count(Note.id) as noteCount from NoteFolder left join Note on NoteFolder.id = Note.folderId where NoteFolder.id = :folderId")
    public abstract NoteFolderWithNoteCount getNoteFolderByFolderId(long folderId);

    /**
     * 根据文件夹名获取文件夹集合
     */
    @Query("select NoteFolder.*,count(Note.id) as noteCount from NoteFolder left join Note on NoteFolder.id = Note.folderId where NoteFolder.folderName like :folderName")
    public abstract List<NoteFolderWithNoteCount> getNoteFoldersByFolderName(String folderName);

    /**
     * 更新或者添加folder
     *
     * @param folder
     */
    public void addOrUpdateFolder(NoteFolder folder) {
        if (folder == null || TextUtils.isEmpty(folder.folderName)) {
            return;
        }
        NoteFolder olderFolder = getNoteFolderByFolderId(folder.id);
        if (olderFolder == null || olderFolder.id < 0) {
            insert(folder);
        } else {
            folder.id = olderFolder.id;
            update(folder);
        }
    }

    /**
     * 根据文件夹名获取文件夹集合
     */
    public List<NoteFolderWithNoteCount> getFoldersByFolderName(String folderName) {
        if (TextUtils.isEmpty(folderName)) {
            return getAllNoteFolders();
        } else {
            String key = "%" + folderName + "%";
            return getNoteFoldersByFolderName(key);
        }
    }


}
