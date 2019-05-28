package com.yuemai.game34999.presentaion.download.net.call;


import com.yuemai.game34999.presentaion.download.mode.Response;
import com.yuemai.game34999.presentaion.download.net.callback.Callback;
import com.yuemai.game34999.presentaion.download.net.request.GetRequest;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  19:41
 * @email : 923080261@qq.com
 * @description :   请求接口
 */
public interface Call<T> {

    /**
     * 同步执行
     * @return 响应包装体
     * @throws Exception
     */
    Response<T> execute() throws Exception;

    /**
     * 异步回调执行
     * @param callback 回调接口
     */
    void execute(Callback<T> callback);

    /**
     * 是否已经执行完毕
     * @return true 已经执行 false 还未执行
     */
    boolean isExecuted();

    /**
     * 取消任务
     */
    void cancel();

    /**
     * 是否取消
     * @return 是否取消
     */
    boolean isCanceled();

    /**
     * 克隆
     * @return Call
     */
    Call<T> clone();

    /**
     * 获取请求实体
     *
     * @return GetRequest
     */
    GetRequest getGetRequest();


}
