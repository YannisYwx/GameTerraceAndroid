package com.yuemai.game34999.util;

import android.text.format.Formatter;

import com.orhanobut.logger.Logger;
import com.yuemai.game34999.GameApp;
import com.yuemai.game34999.data.bean.GameInfo;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/25  14:16
 * @email : 923080261@qq.com
 * @description :
 */
public class AppFormatUtils {
    static DecimalFormat df = new java.text.DecimalFormat("#.0");
    static StringBuilder sb = new StringBuilder();


    public static <T> void log(List<T> objects) {

        sb.delete(0, sb.length());
        assert objects != null;
        for (T obj : objects) {
            sb.append(obj.toString()).append("\n");
        }
        Logger.e(sb.toString());
    }

    /**
     * 转换apk大小
     *
     * @param size 字节长度
     * @return
     */
    public static String formatApkSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    /**
     * 下载次数 小于1w按个计 大于一万按W计
     *
     * @param downloadSize 下载数
     * @return num
     */
    public static String formatDownloadSize(int downloadSize) {
        StringBuilder sb = new StringBuilder();
        if (downloadSize < 1_0000) {
            return sb.append(downloadSize).append("次下载").toString();
        } else {
            float size = downloadSize / 1_0000.0f;
            return sb.append(df.format(size)).append("万次下载").toString();
        }
    }

    /**
     * 获取游戏信息
     *
     * @param gameInfo 游戏
     * @return xx
     */
    public static String formatDownloadInfo(GameInfo gameInfo) {
        return String.format("%s %s", AppFormatUtils.formatDownloadSize(gameInfo.getAndrioddownload()), Formatter.formatFileSize(GameApp.instance, gameInfo.getAndroidfilesize()));
    }


    /**
     * 将下载速度B 转换成 x/s格式
     *
     * @param speed 每秒的下载速度 单位B
     * @return x/s
     */
    public static String formatApkDownloadSpeed(long speed) {
        String downloadSpeed = Formatter.formatFileSize(GameApp.instance, speed);
        return String.format("%s/s", downloadSpeed);
    }

    /**
     * 当前下载与总大小的对比
     *
     * @param currentSize 当前已下载的大小 单位B
     * @param totalSize   APK总大小 单位B
     * @return 125MB/250MB格式
     */
    public static String formatApkDownloadPercent(long currentSize, long totalSize) {
        return String.format("%s/%s",
                Formatter.formatFileSize(GameApp.instance, currentSize),
                Formatter.formatFileSize(GameApp.instance, totalSize));
    }
}
