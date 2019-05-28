package com.yuemai.game34999.data;

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
import com.yuemai.game34999.data.db.DBHelper;
import com.yuemai.game34999.data.http.HttpHelper;
import com.yuemai.game34999.data.prefs.PreferencesHelper;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  17:25
 * @email : 923080261@qq.com
 * @description : 数据管理中心  网络收据的获取、数据库数据的获取、SP数据的获取
 */
public class DataManager implements PreferencesHelper, DBHelper, HttpHelper {
    PreferencesHelper mPreferencesHelper;
    DBHelper mDBHelper;
    HttpHelper mHttpHelper;

    public DataManager(PreferencesHelper preferencesHelper, DBHelper dbHelper, HttpHelper httpHelper) {
        this.mPreferencesHelper = preferencesHelper;
        this.mDBHelper = dbHelper;
        this.mHttpHelper = httpHelper;
    }

    @Override
    public void insert() {
        mDBHelper.insert();
    }

    @Override
    public void delete() {
        mDBHelper.delete();
    }

    @Override
    public void query() {
        mDBHelper.query();
    }

    @Override
    public boolean isFirstIn() {
        mPreferencesHelper.isFirstIn();
        return false;
    }

    @Override
    public void markFirstIn() {

    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void saveToken(String token) {

    }

    @Override
    public Flowable<HttpResponse<UserInfo>> doLoginThirdParty(LoginBean loginBean) {
        return mHttpHelper.doLoginThirdParty(loginBean);
    }

    @Override
    public Flowable<HttpResponse<LoginResult>> doLogin(AccountLoginBean accountLoginBean) {
        return mHttpHelper.doLogin(accountLoginBean);
    }

    @Override
    public Flowable<HttpResponse<List<BannerInfo>>> getBannerInfo() {
        return mHttpHelper.getBannerInfo();
    }

    public Flowable<HttpResponse<List<AppConfiguration>>> getUserIndustry() {
        return getAppConfiguration(AppConfiguration.Configuration.USER_INDUSTRY);
    }

    public Flowable<HttpResponse<List<AppConfiguration>>> getMaritalStatus() {
        return getAppConfiguration(AppConfiguration.Configuration.USER_INDUSTRY);

    }

    public Flowable<HttpResponse<List<AppConfiguration>>> getCapitalposition() {
        return getAppConfiguration(AppConfiguration.Configuration.USER_INDUSTRY);
    }

    @Override
    public Flowable<HttpResponse<List<AppConfiguration>>> getAppConfiguration(String configuration) {
        return mHttpHelper.getAppConfiguration(configuration);
    }

    @Override
    public Flowable<HttpResponse<List<UserMessage>>> getUserMessage(String uid, int pageindex) {
        return mHttpHelper.getUserMessage(uid, pageindex);
    }

    @Override
    public Flowable<HttpResponse<ReadStatus>> setUserMessageIsRead(String id) {
        return mHttpHelper.setUserMessageIsRead(id);
    }

    @Override
    public Flowable<HttpResponse<RegisterResult>> doRegister(RegisterBean registerBean) {
        return mHttpHelper.doRegister(registerBean);
    }

    @Override
    public Flowable<HttpResponse<List<GameInfo>>> getGameList(String tags, int pageindex) {
        return mHttpHelper.getGameList(tags, pageindex);
    }

    @Override
    public Flowable<HttpResponse<List<GameType>>> getAllGameType() {
        return mHttpHelper.getAllGameType();
    }

    @Override
    public Flowable<HttpResponse<List<GameType>>> getGameTypeByParentId(int id) {
        return mHttpHelper.getGameTypeByParentId(id);
    }

    @Override
    public Flowable<HttpResponse<List<GameNewsLabel>>> getGameNewsLabel() {
        return mHttpHelper.getGameNewsLabel();
    }

    @Override
    public Flowable<HttpResponse<List<News>>> getNewsList(int cateid, int pageindex) {
        return mHttpHelper.getNewsList(cateid, pageindex);
    }

    @Override
    public Flowable<HttpResponse<List<Article>>> getArticle(int newsid) {
        return mHttpHelper.getArticle(newsid);
    }

    @Override
    public Flowable<ArticleCommentDetails> getArticleComment(int articleId, int page_size, int hot_size) {
        return mHttpHelper.getArticleComment(articleId, page_size, hot_size);
    }

    @Override
    public Flowable<ArticleComment> getArticleComments(long topic_id, int page_no) {
        return mHttpHelper.getArticleComments(topic_id, page_no);
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public void setPreferencesHelper(PreferencesHelper preferencesHelper) {
        mPreferencesHelper = preferencesHelper;
    }

    public DBHelper getDBHelper() {
        return mDBHelper;
    }

    public void setDBHelper(DBHelper DBHelper) {
        mDBHelper = DBHelper;
    }

    public HttpHelper getHttpHelper() {
        return mHttpHelper;
    }

    public void setHttpHelper(HttpHelper httpHelper) {
        mHttpHelper = httpHelper;
    }
}
