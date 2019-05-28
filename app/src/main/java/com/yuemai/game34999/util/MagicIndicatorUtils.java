package com.yuemai.game34999.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.yannis.common.util.ViewUtils;
import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.widget.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import static com.yannis.common.util.UIUtils.getContext;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/24  16:47
 * @email : 923080261@qq.com
 * @description :
 */
public class MagicIndicatorUtils {

    public static void bindViewpager(MagicIndicator magicIndicator, Context context, ViewPager viewPager, CharSequence[] titles) {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(isAdjustMode(context, titles));
        commonNavigator.setSmoothScroll(true);
        commonNavigator.setSkimOver(true);
        commonNavigator.setScrollPivotX(0.1f);

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setSelectedColor(getContext().getResources().getColor(R.color.themeBluePre));
                simplePagerTitleView.setNormalColor(getContext().getResources().getColor(R.color.text_gray));
                simplePagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
                indicator.setLineHeight(4);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setXOffset(-10);

                indicator.setColors(getContext().getResources().getColor(R.color.themeBluePre));

                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        // must after setNavigator
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDrawingCacheBackgroundColor(Color.RED);
        titleContainer.setDividerPadding(UIUtil.dip2px(getContext(), 12));
        titleContainer.setDividerDrawable(context.getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    public static boolean isAdjustMode(Context context, CharSequence[] titles) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence cs : titles) {
            sb.append(cs);
        }
        Paint paint = new Paint();
        paint.setTextSize(ViewUtils.sp2px(context, 14));
        float titleLength = ViewUtils.getTextRectWidth(paint, sb.toString());
        float sw = ViewUtils.getScreenWidth(context);
        float scale = sw / titleLength;
        if (scale > 1.8f) {
            return true;
        }
        return false;
    }
}
