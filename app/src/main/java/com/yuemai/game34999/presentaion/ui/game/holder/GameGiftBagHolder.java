package com.yuemai.game34999.presentaion.ui.game.holder;

import android.view.View;
import android.widget.ProgressBar;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;

import java.util.Random;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/29  18:43
 * @email : 923080261@qq.com
 * @description :
 */
public class GameGiftBagHolder extends BaseRecycleHolder{
    public GameGiftBagHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(Object data) {
        ProgressBar progressBar = itemView.findViewById(R.id.pb_gift);
        progressBar.setMax(100);
        progressBar.setProgress(new Random().nextInt(100));
    }
}
