package com.yuemai.game34999.data.bean;

import java.io.Serializable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/3  17:43
 * @email : 923080261@qq.com
 * @description : 游戏详情
 */
public class GameInfo implements Serializable {
    private static final long serialVersionUID = 3138897407160772820L;
    private int id;
    private String gname;
    private String gtitle;
    private String gicon;
    private int andrioddownload;
    private int androidfilesize;
    private String androidfileurl;
    private String tags;
    private int versioncode;
    private String versionname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGtitle() {
        return gtitle;
    }

    public void setGtitle(String gtitle) {
        this.gtitle = gtitle;
    }

    public String getGicon() {
        return gicon;
    }

    public void setGicon(String gicon) {
        this.gicon = gicon;
    }

    public int getAndrioddownload() {
        return andrioddownload;
    }

    public void setAndrioddownload(int andrioddownload) {
        this.andrioddownload = andrioddownload;
    }

    public int getAndroidfilesize() {
        return androidfilesize;
    }

    public void setAndroidfilesize(int androidfilesize) {
        this.androidfilesize = androidfilesize;
    }

    public String getAndroidfileurl() {
        return androidfileurl;
    }

    public void setAndroidfileurl(String androidfileurl) {
        this.androidfileurl = androidfileurl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "id=" + id +
                ", gname='" + gname + '\'' +
                ", gtitle='" + gtitle + '\'' +
                ", gicon='" + gicon + '\'' +
                ", andrioddownload='" + andrioddownload + '\'' +
                ", androidfilesize='" + androidfilesize + '\'' +
                ", androidfileurl='" + androidfileurl + '\'' +
                ", tags='" + tags + '\'' +
                ", versioncode='" + versioncode + '\'' +
                ", versionname='" + versionname + '\'' +
                '}';
    }
}
