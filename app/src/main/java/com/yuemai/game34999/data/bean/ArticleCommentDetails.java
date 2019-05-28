package com.yuemai.game34999.data.bean;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/31  16:41
 * @email : 923080261@qq.com
 * @description : 文章评论详情
 */
public class ArticleCommentDetails {
    /**
     * 文章评论数
     */
    private int cmt_sum;
    /**
     * 评论列表
     */
    private List<Comment> comments;
    /**
     * 专业评论
     */
    private List<Comment> expert;
    /**
     * 热门评论
     */
    private List<Comment> hots;

    /**
     * TopicID, 重要
     */
    private long topic_id;

    /**
     * 评论总分页数
     */
    private int total_page_no;
    /**
     * 文章评论数
     */
    private int outer_cmt_sum;
    private int participation_sum;
    private int mode;

    public int getCmt_sum() {
        return cmt_sum;
    }

    public void setCmt_sum(int cmt_sum) {
        this.cmt_sum = cmt_sum;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getExpert() {
        return expert;
    }

    public void setExpert(List<Comment> expert) {
        this.expert = expert;
    }

    public List<Comment> getHots() {
        return hots;
    }

    public void setHots(List<Comment> hots) {
        this.hots = hots;
    }

    public long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(long topic_id) {
        this.topic_id = topic_id;
    }

    public int getTotal_page_no() {
        return total_page_no;
    }

    public void setTotal_page_no(int total_page_no) {
        this.total_page_no = total_page_no;
    }

    public int getOuter_cmt_sum() {
        return outer_cmt_sum;
    }

    public void setOuter_cmt_sum(int outer_cmt_sum) {
        this.outer_cmt_sum = outer_cmt_sum;
    }

    public int getParticipation_sum() {
        return participation_sum;
    }

    public void setParticipation_sum(int participation_sum) {
        this.participation_sum = participation_sum;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "ArticleCommentDetails{" +
                "cmt_sum=" + cmt_sum +
                ", comments=" + comments +
                ", expert=" + expert +
                ", hots=" + hots +
                ", topic_id=" + topic_id +
                ", total_page_no=" + total_page_no +
                ", outer_cmt_sum=" + outer_cmt_sum +
                ", participation_sum=" + participation_sum +
                ", mode=" + mode +
                '}';
    }
}
