package com.yannis.common.net;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  20:07
 * @email : 923080261@qq.com
 * @description :
 */
public class HttpResult<T> {
    public int StatusCode;
    public String InfoMsg;
    public T data;
}
