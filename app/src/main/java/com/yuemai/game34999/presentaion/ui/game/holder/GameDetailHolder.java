package com.yuemai.game34999.presentaion.ui.game.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.presentaion.ui.game.activity.UserCommentActivity;
import com.yuemai.game34999.presentaion.ui.game.adapter.GamePicAdapter;
import com.yuemai.game34999.presentaion.widget.ClassifyView;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.List;

import butterknife.BindView;

import static com.yannis.common.util.UIUtils.getContext;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/9  10:56
 * @email : 923080261@qq.com
 * @description :
 */
public class GameDetailHolder extends BaseRecycleHolder implements ClassifyView.OnClassifyClickListener {
    @BindView(R.id.rv_gamePic)
    RecyclerView mRecyclerView;
    @BindView(R.id.cv_related_recommend)
    ClassifyView mCVRecommend;
    @BindView(R.id.cv_user_comment)
    ClassifyView mCVComment;
    private GamePicAdapter mAdapter;

    public GameDetailHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(Object data) {
        List list = TestDataUtils.getTestDatas(5);
        mAdapter = new GamePicAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mCVComment.setOnClassifyClickListener(this);
        mCVRecommend.setOnClassifyClickListener(this);
    }

    @Override
    public void onClassifyClick(View view) {
        switch (view.getId()) {
            case R.id.cv_related_recommend:

                break;
            case R.id.cv_user_comment:
                startActivity(UserCommentActivity.class);
                break;
            default:
                break;
        }
    }
}
