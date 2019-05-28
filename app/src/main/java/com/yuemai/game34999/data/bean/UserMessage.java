package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/29  11:51
 * @email : 923080261@qq.com
 * @description : 用户消息
 *
 * {
"id": 8,
"uid": 1,
"title": "注册成功",
"aContent": "尊敬的用户,您于 05 25 2017  3:00PM 注册会员成功!",
"isRead": 0,
"readName": "未读",
"createTime": "2017-05-25 15:00:35"
}
 */
public class UserMessage{
    private int id;
    private int uid;
    private String title;
    private String aContent;
    private int isRead;
    private String readName;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getReadName() {
        return readName;
    }

    public void setReadName(String readName) {
        this.readName = readName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", aContent='" + aContent + '\'' +
                ", isRead=" + isRead +
                ", readName='" + readName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
