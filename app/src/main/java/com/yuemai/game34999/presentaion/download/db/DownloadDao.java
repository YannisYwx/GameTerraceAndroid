package com.yuemai.game34999.presentaion.download.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.yuemai.game34999.presentaion.download.db.base.BaseDao;
import com.yuemai.game34999.presentaion.download.db.base.DBHelper;
import com.yuemai.game34999.presentaion.download.progress.Progress;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/6  16:29
 * @email : 923080261@qq.com
 * @description :
 */
public class DownloadDao extends BaseDao<Progress> {

    public DownloadDao() {
        super(new DBHelper());
    }

    public static DownloadDao getInstance() {
        return DownloadDaoHolder.instance;
    }

    private static class DownloadDaoHolder {
        private static final DownloadDao instance = new DownloadDao();
    }

    @Override
    public Progress parseCursorToBean(Cursor cursor) {
        return Progress.parseCursorToBean(cursor);
    }

    @Override
    public ContentValues getContentValues(Progress progress) {
        return Progress.buildContentValues(progress);
    }

    @Override
    public String getTableName() {
        return DBHelper.TABLE_DOWNLOAD;
    }

    @Override
    public void unInit() {
    }

    /**
     * 获取下载任务
     */
    public Progress get(String tag) {
        return queryOne(Progress.TAG + "=?", new String[]{tag});
    }

    /**
     * 移除下载任务
     */
    public void delete(String taskKey) {
        delete(Progress.TAG + "=?", new String[]{taskKey});
    }

    /**
     * 更新下载任务
     */
    public boolean update(Progress progress) {
        return update(progress, Progress.TAG + "=?", new String[]{progress.tag});
    }

    /**
     * 更新下载任务
     */
    public boolean update(ContentValues contentValues, String tag) {
        return update(contentValues, Progress.TAG + "=?", new String[]{tag});
    }

    /**
     * 获取所有下载信息
     */
    public List<Progress> getAll() {
        return query(null, null, null, null, null, Progress.DATE + " ASC", null);
    }

    /**
     * 获取所有下载信息
     */
    public List<Progress> getFinished() {
        return query(null, "status=?", new String[]{Progress.FINISH + ""}, null, null, Progress.DATE + " ASC", null);
    }

    /**
     * 获取所有下载信息
     */
    public List<Progress> getDownloading() {
        return query(null, "status not in(?)", new String[]{Progress.FINISH + ""}, null, null, Progress.DATE + " ASC", null);
    }

    /**
     * 清空下载任务
     */
    public boolean clear() {
        return deleteAll();
    }
}

