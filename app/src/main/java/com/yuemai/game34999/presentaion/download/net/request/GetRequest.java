/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuemai.game34999.presentaion.download.net.request;

import com.yannis.common.util.Preconditions;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.adapter.AdapterParam;
import com.yuemai.game34999.presentaion.download.adapter.CallAdapter;
import com.yuemai.game34999.presentaion.download.mode.HttpHeaders;
import com.yuemai.game34999.presentaion.download.mode.HttpMethod;
import com.yuemai.game34999.presentaion.download.mode.HttpParams;
import com.yuemai.game34999.presentaion.download.net.call.Call;
import com.yuemai.game34999.presentaion.download.net.call.CallPolicy;
import com.yuemai.game34999.presentaion.download.net.callback.Callback;
import com.yuemai.game34999.presentaion.download.net.callback.convert.Converter;
import com.yuemai.game34999.presentaion.download.utils.HttpUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/12
 * 描    述：所有请求的基类，其中泛型 R 主要用于属性设置方法后，返回对应的子类型，以便于实现链式调用
 * 修订历史：
 * ================================================
 */
public class GetRequest<T> implements Serializable {
    private static final long serialVersionUID = -7174118653689916252L;

    protected String url;
    protected String baseUrl;
    protected transient OkHttpClient client;
    protected transient Object tag;
    protected int retryCount;

    /**
     * 添加的param
     */
    protected HttpParams params = new HttpParams();
    /**
     * 添加的header
     */
    protected HttpHeaders headers = new HttpHeaders();

    protected transient okhttp3.Request mRequest;
    protected transient Call<T> call;
    protected transient Callback<T> callback;
    protected transient Converter<T> converter;
    protected transient CallPolicy<T> callPolicy;

    public GetRequest(String url) {
        this.url = url;
        baseUrl = url;
        //默认添加 Accept-Language
//        String acceptLanguage = HttpHeaders.getAcceptLanguage();
//        if (!TextUtils.isEmpty(acceptLanguage)) {
//            headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
//        }
//        //默认添加 User-Agent
//        String userAgent = HttpHeaders.getUserAgent();
//        if (!TextUtils.isEmpty(userAgent)) {
//            headers(HttpHeaders.HEAD_KEY_USER_AGENT, userAgent);
//        }
        retryCount = 3;
    }

    @SuppressWarnings("unchecked")
    public GetRequest tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest retryCount(int retryCount) {
        if (retryCount < 0) {
            throw new IllegalArgumentException("retryCount must > 0");
        }
        this.retryCount = retryCount;
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest client(OkHttpClient client) {
        client = Preconditions.checkNotNull(client, "OkHttpClient == null");
        this.client = client;
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest call(Call<T> call) {
        Preconditions.checkNotNull(call, "call == null");

        this.call = call;
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest converter(Converter<T> converter) {
        Preconditions.checkNotNull(converter, "converter == null");
        this.converter = converter;
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest cachePolicy(CallPolicy<T> callPolicy) {
        Preconditions.checkNotNull(callPolicy, "cachePolicy == null");
        this.callPolicy = callPolicy;
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest headers(HttpHeaders headers) {
        this.headers.put(headers);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest headers(String key, String value) {
        headers.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest removeHeader(String key) {
        headers.remove(key);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest removeAllHeaders() {
        headers.clear();
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(HttpParams params) {
        this.params.put(params);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(Map<String, String> params, boolean... isReplace) {
        this.params.put(params, isReplace);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(String key, String value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(String key, int value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(String key, float value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(String key, double value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(String key, long value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(String key, char value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest params(String key, boolean value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest addUrlParams(String key, List<String> values) {
        params.putUrlParams(key, values);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest removeParam(String key) {
        params.remove(key);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GetRequest removeAllParams() {
        params.clear();
        return this;
    }


    /**
     * 默认返回第一个参数
     */
    public String getUrlParam(String key) {
        List<String> values = params.urlParamsMap.get(key);
        if (values != null && values.size() > 0) {
            return values.get(0);
        }
        return null;
    }

    /**
     * 默认返回第一个参数
     */
    public HttpParams.FileWrapper getFileParam(String key) {
        List<HttpParams.FileWrapper> values = params.fileParamsMap.get(key);
        if (values != null && values.size() > 0) {
            return values.get(0);
        }
        return null;
    }

    public HttpParams getParams() {
        return params;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Object getTag() {
        return tag;
    }

    public CallPolicy<T> getCallPolicy() {
        return callPolicy;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public okhttp3.Request getRequest() {
        return mRequest;
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    public Converter<T> getConverter() {
        // converter 优先级高于 callback
        if (converter == null) {
            converter = callback;
        }
        Preconditions.checkNotNull(converter, "converter == null, do you forget to call GetRequest#converter(Converter<T>) ?");
        return converter;
    }

    /**
     * 获取okhttp的同步call对象
     */
    public okhttp3.Call getRawCall() {
        mRequest = generateRequest();
        if (client == null) {
            client = DownloadManager.getInstance().getOkHttpClient();
        }
        return client.newCall(mRequest);
    }

    /**
     * Rx支持，获取同步call对象
     */
    public Call<T> adapt() {
        if (call == null) {
            return new CallPolicy<>(this);
        } else {
            return call;
        }
    }

    /**
     * Rx支持,获取同步call对象
     */
    public <E> E adapt(CallAdapter<T, E> adapter) {
        Call<T> innerCall = call;
        if (innerCall == null) {
            innerCall = new CallPolicy<>(this);
        }
        return adapter.adapt(innerCall, null);
    }

    /**
     * Rx支持,获取同步call对象
     */
    public <E> E adapt(AdapterParam param, CallAdapter<T, E> adapter) {
        Call<T> innerCall = call;
        if (innerCall == null) {
            innerCall = new CallPolicy<>(this);
        }
        return adapter.adapt(innerCall, param);
    }

    /**
     * 普通调用，阻塞方法，同步请求执行
     */
    public Response execute() throws IOException {
        return getRawCall().execute();
    }

    /**
     * 非阻塞方法，异步请求，但是回调在子线程中执行
     */
    public void execute(Callback<T> callback) {
        Preconditions.checkNotNull(callback, "callback == null");
        this.callback = callback;
        Call<T> call = adapt();
        call.execute(callback);
    }

    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    protected RequestBody generateRequestBody() {
        return null;
    }

    public okhttp3.Request generateRequest() {
        okhttp3.Request.Builder requestBuilder = generateRequestBuilder();
        return requestBuilder.get().url(url).tag(tag).build();
    }

    protected okhttp3.Request.Builder generateRequestBuilder() {
        url = HttpUtils.createUrlFromParams(baseUrl, params.urlParamsMap);
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        return HttpUtils.appendHeaders(requestBuilder, headers);
    }
}
