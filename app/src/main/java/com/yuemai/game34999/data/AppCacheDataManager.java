package com.yuemai.game34999.data;

import android.annotation.SuppressLint;

import com.yannis.common.util.Preconditions;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.data.bean.News;
import com.yuemai.game34999.util.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/10  16:11
 * @email : 923080261@qq.com
 * @description : app 数据缓存管理类
 */
public class AppCacheDataManager {
    /**
     * 游戏列表缓存的数据
     */
    private static Map<String, List<GameInfo>> classifyMap = new HashMap<>();

    /**
     * 新闻列表缓存的数据
     */
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, List<News>> newsMap = new HashMap<>();

    /**
     * 根据获取缓存中数据
     * 如果存在就返回pageIndex*Constants.PAGE_SIZE 如果不存在或者少于当前的页数就返回null
     *
     * @param dataSource 数据源
     * @param key        key
     * @param pageIndex  页码
     * @param <K>        K类型
     * @param <T>        数据类型
     * @return 需要的数据
     */
    private static <K, T> List<T> getCacheData(Map<K, List<T>> dataSource, K key, int pageIndex) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(dataSource);
        List<T> gameInfoList = dataSource.get(key);
        if (gameInfoList == null || gameInfoList.size() == 0) {
            return null;
        } else {
            int countPager = getPageIndex(gameInfoList);
            if (countPager == pageIndex) {
                return gameInfoList;
            } else if (countPager > pageIndex) {
                List<T> need = new ArrayList<>();
                for (int i = 0; i < pageIndex * Constants.PAGE_SIZE; i++) {
                    need.add(gameInfoList.get(i));
                }
                return need;
            } else {
                return null;
            }
        }
    }

    /**
     * 保存缓存数据
     *
     * @param dataSource 数据源
     * @param key        键
     * @param val        值
     * @param <K>        K类型
     * @param <T>        数据类型
     */
    private static <K, T> void putCacheData(Map<K, List<T>> dataSource, K key, List<T> val) {
        Preconditions.checkNotNull(dataSource, "dataSource = null");
        Preconditions.checkNotNull(key, "key = null");
        Preconditions.checkNotNull(val, "val = null");
        if (val.size() == 0) {
            return;
        }
        List<T> all = dataSource.get(key);
        if (all != null) {
            all.addAll(val);
        } else {
            dataSource.put(key, val);
        }
    }

    /*------------------------游戏列表--------------------------------*/

    public static List<GameInfo> getAllClassify(String key) {
        Preconditions.checkNotNull(key);
        return classifyMap.get(key);
    }

    public static List<GameInfo> getClassify(String key, int pageIndex) {
        return getCacheData(classifyMap, key, pageIndex);
    }

    public static void putClassify(final String key, final List<GameInfo> val) {
        putCacheData(classifyMap, key, val);
    }

    /*------------------------新闻列表--------------------------------*/

    public static List<News> getAllNews(int key) {
        Preconditions.checkNotNull(key);
        return newsMap.get(key);
    }


    public static List<News> getNews(int key, int pageIndex) {
        return getCacheData(newsMap, key, pageIndex);
    }

    public static void putNews(int key, final List<News> val) {
        putCacheData(newsMap, key, val);
    }

    /**
     * 保存游戏分类数据
     *
     * @param key
     * @param val
     */
    public static void putClassifyWithDifferent(final String key, final List<GameInfo> val) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(val);
        ThreadUtils.runOnBackgroundThread(() -> {
            List<GameInfo> all = classifyMap.get(key);
            if (all != null) {
                List<GameInfo> needRemove = new ArrayList<>();
                for (GameInfo info1 : val) {
                    for (GameInfo info2 : all) {
                        if (info1.getId() == info2.getId()) {
                            needRemove.add(info2);
                        }
                    }
                }
                all.removeAll(needRemove);
                all.addAll(val);
            } else {
                classifyMap.put(key, val);
            }
        });
    }


    /**
     * 获取当前是第几页的数据
     *
     * @param data 数据源
     * @return 页码
     */
    private static int getPageIndex(List<?> data) {
        Preconditions.checkNotNull(data);
        int pagerIndex;
        int count = data.size();
        pagerIndex = count / Constants.PAGE_SIZE;
        if (count % Constants.PAGE_SIZE != 0) {
            pagerIndex++;
        }
        return pagerIndex;
    }

    /**
     * 清除缓存
     */
    public void clearMemery() {
        classifyMap.clear();
        newsMap.clear();
        System.gc();
    }

}
