package com.yuemai.game34999.data.prefs;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  17:19
 * @email : 923080261@qq.com
 * @description :
 */
public interface PreferencesHelper {
    String IS_FIRST_IN = "_isFirstIn";

    /**
     * 是否是第一次进入
     *
     * @return true 第一次 false 不是
     */
    boolean isFirstIn();


    /**
     * 设置是否是第一次进入
     */
    void markFirstIn();


    String KEY_TOKEN = "TOKEN";

    /**
     * 获取登录的token
     *
     * @return token
     */
    String getToken();

    /**
     * 保存token
     *
     * @param token token
     */
    void saveToken(String token);

//    String KEY_ADDRESS = "ADDRESS";
//
//    /**
//     * 保存位置信息
//     *
//     * @param locationAddress 位置信息
//     */
//    void saveLocationAddress(String locationAddress);
//
//    /**
//     * 获取位置信息
//     *
//     * @return 位置信息
//     */
//    String getLocationAddress();
}
