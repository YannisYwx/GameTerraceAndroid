package com.yannis.common.net;

import com.yannis.common.net.function.HttpResultFunction;
import com.yannis.common.net.function.RetryWhenNetworkException;
import com.yannis.common.net.function.StringFunction;
import com.yannis.common.net.service.CommonService;
import com.yannis.common.net.subscriber.DownLoadSubscribe;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/19  14:51
 * @email : 923080261@qq.com
 * @description :
 */
public class ObservableProvider {
    private final CommonService mCommonService;

    private static class ObservableProviderHolder {
        private static ObservableProvider INSTANCE = new ObservableProvider();
    }

    public static ObservableProvider getInstance() {
        return ObservableProviderHolder.INSTANCE;
    }

    private ObservableProvider() {
        mCommonService = ServiceFactory.getInstance().createSevice(CommonService.class);
    }

    /**
     * 加载数据
     * @param url
     * @return
     */
    public Observable<String> loadString(String url) {
        return mCommonService
                .loadStr(url)
                .compose(RxUtils.<ResponseBody>applySchedulers(RxUtils.DEFAULT_TRANSFORMER))
                .retryWhen(new RetryWhenNetworkException())
                .map(new StringFunction());

    }

    /**
     * 加载自定义的HTTP RESULT数据
     * @param url
     * @param <T>
     * @return
     */
    public <T> Observable<HttpResult<T>> loadHttpResult(String url) {
        return loadString(url).map(new HttpResultFunction<T>());
    }

    /**
     * 下载
     * @param url
     * @param subscribe
     */
    public void download(String url, final DownLoadSubscribe subscribe) {
        mCommonService
                .download(url)
                .compose(RxUtils.<ResponseBody>applySchedulers(RxUtils.IO_TRANSFORMER))
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        subscribe.writeResponseBodyToDisk(responseBody);
                    }
                }).compose(RxUtils.<ResponseBody>applySchedulers(RxUtils.DEFAULT_TRANSFORMER))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        subscribe.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        subscribe.onComplete();
                    }
                });

    }


}
