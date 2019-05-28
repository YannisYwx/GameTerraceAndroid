package com.yuemai.game34999.presentaion.download.net.callback.convert;

import android.os.Environment;
import android.text.TextUtils;

import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.download.net.callback.Callback;
import com.yuemai.game34999.presentaion.download.utils.HttpUtils;
import com.yuemai.game34999.presentaion.download.utils.IOUtils;
import com.yuemai.game34999.util.ThreadUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/9  14:04
 * @email : 923080261@qq.com
 * @description :
 */
public class FileConvert implements Converter<File> {

    /**
     * 下载目标文件夹
     */
    public static final String DM_TARGET_FOLDER = File.separator + "download" + File.separator;
    /**
     * 目标文件存储的文件夹路径
     */
    private String folder;
    /**
     * 目标文件存储的文件名
     */
    private String fileName;
    /**
     * 下载回调
     */
    private Callback<File> callback;

    public FileConvert() {
        this(null);
    }

    public FileConvert(String fileName) {
        this(Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER, fileName);
    }

    public FileConvert(String folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    public void setCallback(Callback<File> callback) {
        this.callback = callback;
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        String url = response.request().url().toString();
        if (TextUtils.isEmpty(folder)) {
            folder = Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER;
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = HttpUtils.getNetFileName(response, url);
        }

        File dir = new File(folder);
        IOUtils.createFolder(dir);
        File file = new File(dir, fileName);
        IOUtils.delFileOrFolder(file);

        InputStream bodyStream = null;
        byte[] buffer = new byte[8192];
        FileOutputStream fileOutputStream = null;
        try {
            ResponseBody body = response.body();
            if (body == null) {
                return null;
            }

            bodyStream = body.byteStream();
            Progress progress = new Progress();
            progress.totalSize = body.contentLength();
            progress.fileName = fileName;
            progress.filePath = file.getAbsolutePath();
            progress.status = Progress.LOADING;
            progress.url = url;
            progress.tag = url;

            int len;
            fileOutputStream = new FileOutputStream(file);
            while ((len = bodyStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);

                if (callback == null) {
                    continue;
                }
                Progress.changeProgress(progress, len, this::onProgress);
            }
            fileOutputStream.flush();
            return file;
        } finally {
            IOUtils.closeQuietly(bodyStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    private void onProgress(final Progress progress) {
        ThreadUtils.runOnUiThread(() -> callback.downloadProgress(progress));
    }

}
