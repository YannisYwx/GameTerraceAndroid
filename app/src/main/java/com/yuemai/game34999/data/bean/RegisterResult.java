package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  16:57
 * @email : 923080261@qq.com
 * @description :
 */
public class RegisterResult extends BaseBean{

    private String utoken;

    public String getUtoken() {
        return utoken;
    }

    public void setUtoken(String utoken) {
        this.utoken = utoken;
    }

    @Override
    public String toString() {
        return "RegisterResult{" +
                "msg='" + msg + '\'' +
                ", flag=" + flag +
                ", utoken='" + utoken + '\'' +
                '}';
    }
}
