package com.yannis.common.net.function;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  20:03
 * @email : 923080261@qq.com
 * @description :
 */
public class StringFunction implements Function<ResponseBody, String> {
    @Override
    public String apply(@NonNull ResponseBody responseBody){
        String result = null;
        try {
            result = responseBody.string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
