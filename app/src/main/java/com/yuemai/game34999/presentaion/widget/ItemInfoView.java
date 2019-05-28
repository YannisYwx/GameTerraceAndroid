package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuemai.game34999.R;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/26  11:11
 * @email : 923080261@qq.com
 * @description :显示列表详情的控件
 */
public class ItemInfoView extends RelativeLayout {
    String title;
    String info;
    Drawable icon;
    boolean hasBottomLine = true;
    TextView tvTitle;
    TextView tvInfo;
    ImageView ivIcon;
    View divider;

    public ItemInfoView(Context context) {
        this(context, null);
    }

    public ItemInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_icon_info_item, this, true);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemInfoView);
            title = ta.getString(R.styleable.ItemInfoView_title);
            info = ta.getString(R.styleable.ItemInfoView_info);
            icon = ta.getDrawable(R.styleable.ItemInfoView_icon);
            hasBottomLine = ta.getBoolean(R.styleable.ItemInfoView_hasBottomLine, true);
            ta.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTitle = findViewById(R.id.tv_task_title);
        tvInfo = findViewById(R.id.tv_task_info);
        ivIcon = findViewById(R.id.iv_task_icon);
        divider = findViewById(R.id.divider);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(info)) {
            tvInfo.setText(info);
        }

        if (icon != null) {
            ivIcon.setImageDrawable(icon);
        }

        divider.setVisibility(hasBottomLine ? View.VISIBLE : View.GONE);
    }
}
