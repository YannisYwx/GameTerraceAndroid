package com.yuemai.game34999.core;

import android.os.Environment;

import com.yuemai.game34999.GameApp;
import com.yuemai.game34999.R;

import java.io.File;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/22  16:55
 * @email : 923080261@qq.com
 * @description : app常量
 */
public interface Constants {

    String LOCATION_ADDRESS = "_location_address";

    /**
     * apk文件下载保存目录
     */
    String DOWNLOAD_FOLDER = "/game34999_download_apk/";

    /**
     * 畅言
     */
    String APP_ID = "cyssBkYEb";

    int PAGE_SIZE = 10;

    /**
     * 移动端平台
     */
    int PLATFORM_CODE = 1;

    /**
     * 需要加载的视图
     */
    interface LoadPagerRes {
        int errorPager = R.layout.pager_error;
        int emptyPager = R.layout.pager_empty;
        int loadingPager = R.layout.pager_loading;
        int btnRetry = R.id.btn_retry;
    }

    String EXTRA = "_extra";

    String TRANSITION_ANIMATION_NEWS_PHOTOS = "transition_animation_news_photos";
    /**
     * 文章id
     */
    String ARTICLE_ID = "ARTICLE_ID";
    /**
     * 文章图片res
     */
    String ARTICLE_IMG_RES = "ARTICLE_IMG_RES";

    String PATH_DATA = GameApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/NetCache";

    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";


    interface SpConstants {
        String IS_FIRST_IN = "_isFirstIn";
    }
}
