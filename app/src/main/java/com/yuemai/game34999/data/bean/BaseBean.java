package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  16:49
 * @email : 923080261@qq.com
 * @description :
 */
public class BaseBean {
    String msg;

    boolean flag;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "msg='" + msg + '\'' +
                ", flag=" + flag +
                '}';
    }
}
