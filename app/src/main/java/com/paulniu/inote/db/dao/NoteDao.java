package com.paulniu.inote.db.dao;

import android.text.TextUtils;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.paulniu.inote.db.entity.Note;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-12-05
 * Time: 23:30
 * Desc: 记事本操作
 * Version:
 */
@Dao
public abstract class NoteDao {

    @Update
    public abstract void update(Note... note);

    @Insert
    public abstract void insert(Note... note);

    @Delete
    public abstract void delete(Note... note);

    @Query("select * from Note")
    public abstract List<Note> getAllNotes();

    /**
     * 根据文件夹id获取记事本
     *
     * @param folderId
     * @return
     */
    @Query("select * from Note where folderId = :folderId order by updateTime desc")
    public abstract List<Note> getNotesByFolder(long folderId);

    /**
     * 根据记事本id获取记事本对象
     *
     * @param id
     * @return
     */
    @Query("select * from Note where id = :id")
    public abstract Note getNoteById(long id);

    /**
     * 根据记事本名获取记事本集合
     */
    @Query("select * from Note where noteTitle like :noteName")
    public abstract List<Note> getNotesByNoteName(String noteName);

    /**
     * 更新或添加Note
     * @param note
     */
    public void addOrUpdateNote(Note note) {
        if (note == null) {
            return;
        }
        Note oldNote = getNoteById(note.id);
        if (oldNote == null || oldNote.id < 0) {
            insert(note);
        } else {
            note.id = oldNote.id;
            update(note);
        }
    }

    /**
     * 根据记事本名获取记事本集合
     */
    public List<Note> getNotesByName(String noteName){
        if (TextUtils.isEmpty(noteName)){
            return getAllNotes();
        }else {
            String key = "%" + noteName + "%";
            return getNotesByNoteName(key);
        }
    }

}
