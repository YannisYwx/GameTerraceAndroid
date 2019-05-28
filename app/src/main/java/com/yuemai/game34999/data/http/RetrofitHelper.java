package com.yuemai.game34999.data.http;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.yannis.common.rx.HttpResponse;
import com.yuemai.game34999.core.Constants;
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
import com.yuemai.game34999.data.http.api.ChangYanApi;
import com.yuemai.game34999.data.http.api.GameApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/11  18:30
 * @email : 923080261@qq.com
 * @description :
 */
public class RetrofitHelper implements HttpHelper {
    GameApi gameApi;
    ChangYanApi chanYanApi;

    @Inject
    public RetrofitHelper(@NonNull GameApi gameApi, @NonNull ChangYanApi chanYanApi) {
        this.gameApi = gameApi;
        this.chanYanApi = chanYanApi;
    }

    @Override
    public Flowable<HttpResponse<UserInfo>> doLoginThirdParty(LoginBean loginBean) {
        return gameApi.doLoginThirdParty(createJsonRequestBody(loginBean));
    }

    @Override
    public Flowable<HttpResponse<LoginResult>> doLogin(AccountLoginBean accountLoginBean) {
        return gameApi.doLogin(createJsonRequestBody(accountLoginBean));
    }

    @Override
    public Flowable<HttpResponse<List<BannerInfo>>> getBannerInfo() {
        return gameApi.getBannerInfo();
    }

    @Override
    public Flowable<HttpResponse<List<AppConfiguration>>> getAppConfiguration(String configuration) {
        return gameApi.getAppConfiguration(configuration);
    }

    @Override
    public Flowable<HttpResponse<List<UserMessage>>> getUserMessage(String uid, int pageindex) {
        return gameApi.getUserMessage(uid, pageindex, Constants.PAGE_SIZE);
    }

    @Override
    public Flowable<HttpResponse<ReadStatus>> setUserMessageIsRead(String id) {
        return gameApi.setUserMessageIsRead(id);
    }

    @Override
    public Flowable<HttpResponse<RegisterResult>> doRegister(RegisterBean registerBean) {
        return gameApi.doRegister(createJsonRequestBody(registerBean));
    }

    @Override
    public Flowable<HttpResponse<List<GameInfo>>> getGameList(String tags, int pageindex) {
        return gameApi.getGameList(tags, Constants.PAGE_SIZE, pageindex);
    }

    @Override
    public Flowable<HttpResponse<List<GameType>>> getAllGameType() {
        return gameApi.getAllGameType();
    }

    @Override
    public Flowable<HttpResponse<List<GameType>>> getGameTypeByParentId(int id) {
        return gameApi.getGameTypeByParentId(id);
    }

    @Override
    public Flowable<HttpResponse<List<GameNewsLabel>>> getGameNewsLabel() {
        return gameApi.getGameNewsLabel();
    }

    @Override
    public Flowable<HttpResponse<List<News>>> getNewsList(int cateid, int pageindex) {
        return gameApi.getNewsList(cateid, Constants.PAGE_SIZE, pageindex);
    }

    @Override
    public Flowable<HttpResponse<List<Article>>> getArticle(int newsid) {
        return gameApi.getArticle(newsid);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Flowable<ArticleCommentDetails> getArticleComment(int articleId, int page_size, int hot_size) {
        return chanYanApi.getArticleComment(Constants.APP_ID, "", String.format("Game_%d", articleId), page_size, hot_size);
    }

    @Override
    public Flowable<ArticleComment> getArticleComments(long topic_id, int page_no) {
        return chanYanApi.getArticleComments(Constants.APP_ID, topic_id, Constants.PAGE_SIZE, page_no);
    }

    private static RequestBody createJsonRequestBody(@NonNull Object obj) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(obj));
    }

    private static String createJsonStr(@NonNull Object obj) {
        String json = null;
        try {
            json = URLEncoder.encode(new Gson().toJson(obj), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return json;
    }

}
