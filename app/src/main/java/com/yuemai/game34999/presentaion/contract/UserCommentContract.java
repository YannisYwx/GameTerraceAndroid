package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.ArticleComment;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/14  16:16
 * @email : 923080261@qq.com
 * @description :
 */
public interface UserCommentContract {

    interface View extends BaseContract.BaseView {

        /**
         * 加载文章评论
         *
         * @param comment
         */
        void loadComments(ArticleComment comment);

        /**
         * 没有更多数据了
         */
        void onNoMoreData();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * 获取评论
         *
         * @param topic_id
         */
        void getCommentList(long topic_id);
    }
}
