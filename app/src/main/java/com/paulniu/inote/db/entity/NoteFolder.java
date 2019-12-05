package com.paulniu.inote.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Coder: niupuyue
 * Date: 2019/12/5
 * Time: 21:30
 * Desc: 文件夹实体对象
 * Version:
 */
@Entity(indices = {@Index(value = {"id", "folderName"})})
public class NoteFolder implements Serializable {

    /**
     * 文件夹id
     */
    @PrimaryKey(autoGenerate = true)
    public long id;

    /**
     * 文件夹名称
     */
    @ColumnInfo(name = "folderName")
    public String folderName;

    /**
     * 文件夹更新的时间(指的是文件夹中的记事本更新的时间)
     */
    @ColumnInfo(name = "updateTime")
    public long updateTime;

    /**
     * 文件创建的时间
     */
    @ColumnInfo(name = "createTime")
    public long createTime;

    /**
     * 文件夹的描述
     */
    @ColumnInfo(name = "folderDes")
    public String folderDes;

    /**
     * 文件夹的类型
     */
    @ColumnInfo(name = "folderType")
    public int folderType;

}
