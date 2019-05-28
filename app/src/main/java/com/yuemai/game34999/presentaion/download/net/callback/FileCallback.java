package com.yuemai.game34999.presentaion.download.net.callback;

import com.yuemai.game34999.presentaion.download.net.callback.convert.FileConvert;

import java.io.File;

import okhttp3.Response;


/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/8  16:47
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class FileCallback implements Callback<File> {
    private FileConvert convert;

    public FileCallback() {
        this(null);
    }

    public FileCallback(String destFileName) {
        this(null, destFileName);
    }

    public FileCallback(String destFileDir, String destFileName) {
        convert = new FileConvert(destFileDir, destFileName);
        convert.setCallback(this);
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        File file = convert.convertResponse(response);
        response.close();
        return file;
    }

}
