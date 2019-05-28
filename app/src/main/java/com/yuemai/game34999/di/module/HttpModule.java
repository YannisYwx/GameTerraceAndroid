package com.yuemai.game34999.di.module;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.yannis.common.util.NetStateUtils;
import com.yuemai.game34999.BuildConfig;
import com.yuemai.game34999.GameApp;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.data.http.api.ChangYanApi;
import com.yuemai.game34999.data.http.api.GameApi;
import com.yuemai.game34999.di.qualifier.ApplicationContext;
import com.yuemai.game34999.di.qualifier.ChangYanUrl;
import com.yuemai.game34999.di.qualifier.GameUrl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/11  18:33
 * @email : 923080261@qq.com
 * @description : 提供全局的Http依赖
 */
@Module
public class HttpModule {

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    @GameUrl
    Retrofit provideGameApiRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GameApi.HOST);
    }

    @Singleton
    @Provides
    @ChangYanUrl
    Retrofit provideChangYanApiRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ChangYanApi.HOST);
    }

    @Singleton
    @Provides
    GameApi provideGameApi(@GameUrl Retrofit retrofit) {
        return retrofit.create(GameApi.class);
    }

    @Singleton
    @Provides
    ChangYanApi provideChangYanApi(@ChangYanUrl Retrofit retrofit) {
        return retrofit.create(ChangYanApi.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(List<Interceptor> interceptors, OkHttpClient.Builder builder) {
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        //设置缓存
        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.interceptors().addAll(interceptors);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    @Singleton
    @Provides
    public List<Interceptor> provideInterceptors(@ApplicationContext GameApp app) {
        List<Interceptor> interceptors = new ArrayList<>();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptors.add(loggingInterceptor);
        }
//        //日志输出
//        Interceptor loggingInterceptor = (Interceptor.Chain chain) -> {
//            GetRequest mGetRequest = chain.mGetRequest();
//            long t1 = System.nanoTime();
//            Logger.e(String.format("Sending mGetRequest %s on %s%n%s",
//                    mGetRequest.url(), chain.connection(), mGetRequest.headers()));
//
//            Response response = chain.proceed(mGetRequest);
//            long t2 = System.nanoTime();
//            Logger.e(String.format("Received response for %s in %.1fms%n%s",
//                    response.mGetRequest().url(), (t2 - t1) / 1e6d, response.headers()));
//
//            MediaType mediaType = response.body().contentType();
//            String content = response.body().string();
//            Logger.e(content);
//
//            response = response.newBuilder()
//                    .body(ResponseBody.create(mediaType, content))
//                    .build();
//
//            return response;
//        };

        //提交数据
        Interceptor authInterceptor = chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Authorization")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();

            return chain.proceed(request.newBuilder()
                    .build());
        };

        //缓存数据
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
            if (!NetStateUtils.isNetworkConnected(app)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetStateUtils.isNetworkConnected(app)) {
                String cacheControl = originalResponse.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                //这里的设置的是我们的没有网络的缓存时间
                int maxTime = 4 * 24 * 60 * 60;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                        .removeHeader("Pragma")
                        .build();
            }
        };

//        interceptors.add(loggingInterceptor);
        interceptors.add(authInterceptor);
        interceptors.add(cacheInterceptor);
        return interceptors;
    }


    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
