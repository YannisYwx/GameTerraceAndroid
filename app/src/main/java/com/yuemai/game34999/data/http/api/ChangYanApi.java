package com.yuemai.game34999.data.http.api;

import com.yuemai.game34999.data.bean.ArticleComment;
import com.yuemai.game34999.data.bean.ArticleCommentDetails;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/31  15:32
 * @email : 923080261@qq.com
 * @description :
 */
public interface ChangYanApi {
    String HOST = "http://changyan.sohu.com/";

    /**
     * 获取文章评论
     *
     * @param client_id       appid
     * @param topic_source_id Game_article_id
     * @param page_size       加载评论条数
     * @param hot_size        热门条数
     * @return 文章评论
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/2/topic/load")
    Flowable<ArticleCommentDetails> getArticleComment(@Query("client_id") String client_id, @Query("topic_url") String topic_url, @Query("topic_source_id") String topic_source_id, @Query("page_size") int page_size, @Query("hot_size") int hot_size);

    /**
     * 获取文章评论列表
     *
     * @param client_id: AppID, 必须
     * @param topic_id   必须
     * @param page_size: 每页评论数
     * @param page_no    第几页
     * @return 文章评论
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/2/topic/comments")
    Flowable<ArticleComment> getArticleComments(@Query("client_id") String client_id, @Query("topic_id") long topic_id, @Query("page_size") int page_size, @Query("page_no") int page_no);
}
