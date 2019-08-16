package com.paulniu.inote.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Coder: niupuyue
 * Date: 2019/8/16
 * Time: 10:12
 * Desc: 数据库帮助类
 * Version:
 */
public class DBHelper extends SQLiteOpenHelper {

    // 数据库名称
    private static final String DB_NAME = "inote.db";
    // 数据库版本
    private static final int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建分类表
        db.execSQL("create table i_folder(folder_id integer primary key autoincrement,folder_name varchar,folder_create_date datetime,folder_type integer)");
        //创建笔记表
        db.execSQL("create table i_memo(memo_id integer primary key autoincrement,memo_title varchar,memo_content varchar,folder_id integer,memo_create_date datetime)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
