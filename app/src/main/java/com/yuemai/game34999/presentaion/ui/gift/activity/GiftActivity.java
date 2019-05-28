package com.yuemai.game34999.presentaion.ui.gift.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.game.adapter.GameGiftBagAdapter;
import com.yuemai.game34999.presentaion.ui.gift.fragment.GiftFragment;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.List;

import butterknife.BindView;

import static com.yannis.common.util.UIUtils.getContext;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/13  10:13
 * @email : 923080261@qq.com
 * @description :
 */
public class GiftActivity extends AbstractMvpLoadPagerActivity {
    @BindView(R.id.iv_gameIcon)
    ImageView ivGameIcon;
    @BindView(R.id.tv_gameName)
    TextView tvGameName;
    @BindView(R.id.tv_gameInfo)
    TextView tvGameDownloadInfo;
    @BindView(R.id.rv_gameGift)
    RecyclerView mRecyclerView;
    GameGiftBagAdapter mAdapter;
    List datas;

    @Override
    public int setContentLayout() {
        return R.layout.activity_gift;
    }

    @Override
    protected String initTitle() {
        return getIntent().getStringExtra(GiftFragment.KEY_TITLE);
    }

    @Override
    protected void refreshContentView(View contentView) {
        mAdapter = new GameGiftBagAdapter(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected int loadDataFromCache() {
        datas = TestDataUtils.getTestDatas();
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
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
