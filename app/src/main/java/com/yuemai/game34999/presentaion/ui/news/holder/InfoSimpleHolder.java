package com.yuemai.game34999.presentaion.ui.news.holder;

import android.view.View;
import android.widget.TextView;

import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.News;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/24  19:04
 * @email : 923080261@qq.com
 * @description :
 */
public class InfoSimpleHolder extends BaseRecycleHolder<News> {
    @BindView(R.id.tv_information_title)
    TextView tvTitle;
    @BindView(R.id.tv_information_preview_num)
    TextView tvPreviewNum;
    @BindView(R.id.tv_information_typ)
    TextView tvType;
    @BindView(R.id.tv_information_time)
    TextView tvTime;
    String[] infoTypes;
    private boolean isShowType;

    public InfoSimpleHolder(View itemView, boolean isShowType) {
        super(itemView);
        infoTypes = itemView.getContext().getResources().getStringArray(R.array.informationArray);
        this.isShowType = isShowType;
    }

    @Override
    protected void refreshViewHolder(News data) {
        tvPreviewNum.setText(data.getReadNum() + "");
        tvTime.setText(data.getCreateTime());
        tvTitle.setText(data.getNewsTitle());
        if (isShowType) {
            String type = data.getNewsTitle();
            tvType.setText(type);
            int ii = 0;
            switch (ii) {
                case 7:
                    tvType.setBackgroundColor(getColor(R.color.info_interview));
                    break;
                case 1:
                    tvType.setBackgroundColor(getColor(R.color.info_news));
                    break;
                case 2:
                    tvType.setBackgroundColor(getColor(R.color.info_dynamic));
                    break;
                case 3:
                    tvType.setBackgroundColor(getColor(R.color.info_movie));
                    break;
                case 4:
                    tvType.setBackgroundColor(getColor(R.color.info_strategy));
                    break;
                case 5:
                    tvType.setBackgroundColor(getColor(R.color.info_data));
                    break;
                case 6:
                    tvType.setBackgroundColor(getColor(R.color.info_appraisal));
                    break;
                default:
                    break;
            }
        } else {
            tvType.setVisibility(View.GONE);
        }
    }
}
