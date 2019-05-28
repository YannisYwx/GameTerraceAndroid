package com.yuemai.game34999.presentaion.ui.me.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.WaveView;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerFragment;
import com.yuemai.game34999.presentaion.ui.game.activity.DownloadManageActivity;
import com.yuemai.game34999.presentaion.ui.home.activity.GameListActivity;
import com.yuemai.game34999.presentaion.ui.home.activity.MessagePagerActivity;
import com.yuemai.game34999.presentaion.ui.main.activity.LoginActivity;
import com.yuemai.game34999.presentaion.ui.me.activity.CreditsActivity;
import com.yuemai.game34999.presentaion.ui.me.activity.FeedbackActivity;
import com.yuemai.game34999.presentaion.ui.me.activity.SettingsActivity;
import com.yuemai.game34999.presentaion.ui.me.activity.UserInfoActivity;
import com.yuemai.game34999.presentaion.widget.NormalItemView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  10:44
 * @email : 923080261@qq.com
 * @description : 我的界面
 */
public class MeFragment extends AbstractMvpLoadPagerFragment {
    @BindView(R.id.civ_me_userHeader)
    CircleImageView mCircleImageView;
    @BindView(R.id.tv_me_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_me_userID)
    TextView tvUserID;
    @BindView(R.id.iv_me_download)
    ImageView ivDownload;
    @BindView(R.id.iv_me_msg)
    ImageView ivMsg;
    @BindView(R.id.niv_me_recharge)
    NormalItemView nivRecharg;
    @BindView(R.id.niv_me_integral)
    NormalItemView nivIntegral;
    @BindView(R.id.niv_me_game)
    NormalItemView nivGame;
    @BindView(R.id.niv_me_gift)
    NormalItemView nivGift;
    @BindView(R.id.niv_me_setting)
    NormalItemView nivSetting;
    @BindView(R.id.niv_me_feedback)
    NormalItemView nivFeedback;
    @BindView(R.id.wave_view)
    WaveView mWaveView;
    @BindView(R.id.ll_userInfo)
    View mView;
    @Override
    public View initContentView() {
        return View.inflate(mContext, R.layout.fragment_me, null);
    }

    @Override
    public int initDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2,-2);
        lp.gravity = Gravity.CENTER;
        mWaveView.setOnWaveAnimationListener(y -> {
            lp.setMargins(0,0,0,(int)y+2);
            mView.setLayoutParams(lp);
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.civ_me_userHeader, R.id.niv_me_recharge, R.id.niv_me_integral,
            R.id.niv_me_gift, R.id.niv_me_game, R.id.niv_me_feedback,
            R.id.niv_me_setting, R.id.iv_me_download, R.id.iv_me_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_me_userHeader:
                forward(UserInfoActivity.class);
                break;
            case R.id.niv_me_recharge:
                forward(LoginActivity.class);
                break;
            case R.id.niv_me_integral:
                forward(CreditsActivity.class);
                break;
            case R.id.niv_me_gift:
                Bundle bundle = new Bundle();
                bundle.putInt(GameListActivity.KEY_GAME_LIST_TYPE, GameListActivity.MY_GIFT_BAG);
                forward(GameListActivity.class, bundle);
                break;
            case R.id.niv_me_game:
                break;
            case R.id.niv_me_feedback:
                forward(FeedbackActivity.class);
                break;
            case R.id.niv_me_setting:
                forward(SettingsActivity.class);
                break;
            case R.id.iv_me_download:
                forward(DownloadManageActivity.class);
                break;
            case R.id.iv_me_msg:
                forward(MessagePagerActivity.class);
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
    public void injectThis(FragmentComponent fragmentComponent) {

    }
}
