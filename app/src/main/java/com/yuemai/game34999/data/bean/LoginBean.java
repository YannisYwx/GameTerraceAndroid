package com.yuemai.game34999.data.bean;

import static com.yuemai.game34999.core.Constants.PLATFORM_CODE;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/21  11:27
 * @email : 923080261@qq.com
 * @description :
 */
public class LoginBean {
    /**
     * 0:qq 1:weChat 2: weiBo
     */
    private int loginType;
    private String uid;
    private String userName;
    private String headIconUrl;
    private String gender;
    /**
     * 设备平台 101 android
     */
    private int useCode;
    private String userToken;

    public LoginBean() {
        useCode = PLATFORM_CODE;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUseCode() {
        return useCode;
    }

    public void setUseCode(int useCode) {
        this.useCode = useCode;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "loginType=" + loginType +
                ", uid='" + uid + '\'' +
                ", userName='" + userName + '\'' +
                ", headIconUrl='" + headIconUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", useCode=" + useCode +
                ", userToken='" + userToken + '\'' +
                '}';
    }
}
