package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  16:09
 * @email : 923080261@qq.com
 * @description : 注册实体类
 */
public class RegisterBean {
    private String uaccount;
    private String authcode;
    private String pwd;
    private String salt;
    private int napwdcom;
    private String invitationCode;
    private String ip;
    private String address;
    private int terminal;
    private String usertoken;

    public String getUaccount() {
        return uaccount;
    }

    public void setUaccount(String uaccount) {
        this.uaccount = uaccount;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getNapwdcom() {
        return napwdcom;
    }

    public void setNapwdcom(int napwdcom) {
        this.napwdcom = napwdcom;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "uaccount='" + uaccount + '\'' +
                ", authcode='" + authcode + '\'' +
                ", pwd='" + pwd + '\'' +
                ", salt='" + salt + '\'' +
                ", napwdcom=" + napwdcom +
                ", invitationCode='" + invitationCode + '\'' +
                ", ip='" + ip + '\'' +
                ", address='" + address + '\'' +
                ", terminal=" + terminal +
                ", usertoken='" + usertoken + '\'' +
                '}';
    }
}
