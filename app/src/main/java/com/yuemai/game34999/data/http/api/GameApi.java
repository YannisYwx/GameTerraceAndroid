package com.yuemai.game34999.data.http.api;

import com.yannis.common.rx.HttpResponse;
import com.yuemai.game34999.data.bean.AccountLoginBean;
import com.yuemai.game34999.data.bean.AppConfiguration;
import com.yuemai.game34999.data.bean.Article;
import com.yuemai.game34999.data.bean.BannerInfo;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.data.bean.GameNewsLabel;
import com.yuemai.game34999.data.bean.GameType;
import com.yuemai.game34999.data.bean.LoginResult;
import com.yuemai.game34999.data.bean.News;
import com.yuemai.game34999.data.bean.ReadStatus;
import com.yuemai.game34999.data.bean.RegisterResult;
import com.yuemai.game34999.data.bean.UserInfo;
import com.yuemai.game34999.data.bean.UserMessage;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/8  17:36
 * @email : 923080261@qq.com
 * @description :
 * ---------------------------------------------------------------------------------------------------
 * @GET 表明这是get请求
 * @POST 表明这是post请求
 * @PUT 表明这是put请求
 * @DELETE 表明这是delete请求
 * @PATCH 表明这是一个patch请求，该请求是对put请求的补充，用于更新局部资源
 * @HEAD 表明这是一个head请求
 * @OPTIONS 表明这是一个option请求
 * @HTTP 通用注解, 可以替换以上所有的注解，其拥有三个属性：method，path，hasBody
 * @Headers 用于添加固定请求头，可以同时添加多个。通过该注解添加的请求头不会相互覆盖，而是共同存在
 * @Header 作为方法的参数传入，用于添加不固定值的Header，该注解会更新已有的请求头
 * @Body 多用于post请求发送非表单数据, 比如想要以post方式传递json格式数据
 * @Filed 多用于post请求中表单字段, Filed和FieldMap需要FormUrlEncoded结合使用
 * @FiledMap 和@Filed作用一致，用于不确定表单参数
 * @Part 用于表单字段, Part和PartMap与Multipart注解结合使用, 适合文件上传的情况
 * @PartMap 用于表单字段, 默认接受的类型是Map<String,REquestBody>，可用于实现多文件上传
 * <p>
 * Part标志上文的内容可以是富媒体形势，比如上传一张图片，上传一段音乐，即它多用于字节流传输。
 * 而Filed则相对简单些，通常是字符串键值对。
 * </p>
 * Part标志上文的内容可以是富媒体形势，比如上传一张图片，上传一段音乐，即它多用于字节流传输。
 * 而Filed则相对简单些，通常是字符串键值对。
 * @Path 用于url中的占位符,{占位符}和PATH只用在URL的path部分，url中的参数使用Query和QueryMap代替，保证接口定义的简洁
 * @Query 用于Get中指定参数
 * @QueryMap 和Query使用类似
 * @Url 指定请求路径
 * ----------------------------------------------------------------------------------------------------------
 */
public interface GameApi {

    String HOST = "http://192.168.0.110:8066/";

    /**
     * 第三方登录
     *
     * @param requestBody
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/account/register_app")
    Flowable<HttpResponse<RegisterResult>> doRegister(@Body RequestBody requestBody);

    /**
     * 第三方登录
     *
     * @param requestBody
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/login/PostLogin")
    Flowable<HttpResponse<UserInfo>> doLoginThirdParty(@Body RequestBody requestBody);

    /**
     * 第三方登录
     *
     * @param requestBody
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/account/submitlogin_app")
    Flowable<HttpResponse<LoginResult>> doLogin(@Body RequestBody requestBody);

    /**
     * 第三方登录
     *
     * @param requestBody
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/account/submitlogin_app")
    Flowable<HttpResponse<LoginResult>> doLogin(@Body AccountLoginBean requestBody);

    /**
     * 获取广告数据
     *
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/account/getbannerinfo")
    Flowable<HttpResponse<List<BannerInfo>>> getBannerInfo();

    /**
     * 获取配置信息
     *
     * @param configuration 需要的配置:行业配置:getuserindustry/婚姻配置:getmaritalstatus/资金配置:getcapitalposition
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/member/{configuration}")
    Flowable<HttpResponse<List<AppConfiguration>>> getAppConfiguration(@Path("configuration") String configuration);

    /**
     * 获取用户信息
     *
     * @param uid       用户id
     * @param pageindex index
     * @param pagesize  size
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/member/getusermessage_app")
    Flowable<HttpResponse<List<UserMessage>>> getUserMessage(@Query("uid") String uid, @Query("pageindex") int pageindex, @Query("pagesize") int pagesize);

    /**
     * 设置消息已读
     *
     * @param id 消息id
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/member/setusermessageisread/{id}")
    Flowable<HttpResponse<ReadStatus>> setUserMessageIsRead(@Path("id") String id);

    /**
     * 获取制定类型游戏列表
     *
     * @param tags      游戏类型
     * @param pageindex index
     * @param pagesize  size
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/game/getgamelist")
    Flowable<HttpResponse<List<GameInfo>>> getGameList(@Query("tags") String tags, @Query("pagesize") int pagesize, @Query("pageindex") int pageindex);

    /**
     * 获取游戏类型
     *
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/game/getallgametype")
    Flowable<HttpResponse<List<GameType>>> getAllGameType();

    /**
     * 根据id获取游戏类型
     *
     * @param id 游戏类型id
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/game/getgametypebyparentid/{id}")
    Flowable<HttpResponse<List<GameType>>> getGameTypeByParentId(@Path("id") int id);

    /**
     * 获取新闻标签
     *
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/news/getnewscategory")
    Flowable<HttpResponse<List<GameNewsLabel>>> getGameNewsLabel();

    /**
     * 获取新闻列表
     *
     * @param cateid    新闻类型
     * @param pageindex index
     * @param pagesize  size
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/news/getnewslist")
    Flowable<HttpResponse<List<News>>> getNewsList(@Query("cateid") int cateid, @Query("pagesize") int pagesize, @Query("pageindex") int pageindex);

    /**
     * 获取文章详情
     *
     * @param newsid 文章id
     * @return
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/news/getnewsdetail")
    Flowable<HttpResponse<List<Article>>> getArticle(@Query("newsid") int newsid);
}