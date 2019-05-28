package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yannis.common.util.ViewUtils;
import com.yuemai.game34999.R;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/9  14:54
 * @email : 923080261@qq.com
 * @description :分类的view
 */
public class ClassifyView extends RelativeLayout {
    private View colorView;
    private TextView leftTextView;
    private TextView rightTextView;
    private Drawable drawable;
    private String leftText;
    private String rightText;
    private int vColor = DEFAULT_COLOR;
    private int leftTextColor = Color.parseColor("#333333");
    private int rightTextColor = DEFAULT_COLOR;
    public static int DEFAULT_COLOR = Color.parseColor("#4387f6");

    private OnClassifyClickListener mListener;

    public ClassifyView(Context context) {
        this(context, null);
    }

    public ClassifyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassifyView);
            drawable = ta.getDrawable(R.styleable.ClassifyView_cv_rightTextDrawable);
            leftText = ta.getString(R.styleable.ClassifyView_cv_leftText);
            leftTextColor = ta.getColor(R.styleable.ClassifyView_cv_leftTextColor, Color.parseColor("#333333"));
            rightText = ta.getString(R.styleable.ClassifyView_cv_rightText);
            rightTextColor = ta.getColor(R.styleable.ClassifyView_cv_rightTextColor, DEFAULT_COLOR);
            vColor = ta.getColor(R.styleable.ClassifyView_cv_view_color, DEFAULT_COLOR);
            ta.recycle();
        }
        LayoutInflater.from(context).inflate(R.layout.view_guide_layout, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        colorView = findViewById(R.id.view_l);
        leftTextView = findViewById(R.id.tv_left);
        rightTextView = findViewById(R.id.tv_right);
        colorView.setBackgroundColor(vColor);
        if (!TextUtils.isEmpty(leftText)) {
            leftTextView.setTextColor(leftTextColor);
            leftTextView.setText(leftText);
            leftTextView.setVisibility(VISIBLE);
        } else {
            leftTextView.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(rightText)) {
            rightTextView.setTextColor(rightTextColor);
            rightTextView.setText(rightText);
            rightTextView.setVisibility(VISIBLE);
        } else {
            rightTextView.setVisibility(GONE);
        }

        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            rightTextView.setCompoundDrawables(null, null, drawable, null);
            rightTextView.setCompoundDrawablePadding(10);
        }

        rightTextView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onClassifyClick(this);
            }
        });
    }

    public void setLeftText(String text) {
        if (TextUtils.isEmpty(text) && leftTextView != null) {
            leftTextView.setText(text);
        }
    }

    public void setRightText(String text) {
        if (TextUtils.isEmpty(text) && rightTextView != null) {
            rightTextView.setText(text);
        }
    }

    public void setDrawableRight(Drawable drawable) {
        if (drawable == null) return;
        this.drawable = drawable;
        rightTextView.setCompoundDrawables(null, null, drawable, null);
        rightTextView.setCompoundDrawablePadding(ViewUtils.dip2px(this.getContext(), 5));
    }

    public void setColor(@ColorInt int colorRes) {
        if (colorView != null) {
            colorView.setBackgroundColor(colorRes);
        }
    }

    public interface OnClassifyClickListener {
        /**
         * 分类导航条点击事件
         * @param view 当前的view
         */
        void onClassifyClick(View view);
    }

    public void setOnClassifyClickListener(OnClassifyClickListener listener) {
        mListener = listener;
    }
}
