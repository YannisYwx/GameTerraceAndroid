package com.yuemai.game34999.presentaion.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.game.activity.DownloadManageActivity;
import com.yuemai.game34999.presentaion.ui.game.activity.GameInfoActivity;
import com.yuemai.game34999.presentaion.ui.home.contract.GameListContract;
import com.yuemai.game34999.presentaion.ui.home.presenter.GameListPresenter;
import com.yuemai.game34999.presentaion.ui.main.adapter.AbstractCommonItemAdapter;
import com.yuemai.game34999.presentaion.widget.CommonItemView;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.List;

import butterknife.BindView;


/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/27  12:00
 * @email : 923080261@qq.com
 * @description :更多游戏 新游
 */
public class GameListActivity extends AbstractMvpLoadPagerActivity<GameListPresenter> implements GameListContract.View {
    @BindView(R.id.rv_moreGame)
    RecyclerView mRecyclerView;
    private List datas;
    public static final String KEY_GAME_LIST_TYPE = "GAME_LIST_TYPE";
    public static final int NEW_GAME = 0;
    public static final int MORE_GAME = 1;
    public static final int MY_GIFT_BAG = 2;

    private int type;

    public static void start(Context context,int type){
        Bundle bundle = new Bundle();
        bundle.putInt(GameListActivity.KEY_GAME_LIST_TYPE, type);
        Intent intent = new Intent(context,GameListActivity.class);
        intent.putExtra(EXTRA, bundle);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        super.init();
        type = getIntent().getBundleExtra(EXTRA).getInt(KEY_GAME_LIST_TYPE);
    }

    @Override
    public void injectThis(ActivityComponent component) {

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_more_game;
    }

    @Override
    protected String initTitle() {
        return getGameListTitle();
    }

    private String getGameListTitle() {
        @StringRes int titleRes;
        switch (type) {
            case NEW_GAME:
                titleRes = R.string.title_newGame;
                break;
            case MORE_GAME:
                titleRes = R.string.title_more;
                break;
            case MY_GIFT_BAG:
                titleRes = R.string.title_myGiftBag;
                break;
            default:
                titleRes = R.string.title_newGame;
                break;
        }

        return getString(titleRes);
    }

    @Override
    protected void refreshContentView(View contentView) {
        AbstractCommonItemAdapter adapter = new AbstractCommonItemAdapter(datas) {
            @Override
            public CommonItemView createCommonItem() {
                switch (type) {
                    case MY_GIFT_BAG:
                        return CommonItemView.createNormalItemView(GameListActivity.this, CommonItemView.Type.copy);
                    case MORE_GAME:
                    case NEW_GAME:
                    default:
                        CommonItemView commonItemView = CommonItemView.createDownloadButtonItemView(GameListActivity.this);
                        commonItemView.setForwardActivity(GameInfoActivity.class);
                        return commonItemView;

                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(GameListActivity.this));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
    public void initTitleBar() {
        super.initTitleBar();
        if (type != MY_GIFT_BAG) {
            mTitleBar.setRightDrawable(getResources().getDrawable(R.drawable.download_selector));
        }
    }

    @Override
    public void onRightTitleBarClick(@NonNull View view) {
        super.onRightTitleBarClick(view);
        forward(DownloadManageActivity.class);
    }


    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }
}
