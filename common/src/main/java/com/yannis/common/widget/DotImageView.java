package com.yannis.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.yannis.common.R;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/29  11:22
 * @email : 923080261@qq.com
 * @description : 带数字提示的图片
 */
public class DotImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint paint;
    /**
     * 数字值
     */
    private int msgNum = 0;
    /**
     * 文字的bound
     */
    private Rect textBounds;
    /**
     * 点大小
     */
    private int dotSize = 6;
    /**
     * 点颜色
     */
    private int dotColor;
    /**
     * 数字颜色
     */
    private int numColor;

    public DotImageView(Context context) {
        this(context, null);
    }

    public DotImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DotImageView);
        dotColor = ta.getColor(R.styleable.DotImageView_dotColor, getResources().getColor(android.R.color.holo_red_light));
        numColor = ta.getColor(R.styleable.DotImageView_numColor, getResources().getColor(android.R.color.white));
        ta.recycle();
        textBounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (msgNum <= 0) {
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int radius;
        if (measuredHeight > measuredWidth) {
            radius = measuredWidth / dotSize;
        } else {
            radius = measuredHeight / dotSize;
        }
        paint.setColor(dotColor);
        canvas.drawCircle(measuredWidth - radius, radius, radius, paint);
        paint.setColor(numColor);
        paint.setTextSize(radius);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(String.valueOf(msgNum), 0, String.valueOf(msgNum).length(), textBounds);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (radius * 2 - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(String.valueOf(msgNum), getMeasuredWidth() - radius - textBounds.width() / 2, baseline, paint);
    }

    /**
     * 设置数字
     *
     * @param msgNum 数字
     */
    public void setMsgNum(int msgNum) {
        if (msgNum > 99) {
            this.msgNum = 99;
        } else if (msgNum < 0) {
            this.msgNum = 0;
        } else {
            this.msgNum = msgNum;
        }
        postInvalidate();
    }

    /**
     * 设置点大小
     *
     * @param dotSize 点大小
     */
    public void setDotSize(int dotSize) {
        if (dotSize > 10 || dotSize <= 1) {
            return;
        }
        this.dotSize = dotSize;
        postInvalidate();
    }

}
