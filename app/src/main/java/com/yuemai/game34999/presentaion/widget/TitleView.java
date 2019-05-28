package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuemai.game34999.R;


/*
 *  @项目名：  RenRenLeXiang
 *  @邮箱：    liucong2012and@163.com 
 *  @创建者:   liucong
 *  @创建时间:  2017/3/31 22:45
 *  @描述：    TODO
 */
public class TitleView extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = "ItemView";
    private RelativeLayout mRl;
    private TextView mTvRight;
    private OnBackClickListener mListener;
    private TextView mTvTitle;
    private View mTitleParent;

    public TitleView(Context context) {
        super(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_title, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleStyle);
        String title = typedArray.getString(R.styleable.TitleStyle_titleCon);

        mTvTitle = findViewById(R.id.view_title_text);
        ImageView back = findViewById(R.id.view_title_back);
        mTitleParent = findViewById(R.id.view_title);

        mTvTitle.setText(title);
        back.setOnClickListener(this);

        typedArray.recycle();
    }


    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.back();
        }
    }

    public void setOnBackClickListener(OnBackClickListener listener) {
        mListener = listener;
    }

    public interface OnBackClickListener {
        void back();
    }

    //暴露一个设置标题的方法
    public void setTitleContent(String title) {
        mTvTitle.setText(title);
    }

    //暴露一个设置背景的方法
    public void setTitleViewBg(int colorId) {
        mTitleParent.setBackgroundColor(colorId);
    }
}
