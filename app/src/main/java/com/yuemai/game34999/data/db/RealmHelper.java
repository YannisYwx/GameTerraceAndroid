package com.yuemai.game34999.data.db;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  17:21
 * @email : 923080261@qq.com
 * @description :
 */
public class RealmHelper implements DBHelper {
    @Inject
    public RealmHelper() {
    }

    @Override
    public void insert() {
        Logger.e("RealmHelper----------------insert");
    }

    @Override
    public void delete() {
        Logger.e("RealmHelper----------------delete");
    }

    @Override
    public void query() {
        Logger.e("RealmHelper----------------query");
    }
}
