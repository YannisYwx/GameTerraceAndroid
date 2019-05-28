package com.yannis.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.yannis.common.R;
import com.yannis.common.util.ViewUtils;


/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  11:24
 * @email : 923080261@qq.com
 * @description :
 */
public class ItemView extends View {
    private static final float TEXT_SIZE_OF_HEIGHT_SCALE = 1 / 3.5f;
    private static final float MARGIN_LEFT_RIGHT_OF_WIDTH = 1 / 20.0f;

    private String label;
    private String value;
    private boolean hasBottomLine;
    private boolean hasTopLine;
    private int lineColor;
    private int labelColor;
    private int valueColor;

    private Paint paint;

    private float strokeWidth = 4;

    private int width, height;
    private float textSize;
    private float marginLR;

    private Drawable iconRight;

    public ItemView(Context context) {
        super(context);
        init();
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        label = ta.getString(R.styleable.ItemView_label);
        value = ta.getString(R.styleable.ItemView_value);
        hasBottomLine = ta.getBoolean(R.styleable.ItemView_hasBottomLine, false);
        hasTopLine = ta.getBoolean(R.styleable.ItemView_hasTopLine, false);
        lineColor = ta.getColor(R.styleable.ItemView_lineColor, Color.parseColor("#D9D9D9"));
        labelColor = ta.getColor(R.styleable.ItemView_labelColor, Color.parseColor("#FFFFFF"));
        valueColor = ta.getColor(R.styleable.ItemView_valueColor, Color.parseColor("#787878"));
        ta.recycle();
        iconRight = context.getResources().getDrawable(R.drawable.icon_arrow);
        label = label == null || label.equals("") ? "" : label;
        value = value == null || value.equals("") ? "" : value;
        init();

    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.textSize = height * TEXT_SIZE_OF_HEIGHT_SCALE;
        this.marginLR = width * MARGIN_LEFT_RIGHT_OF_WIDTH;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(lineColor);
        paint.setStrokeWidth(strokeWidth);
        if (hasBottomLine) {
            canvas.drawLine(0 + height * 0.25f, height, width, height, paint);
        }

        if (hasTopLine) {
            canvas.drawLine(0 + height * 0.25f, 0, width, 0, paint);
        }
        paint.setTextSize(textSize);
        paint.setColor(labelColor);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(label, marginLR, ViewUtils.correctTextY(height / 2.0f, paint), paint);

        int iconH = iconRight.getIntrinsicHeight();
        int iconW = iconRight.getIntrinsicWidth();
        iconRight.setBounds(width - (int) marginLR - iconW, height / 2 - iconH / 2, width - (int) marginLR, height / 2 + iconH / 2);
        iconRight.draw(canvas);

        paint.setTextSize(textSize);
        paint.setColor(valueColor);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(value, width - marginLR * 1.2f - iconW, ViewUtils.correctTextY(height / 2.0f, paint), paint);

    }

    public void setValue(String value) {
        this.value = value;
        invalidate();
    }

    public String getValue() {
        return value;
    }
}
