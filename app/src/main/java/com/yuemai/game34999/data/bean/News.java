package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/25  17:24
 * @email : 923080261@qq.com
 * @description :
 */
public class News {
    private int ID;
    private String NewsTitle;
    private String NewsIcon;
    private int ReadNum;
    private String CreateTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsIcon() {
        return NewsIcon;
    }

    public void setNewsIcon(String newsIcon) {
        NewsIcon = newsIcon;
    }

    public int getReadNum() {
        return ReadNum;
    }

    public void setReadNum(int readNum) {
        ReadNum = readNum;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    @Override
    public String toString() {
        return "News{" +
                "ID=" + ID +
                ", NewsTitle='" + NewsTitle + '\'' +
                ", NewsIcon='" + NewsIcon + '\'' +
                ", ReadNum=" + ReadNum +
                ", CreateTime='" + CreateTime + '\'' +
                '}';
    }
}
