package com.yannis.common.base.mvp;

import android.support.annotation.NonNull;

import com.yannis.common.base.BaseFragment;
import com.yannis.common.base.mvp.inter.IFragmentView;
import com.yannis.common.base.mvp.inter.IPresenter;
import com.yannis.common.util.CheckUtil;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/23  15:41
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends BaseFragment implements IFragmentView<P> {

    protected P presenter;

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.onDetach();
        }
    }


    @Override
    public void setPresenter(@NonNull P presenter) {
        CheckUtil.checkNotNull(presenter);
        this.presenter = presenter;
        presenter.onAttach();

    }

}
