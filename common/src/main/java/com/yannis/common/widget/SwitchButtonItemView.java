package com.yannis.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yannis.common.R;
import com.yannis.common.util.ViewUtils;


/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/18  15:06
 * @email : 923080261@qq.com
 * @description : 通用的列表item
 */
public class SwitchButtonItemView extends RelativeLayout {
    TextView tvLabel;
    SwitchButton mSwitchButton;
    private String label;
    private Drawable mDrawable;
    private boolean hasBottomLine = true;
    private boolean hasToggleButton = true;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int dividerColor = Color.parseColor("#E3E3E3");
    private float dividerHeight;
    private Paint mPaint;


    public SwitchButtonItemView(@NonNull Context context) {
        this(context, null);
    }

    public SwitchButtonItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButtonItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        inflate(context, R.layout.view_toggle_button, this);
    }


    private void init(Context context, AttributeSet attrs) {
        dividerHeight = ViewUtils.dip2px(context, 0.88f);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitchButtonItemView);
            this.label = ta.getString(R.styleable.SwitchButtonItemView_sbi_label);
            this.hasBottomLine = ta.getBoolean(R.styleable.SwitchButtonItemView_sbi_hasBottomLine, true);
            this.hasToggleButton = ta.getBoolean(R.styleable.SwitchButtonItemView_sbi_hasToggleButton, true);
            this.mDrawable = ta.getDrawable(R.styleable.SwitchButtonItemView_sbi_rightIcon);
            this.dividerColor = ta.getColor(R.styleable.SwitchButtonItemView_sbi_dividerColor, Color.parseColor("#E3E3E3"));
            this.dividerHeight = ta.getDimension(R.styleable.SwitchButtonItemView_sbi_dividerHeight, dividerHeight);
            this.paddingLeft = (int) ta.getDimension(R.styleable.SwitchButtonItemView_sbi_dividerPaddingLeft, 0);
            this.paddingRight = (int) ta.getDimension(R.styleable.SwitchButtonItemView_sbi_dividerPaddingRight, 0);
            ta.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStrokeWidth(dividerHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvLabel = findViewById(R.id.tv_label);
        mSwitchButton = findViewById(R.id.switch_button);
        mSwitchButton.setVisibility(hasToggleButton ? VISIBLE : GONE);


        if (!TextUtils.isEmpty(label)) {
            tvLabel.setText(label);
        }

        if (mDrawable != null) {
            /// 这一步必须要做,否则不会显示.
            mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
            tvLabel.setCompoundDrawables(null, null, mDrawable, null);
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (hasBottomLine) {
            canvas.drawLine(paddingLeft, getMeasuredHeight(), getMeasuredWidth() - paddingRight, getMeasuredHeight(), mPaint);
        }
    }

    public void setBackground(@DrawableRes int res) {
        setBackgroundResource(res);
        invalidate();
    }


}