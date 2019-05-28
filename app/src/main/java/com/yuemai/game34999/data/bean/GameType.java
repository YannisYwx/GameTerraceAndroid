package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/3  18:04
 * @email : 923080261@qq.com
 * @description :游戏分类
 */
public class GameType {
    private int id;
    private String cname;
    private String icon;
    private String imgurl;
    private String tags;
    private int iconRes;
    private int bgRes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public int getBgRes() {
        return bgRes;
    }

    public void setBgRes(int bgRes) {
        this.bgRes = bgRes;
    }



    @Override
    public String toString() {
        return "GameType{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", icon='" + icon + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", tags='" + tags + '\'' +
                ", iconRes=" + iconRes +
                ", bgRes=" + bgRes +
                '}';
    }
}
