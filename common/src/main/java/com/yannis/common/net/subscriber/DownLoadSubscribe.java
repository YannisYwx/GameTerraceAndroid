package com.yannis.common.net.subscriber;

import android.support.annotation.NonNull;

import com.yannis.common.YannisApp;
import com.yannis.common.util.FileUtils;
import com.yannis.common.util.UIUtils;

import org.reactivestreams.Subscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/19  11:35
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class DownLoadSubscribe implements Subscriber<ResponseBody> {
    private String mSaveFilePath;
    private File mFile;

    public DownLoadSubscribe(@NonNull String fileName) {
        mSaveFilePath = FileUtils.getCacheDir(YannisApp.getInstance()).getAbsolutePath();
        mFile = new File(mSaveFilePath + File.separator + fileName);
    }

    public DownLoadSubscribe(@NonNull String filePath, @NonNull String fileName) {
        mSaveFilePath = filePath;
        mFile = new File(mSaveFilePath + File.separator + fileName);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        onCompleted(mFile);
    }

    public File getFile() {
        return mFile;
    }


    /**
     * 文件下载完成
     * @param file 文件
     */
    public abstract void onCompleted(File file);

    /**
     * 下载进度
     * @param progress 当前进度
     * @param downloadByte 当前下载字节
     * @param totalByte 总字节
     */
    public abstract void onProgress(double progress, long downloadByte, long totalByte);

    private long fileSizeDownloaded = 0;
    private long fileSize = 0;
    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                fileSize = body.contentLength();


                inputStream = body.byteStream();
                outputStream = new FileOutputStream(getFile());

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    UIUtils.getMainHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            onProgress(fileSizeDownloaded * 1.0f / fileSize, fileSizeDownloaded, fileSize);

                        }
                    });
                }

                outputStream.flush();

                return true;
            } catch (final IOException e) {
                UIUtils.getMainHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        onError(e);
                    }
                });

                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (final IOException e) {
            UIUtils.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    onError(e);
                }
            });
            return false;
        }
    }
}
