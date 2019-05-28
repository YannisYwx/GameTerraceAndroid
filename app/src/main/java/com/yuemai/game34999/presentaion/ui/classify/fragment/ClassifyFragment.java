package com.yuemai.game34999.presentaion.ui.classify.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.GridView;

import com.yannis.common.util.UIUtils;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameType;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerFragment;
import com.yuemai.game34999.presentaion.contract.ClassifyContract;
import com.yuemai.game34999.presentaion.presenter.classify.ClassifyPresenter;
import com.yuemai.game34999.presentaion.ui.classify.activity.GameGenreActivity;
import com.yuemai.game34999.presentaion.ui.classify.adapter.ClassifyAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yannis.common.widget.LoadingPager.LoadingState.stateSuccess;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.GameClassify.act;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.GameClassify.chess;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.GameClassify.leisurePuzzle;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.GameClassify.rolePlay;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.GameClassify.sim;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.GameClassify.sportsCompetition;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.GameClassify.stg;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.GameClassify.towerDefense;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  10:43
 * @email : 923080261@qq.com
 * @description :游戏分类列表
 */
public class ClassifyFragment extends AbstractMvpLoadPagerFragment<ClassifyPresenter> implements ClassifyContract.View {
    public static final String KEY_GAME_CLASSIFY = "GAME_CLASSIFY";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_TAGS = "TAGS";
    @BindView(R.id.gv_classify)
    GridView mGridView;
    private List<GameType> mGameTypeList = new ArrayList<>();
    private final static String[] TITLES = UIUtils.getContext().getResources().getStringArray(R.array.game_classify);

    private final static int[] BGS = new int[]{
            R.drawable.classify_feixing_bg,
            R.drawable.classify_xiuxian_bg,
            R.drawable.classify_tiyu_bg,
            R.drawable.classify_moni_bg,
            R.drawable.classify_gedoui_bg,
            R.drawable.classify_qipai_bg,
            R.drawable.classify_celue_bg,
            R.drawable.classify_juese_bg,
    };

    private final static int[] ICONS = new int[]{
            R.drawable.icon_feixing,
            R.drawable.icon_xiuxian,
            R.drawable.icon_jingji,
            R.drawable.icon_moni,
            R.drawable.icon_gedou,
            R.drawable.icon_qipai,
            R.drawable.icon_celue,
            R.drawable.icon_juese,
    };


    public static ClassifyFragment getInstance() {
        return new ClassifyFragment();
    }

    @Override
    public View initContentView() {
        return View.inflate(UIUtils.getContext(), R.layout.fragment_sort, null);
    }

    @Override
    public int initDataFromCache() {
        return stateSuccess;
    }

    @Override
    public int initData() {
        return stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
    }

    @Override
    public void initEvent() {
        mGridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle extras = new Bundle();
            extras.putInt(KEY_GAME_CLASSIFY, getGameClassify(i));
            extras.putString(KEY_TITLE, TITLES[i]);
            extras.putStringArray(KEY_TAGS, mGameTypeList.get(i).getTags().split(","));
            Intent intent = new Intent(mContext, GameGenreActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        });
    }

    @Override
    protected void lazyLoad() {
        presenter.getGameClassify();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void showGameClassify(List<GameType> gameTypeList) {
        mGameTypeList.clear();
        mGameTypeList.addAll(gameTypeList);
        for (int i = 0; i < gameTypeList.size(); i++) {
            gameTypeList.get(i).setIconRes(ICONS[i]);
            gameTypeList.get(i).setBgRes(BGS[i]);
        }
        ClassifyAdapter adapter = new ClassifyAdapter(getContext(), mGameTypeList);
        mGridView.setAdapter(adapter);
    }

    @IntDef({stg, leisurePuzzle, sportsCompetition, rolePlay, act, chess, sim, towerDefense})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GameClassify {
        /**
         * 策略塔防
         */
        int stg = 0;
        /**
         * 飞行射击
         */
        int leisurePuzzle = 1;
        /**
         * 休闲益智
         */
        int sportsCompetition = 2;
        /**
         * 体育竞技
         */
        int rolePlay = 3;
        /**
         * 角色扮演
         */
        int act = 4;
        /**
         * 格斗动作
         */
        int chess = 5;
        /**
         * 棋牌天地
         */
        int sim = 6;
        /**
         * 模拟经营
         */
        int towerDefense = 7;
    }

    public static
    @ClassifyFragment.GameClassify
    int getGameClassify(int pos) {
        switch (pos) {
            case 0:
                return stg;
            case 1:
                return leisurePuzzle;
            case 2:
                return sportsCompetition;
            case 3:
                return rolePlay;
            case 4:
                return act;
            case 5:
                return chess;
            case 6:
                return sim;
            case 7:
                return towerDefense;
            default:
                return stg;
        }
    }

}
