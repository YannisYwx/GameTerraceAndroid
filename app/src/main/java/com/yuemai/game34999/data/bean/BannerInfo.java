package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  12:50
 * @email : 923080261@qq.com
 * @description :
 */
public class BannerInfo {
    private int id;
    private String bannerName;
    private String serverImgurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getServerImgurl() {
        return serverImgurl;
    }

    public void setServerImgurl(String serverImgurl) {
        this.serverImgurl = serverImgurl;
    }

    @Override
    public String toString() {
        return "BannerInfo{" +
                "id=" + id +
                ", bannerName='" + bannerName + '\'' +
                ", serverImgurl='" + serverImgurl + '\'' +
                '}';
    }
}
