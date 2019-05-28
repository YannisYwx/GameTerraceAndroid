package com.yuemai.game34999.core.mvp;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.ApiException;
import com.yuemai.game34999.core.mvp.inter.BaseContract;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/21  15:06
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    private BaseContract.BaseView mView;
    private String errorMsg;

    public CommonSubscriber(BaseContract.BaseView view) {
        mView = view;
    }

    public CommonSubscriber(BaseContract.BaseView view, String errorMsg) {
        mView = view;
        this.errorMsg = errorMsg;
    }

    @Override
    public void onError(Throwable t) {
        Logger.e("CommonSubscriber ---------------------- onError " + t.getMessage() + " --- " + t.toString());
        if (mView != null) {
            if (!TextUtils.isEmpty(errorMsg)) {
                mView.showMsg(errorMsg);
            } else if (t instanceof ApiException) {
                mView.showMsg(t.toString());
            } else if (t instanceof HttpException) {
                mView.showMsg("数据加载失败ヽ(≧Д≦)ノ");
            } else {
                mView.showMsg("未知错误ヽ(≧Д≦)ノ" + "\n" + t.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {
        Logger.e("CommonSubscriber ---------------------- onComplete");
        if (mView != null) {
            mView.onComplete();
        }
    }
}
