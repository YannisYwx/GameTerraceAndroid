package com.yannis.common.net;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  18:02
 * @email : 923080261@qq.com
 * @description : 线程调度
 */
public class RxUtils {

    /**
     * 线程调度 io-main_thread
     */
    public static final ObservableTransformer DEFAULT_TRANSFORMER = new ObservableTransformer() {
        @Override public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 线程调度 io
     */
    public static final ObservableTransformer IO_TRANSFORMER = new ObservableTransformer() {
        @Override public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }
    };


    public static final <T> ObservableTransformer<T, T> applySchedulers(ObservableTransformer transformer){
        return (ObservableTransformer<T, T>)transformer;
    }
}
