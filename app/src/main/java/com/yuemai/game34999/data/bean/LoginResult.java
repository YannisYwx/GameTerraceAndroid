package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/25  19:10
 * @email : 923080261@qq.com
 * @description :{"result":true,"uaccount":"13600169041","uid":"40","realname":"","usertoken":"F2D8AB7C3B3D78DA08D48A7EC5607C60"},
 */
public class LoginResult {
    private boolean result;
    private String uaccount;
    private String uid;
    private String realname;
    private String usertoken;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getUaccount() {
        return uaccount;
    }

    public void setUaccount(String uaccount) {
        this.uaccount = uaccount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "result=" + result +
                ", uaccount='" + uaccount + '\'' +
                ", uid='" + uid + '\'' +
                ", realname='" + realname + '\'' +
                ", usertoken='" + usertoken + '\'' +
                '}';
    }
}
