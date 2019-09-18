package com.paulniu.inote.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.format.DateUtils;

import com.niupuyue.mylibrary.utils.TimeUtility;
import com.paulniu.inote.data.FolderModel;
import com.paulniu.inote.data.MemoModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/8/16
 * Time: 10:22
 * Desc: 文件夹数据库操作集合
 * Version:
 */
public class FolderDao {

    private DBHelper helper;
    private MemoDao memoDao;

    public FolderDao(Context mContext) {
        helper = new DBHelper(mContext);
        memoDao = new MemoDao(mContext);
    }

    /**
     * 创建一个新的文件夹
     */
    public long insertFolder(String folderName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into i_folder(folder_name,folder_create_date,folder_type) values (?,?,?)";
        long ret = 0;
        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();
        try {
            statement.bindString(1, folderName);
            statement.bindString(2, TimeUtility.convertToString(System.currentTimeMillis(), TimeUtility.TIME_FORMAT));
            statement.bindLong(3, 1);// 目前默认是1  普通备忘录
            ret = statement.executeInsert();
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return ret;
    }

    /**
     * 查询所有的文件夹
     */
    public List<FolderModel> getAllFolders() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<FolderModel> folderModels = new ArrayList<>();
        String sql = "select * from i_folder";
        Cursor cursor = null;
        FolderModel folderModel = null;
        try {
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                folderModel = new FolderModel();
                folderModel.setFolderId(cursor.getInt(cursor.getColumnIndex("folder_id")));
                folderModel.setFolderName(cursor.getString(cursor.getColumnIndex("folder_name")));
                folderModel.setFolderDate(cursor.getString(cursor.getColumnIndex("folder_create_date")));
                folderModel.setFolderType(cursor.getInt(cursor.getColumnIndex("folder_type")));
                folderModel.setFolderNumbers(memoDao.getMemoCountByFolder(folderModel.getFolderId()));
                folderModels.add(folderModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return folderModels;
    }

    /**
     * 根据folderid查询到folder对象
     */
    public FolderModel getFolderById(int folderId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        FolderModel folderModel = null;
        String sql = "select * from i_folder where folder_id = " + folderId;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                folderModel = new FolderModel();
                folderModel.setFolderId(cursor.getInt(cursor.getColumnIndex("folder_id")));
                folderModel.setFolderName(cursor.getString(cursor.getColumnIndex("folder_name")));
                folderModel.setFolderDate(cursor.getString(cursor.getColumnIndex("folder_create_date")));
                folderModel.setFolderType(cursor.getInt(cursor.getColumnIndex("folder_type")));
                folderModel.setFolderNumbers(memoDao.getMemoCountByFolder(folderId));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
            if (null != db) {
                db.close();
            }
        }
        return folderModel;
    }

    /**
     * 删除某个文件夹
     */
    public int deleteFolder(int folderId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int ret = 0;
        try {
            ret = db.delete("i_folder", "folder_id=?", new String[]{String.valueOf(folderId)});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return ret;
    }

}
