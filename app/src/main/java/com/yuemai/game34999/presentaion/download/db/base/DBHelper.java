package com.yuemai.game34999.presentaion.download.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yuemai.game34999.GameApp;
import com.yuemai.game34999.presentaion.download.progress.Progress;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/5  11:47
 * @email : 923080261@qq.com
 * @description :
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_CACHE_NAME = "game34999.db";
    private static final int DB_CACHE_VERSION = 1;
    public static final String TABLE_DOWNLOAD = "download";

    static final Lock lock = new ReentrantLock();

    private TableEntity downloadTableEntity = new TableEntity(TABLE_DOWNLOAD);

    public DBHelper() {
        this(GameApp.getInstance());
    }

    public DBHelper(Context context) {
        super(context, DB_CACHE_NAME, null, DB_CACHE_VERSION);
        downloadTableEntity.addColumn(new ColumnEntity(Progress.TAG, "VARCHAR", true, true))
                .addColumn(new ColumnEntity(Progress.URL, "VARCHAR"))
                .addColumn(new ColumnEntity(Progress.FOLDER, "VARCHAR"))
                .addColumn(new ColumnEntity(Progress.FILE_PATH, "VARCHAR"))
                .addColumn(new ColumnEntity(Progress.FILE_NAME, "VARCHAR"))
                .addColumn(new ColumnEntity(Progress.FRACTION, "VARCHAR"))
                .addColumn(new ColumnEntity(Progress.PERCENT, "INTEGER"))
                .addColumn(new ColumnEntity(Progress.TOTAL_SIZE, "INTEGER"))
                .addColumn(new ColumnEntity(Progress.CURRENT_SIZE, "INTEGER"))
                .addColumn(new ColumnEntity(Progress.STATUS, "INTEGER"))
                .addColumn(new ColumnEntity(Progress.ERROR_CODE, "INTEGER"))
                .addColumn(new ColumnEntity(Progress.PRIORITY, "INTEGER"))
                .addColumn(new ColumnEntity(Progress.DATE, "INTEGER"))
                .addColumn(new ColumnEntity(Progress.REQUEST, "BLOB"))
                .addColumn(new ColumnEntity(Progress.EXTRA1, "BLOB"))
                .addColumn(new ColumnEntity(Progress.EXTRA2, "BLOB"))
                .addColumn(new ColumnEntity(Progress.EXTRA3, "BLOB"));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(downloadTableEntity.buildTableString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (DBUtils.isNeedUpgradeTable(db, downloadTableEntity)) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOAD);
        }
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

