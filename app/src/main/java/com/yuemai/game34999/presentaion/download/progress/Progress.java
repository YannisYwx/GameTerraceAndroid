package com.yuemai.game34999.presentaion.download.progress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.SystemClock;
import android.text.format.Formatter;

import com.orhanobut.logger.Logger;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.download.mode.Priority;
import com.yuemai.game34999.presentaion.download.net.request.GetRequest;
import com.yuemai.game34999.presentaion.download.utils.IOUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  10:52
 * @email : 923080261@qq.com
 * @description : 下载的进度管理器
 */
public class Progress implements Serializable {
    private static final long serialVersionUID = 6353658567594109891L;

    public static String status(int status) {
        String strStatus = "";
        switch (status) {
            case NONE:
                strStatus = "初始状态";
                break;
            case WAITING:
                strStatus = "等待";
                break;
            case LOADING:
                strStatus = "下载中";
                break;
            case PAUSE:
                strStatus = "暂停";
                break;
            case ERROR:
                strStatus = "错误";
                break;
            case FINISH:
                strStatus = "完成";
                break;
            default:
                break;
        }
        return strStatus;
    }

    public static String errorMsg(int errorCode) {
        String errorMsg;
        switch (errorCode) {
            case ErrorCode.NONE:
                errorMsg = "正常";
                break;
            case ErrorCode.NOT_FOUND:
                errorMsg = "暂无资源";
                break;
            case ErrorCode.SERVICE_ERROR:
                errorMsg = "服务器异常";
                break;
            case ErrorCode.NO_RESOURCE:
                errorMsg = "暂无资源";
                break;
            case ErrorCode.NO_STORAGE_PERMISSION:
                errorMsg = "手动开启文件读写权限";
                break;
            default:
                errorMsg = "正常";
                break;
        }
        return errorMsg;
    }

    /**
     * 下载进度最大值
     */
    public static float MAX_PROGRESS = 1.0F;
    /**
     * 下载进度最小值
     */
    public static float MIN_PROGRESS = .0F;
    /**
     * 回调刷新时间（单位ms）
     */
    public static long REFRESH_TIME = 300;
    /**
     * 无状态
     */
    public static final int NONE = 0;
    /**
     * 等待
     */
    public static final int WAITING = 1;
    /**
     * 下载中
     */
    public static final int LOADING = 2;
    /**
     * 暂停
     */
    public static final int PAUSE = 3;
    /**
     * 错误
     */
    public static final int ERROR = 4;
    /**
     * 完成
     */
    public static final int FINISH = 5;

    public static final String TAG = "tag";
    public static final String URL = "url";
    public static final String FOLDER = "folder";
    public static final String FILE_PATH = "filePath";
    public static final String FILE_NAME = "fileName";
    public static final String FRACTION = "fraction";
    public static final String PERCENT = "percent";
    public static final String TOTAL_SIZE = "totalSize";
    public static final String CURRENT_SIZE = "currentSize";
    public static final String STATUS = "status";
    public static final String PRIORITY = "priority";
    public static final String DATE = "date";
    public static final String ERROR_CODE = "errorCode";
    public static final String REQUEST = "mGetRequest";
    public static final String EXTRA1 = "extra1";
    public static final String EXTRA2 = "extra2";
    public static final String EXTRA3 = "extra3";

    /**
     * 下载的标识键
     */
    public String tag;
    /**
     * 网址
     */
    public String url;
    /**
     * 保存文件夹
     */
    public String folder;
    /**
     * 保存文件地址
     */
    public String filePath;
    /**
     * 保存的文件名
     */
    public String fileName;
    /**
     * 下载的进度，0-1
     */
    public float fraction;
    /**
     * 下载的百分比 0-100
     */
    public int percent;
    /**
     * 总字节长度, byte
     */
    public long totalSize;
    /**
     * 本次下载的大小, byte
     */
    public long currentSize;
    /**
     * 网速，byte/s
     */
    public transient long speed;
    /**
     * 当前状态
     */
    public int status;
    /**
     * 错误码
     */
    public int errorCode;
    /**
     * 任务优先级
     */
    public int priority;
    /**
     * 创建时间
     */
    public long date;

    /**
     * 网络请求
     */
    public GetRequest<?> mGetRequest;
    /**
     * 额外的数据
     */
    public Serializable extra1;
    /**
     * 额外的数据
     */
    public Serializable extra2;
    /**
     * 额外的数据
     */
    public Serializable extra3;
    /**
     * 当前进度出现的异常
     */
    public Throwable exception;
    /**
     * 每一小段时间间隔的网络流量
     */
    private transient long tempSize;
    /**
     * 最后一次刷新的时间
     */
    private transient long lastRefreshTime;
    /**
     * 网速做平滑的缓存，避免抖动过快
     */
    private transient List<Long> speedBuffer;

    public interface ErrorCode {
        /**
         * 正常
         */
        int NONE = 0;
        /**
         * 404资源未发现
         */
        int NOT_FOUND = 1;
        /**
         * 服务器异常
         */
        int SERVICE_ERROR = 2;
        /**
         * 没有该资源
         */
        int NO_RESOURCE = 3;
        /**
         * 没有存储权限
         */
        int NO_STORAGE_PERMISSION = 4;

    }

    public Progress() {
        lastRefreshTime = SystemClock.elapsedRealtime();
        totalSize = -1;
        priority = Priority.DEFAULT;
        date = System.currentTimeMillis();
        speedBuffer = new ArrayList<>();
    }

    public static Progress changeProgress(Progress progress, long writeSize, Action action) {
        return changeProgress(progress, writeSize, progress.totalSize, action);
    }

    public static Progress changeProgress(final Progress progress, long writeSize, long totalSize, final Action action) {
        progress.totalSize = totalSize;
        progress.currentSize += writeSize;
        progress.tempSize += writeSize;

        long currentTime = SystemClock.elapsedRealtime();
        boolean isNotify = (currentTime - progress.lastRefreshTime) >= REFRESH_TIME;
        if (isNotify || progress.currentSize == totalSize) {
            long diffTime = currentTime - progress.lastRefreshTime;
            if (diffTime == 0) {
                diffTime = 1;
            }
            progress.fraction = progress.currentSize * 1.0f / totalSize;
            progress.percent = (int) (progress.fraction * 100);
            progress.speed = progress.bufferSpeed(progress.tempSize * 1000 / diffTime);
            progress.lastRefreshTime = currentTime;
            progress.tempSize = 0;
            if (action != null) {
                action.call(progress);
            }
        }
        return progress;
    }

    /**
     * 平滑网速，避免抖动过大
     */
    private long bufferSpeed(long speed) {
        speedBuffer.add(speed);
        if (speedBuffer.size() > 10) {
            speedBuffer.remove(0);
        }
        long sum = 0;
        for (float speedTemp : speedBuffer) {
            sum += speedTemp;
        }
        return sum / speedBuffer.size();
    }

    /**
     * 转换进度信息
     */
    public void from(Progress progress) {
        totalSize = progress.totalSize;
        currentSize = progress.currentSize;
        fraction = progress.fraction;
        percent = progress.percent;
        speed = progress.speed;
        lastRefreshTime = progress.lastRefreshTime;
        tempSize = progress.tempSize;
    }

    public interface Action {
        /**
         * 回调接口
         *
         * @param progress 进度
         */
        void call(Progress progress);
    }

    public static ContentValues buildContentValues(Progress progress) {
        ContentValues values = new ContentValues();
        values.put(TAG, progress.tag);
        values.put(URL, progress.url);
        values.put(FOLDER, progress.folder);
        values.put(FILE_PATH, progress.filePath);
        values.put(FILE_NAME, progress.fileName);
        values.put(FRACTION, progress.fraction);
        values.put(PERCENT, progress.percent);
        values.put(TOTAL_SIZE, progress.totalSize);
        values.put(ERROR_CODE, progress.errorCode);
        values.put(CURRENT_SIZE, progress.currentSize);
        values.put(STATUS, progress.status);
        values.put(PRIORITY, progress.priority);
        values.put(DATE, progress.date);
        values.put(REQUEST, IOUtils.toByteArray(progress.mGetRequest));
        values.put(EXTRA1, IOUtils.toByteArray(progress.extra1));
        values.put(EXTRA2, IOUtils.toByteArray(progress.extra2));
        values.put(EXTRA3, IOUtils.toByteArray(progress.extra3));
        return values;
    }

    public static ContentValues buildUpdateContentValues(Progress progress) {
        ContentValues values = new ContentValues();
        values.put(FRACTION, progress.fraction);
        values.put(PERCENT, progress.percent);
        values.put(TOTAL_SIZE, progress.totalSize);
        values.put(CURRENT_SIZE, progress.currentSize);
        values.put(STATUS, progress.status);
        values.put(ERROR_CODE, progress.errorCode);
        values.put(PRIORITY, progress.priority);
        values.put(DATE, progress.date);
        return values;
    }

    public static Progress parseCursorToBean(Cursor cursor) {
        Progress progress = new Progress();
        progress.tag = cursor.getString(cursor.getColumnIndex(Progress.TAG));
        progress.url = cursor.getString(cursor.getColumnIndex(Progress.URL));
        progress.folder = cursor.getString(cursor.getColumnIndex(Progress.FOLDER));
        progress.filePath = cursor.getString(cursor.getColumnIndex(Progress.FILE_PATH));
        progress.fileName = cursor.getString(cursor.getColumnIndex(Progress.FILE_NAME));
        progress.fraction = cursor.getFloat(cursor.getColumnIndex(Progress.FRACTION));
        progress.percent = cursor.getInt(cursor.getColumnIndex(Progress.PERCENT));
        progress.totalSize = cursor.getLong(cursor.getColumnIndex(Progress.TOTAL_SIZE));
        progress.currentSize = cursor.getLong(cursor.getColumnIndex(Progress.CURRENT_SIZE));
        progress.status = cursor.getInt(cursor.getColumnIndex(Progress.STATUS));
        progress.errorCode = cursor.getInt(cursor.getColumnIndex(Progress.ERROR_CODE));
        progress.priority = cursor.getInt(cursor.getColumnIndex(Progress.PRIORITY));
        progress.date = cursor.getLong(cursor.getColumnIndex(Progress.DATE));
        progress.mGetRequest = (GetRequest<?>) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(Progress.REQUEST)));
        progress.extra1 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(Progress.EXTRA1)));
        progress.extra2 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(Progress.EXTRA2)));
        progress.extra3 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(Progress.EXTRA3)));
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Progress progress = (Progress) o;
        return tag != null ? tag.equals(progress.tag) : progress.tag == null;

    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "fraction=" + fraction +
                ", percent=" + percent +
                ", totalSize=" + totalSize +
                ", currentSize=" + currentSize +
                ", speed=" + speed +
                ", status=" + status +
                ", errorCode=" + errorCode +
                ", priority=" + priority +
                ", folder=" + folder +
                ", filePath=" + filePath +
                ", fileName=" + fileName +
                ", tag=" + tag +
                ", url=" + url +
                '}';
    }

    public static void log(Progress progress) {
        String sb = "Progress -->" +
                "\n" + "【" +
                "\n" + "文件名 = " + progress.fileName +
                "\n" + "url = " + progress.url +
                "\n" + "tag = " + progress.tag +
                "\n" + "本地路径 = " + progress.filePath +
                "\n" + "文件目录 = " + progress.folder +
                "\n" + "下载进度 = " + progress.fraction +
                "\n" + "status(before) = " + progress.status +
                "\n" + "errorCode(错误码) = " + progress.errorCode +
                "\n" + "已下载大小 = " + progress.currentSize +
                "\n" + "文件总大小 = " + progress.totalSize +
                "\n" + "额外附带数据 = " + ((GameInfo) progress.extra1).toString() +
                "\n" + "】";
        Logger.e(sb);
    }

    public static void log(String tag, Progress progress) {
        String sb = tag + " Progress -->" +
                "\n" + "【" +
                "\n" + "文件名 = " + progress.fileName +
                "\n" + "url = " + progress.url +
                "\n" + "tag = " + progress.tag +
                "\n" + "本地路径 = " + progress.filePath +
                "\n" + "文件目录 = " + progress.folder +
                "\n" + "下载进度 = " + progress.fraction +
                "\n" + "status(before) = " + progress.status +
                "\n" + "已下载大小 = " + progress.currentSize +
                "\n" + "文件总大小 = " + progress.totalSize +
                "\n" + "额外附带数据 = " + ((GameInfo) progress.extra1).toString() +
                "\n" + "】";
        Logger.e(sb);
    }

    /**
     * 获取apk文件下载的占比
     *
     * @param context  上下文
     * @param progress 当前下载的详情
     * @return XXMB/XXXMB
     */
    public static String getApkDownloadedPercent(Context context, Progress progress) {
        String downloadSize = Formatter.formatFileSize(context, progress.currentSize);
        downloadSize = cutSuffix(context, downloadSize);
        String totalSize = Formatter.formatFileSize(context, progress.totalSize).trim();
        return String.format("%s/%s", downloadSize, totalSize);
    }

    /**
     * 剪切掉单位
     *
     * @param context
     * @param fileSize
     * @return
     */
    public static String cutSuffix(Context context, String fileSize) {
        String m = fileSize;
        String pb = "PB";
        String tb = "TB";
        String gb = "GB";
        String mb = "MB";
        String kb = "kB";
        String b = "B";
        if (m.contains(pb)) {
            m = m.replace(pb, "");
        }
        if (m.contains(tb)) {
            m = m.replace(tb, "");
        }
        if (m.contains(gb)) {
            m = m.replace(gb, "");
        }
        if (m.contains(mb)) {
            m = m.replace(mb, "");
        }
        if (m.contains(kb)) {
            m = m.replace(kb, "");
        }
        if (m.contains(b)) {
            m = m.replace(b, "");
        }
        m = m.trim();
        return m;
    }

    /**
     * 获取apk文件下载的占比
     *
     * @param context     上下文
     * @param currentSize 当前下载大小
     * @param totalSize   总大小
     * @return XXMB/XXXMB
     */
    public static String getApkDownloadedPercent(Context context, long currentSize, long totalSize) {
        return String.format("%s/%s", Formatter.formatFileSize(context, currentSize), Formatter.formatFileSize(context, totalSize));
    }

}
