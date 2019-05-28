package com.yannis.common.net.interceptor;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  16:05
 * @email : 923080261@qq.com
 * @description : 强制从网络获取
 */
public class ForceNetworkControlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();

        Response response = chain.proceed(request);

        return response;
    }
}
