package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.Article;
import com.yuemai.game34999.data.bean.ArticleCommentDetails;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/26  16:27
 * @email : 923080261@qq.com
 * @description :
 */
public interface ArticleContract {

    interface View extends BaseContract.BaseView {

        /**
         * 显示文章
         *
         * @param articleList 文章
         */
        void showArticle(List<Article> articleList);

        /**
         * 显示文章评论详情
         *
         * @param articleCommentDetails 评论详情
         */
        void showArticleCommentDetails(ArticleCommentDetails articleCommentDetails);
    }

    interface Presenter extends BaseContract.BasePresenter<ArticleContract.View> {

        /**
         * 加载文章详情
         *
         * @param articleId 文章id
         */
        void loadArticle(int articleId);

        /**
         * 加载文章评论详情
         *
         * @param articleId 文章id
         */
        void loadArticleCommentDetails(int articleId);

    }


}
