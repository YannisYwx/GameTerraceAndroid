package com.yannis.common.net.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Url;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/19  10:28
 * @email : 923080261@qq.com
 * @description : 通用的加载
 */
public interface CommonService {

    /**
     * 加载字符串数据
     * @param url path
     * @return ResponseBody
     */
    Observable<ResponseBody> loadStr(@Url String url);

    /**
     * 下载
     * @param url path
     * @return ResponseBody
     */
    Observable<ResponseBody> download(@Url String url);
}
