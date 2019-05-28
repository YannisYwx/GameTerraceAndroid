package com.yuemai.game34999.data.prefs;

import com.yannis.common.util.SPUtils;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  17:22
 * @email : 923080261@qq.com
 * @description :
 */
public class AppPreferencesHelper implements PreferencesHelper {

    @Inject
    public AppPreferencesHelper() {
    }

    @Override
    public boolean isFirstIn() {
        return (boolean) SPUtils.get(IS_FIRST_IN, true);
    }

    @Override
    public void markFirstIn() {
        SPUtils.put(IS_FIRST_IN, true);
    }

    @Override
    public String getToken() {
        return (String) SPUtils.get(KEY_TOKEN, "");
    }

    @Override
    public void saveToken(String token) {
        SPUtils.put(KEY_TOKEN, token);
    }


    private static class GamePreferencesHelperHolder {
        private static AppPreferencesHelper INSTANCE = new AppPreferencesHelper();
    }

    public static AppPreferencesHelper getInstance() {
        return GamePreferencesHelperHolder.INSTANCE;
    }
}
