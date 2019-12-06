package com.paulniu.inote.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.paulniu.inote.db.dao.FolderDao;
import com.paulniu.inote.db.dao.NoteDao;
import com.paulniu.inote.db.entity.Note;
import com.paulniu.inote.db.entity.NoteFolder;

/**
 * Coder: niupuyue
 * Date: 2019/12/5
 * Time: 21:18
 * Desc:
 * Version:
 */
@Database(entities = {NoteFolder.class, Note.class}, version = 1)
@TypeConverters({})
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;

    public abstract FolderDao noteFolderDao();

    public abstract NoteDao noteDao();

    /**
     *  此处填写数据表需要的dao文件
     */


    /**
     * 此处添加数据库版本升级操作
     */
    public static final Migration migration_0_1 = new Migration(0, 1) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 创建文件夹表
            database.execSQL("DROP TABLE IF EXISTS `NoteFolder`");
            database.execSQL("CREATE TABLE IF NOT EXISTS `NoteFolder` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `folderName` TEXT, `createTime` INTEGER, `updateTime` INTEGER, `folderDes` TEXT, `folderType` INTEGER)");
            database.execSQL("CREATE INDEX `index_NodeFolder_id_folderName` on `NoteFolder` (`id`,`folderName`)");

            // 创建记事本表
            database.execSQL("DROP TABLE IF EXISTS `Note`");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Note` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,`folderId` INTEGER, `noteTitle` TEXT, `noteSubTitle` TEXT, `noteContent` TEXT, `createTime` INTEGER, `updateTime` INTEGER,`noteType` INTEGER)");
            database.execSQL("CREATE INDEX `index_Note_id_folderId` on `Note` (`id`,`folderId`)");
        }
    };

    public static AppDataBase getInstance(Context context) {
        synchronized (AppDataBase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "inote.db")
                        .setJournalMode(JournalMode.TRUNCATE)
                        .allowMainThreadQueries()
                        .addMigrations(migration_0_1)
                        .build();
            }
            return INSTANCE;
        }
    }

}
