package com.yannis.common.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  16:52
 * @email : 923080261@qq.com
 * @description :
 */
public class ServiceFactory {
    private OkHttpClient mOkHttpClient;
    private Gson mGson;

    private ServiceFactory() {
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        mOkHttpClient = OkhttpFactory.getDefaultOkHttpClient();
    }


    public static ServiceFactory getNoCacheInstance() {
        ServiceFactory factory = ServiceFactoryHolder.INSTANCE;
        factory.mOkHttpClient = OkhttpFactory.getOkHttpClient();
        return factory;
    }

    private static class ServiceFactoryHolder {
        private static ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return ServiceFactoryHolder.INSTANCE;
    }

    public <T> T createSevice(Class<T> serviceClass) {
        String baseUrl = "";
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .build();
        return retrofit.create(serviceClass);
    }
}
