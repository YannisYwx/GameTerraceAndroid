package com.yannis.common.rx;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/7  16:59
 * @email : 923080261@qq.com
 * @description : 暂定的网络格式
 */
public class HttpResponse<T> {
    private int StatusCode;
    private String Message;
    private boolean isError;
    private T Data;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "StatusCode=" + StatusCode +
                ", Message='" + Message + '\'' +
                ", isError=" + isError +
                ", Data=" + Data +
                '}';
    }
}
