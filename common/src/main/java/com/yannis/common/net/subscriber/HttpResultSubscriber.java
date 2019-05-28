package com.yannis.common.net.subscriber;

import android.text.TextUtils;

import com.yannis.common.net.HttpResult;

import org.reactivestreams.Subscriber;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/19  14:06
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class HttpResultSubscriber<T> implements Subscriber<HttpResult<T>> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            e.printStackTrace();
            if (e.getMessage() == null) {
                _onError(new Throwable(e.toString()));
            } else {
                _onError(new Throwable(e.getMessage()));
            }
        } else {
            _onError(new Exception("null message"));
        }
    }

    @Override
    public void onNext(HttpResult<T> t) {
        if (!TextUtils.isEmpty(t.InfoMsg)) {
            onSuccess(t.data);
        } else {
            _onError(new Throwable(String.format("errorCode = $d, errorMsg = $s", t.StatusCode, t.InfoMsg)));
        }
    }

    public abstract void onSuccess(T t);

    public abstract void _onError(Throwable e);
}
