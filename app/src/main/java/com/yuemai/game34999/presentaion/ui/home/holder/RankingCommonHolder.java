package com.yuemai.game34999.presentaion.ui.home.holder;

import android.view.View;

import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.presentaion.ui.game.activity.GameInfoActivity;
import com.yuemai.game34999.presentaion.widget.CommonItemView;
import com.yuemai.game34999.presentaion.widget.DownloadProgressButton;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  14:56
 * @email : 923080261@qq.com
 * @description :
 */
public class RankingCommonHolder extends BaseRecycleHolder implements DownloadProgressButton.OnDownLoadClickListener {
    DownloadProgressButton mProgressButton;
    CommonItemView mCommonItemView;

    private Observable<Long> mObservable;
    private Disposable sub;

    public RankingCommonHolder(View itemView) {
        super(itemView);
        mCommonItemView = (CommonItemView) itemView;
    }

    @Override
    protected void refreshViewHolder(Object data) {
        mProgressButton = mCommonItemView.getBtnDownload();
        mProgressButton.setOnDownLoadClickListener(this);
        mObservable = Observable.interval(700, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread());
        itemView.setOnClickListener(view -> startActivity(GameInfoActivity.class));

    }

    @Override
    public void clickDownload() {
        sub = mObservable.subscribe(aLong -> {
            if (mProgressButton.getState() == DownloadProgressButton.ButtonStatus.FINISH) {
                sub.dispose();
            } else {
                int progress = new Random().nextInt(10);
                mProgressButton.setProgress(mProgressButton.getProgress() + progress);
            }

        });
    }

    @Override
    public void clickPause() {
        sub.dispose();
        mProgressButton.pause();
    }

    @Override
    public void clickResume() {
        clickDownload();
    }

    @Override
    public void clickFinish() {
        sub.dispose();
        mProgressButton.reset();

    }

    @Override
    public void clickDisenable() {

    }
}
