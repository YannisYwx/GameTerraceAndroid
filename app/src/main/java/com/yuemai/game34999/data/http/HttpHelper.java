package com.yuemai.game34999.data.http;

import com.yannis.common.rx.HttpResponse;
import com.yuemai.game34999.data.bean.AccountLoginBean;
import com.yuemai.game34999.data.bean.AppConfiguration;
import com.yuemai.game34999.data.bean.Article;
import com.yuemai.game34999.data.bean.ArticleComment;
import com.yuemai.game34999.data.bean.ArticleCommentDetails;
import com.yuemai.game34999.data.bean.BannerInfo;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.data.bean.GameNewsLabel;
import com.yuemai.game34999.data.bean.GameType;
import com.yuemai.game34999.data.bean.LoginBean;
import com.yuemai.game34999.data.bean.LoginResult;
import com.yuemai.game34999.data.bean.News;
import com.yuemai.game34999.data.bean.ReadStatus;
import com.yuemai.game34999.data.bean.RegisterBean;
import com.yuemai.game34999.data.bean.RegisterResult;
import com.yuemai.game34999.data.bean.UserInfo;
import com.yuemai.game34999.data.bean.UserMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/11  18:26
 * @email : 923080261@qq.com
 * @description : 所有网络请求接口
 */
public interface HttpHelper {

    /**
     * 第三方登录
     *
     * @param loginBean
     * @return
     */
    Flowable<HttpResponse<UserInfo>> doLoginThirdParty(LoginBean loginBean);

    /**
     * 账号登录
     *
     * @param accountLoginBean
     * @return
     */
    Flowable<HttpResponse<LoginResult>> doLogin(AccountLoginBean accountLoginBean);


    /**
     * 获取头部广告数据
     *
     * @return
     */
    Flowable<HttpResponse<List<BannerInfo>>> getBannerInfo();

    /**
     * 获取配置信息
     *
     * @param configuration 需要的配置:
     *                      行业配置:getuserindustry/
     *                      婚姻配置:getmaritalstatus/
     *                      资金配置:getcapitalposition
     * @return
     */
    Flowable<HttpResponse<List<AppConfiguration>>> getAppConfiguration(String configuration);

    /**
     * 获取用户信息
     *
     * @param uid       用户id
     * @param pageindex index
     * @return
     */
    Flowable<HttpResponse<List<UserMessage>>> getUserMessage(String uid, int pageindex);

    /**
     * 设置消息已读
     *
     * @param id 消息id
     * @return
     */
    Flowable<HttpResponse<ReadStatus>> setUserMessageIsRead(String id);


    /**
     * 注册
     *
     * @param registerBean
     * @return
     */
    Flowable<HttpResponse<RegisterResult>> doRegister(RegisterBean registerBean);

    /**
     * 获取制定类型游戏列表
     *
     * @param tags      游戏类型
     * @param pageindex index
     * @return
     */
    Flowable<HttpResponse<List<GameInfo>>> getGameList(String tags, int pageindex);

    /**
     * 获取游戏类型
     *
     * @return
     */
    Flowable<HttpResponse<List<GameType>>> getAllGameType();

    /**
     * 根据id获取游戏类型
     *
     * @param id 游戏类型id
     * @return
     */
    Flowable<HttpResponse<List<GameType>>> getGameTypeByParentId(int id);

    /**
     * 获取新闻标签
     *
     * @return
     */
    Flowable<HttpResponse<List<GameNewsLabel>>> getGameNewsLabel();

    /**
     * 获取新闻列表
     *
     * @param cateid    新闻类型
     * @param pageindex index
     * @return
     */
    Flowable<HttpResponse<List<News>>> getNewsList(int cateid, int pageindex);

    /**
     * 获取文章详情
     *
     * @param newsid 文章id
     * @return
     */
    Flowable<HttpResponse<List<Article>>> getArticle(int newsid);

    /**
     * 获取文章评论
     *
     * @param articleId Game_article_id
     * @param page_size 加载评论条数
     * @param hot_size  热门条数
     * @return 文章评论
     */
    Flowable<ArticleCommentDetails> getArticleComment(int articleId, int page_size, int hot_size);

    /**
     * 获取文章评论列表
     *
     * @param topic_id 必须
     * @param page_no  第几页
     * @return 文章评论
     */
    Flowable<ArticleComment> getArticleComments(long topic_id, int page_no);

}
