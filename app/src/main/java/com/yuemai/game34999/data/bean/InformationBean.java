package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/24  17:57
 * @email : 923080261@qq.com
 * @description :
 */
public class InformationBean {
    private String imgUrl;
    private String title;
    private String info;
    private String date;
    private String previewNum;
    private String gameType;
    private int informationType;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPreviewNum() {
        return previewNum;
    }

    public void setPreviewNum(String previewNum) {
        this.previewNum = previewNum;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getInformationType() {
        return informationType;
    }

    public void setInformationType(int informationType) {
        this.informationType = informationType;
    }

    @Override
    public String toString() {
        return "InformationBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", date='" + date + '\'' +
                ", previewNum='" + previewNum + '\'' +
                ", gameType='" + gameType + '\'' +
                ", informationType=" + informationType +
                '}';
    }
}
