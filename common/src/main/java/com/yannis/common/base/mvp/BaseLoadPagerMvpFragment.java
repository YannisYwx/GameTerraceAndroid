package com.yannis.common.base.mvp;

import android.support.annotation.NonNull;

import com.yannis.common.base.mvp.inter.IFragmentView;
import com.yannis.common.base.mvp.inter.IPresenter;
import com.yannis.common.util.CheckUtil;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/23  15:41
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class BaseLoadPagerMvpFragment<P extends IPresenter> extends BaseLoadPagerFragment implements IFragmentView<P> {

    protected P presenter;

//    protected BaseLoadPagerMvpFragment(@LayoutRes int loadingPager, @LayoutRes int errorPager, @LayoutRes int emptyPager, @IdRes int btnRetry) {
//        super(loadingPager, errorPager, emptyPager, btnRetry);
//    }

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
