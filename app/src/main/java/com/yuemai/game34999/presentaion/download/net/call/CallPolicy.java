package com.yuemai.game34999.presentaion.download.net.call;

import com.google.common.base.Preconditions;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.mode.Response;
import com.yuemai.game34999.presentaion.download.net.callback.Callback;
import com.yuemai.game34999.presentaion.download.net.exception.HttpException;
import com.yuemai.game34999.presentaion.download.net.request.GetRequest;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/8  18:15
 * @email : 923080261@qq.com
 * @description : 已经准备好的请求代理
 */
public class CallPolicy<T> implements Call<T> {
    protected GetRequest<T> mGetRequest;
    protected volatile boolean canceled;
    protected volatile int currentRetryCount = 0;
    protected boolean executed;
    protected okhttp3.Call rawCall;
    protected Callback<T> mCallback;


    public CallPolicy(GetRequest<T> getRequest) {
        this.mGetRequest = getRequest;
    }

    @Override
    public Response<T> execute() throws Exception {
        return requestSync();
    }

    @Override
    public void execute(Callback<T> callback) {
        mCallback = Preconditions.checkNotNull(callback,"callback == null");
        requestAsync(mCallback);
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    @Override
    public void cancel() {
        canceled = true;
        if (rawCall != null) {
            rawCall.cancel();
        }
    }

    @Override
    public boolean isCanceled() {
        if (canceled) {
            return true;
        }
        synchronized (this) {
            return rawCall != null && rawCall.isCanceled();
        }
    }

    @Override
    public Call<T> clone() {
        return new CallPolicy<>(mGetRequest);
    }

    @Override
    public GetRequest getGetRequest() {
        return mGetRequest;
    }

    /**
     * 同步请求
     * @return
     */
    private Response<T> requestSync() {
        try {
            prepareRawCall();
        } catch (Throwable throwable) {
            return Response.error(false, rawCall, null, throwable);
        }
        return requestNetworkSync();
    }

    /**
     * 异步请求
     * @param callback 回到接口
     */
    private void requestAsync(Callback<T> callback) {
        mCallback = callback;
        runOnUiThread(() -> {
            mCallback.onStart(mGetRequest);
            try {
                prepareRawCall();
            } catch (Throwable throwable) {
                Response<T> error = Response.error(false, rawCall, null, throwable);
                mCallback.onError(error);
                return;
            }
            requestNetworkAsync();
        });
    }


    /**
     * 主线程回调
     *
     * @param run
     */
    private void runOnUiThread(Runnable run) {
        DownloadManager.getInstance().getDelivery().post(run);
    }

    /**
     * 同步网络请求
     *
     *
     * @return
     */
    private Response<T> requestNetworkSync() {
        try {
            okhttp3.Response response = rawCall.execute();
            int responseCode = response.code();

            //network error
            if (responseCode == 404 || responseCode >= 500) {
                return Response.error(false, rawCall, response, HttpException.NET_ERROR());
            }

            T body = mGetRequest.getConverter().convertResponse(response);
            return Response.success(false, body, rawCall, response);
        } catch (Throwable throwable) {
            if (throwable instanceof SocketTimeoutException && currentRetryCount < mGetRequest.getRetryCount()) {
                currentRetryCount++;
                rawCall = mGetRequest.getRawCall();
                if (canceled) {
                    rawCall.cancel();
                } else {
                    requestNetworkSync();
                }
            }
            return Response.error(false, rawCall, null, throwable);
        }
    }

    /**
     * 异步网络请求
     */
    private void requestNetworkAsync() {
        rawCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (e instanceof SocketTimeoutException && currentRetryCount < mGetRequest.getRetryCount()) {
                    //retry when timeout
                    currentRetryCount++;
                    rawCall = mGetRequest.getRawCall();
                    if (canceled) {
                        rawCall.cancel();
                    } else {
                        rawCall.enqueue(this);
                    }
                } else {
                    if (!call.isCanceled()) {
                        Response<T> error = Response.error(false, call, null, e);
                        onError(error);
                    }
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                int responseCode = response.code();

                //network error
                if (responseCode == 404 || responseCode >= 500) {
                    Response<T> error = Response.error(false, call, response, HttpException.NET_ERROR());
                    onError(error);
                    return;
                }

                try {
                    T body = mGetRequest.getConverter().convertResponse(response);
                    Response<T> success = Response.success(false, body, call, response);
                    onSuccess(success);
                } catch (Throwable throwable) {
                    Response<T> error = Response.error(false, call, response, throwable);
                    onError(error);
                }
            }
        });
    }

    /**
     * 获得Okhttp call
     * @throws Throwable
     */
    public synchronized void prepareRawCall() throws Throwable {
        if (executed) {
            throw HttpException.COMMON("Already executed!");
        }
        executed = true;
        rawCall = mGetRequest.getRawCall();
        if (canceled) {
            rawCall.cancel();
        }
    }

    /**
     * 网络请求成功
     * @param success 成功结果
     */
    private void onSuccess(final Response<T> success) {
        runOnUiThread(() -> {
            mCallback.onSuccess(success);
            mCallback.onFinish();
        });
    }

    /**
     * 网络请求失败
     * @param error 失败结果
     */
    private void onError(final Response<T> error) {
        runOnUiThread(() -> {
            mCallback.onError(error);
            mCallback.onFinish();
        });
    }

}
