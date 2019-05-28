package com.yuemai.game34999.presentaion.ui.gift.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.NestedGridView;
import com.yannis.common.widget.TitleBar;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerFragment;
import com.yuemai.game34999.presentaion.ui.gift.activity.GiftActivity;
import com.yuemai.game34999.presentaion.ui.gift.adapter.GiftBagAdapter;
import com.yuemai.game34999.presentaion.ui.home.activity.GameListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  10:43
 * @email : 923080261@qq.com
 * @description :
 */
public class GiftFragment extends AbstractMvpLoadPagerFragment {
    public static final String KEY_TITLE = "TITLE";

    @BindView(R.id.gv_gift_bag)
    NestedGridView mNestedGridView;
    @BindView(R.id.ll_recommendGiftFirst)
    LinearLayout recommendGiftFirst;
    @BindView(R.id.ll_recommendGiftSecond)
    LinearLayout recommendGiftSecond;
    @BindView(R.id.ll_recommendGiftThird)
    LinearLayout recommendGiftThird;
    @BindView(R.id.view_titleBar)
    TitleBar mTitleBar;

    private List list;

    @Override
    public View initContentView() {
        View view = View.inflate(getContext(), R.layout.fragment_gift_bag, null);
        return view;
    }

    @Override
    public int initDataFromCache() {
        list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add("");
        }
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        GiftBagAdapter adapter = new GiftBagAdapter(getContext(), R.layout.item_gift_bag, list);
        mNestedGridView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        mNestedGridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getContext(), GiftActivity.class);
            intent.putExtra(KEY_TITLE, "" + i);
            startActivity(intent);
        });

        mTitleBar.setOnEventTriggerListener(type -> {
            if (type == TitleBar.Event.textRight) {
                Bundle bundle = new Bundle();
                bundle.putInt(GameListActivity.KEY_GAME_LIST_TYPE, GameListActivity.MY_GIFT_BAG);
                forward(GameListActivity.class, bundle);
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.ll_recommendGiftFirst, R.id.ll_recommendGiftSecond, R.id.ll_recommendGiftThird})
    public void onRecommendGiftClick(View view) {
        Intent intent = new Intent(getContext(), GiftActivity.class);
        switch (view.getId()) {
            case R.id.ll_recommendGiftFirst:
                intent.putExtra(KEY_TITLE, "王者荣耀");
                break;
            case R.id.ll_recommendGiftSecond:
                intent.putExtra(KEY_TITLE, "部落冲突");
                break;
            case R.id.ll_recommendGiftThird:
                intent.putExtra(KEY_TITLE, "极限特工");
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {

    }
}
