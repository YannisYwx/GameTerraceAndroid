package com.yuemai.game34999.presentaion.ui.news.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yannis.common.util.DateUtils;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.Comment;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/2/1  14:35
 * @email : 923080261@qq.com
 * @description : 评论
 */
public class CommentHolder extends BaseRecycleHolder<Comment> {
    @BindView(R.id.tv_commentUserName)
    TextView tvUserName;
    @BindView(R.id.tv_publishTime)
    TextView tvPublishTime;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_like)
    TextView tvLike;

    public CommentHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(Comment data) {
        if (data == null) {
            return;
        }
        tvComment.setText(data.getContent());
        tvPublishTime.setText(DateUtils.getDesignedDataAndTime(data.getCreate_time()));
        tvLike.setText(String.valueOf(data.getSupport_count()));
        String userName = data.getPassport().getNick_name();
        if (TextUtils.isEmpty(userName)) {
            userName = "匿名";
        }
        tvUserName.setText(userName);
    }
}
