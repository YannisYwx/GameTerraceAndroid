package com.yuemai.game34999.presentaion.ui.game.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.ArticleComment;
import com.yuemai.game34999.data.bean.Comment;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.contract.UserCommentContract;
import com.yuemai.game34999.presentaion.presenter.game.UserCommentPresenter;
import com.yuemai.game34999.presentaion.ui.game.adapter.UserCommentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/9  18:39
 * @email : 923080261@qq.com
 * @description : 用户评论
 */
public class UserCommentActivity extends AbstractMvpLoadPagerActivity<UserCommentPresenter> implements UserCommentContract.View {
    private static final String KEY_COMMENT_ID = "COMMENT_ID";
    @BindView(R.id.rv_userComment)
    RecyclerView mRecyclerView;
    UserCommentAdapter mAdapter;
    List<Comment> mComments = new ArrayList<>();
    private long topic_id;
    private boolean isNoMore = false;

    public static void start(Context context, long commentId) {
        Intent intent = new Intent(context, UserCommentActivity.class);
        intent.putExtra(KEY_COMMENT_ID, commentId);
        context.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_userComment);
    }

    @Override
    protected void refreshContentView(View contentView) {

    }

    @Override
    public void init() {
        super.init();
        topic_id = getIntent().getLongExtra(KEY_COMMENT_ID, -1);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_user_comment;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        presenter.getCommentList(topic_id);
    }

    @Override
    public void injectThis(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateLoading;
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
        showToast(msg);
    }

    @OnClick(R.id.btn_iComment)
    public void comment() {

    }

    @Override
    public void loadComments(ArticleComment comment) {
        showSuccessView();
        if (comment.getComments().size() == 0) {
            showToast("没有更多了");
            return;
        }
        if (mComments.size() == 0) {
            mComments.addAll(comment.getComments());
            mAdapter = new UserCommentAdapter(mComments);
            mAdapter.setOnLoadMoreDataListener(() -> {
                if (isNoMore) {
                    mAdapter.noMoreData();
                    return;
                }
                presenter.getCommentList(topic_id);
            });
            LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setLayoutAnimation(controller);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.loadMoreData(comment.getComments());
        }
    }

    @Override
    public void onNoMoreData() {
        isNoMore = true;
        showSuccessView();
        showToast("没有更多数据了");
        mAdapter.noMoreData();
    }
}
