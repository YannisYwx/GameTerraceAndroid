package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/25  19:02
 * @email : 923080261@qq.com
 * @description :
 * {"account":"13600169041","address":"","pwd":"123456","terminal":"1"}
 */
public class AccountLoginBean {
    private String account;
    private String address;
    private String pwd;
    private String terminal;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        return "AccountLoginBean{" +
                "account='" + account + '\'' +
                ", address='" + address + '\'' +
                ", pwd='" + pwd + '\'' +
                ", terminal='" + terminal + '\'' +
                '}';
    }
}
