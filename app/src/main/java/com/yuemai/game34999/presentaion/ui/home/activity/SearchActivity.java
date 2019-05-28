package com.yuemai.game34999.presentaion.ui.home.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.TagGroupLayout;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.widget.FlowLayout;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/28  16:02
 * @email : 923080261@qq.com
 * @description :
 */
public class SearchActivity extends AbstractMvpLoadPagerActivity {
    private String[] mHistory = new String[]{"a", "B", "C","部落冲突", "王者荣耀", "守卫者", "三国战纪",
            "拳皇98c", "火柴人大战", "大战僵尸", "水果塔防",
            "猩球大战", "Pascal", "元气战士", "Winner", "口袋妖怪"};
    private String[] mHot = new String[]{"魂之轨迹", "火柴人联盟2", "王者荣耀", "萌幻西游",
            "皇室战争", "诛仙", "口袋妖怪3DS", "最终幻想：觉醒",
            "剑与家园", "荒野行动"};

    @BindView(R.id.fl_searchHistory)
    FlowLayout flSearchHistory;
    @BindView(R.id.fl_hotSearch)
    FlowLayout flHotSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tgl_history)
    TagGroupLayout tagViewHistory;
    @BindView(R.id.tgl_hot)
    TagGroupLayout tagViewHot;
    private LayoutInflater mInflater;

    @Override
    public int setContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_search);
    }

    @Override
    protected void refreshContentView(View contentView) {
        mInflater = LayoutInflater.from(this);
        initSearchData();
    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public boolean isShowToolbar() {
        return false;
    }

    @Override
    public void initEvent() {
        super.initEvent();
        registerOnClickListener(tvCancel, tvDelete);
    }

    /**
     * 将数据放入流式布局
     */
    private void initSearchData() {
        for (String aMHistory : mHistory) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.search_label_tv, flSearchHistory, false);
            tv.setText(aMHistory);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(v -> {
                //加入搜索历史纪录记录
                Toast.makeText(SearchActivity.this, str, Toast.LENGTH_LONG).show();
            });
            flSearchHistory.addView(tv);
        }
        for (String aMHot : mHot) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.search_label_tv, flSearchHistory, false);
            tv.setText(aMHot);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(v -> {
                //加入搜索历史纪录记录
                Toast.makeText(SearchActivity.this, str, Toast.LENGTH_LONG).show();
            });
            flHotSearch.addView(tv);
        }

        tagViewHistory.setTags(TagGroupLayout.TagColor.getRandomColors(mHistory.length), mHistory);
        tagViewHot.setTags(TagGroupLayout.TagColor.getRandomColors(mHot.length), mHot);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_delete:
                //清除历史搜索记录
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
    public void injectThis(ActivityComponent component) {

    }
}
