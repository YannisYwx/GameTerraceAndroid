package com.yannis.common.base.mvp.inter;

import android.support.annotation.NonNull;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/23  16:29
 * @email : 923080261@qq.com
 * @description :
 */
public interface IFragmentView<P extends IPresenter> extends IView {

    /**
     * 设置Presenter
     * @param presenter presenter
     */
    void setPresenter(@NonNull P presenter);
}
