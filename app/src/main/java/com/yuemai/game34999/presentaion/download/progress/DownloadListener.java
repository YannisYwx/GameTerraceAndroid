package com.yuemai.game34999.presentaion.download.progress;

import com.yannis.common.util.Preconditions;

import java.io.File;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  14:38
 * @email : 923080261@qq.com
 * @description : 带有Tag标识的下载监听
 */
public abstract class DownloadListener implements ProgressListener<File> {

    public final Object tag;

    public DownloadListener(Object tag) {
        this.tag = tag;
    }

    /**
     * 创建tag标志
     * 最好最后以url结尾
     *
     * @param args tag array
     * @return arg1-arg2-arg3....-tag
     */
    public static String createTag(String... args) {
        StringBuilder stringBuilder = new StringBuilder(args[0]);
        Preconditions.checkNotNull(args);
        if (args.length == 0) {
            throw new IllegalArgumentException("args.length = 0");
        }
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]).append((i != args.length - 1) ? "-" : "");
        }

        return stringBuilder.toString();
    }
}
