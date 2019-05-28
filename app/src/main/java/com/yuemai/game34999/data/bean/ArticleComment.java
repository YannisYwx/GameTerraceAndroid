package com.yuemai.game34999.data.bean;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/31  16:41
 * @email : 923080261@qq.com
 * @description : 文章评论
 */
public class ArticleComment {
    /**
     * 文章评论数
     */
    private int cmt_sum;
    private int cmt_cnt;
    /**
     * 评论列表
     */
    private List<Comment> comments;

    /**
     * TopicID, 重要
     */
    private long topic_id;

    public int getCmt_sum() {
        return cmt_sum;
    }

    public void setCmt_sum(int cmt_sum) {
        this.cmt_sum = cmt_sum;
    }

    public int getCmt_cnt() {
        return cmt_cnt;
    }

    public void setCmt_cnt(int cmt_cnt) {
        this.cmt_cnt = cmt_cnt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(long topic_id) {
        this.topic_id = topic_id;
    }

    @Override
    public String toString() {
        return "ArticleComment{" +
                "cmt_sum=" + cmt_sum +
                ", cmt_cnt=" + cmt_cnt +
                ", comments=" + comments +
                ", topic_id=" + topic_id +
                '}';
    }
}
