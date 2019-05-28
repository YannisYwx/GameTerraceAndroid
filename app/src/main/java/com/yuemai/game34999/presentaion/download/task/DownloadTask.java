package com.yuemai.game34999.presentaion.download.task;

import android.content.ContentValues;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yannis.common.util.Preconditions;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.db.DownloadDao;
import com.yuemai.game34999.presentaion.download.mode.HttpHeaders;
import com.yuemai.game34999.presentaion.download.net.exception.HttpException;
import com.yuemai.game34999.presentaion.download.net.exception.OkGoException;
import com.yuemai.game34999.presentaion.download.net.exception.StorageException;
import com.yuemai.game34999.presentaion.download.net.request.GetRequest;
import com.yuemai.game34999.presentaion.download.progress.DownloadListener;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.download.task.queue.PriorityRunnable;
import com.yuemai.game34999.presentaion.download.utils.HttpUtils;
import com.yuemai.game34999.presentaion.download.utils.IOUtils;
import com.yuemai.game34999.util.ThreadUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  16:18
 * @email : 923080261@qq.com
 * @description : 下载任务
 */
public class DownloadTask implements Runnable {
    private static final int BUFFER_SIZE = 1024 * 8;
    /**
     * 当前的进度
     */
    public Progress progress;
    /**
     * 下载监听
     */
    public Map<Object, DownloadListener> listeners;
    /**
     * 下载线程池
     */
    private ThreadPoolExecutor executor;
    /**
     * 单个的下载优先级任务
     */
    private PriorityRunnable priorityRunnable;

    public DownloadTask(String tag, GetRequest<File> getRequest) {
        progress = new Progress();
        progress.tag = Preconditions.checkNotNull(tag, "tag == null");
        progress.folder = DownloadManager.getInstance().getFolder();
        progress.url = getRequest.getBaseUrl();
        progress.status = Progress.NONE;
        progress.totalSize = -1;
        progress.mGetRequest = getRequest;

        executor = DownloadManager.getInstance().getThreadPool().getExecutor();
        listeners = new HashMap<>();
    }

    public DownloadTask(Progress progress) {
        this.progress = Preconditions.checkNotNull(progress, "progress == null");
        executor = DownloadManager.getInstance().getThreadPool().getExecutor();
        listeners = new HashMap<>();
    }

    public DownloadTask folder(String folder) {
        if (folder != null && !TextUtils.isEmpty(folder.trim())) {
            progress.folder = folder;
        } else {
            Logger.w("folder is null, ignored!");
        }
        return this;
    }

    public DownloadTask fileName(String fileName) {
        if (fileName != null && !TextUtils.isEmpty(fileName.trim())) {
            progress.fileName = fileName;
        } else {
            Logger.w("fileName is null, ignored!");
        }
        return this;
    }

    public DownloadTask priority(int priority) {
        progress.priority = priority;
        return this;
    }

    public DownloadTask extra1(Serializable extra1) {
        progress.extra1 = extra1;
        return this;
    }

    public DownloadTask extra2(Serializable extra2) {
        progress.extra2 = extra2;
        return this;
    }

    public DownloadTask extra3(Serializable extra3) {
        progress.extra3 = extra3;
        return this;
    }

    /**
     * 将下载详情保存到数据库
     *
     * @return this
     */
    public DownloadTask save() {
        if (!TextUtils.isEmpty(progress.folder) && !TextUtils.isEmpty(progress.fileName)) {
            progress.filePath = new File(progress.folder, progress.fileName).getAbsolutePath();
        }
        DownloadDao.getInstance().replace(progress);
        return this;
    }

    public DownloadTask register(DownloadListener listener) {
        if (listener != null) {
            listeners.put(listener.tag, listener);
        }
        return this;
    }

    public void unRegister(DownloadListener listener) {
        final DownloadListener downloadListener = Preconditions.checkNotNull(listener, "listener == null");
        listeners.remove(downloadListener.tag);
    }

    public void unRegister(String tag) {
        Preconditions.checkNotNull(tag, "tag == null");
        listeners.remove(tag);
    }

    /**
     * 开始执行下载任务
     */
    public void start() {
        //当前数据库如果没有该任务 那么就抛出异常
        if (DownloadManager.getInstance().getTask(progress.tag) == null || DownloadDao.getInstance().get(progress.tag) == null) {
            throw new IllegalStateException("you must call DownloadTask#save() before DownloadTask#start()！");
        }
        //只有当前的状态是 未操作、暂停、错误这三种才会执行任务
        if (progress.status == Progress.NONE || progress.status == Progress.PAUSE || progress.status == Progress.ERROR) {
            postOnStart(progress);
            postWaiting(progress);
            priorityRunnable = new PriorityRunnable(progress.priority, this);
            executor.execute(priorityRunnable);
        } else if (progress.status == Progress.FINISH) {
            if (progress.filePath == null) {
                postOnError(progress, new StorageException("the file of the task with tag:" + progress.tag + " may be invalid or damaged, please call the method restart() to download again！"));
            } else {
                File file = new File(progress.filePath);
                if (file.exists() && file.length() == progress.totalSize) {
                    postOnFinish(progress, new File(progress.filePath));
                } else {
                    postOnError(progress, new StorageException("the file " + progress.filePath + " may be invalid or damaged, please call the method restart() to download again！"));
                }
            }
        } else {
            Logger.w("the task with tag " + progress.tag + " is already in the download queue, current task status is " + progress.status);
        }
    }

    /**
     * 重新开始任务  会先删除已下载的文件
     * 将下载的状态置为初始状态
     */
    public void restart() {
        pause();
        IOUtils.delFileOrFolder(progress.filePath);
        progress.status = Progress.NONE;
        progress.currentSize = 0;
        progress.fraction = 0;
        progress.speed = 0;
        DownloadDao.getInstance().replace(progress);
        start();
    }

    /**
     * 暂停的方法
     * 1：当前是等待状态 直接将下载状态置为暂停PAUSE 并保存数据库
     * 2：当前状态是正在下载 将下载速度置为0 状态置为暂停PAUSE
     */
    public void pause() {
        executor.remove(priorityRunnable);
        if (progress.status == Progress.WAITING) {
            postPause(progress);
        } else if (progress.status == Progress.LOADING) {
            progress.speed = 0;
            progress.status = Progress.PAUSE;
            postPause(progress);
        } else {
            Logger.w("only the task with status WAITING(1) or LOADING(2) can pause, current status is " + progress.status);
        }
    }

    /**
     * 移除改任务 不会删除源文件
     */
    public void remove() {
        remove(false);
    }

    /**
     * 移除下载任务
     *
     * @param isDeleteFile 是否删除文件 true 删除源文件 false 不删除
     * @return 下载任务
     */
    public DownloadTask remove(boolean isDeleteFile) {
        pause();
        if (isDeleteFile) {
            IOUtils.delFileOrFolder(progress.filePath);
        }
        DownloadDao.getInstance().delete(progress.tag);
        DownloadTask task = DownloadManager.getInstance().removeTask(progress.tag);
        postOnRemove(progress);
        return task;
    }

    @Override
    public void run() {
        //check breakpoint
        long startPosition = progress.currentSize;
        if (startPosition < 0) {
            postOnError(progress, OkGoException.BREAKPOINT_EXPIRED());
            return;
        }
        if (startPosition > 0) {
            if (!TextUtils.isEmpty(progress.filePath)) {
                File file = new File(progress.filePath);
                if (!file.exists()) {
                    postOnError(progress, OkGoException.BREAKPOINT_NOT_EXIST());
                    return;
                }
            }
        }

        //mGetRequest network from startPosition
        Response response;
        try {
            GetRequest<?> getRequest = progress.mGetRequest;
            getRequest.headers(HttpHeaders.HEAD_KEY_RANGE, "bytes=" + startPosition + "-");
            response = getRequest.execute();
        } catch (IOException e) {
            postOnError(progress, e);
            return;
        }
        //check network data
        int code = response.code();
        if (code == 404 || code >= 500) {
            if (code == 404) {
                progress.errorCode = Progress.ErrorCode.NOT_FOUND;
            } else {
                progress.errorCode = Progress.ErrorCode.SERVICE_ERROR;
            }
            postOnError(progress, HttpException.NET_ERROR());
            return;
        }
        ResponseBody body = response.body();
        if (body == null) {
            postOnError(progress, new HttpException("response body is null"));
            return;
        }
        if (progress.totalSize == -1) {
            progress.totalSize = body.contentLength();
        }

        //create filename
        String fileName = progress.fileName;
        if (TextUtils.isEmpty(fileName)) {
            fileName = HttpUtils.getNetFileName(response, progress.url);
            progress.fileName = fileName;
        }
        if (!IOUtils.createFolder(progress.folder)) {
            progress.errorCode = Progress.ErrorCode.NO_STORAGE_PERMISSION;
            postOnError(progress, StorageException.NOT_AVAILABLE());
            return;
        }

        //create and check file
        File file;
        if (TextUtils.isEmpty(progress.filePath)) {
            file = new File(progress.folder, fileName);
            progress.filePath = file.getAbsolutePath();
        } else {
            file = new File(progress.filePath);
        }
        //开始位置大于0但文件却不存在
        if (startPosition > 0 && !file.exists()) {
            postOnError(progress, OkGoException.BREAKPOINT_EXPIRED());
            return;
        }
        //开始位置大于总长度说明文件有问题
        if (startPosition > progress.totalSize) {
            postOnError(progress, OkGoException.BREAKPOINT_EXPIRED());
            return;
        }
        //文件存在 但开始位置为0就删除源文件重新开始
        if (startPosition == 0 && file.exists()) {
            IOUtils.delFileOrFolder(file);
        }
        if (startPosition == progress.totalSize && startPosition > 0) {
            if (file.exists() && startPosition == file.length()) {
                postOnFinish(progress, file);
                return;
            } else {
                postOnError(progress, OkGoException.BREAKPOINT_EXPIRED());
                return;
            }
        }

        //开始下载文件
        RandomAccessFile randomAccessFile;
        try {
            //将文件移动到开始位置
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(startPosition);
            progress.currentSize = startPosition;
        } catch (Exception e) {
            postOnError(progress, e);
            return;
        }
        try {
            //更新数据库
            DownloadDao.getInstance().replace(progress);
            //开始文件写入操作
            download(body.byteStream(), randomAccessFile, progress);
        } catch (IOException e) {
            Logger.e("postOnError --------------IOException-error = " + e.toString() + " msg = " + e.getMessage());
            postOnError(progress, e);
            return;
        }

        //check finish status
        if (progress.status == Progress.PAUSE) {
            postPause(progress);
        } else if (progress.status == Progress.LOADING) {
            if (file.length() == progress.totalSize) {
                postOnFinish(progress, file);
            } else {
                postOnError(progress, OkGoException.BREAKPOINT_EXPIRED());
            }
        } else {
            postOnError(progress, OkGoException.UNKNOWN());
        }
    }

    /**
     * 执行文件下载
     * 当300ms刷新间隔或下载完毕的时候会将下载的状态保存到数据库 然后通知主线程刷新操作
     *
     * @param input    数据输入流
     * @param out      文件输出流
     * @param progress 下载详情
     * @throws IOException 异常
     */
    private void download(InputStream input, RandomAccessFile out, Progress progress) throws IOException {
        if (input == null || out == null) {
            return;
        }
        progress.status = Progress.LOADING;
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        int len;
        try {
            while ((len = in.read(buffer, 0, BUFFER_SIZE)) != -1 && progress.status == Progress.LOADING) {
                out.write(buffer, 0, len);
                Progress.changeProgress(progress, len, progress.totalSize, this::postLoading);
            }
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 通知主线程下载开始
     * 将下载状态置为NONE初始状态
     *
     * @param progress 下载详情
     */
    private void postOnStart(final Progress progress) {
        progress.speed = 0;
        progress.status = Progress.NONE;
        updateDatabase(progress);
        ThreadUtils.runOnUiThread(() -> {
            for (DownloadListener listener : listeners.values()) {
                listener.onStart(progress);
            }
        });
    }

    /**
     * 通知主线程下载等待
     * 将下载速度置为0 状态为等待
     *
     * @param progress 下载详情
     */
    private void postWaiting(final Progress progress) {
        progress.speed = 0;
        progress.status = Progress.WAITING;
        updateDatabase(progress);
        ThreadUtils.runOnUiThread(() -> {
            for (DownloadListener listener : listeners.values()) {
                listener.onProgress(progress);
            }
        });
    }

    /**
     * 通知主线程下载被暂停
     * 会将当前任务状态置为暂停状态 然后写入数据库保存
     *
     * @param progress 下载详情
     */
    private void postPause(final Progress progress) {
        progress.speed = 0;
        progress.status = Progress.PAUSE;
        updateDatabase(progress);
        ThreadUtils.runOnUiThread(() -> {
            for (DownloadListener listener : listeners.values()) {
                Logger.e("---------postPause = " + progress.toString());
                listener.onProgress(progress);
            }
        });
    }

    /**
     * 通知主线程下载桩体
     * 每次写入文件通知下载刷新时都会写入数据库
     * 当app退出的时候回保存下载的最后的状态下载的大小到数据库  再次进入的时候恢复
     *
     * @param progress 下载详情
     */
    private void postLoading(final Progress progress) {
        //修改数据库
        updateDatabase(progress);
        ThreadUtils.runOnUiThread(() -> {
            for (DownloadListener listener : listeners.values()) {
                listener.onProgress(progress);
            }
        });
    }

    /**
     * 通知主线程下载出现异常
     *
     * @param progress  下载详情
     * @param throwable 下载的异常：
     */
    private void postOnError(final Progress progress, final Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append("postOnError 下载出错-->");
        sb.append("\n").append("【");
        sb.append("\n").append("文件名 = ").append(progress.fileName);
        sb.append("\n").append("url = ").append(progress.url);
        sb.append("\n").append("tag = ").append(progress.tag);
        sb.append("\n").append("本地路径 = ").append(progress.filePath);
        sb.append("\n").append("文件目录 = ").append(progress.folder);
        sb.append("\n").append("下载进度 = ").append(progress.fraction);
        sb.append("\n").append("status(before) = ").append(progress.status);
        sb.append("\n").append("已下载大小 = ").append(progress.currentSize);
        sb.append("\n").append("文件总大小 = ").append(progress.totalSize);
        sb.append("\n").append("额外附带数据 = ").append(progress.extra1.toString());
        sb.append("\n").append("】");
        sb.append("\n").append("错误 --> 【 Error = ").append(throwable.toString());
        sb.append("\n").append("Message = ").append(throwable.getMessage()).append(" 】");
        Logger.e(sb.toString());
        progress.speed = 0;
        progress.status = Progress.ERROR;
        progress.exception = throwable;
        updateDatabase(progress);
        ThreadUtils.runOnUiThread(() -> {
            for (DownloadListener listener : listeners.values()) {
                listener.onProgress(progress);
                listener.onError(progress);
            }
        });
    }

    /**
     * 通知主线程下载完成
     *
     * @param progress 下载详情
     * @param file     已经下载好的文件
     */
    private void postOnFinish(final Progress progress, final File file) {
        progress.speed = 0;
        progress.fraction = 1.0f;
        progress.status = Progress.FINISH;
        updateDatabase(progress);
        ThreadUtils.runOnUiThread(() -> {
            for (DownloadListener listener : listeners.values()) {
                listener.onProgress(progress);
                listener.onFinish(file, progress);
            }
        });
    }

    /**
     * 通知主线程下载任务被移除
     *
     * @param progress 下载详情
     */
    private void postOnRemove(final Progress progress) {
        updateDatabase(progress);
        ThreadUtils.runOnUiThread(() -> {
            for (DownloadListener listener : listeners.values()) {
                listener.onRemove(progress);
            }
            listeners.clear();
        });
    }

    /**
     * 修改数据库
     *
     * @param progress 下载详情
     */
    private void updateDatabase(Progress progress) {
        ContentValues contentValues = Progress.buildUpdateContentValues(progress);
        DownloadDao.getInstance().update(contentValues, progress.tag);
    }
}
