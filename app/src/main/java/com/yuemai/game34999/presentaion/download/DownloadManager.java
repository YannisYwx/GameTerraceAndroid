package com.yuemai.game34999.presentaion.download;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.common.base.Preconditions;
import com.orhanobut.logger.Logger;
import com.yannis.common.thread.ThreadPoolManager;
import com.yuemai.game34999.presentaion.download.db.DownloadDao;
import com.yuemai.game34999.presentaion.download.net.interceptor.HttpLoggingInterceptor;
import com.yuemai.game34999.presentaion.download.net.request.GetRequest;
import com.yuemai.game34999.presentaion.download.progress.DownloadListener;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;
import com.yuemai.game34999.presentaion.download.task.executor.DownloadThreadPool;
import com.yuemai.game34999.presentaion.download.task.executor.ObserveExecutor;
import com.yuemai.game34999.presentaion.download.utils.IOUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/6  15:49
 * @email : 923080261@qq.com
 * @description : 下载管理类
 */
public class DownloadManager {
    private Application mContext;
    private int retryCount = 3;
    /**
     * 默认的超时时间
     */
    public static final long DEFAULT_MILLISECONDS = 60000;
    /**
     * 下载进度刷新的时间（单位ms）
     */
    public static long REFRESH_TIME = 300;
    /**
     * 用于在主线程执行的调度器
     */
    private Handler mDelivery;
    /**
     * ok请求的客户端
     */
    private OkHttpClient okHttpClient;

    /*------------------------下载管理---------------------------*/
    /**
     * 下载的默认文件夹
     */
    private String folder;
    /**
     * 下载的线程池
     */
    private DownloadThreadPool threadPool;
    /**
     * 所有任务
     */
    private ConcurrentHashMap<String, DownloadTask> taskMap;

    private static DownloadDao sDao = new DownloadDao();

    @SuppressLint("StaticFieldLeak")
    private static DownloadManager instance = new DownloadManager();

    /**
     * 认为类型
     */
    public enum TaskStatusType {
        /**
         * 所有任务
         */
        ALL_TASK,
        /**
         * 还在进行中的任务
         */
        DOWNLOADING_TASK,
        /**
         * 已经完成的任务
         */
        FINISH_TASK
    }


    private DownloadManager() {
        mDelivery = new Handler(Looper.getMainLooper());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClient = builder.build();

        folder = Environment.getExternalStorageDirectory() + File.separator + "download" + File.separator;
        IOUtils.createFolder(folder);
        threadPool = DownloadThreadPool.getInstance();
        taskMap = new ConcurrentHashMap<>();

        //校验数据的有效性，防止下载过程中退出，第二次进入的时候，由于状态没有更新导致的状态错误
        List<Progress> taskList = sDao.getDownloading();
        Log.e("GameApp", "初始化数据库====================================================Start");
        if (taskList != null && taskList.size() > 0) {
            for (Progress info : taskList) {
                Progress.log(info);
                if (info.status == Progress.WAITING || info.status == Progress.LOADING || info.status == Progress.PAUSE) {
                    info.status = Progress.NONE;
                }
            }
            sDao.replace(taskList);
        }
        Log.e("GameApp", "初始化数据库====================================================End");
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public void init(Application app) {
        this.mContext = Preconditions.checkNotNull(app, "Application = null");
    }

    public static GetRequest<File> get(String url) {
        return new GetRequest<>(url);
    }

    public static DownloadTask request(String tag, GetRequest<File> getRequest) {
        Map<String, DownloadTask> taskMap = DownloadManager.getInstance().getTaskMap();
        DownloadTask task = taskMap.get(tag);
        if (task == null) {
            task = new DownloadTask(tag, getRequest);
            taskMap.put(tag, task);
        }
        return task;
    }

    /**
     * 创建一个task
     *
     * @param tag task的标识
     * @param url 下载的url
     * @return task
     */
    public static DownloadTask request(String tag, String url) {
        return request(tag, get(url));
    }

    /**
     * 创建一个task
     *
     * @param url url为task的标识和下载的url
     * @return task
     */
    public static DownloadTask request(String url) {
        return request(url, url);
    }

    /**
     * 从数据库中恢复任务
     */
    public static DownloadTask restore(Progress progress) {
        Map<String, DownloadTask> taskMap = DownloadManager.getInstance().getTaskMap();
        DownloadTask task = taskMap.get(progress.tag);
        if (task == null) {
            task = new DownloadTask(progress);
            taskMap.put(progress.tag, task);
        }
        return task;
    }

    /**
     * 从数据库中恢复任务
     */
    public static List<DownloadTask> restore(List<Progress> progressList) {
        Map<String, DownloadTask> taskMap = DownloadManager.getInstance().getTaskMap();
        List<DownloadTask> tasks = new ArrayList<>();
        for (Progress progress : progressList) {
            DownloadTask task = taskMap.get(progress.tag);
            if (task == null) {
                task = new DownloadTask(progress);
                taskMap.put(progress.tag, task);
            }
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * 获取所有任务
     *
     * @return 任务集合
     */
    public static List<DownloadTask> getAllTask() {
        return restore(getProgressList(TaskStatusType.ALL_TASK));
    }

    /**
     * 获取所有正在下载中的任务（未完成的）
     *
     * @return 任务集合
     */
    public static List<DownloadTask> getDownloadingTask() {
        return restore(getProgressList(TaskStatusType.DOWNLOADING_TASK));
    }

    /**
     * 获取所有已完成任务
     *
     * @return 任务集合
     */
    public static List<DownloadTask> getFinishTask() {
        return restore(getProgressList(TaskStatusType.FINISH_TASK));
    }

    /**
     * 从数据库中恢复所有任务
     */
    public static void restoreAllTask() {
        ThreadPoolManager.getInstance().execute(() -> restore(getProgressList(TaskStatusType.ALL_TASK)));
    }

    /**
     * 从数据库中获取任务
     *
     * @param type 任务状态
     * @return 任务集合
     */
    public static List<Progress> getProgressList(TaskStatusType type) {
        switch (type) {
            case ALL_TASK:
                return DownloadDao.getInstance().getAll();
            case DOWNLOADING_TASK:
                return DownloadDao.getInstance().getDownloading();
            case FINISH_TASK:
                return DownloadDao.getInstance().getFinished();
            default:
                return null;
        }

    }


    /**
     * 开始所有任务
     */
    public void startAll() {
        for (Map.Entry<String, DownloadTask> entry : taskMap.entrySet()) {
            DownloadTask task = entry.getValue();
            if (task == null) {
                Logger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            task.start();
        }
    }

    /**
     * 暂停全部任务
     */
    public void pauseAll() {
        //先停止未开始的任务
        for (Map.Entry<String, DownloadTask> entry : taskMap.entrySet()) {
            DownloadTask task = entry.getValue();
            if (task == null) {
                Logger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            if (task.progress.status != Progress.LOADING) {
                task.pause();
            }
        }
        //再停止进行中的任务
        for (Map.Entry<String, DownloadTask> entry : taskMap.entrySet()) {
            DownloadTask task = entry.getValue();
            if (task == null) {
                Logger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            if (task.progress.status == Progress.LOADING) {
                task.pause();
            }
        }
    }

    /**
     * 删除所有任务
     */
    public void removeAll() {
        removeAll(false);
    }

    /**
     * 删除所有任务
     *
     * @param isDeleteFile 删除任务是否删除文件
     */
    public void removeAll(boolean isDeleteFile) {
        Map<String, DownloadTask> map = new HashMap<>(taskMap);
        //先删除未开始的任务
        for (Map.Entry<String, DownloadTask> entry : map.entrySet()) {
            DownloadTask task = entry.getValue();
            if (task == null) {
                Logger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            if (task.progress.status != Progress.LOADING) {
                task.remove(isDeleteFile);
            }
        }
        //再删除进行中的任务
        for (Map.Entry<String, DownloadTask> entry : map.entrySet()) {
            DownloadTask task = entry.getValue();
            if (task == null) {
                Logger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            if (task.progress.status == Progress.LOADING) {
                task.remove(isDeleteFile);
            }
        }
    }

    /**
     * 设置下载目录
     */
    public String getFolder() {
        return folder;
    }

    public DownloadManager setFolder(String folder) {
        this.folder = folder;
        return this;
    }

    public DownloadThreadPool getThreadPool() {
        return threadPool;
    }

    public Map<String, DownloadTask> getTaskMap() {
        return taskMap;
    }

    public DownloadTask getTask(String tag) {
        Logger.e(" tag = " + tag + " taskMap == null - > " + (taskMap == null));
        return taskMap.get(tag);
    }

    public boolean hasTask(String tag) {
        return taskMap.containsKey(tag);
    }

    public DownloadTask removeTask(String tag) {
        return taskMap.remove(tag);
    }

    public void addOnAllTaskEndListener(ObserveExecutor.OnAllTaskEndListener listener) {
        threadPool.getExecutor().addOnAllTaskEndListener(listener);
    }

    public void removeOnAllTaskEndListener(ObserveExecutor.OnAllTaskEndListener listener) {
        threadPool.getExecutor().removeOnAllTaskEndListener(listener);
    }

    /**
     * 移除所有监听
     */
    public void removeAllDownloadListener() {
        if (taskMap != null) {
            for (DownloadTask task : taskMap.values()) {
                task.listeners.clear();
            }
        }
    }

    /**
     * 移除下载监听 会自动加上url
     *
     * @param key listener key
     */
    public void removeDownloadListenerByKeyTag(String key) {
        if (taskMap != null) {
            for (DownloadTask task : taskMap.values()) {
                task.unRegister(DownloadListener.createTag(key, task.progress.tag));
            }
        }
    }

    public Context getContext() {
        return mContext;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
