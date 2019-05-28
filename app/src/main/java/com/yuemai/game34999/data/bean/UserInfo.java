package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/14  17:20
 * @email : 923080261@qq.com
 * @description : 用户信息
 */
public class UserInfo {
    private int id;
    private String headImgUrl;
    private String nick;
    private int sex;
    private String phone;
    private int age;
    private String birth;
    private String email;
    private String state;
    private int province;
    private int city;
    private int country;
    private String detailAddress;
    private int integral;
    private String userToken;
    private int useCode;
    private int loginType;
    private String qqCode;
    private String wechatCode;
    private String microBlogCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getUseCode() {
        return useCode;
    }

    public void setUseCode(int useCode) {
        this.useCode = useCode;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getQqCode() {
        return qqCode;
    }

    public void setQqCode(String qqCode) {
        this.qqCode = qqCode;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public String getMicroBlogCode() {
        return microBlogCode;
    }

    public void setMicroBlogCode(String microBlogCode) {
        this.microBlogCode = microBlogCode;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", nick='" + nick + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", birth='" + birth + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", province=" + province +
                ", city=" + city +
                ", country=" + country +
                ", detailAddress='" + detailAddress + '\'' +
                ", integral=" + integral +
                ", userToken='" + userToken + '\'' +
                ", useCode=" + useCode +
                ", loginType=" + loginType +
                ", qqCode='" + qqCode + '\'' +
                ", wechatCode='" + wechatCode + '\'' +
                ", microBlogCode='" + microBlogCode + '\'' +
                '}';
    }
}
