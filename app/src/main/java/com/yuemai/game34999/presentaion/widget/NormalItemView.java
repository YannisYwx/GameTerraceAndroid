package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yannis.common.util.ViewUtils;
import com.yuemai.game34999.R;

/*
 *  @项目名：  GameTerraceAndroid
 *  @创建者:   liucong
 *  @创建时间:  2017/7/6 11:07
 *  @邮箱：    liucong2012and@163.com 
 *  @描述：    
 */
public class NormalItemView extends RelativeLayout {
    private TextView mTvRight;

    public NormalItemView(Context context) {
        super(context, null);
    }

    public NormalItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_normal_item, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleItem);

        String leftText = typedArray.getString(R.styleable.SimpleItem_leftText);//左侧文本
        String rightText = typedArray.getString(R.styleable.SimpleItem_rightText);//右侧文本
        boolean isDivider = typedArray.getBoolean(R.styleable.SimpleItem_isDivider, true);//是否有分割线
        boolean isArrow = typedArray.getBoolean(R.styleable.SimpleItem_isArrow, true);//是否有箭头
        Drawable drawableLeft = typedArray.getDrawable(R.styleable.SimpleItem_drawableLeft);//左边图片


        TextView tvLeft = findViewById(R.id.view_item_text_left);
        mTvRight = findViewById(R.id.view_item_text_right);
        ImageView arrow = findViewById(R.id.view_item_arrow);
        View divider = findViewById(R.id.view_item_divider);

        tvLeft.setText(leftText);
        mTvRight.setText(rightText);
        divider.setVisibility(isDivider ? VISIBLE : INVISIBLE);
        arrow.setVisibility(isArrow ? VISIBLE : INVISIBLE);

        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        tvLeft.setCompoundDrawables(drawableLeft, null, null, null);
        tvLeft.setCompoundDrawablePadding(ViewUtils.dip2px(context, 10));

        typedArray.recycle();
    }

    //暴露给外界一个设置文本内容的方法
    public void setTextContent(String textContent) {
        mTvRight.setText(textContent);
    }

    //暴露给外面获取文本内容
    public String getTextContent() {
        String text = mTvRight.getText().toString().trim();
        return text;
    }

    public void setTextColor(int color) {
        mTvRight.setTextColor(color);
    }
}
