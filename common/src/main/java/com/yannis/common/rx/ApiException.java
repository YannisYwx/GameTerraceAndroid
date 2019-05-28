package com.yannis.common.rx;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/7  17:08
 * @email : 923080261@qq.com
 * @description :
 */
public class ApiException extends Exception {
    private int errorCode = -1;

    public ApiException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
