package com.paulniu.inote.db;

import android.text.TextUtils;

import com.paulniu.inote.App;
import com.paulniu.inote.db.entity.NoteFolder;
import com.paulniu.inote.db.entity.NoteFolderWithNoteCount;

import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-12-05
 * Time: 23:31
 * Desc: 文件夹操作
 * Version:
 */
public class FolderDaoSource {

    public static void addOrUpdate(NoteFolder folder) {
        if (folder == null || TextUtils.isEmpty(folder.folderName)) {
            return;
        }
        AppDataBase.getInstance(App.getContext()).noteFolderDao().addOrUpdateFolder(folder);
    }

    public static void delete(NoteFolder folder) {
        if (folder == null) {
            return;
        }
        AppDataBase.getInstance(App.getContext()).noteFolderDao().delete(folder);
    }

    /**
     * 查询所有的文件夹
     */
    public static List<NoteFolder> getAllFolder() {
        List<NoteFolderWithNoteCount> noteFolders = AppDataBase.getInstance(App.getContext()).noteFolderDao().getAllNoteFolders();
        List<NoteFolder> folders = new ArrayList<>();
        for (NoteFolderWithNoteCount folder : noteFolders) {
            if (folder != null) {
                folders.add(folder);
            }
        }
        return folders;
    }

    /**
     * 根据文件夹名称查询文件夹
     */
    public static List<NoteFolder> getFoldersByFolderName(String folderName) {
        List<NoteFolderWithNoteCount> folderWithNoteCounts = AppDataBase.getInstance(App.getContext()).noteFolderDao().getFoldersByFolderName(folderName);
        List<NoteFolder> folders = new ArrayList<>();
        for (NoteFolderWithNoteCount folder : folderWithNoteCounts) {
            if (folder != null) {
                folders.add(folder);
            }
        }
        return folders;
    }

    /**
     * 根据folderid获取文件夹对象
     */
    public static NoteFolder getFolderByFolderId(long folderId) {
        if (folderId < 0) {
            return null;
        }
        return AppDataBase.getInstance(App.getContext()).noteFolderDao().getNoteFolderByFolderId(folderId);
    }

}
