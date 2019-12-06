package com.paulniu.inote.db.entity;

import androidx.room.Ignore;

/**
 * Coder: niupuyue
 * Date: 2019/12/6
 * Time: 14:26
 * Desc:
 * Version:
 */
public class NoteFolderWithNoteCount extends NoteFolder {

    /**
     * 文件夹中记事本的数量
     */
    public int noteCount;

}
