package com.yuemai.game34999.presentaion.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.yannis.common.base.BaseActivity;
import com.yannis.common.util.SPUtils;
import com.yuemai.game34999.R;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.data.prefs.AppPreferencesHelper;
import com.yuemai.game34999.presentaion.widget.ScaleCircleNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/19  18:49
 * @email : 923080261@qq.com
 * @description :
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.btn_toMain)
    Button btnStart;
    private @IdRes
    int[] resIds = {R.drawable.launchimage_1, R.drawable.launchimage_2, R.drawable.launchimage_3};
    Animation btnEnter;
    Animation btnExit;

    public static void start(Context context) {
        context.startActivity(new Intent(context, GuideActivity.class));
    }

    @Override
    public void init() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this);
        btnEnter = AnimationUtils.loadAnimation(this, R.anim.btn_enter);
        btnExit = AnimationUtils.loadAnimation(this, R.anim.btn_exit);
        btnEnter.setInterpolator(new OvershootInterpolator());
    }

    @OnClick(R.id.btn_toMain)
    public void toHomePager() {
        SPUtils.put(Constants.SpConstants.IS_FIRST_IN, false);
        AppPreferencesHelper.getInstance().markFirstIn();
        MainActivity.start(this);
        finish();
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_guide;
    }

    private int pagerPosition;

    @Override
    public void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == resIds.length - 1) {
                    btnStart.startAnimation(btnEnter);
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    if (pagerPosition == resIds.length - 1 && position == resIds.length - 2) {
                        btnStart.startAnimation(btnExit);
                    }
                    btnStart.setVisibility(View.GONE);
                }
                pagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化pager
     */
    private void initPager() {
        //构建数据
        List<View> list = new ArrayList<>();

        for (int i = 0; i < resIds.length; i++) {
            ImageView iv = new ImageView(this);

            iv.setImageResource(resIds[i]);
            //铺满x和y轴
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            list.add(iv);
        }


        //关联适配器
        GuideAdapter adapter = new GuideAdapter(list);
        mViewPager.setAdapter(adapter);
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator);
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(resIds.length);
        scaleCircleNavigator.setNormalCircleColor(getResources().getColor(R.color.alpha_30_white));
        scaleCircleNavigator.setSelectedCircleColor(Color.WHITE);
        scaleCircleNavigator.setCircleClickListener(index -> mViewPager.setCurrentItem(index));
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void initData() {
        initPager();
    }

    public class GuideAdapter extends PagerAdapter {

        List<View> mList;

        GuideAdapter(List<View> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //1. 从集合中取出对应的view
            View view = mList.get(position);
            //2. 添加到容器中
            container.addView(view);
            //3. 返回这个view的标记 ，其实就返回这个view即可
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
