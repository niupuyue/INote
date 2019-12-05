package com.paulniu.inote.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Coder: niupuyue
 * Date: 2019/12/5
 * Time: 21:41
 * Desc: 记事本实体对象
 * Version:
 */
@Entity(indices = {@Index(value = {"id", "folderId"})})
public class Note implements Serializable {

    /**
     * 记事本id
     */
    @PrimaryKey(autoGenerate = true)
    public long id;

    /**
     * 记事本所在的文件夹id
     */
    @ColumnInfo(name = "folderId")
    public long folderId;

    /**
     * 记事本名称(标题)
     */
    @ColumnInfo(name = "noteTitle")
    public String noteTitle;

    /**
     * 记事本副标题(摘要)
     */
    @ColumnInfo(name = "noteSubTitle")
    public String noteSubTitle;

    /**
     * 记事本正文(一般不需要展示在列表中)
     */
    @ColumnInfo(name = "noteContent")
    public String noteContent;

    /**
     * 记事本创建时间
     */
    @ColumnInfo(name = "createTime")
    public long createTime;

    /**
     * 记事本更新(修改)时间
     */
    @ColumnInfo(name = "updateTime")
    public long updateTime;

    /**
     * 记事本类型
     */
    @ColumnInfo(name = "noteType")
    public int noteType;

}
