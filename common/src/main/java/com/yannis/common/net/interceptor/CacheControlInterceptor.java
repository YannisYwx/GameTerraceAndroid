package com.yannis.common.net.interceptor;

import android.text.TextUtils;

import com.yannis.common.YannisApp;
import com.yannis.common.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  16:05
 * @email : 923080261@qq.com
 * @description : 网络缓存拦截器
 * 没有网络的情况下就从缓存中取
 * 有网络的情况则从网络获取
 */
public class CacheControlInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected(YannisApp.getInstance())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (NetworkUtils.isConnected(YannisApp.getInstance())) {
            int maxAge = 60 * 60 * 2;//默认缓存两个小时
            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=" + maxAge;
            }
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();

        } else {
            int maxStale = 60 * 60 * 24 * 30;
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
