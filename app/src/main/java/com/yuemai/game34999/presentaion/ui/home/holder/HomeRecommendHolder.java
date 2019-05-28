package com.yuemai.game34999.presentaion.ui.home.holder;

import android.view.View;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.presentaion.ui.game.activity.GameInfoActivity;
import com.yuemai.game34999.presentaion.widget.ApkInfoCardView;
import com.yuemai.game34999.presentaion.widget.DownloadProgressButton;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  10:10
 * @email : 923080261@qq.com
 * @description :
 */
public class HomeRecommendHolder extends BaseRecycleHolder implements ApkInfoCardView.OnApkCardClickListener{

    @BindView(R.id.acv_one)
    ApkInfoCardView mCardView1;
    @BindView(R.id.acv_two)
    ApkInfoCardView mCardView2;
    @BindView(R.id.acv_third)
    ApkInfoCardView mCardView3;
    @BindView(R.id.acv_four)
    ApkInfoCardView mCardView4;

    public HomeRecommendHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(Object data) {
        mCardView1.setApkCardListener(this);
        mCardView2.setApkCardListener(this);
        mCardView3.setApkCardListener(this);
        mCardView4.setApkCardListener(this);
    }

    @Override
    public void onIconClick(View view) {
        switch (view.getId()){
            case R.id.acv_one:
                startActivity(GameInfoActivity.class);
                break;
            case R.id.acv_two:
                startActivity(GameInfoActivity.class);
                break;
            case R.id.acv_third:
                startActivity(GameInfoActivity.class);
                break;
            case R.id.acv_four:
                startActivity(GameInfoActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDownLoadButtonClick(@ApkInfoCardView.ApkCardEvent int event, View view, DownloadProgressButton button) {

    }
}
