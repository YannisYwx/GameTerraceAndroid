package com.yannis.common.net.function;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  19:03
 * @email : 923080261@qq.com
 * @description : 网络出错是重新连接
 * final Function<? super Observable<Throwable>, ? extends ObservableSource<?>> handler
 */
public class RetryWhenNetworkException implements Function<Observable<? extends Throwable>, ObservableSource<?>> {
    private int count = 3;//retry count
    private long delay = 3_000;//delay time

    public RetryWhenNetworkException() {

    }

    public RetryWhenNetworkException(int count) {
        this.count = count;
    }

    public RetryWhenNetworkException(int count, long delay) {
        this.count = Math.max(2, count);
        this.delay = Math.max(1_000, delay);
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
        return observable.zipWith(Observable.range(1, count), new BiFunction<Throwable, Integer, Wrapper>() {


            @Override
            public Wrapper apply(@NonNull Throwable t, @NonNull Integer integer) throws Exception {
                return new Wrapper(t, integer);
            }
        }).flatMap(new Function<Wrapper, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Wrapper wrapper) throws Exception {
                if ((wrapper.throwable instanceof ConnectException
                        || wrapper.throwable instanceof SocketTimeoutException
                        || wrapper.throwable instanceof TimeoutException)
                        && wrapper.index < count + 1) {
                    return Observable.timer(delay + (wrapper.index - 1) * delay, TimeUnit.MILLISECONDS);
                }
                return Observable.error(wrapper.throwable);
            }
        });
    }


    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
