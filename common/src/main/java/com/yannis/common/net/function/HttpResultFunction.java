package com.yannis.common.net.function;

import com.yannis.common.json.JsonConvert;
import com.yannis.common.net.HttpResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  20:07
 * @email : 923080261@qq.com
 * @description :
 */
public class HttpResultFunction<T> implements Function<String, HttpResult<T>> {
    @Override
    public HttpResult<T> apply(@NonNull String s) throws Exception {
        JsonConvert<HttpResult<T>> convert = new JsonConvert<>();
        return convert.parseData(s);
    }
}
