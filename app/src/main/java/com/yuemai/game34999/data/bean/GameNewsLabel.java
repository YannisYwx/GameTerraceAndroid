package com.yuemai.game34999.data.bean;

import java.io.Serializable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/25  11:14
 * @email : 923080261@qq.com
 * @description : 新闻标签
 */
public class GameNewsLabel implements Serializable{
    private static final long serialVersionUID = 6201788777861355959L;
    private int ID;
    private String CName;
    private String Tags;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    @Override
    public String toString() {
        return "GameNewsLabel{" +
                "ID=" + ID +
                ", CName='" + CName + '\'' +
                ", Tags='" + Tags + '\'' +
                '}';
    }
}
