package com.paulniu.inote.db;

import com.paulniu.inote.App;
import com.paulniu.inote.db.entity.Note;
import com.paulniu.inote.db.entity.NoteFolder;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-12-05
 * Time: 23:31
 * Desc: 备忘录数据操作
 * Version:
 */
public class NoteDaoSource {

    public static void addOrUpdate(Note note) {
        if (note == null) {
            return;
        }
        AppDataBase.getInstance(App.getContext()).noteDao().addOrUpdateNote(note);
    }

    public static void delete(Note note) {
        if (note == null || note.id < 0) {
            return;
        }
        AppDataBase.getInstance(App.getContext()).noteDao().delete(note);
    }

    /**
     * 查询某个文件夹下的所有记事本
     */
    public static List<Note> getNotesByFolder(long folderId) {
        return AppDataBase.getInstance(App.getContext()).noteDao().getNotesByFolder(folderId);
    }

    /**
     * 根据记事本名称查询记事本集合
     */
    public static List<Note> getNotesByNoteName(String noteName) {
        return AppDataBase.getInstance(App.getContext()).noteDao().getNotesByName(noteName);
    }

}
