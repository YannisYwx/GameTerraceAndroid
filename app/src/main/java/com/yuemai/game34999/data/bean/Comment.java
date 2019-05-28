package com.yuemai.game34999.data.bean;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/31  15:36
 * @email : 923080261@qq.com
 * @description : 评论
 * <p>
 * {
 * "attachments": [],
 * <p>
 * "comment_id": 1431723927,
 * <p>
 * "comments": [],
 * <p>
 * "content": "斤斤计较",
 * "create_time": 1501642718000,
 * "elite": false,
 * "floor_count": 0,
 * "from": "就不告诉你我是页面仔wap版",
 * "hide": false,
 * "hide_floor": false,
 * "highlight": false,
 * "ip": "123.126.70.239",
 * "ip_location": "北京市",
 * <p>
 * "metadata": "{\"clientPort\":\"47317\"}",
 * <p>
 * "metadataAsJson": {
 * "clientPort": "47317"
 * },
 * <p>
 * "oppose_count": 0,
 * <p>
 * <p>
 * "passport": {
 * "expired": false,
 * "fee": 0,
 * "followers_count": 0,
 * "from": "",
 * "grant": false,
 * "img_url": "http://wx.qlogo.cn/mmopen/atScMCT1D5iaaelbGeUfaCKMUWTYqmU90iaMicPI2I6293iaWO2E6qjX3687ia7mhZpm06RHOCW9H8xwkZoy12ia6683UMteoFRwXa/0",
 * "is_official": false,
 * "is_shared": false,
 * "nickname": "达芬奇",
 * "platform_id": 13,
 * "user_id": 852380481
 * },
 * <p>
 * <p>
 * "quick": false,
 * "reply_count": 0,
 * "reply_id": 1431723822,
 * <p>
 * <p>
 * <p>
 * "reply_passport": {
 * "expired": false,
 * "fee": 0,
 * "followers_count": 0,
 * "from": "",
 * "grant": false,
 * "img_url": "http://wx.qlogo.cn/mmopen/atScMCT1D5iaaelbGeUfaCKMUWTYqmU90iaMicPI2I6293iaWO2E6qjX3687ia7mhZpm06RHOCW9H8xwkZoy12ia6683UMteoFRwXa/0",
 * "is_official": false,
 * "is_shared": false,
 * "nickname": "达芬奇",
 * "platform_id": 13,
 * "user_id": 852380481
 * },
 * <p>
 * <p>
 * <p>
 * "score": 0,
 * "status": 0,
 * "support_count": 0,
 * "top": false,
 * <p>
 * "userScore": {
 * "isvId": 0,
 * "level": 2,
 * "levelUp": 0,
 * "privilege": {},
 * "score": 7,
 * "title": "冒泡",
 * "userId": 0
 * },
 * <p>
 * "user_id": 852380481
 * }
 */
public class Comment {
    /**
     * 评论id
     */
    private int comment_id;
    /**
     * 附加评论
     */
    private List<Comment> comments;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    private long create_time;
    /**
     * 踩的次数
     */
    private int oppose_count;
    /**
     * 赞的次数
     */
    private int support_count;
    /**
     * 回复的条数
     */
    private int reply_count;
    /**
     * 回复id
     */
    private int reply_id;

    private String ip;

    private String ip_location;
    /**
     * 评论者信息
     */
    private Passport passport;
    /**
     * 附加评论者信息
     */
    private Passport reoly_passport;
    /**
     * 用户id
     */
    private int user_id;
    /**
     * 是否置顶
     */
    private boolean top;
    /**
     * 一共有多少楼
     */
    private int floor_count;


    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getOppose_count() {
        return oppose_count;
    }

    public void setOppose_count(int oppose_count) {
        this.oppose_count = oppose_count;
    }

    public int getSupport_count() {
        return support_count;
    }

    public void setSupport_count(int support_count) {
        this.support_count = support_count;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp_location() {
        return ip_location;
    }

    public void setIp_location(String ip_location) {
        this.ip_location = ip_location;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Passport getReoly_passport() {
        return reoly_passport;
    }

    public void setReoly_passport(Passport reoly_passport) {
        this.reoly_passport = reoly_passport;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public int getFloor_count() {
        return floor_count;
    }

    public void setFloor_count(int floor_count) {
        this.floor_count = floor_count;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", comments=" + comments +
                ", content='" + content + '\'' +
                ", create_time=" + create_time +
                ", oppose_count=" + oppose_count +
                ", support_count=" + support_count +
                ", reply_count=" + reply_count +
                ", reply_id=" + reply_id +
                ", ip=" + ip +
                ", ip_location=" + ip_location +
                ", passport=" + passport +
                ", reoly_passport=" + reoly_passport +
                ", user_id=" + user_id +
                ", top=" + top +
                ", floor_count=" + floor_count +
                '}';
    }

    /**
     * 评论者信息
     */
    public static class Passport {
        /**
         * 发布者头像
         */
        private String img_url;
        /**
         * 发布者昵称
         */
        private String nick_name;
        /**
         * 用户id
         */
        private int user_id;

        private boolean expired;
        private boolean is_official;
        private boolean is_shared;
        private boolean grant;
        private int platform_id;
        private String from;
        private int fee;
        private int followers_count;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public boolean isIs_official() {
            return is_official;
        }

        public void setIs_official(boolean is_official) {
            this.is_official = is_official;
        }

        public boolean isIs_shared() {
            return is_shared;
        }

        public void setIs_shared(boolean is_shared) {
            this.is_shared = is_shared;
        }

        public boolean isGrant() {
            return grant;
        }

        public void setGrant(boolean grant) {
            this.grant = grant;
        }

        public int getPlatform_id() {
            return platform_id;
        }

        public void setPlatform_id(int platform_id) {
            this.platform_id = platform_id;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getFollowers_count() {
            return followers_count;
        }

        public void setFollowers_count(int followers_count) {
            this.followers_count = followers_count;
        }

        @Override
        public String toString() {
            return "Passport{" +
                    "img_url='" + img_url + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", user_id=" + user_id +
                    ", expired=" + expired +
                    ", is_official=" + is_official +
                    ", is_shared=" + is_shared +
                    ", grant=" + grant +
                    ", platform_id=" + platform_id +
                    ", from='" + from + '\'' +
                    ", fee=" + fee +
                    ", followers_count=" + followers_count +
                    '}';
        }
    }


}
