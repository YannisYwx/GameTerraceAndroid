package com.yuemai.game34999.presentaion.download.mode;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  14:21
 * @email : 923080261@qq.com
 * @description :优先级
 */
public interface Priority {
    int UI_TOP = Integer.MAX_VALUE;
    int UI_NORMAL = 1000;
    int UI_LOW = 100;
    int DEFAULT = 0;
    int BG_TOP = -100;
    int BG_NORMAL = -1000;
    int BG_LOW = Integer.MIN_VALUE;
}
