package com.paulniu.inote.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Coder: niupuyue
 * Date: 2019/8/16
 * Time: 10:25
 * Desc: 备忘录数据库操作工具类
 * Version:
 */
public class MemoDao {

    private DBHelper helper;

    public MemoDao(Context mContext) {
        helper = new DBHelper(mContext);
    }

    /**
     * 查询某个文件夹中有多少个备忘录
     */
    public int getMemoCountByFolder(int folderId) {
        if (folderId < 0) return 0;
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = 0;
        String sql = "select count(*) from i_memo where folder_id =" + folderId + " order by memo_create_date desc";
        Cursor cursor;
        try {
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

}
