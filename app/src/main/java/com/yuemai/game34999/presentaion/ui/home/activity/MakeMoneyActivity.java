package com.yuemai.game34999.presentaion.ui.home.activity;

import android.view.View;

import com.yannis.common.util.UIUtils;
import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.widget.ItemInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  19:34
 * @email : 923080261@qq.com
 * @description : 赚钱页面
 */
public class MakeMoneyActivity extends AbstractMvpLoadPagerActivity {
    @BindView(R.id.iiv_singIn)
    ItemInfoView iiv_singIn;
    @BindView(R.id.iiv_inviteFriends)
    ItemInfoView iiv_inviteFriends;
    @BindView(R.id.iiv_payGame)
    ItemInfoView iiv_payGame;
    @BindView(R.id.iiv_exchange)
    ItemInfoView iiv_exchange;
    @BindView(R.id.iiv_draw)
    ItemInfoView iiv_draw;


    @Override
    protected String initTitle() {
        return getResources().getString(R.string.title_make_money);
    }

    @Override
    protected void refreshContentView(View contentView) {

    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public @LoadingPager.LoadingState
    int loadData() {
        showToast("加载数据");
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_make_money;
    }

    @OnClick({R.id.iiv_inviteFriends, R.id.iiv_singIn, R.id.iiv_payGame, R.id.iiv_exchange, R.id.iiv_draw})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iiv_singIn:
                UIUtils.showToast("1");
                break;
            case R.id.iiv_inviteFriends:
                UIUtils.showToast("2");
                break;
            case R.id.iiv_payGame:
                UIUtils.showToast("3");
                break;
            case R.id.iiv_exchange:
                UIUtils.showToast("4");
                break;
            case R.id.iiv_draw:
                UIUtils.showToast("5");
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(ActivityComponent component) {

    }
}
