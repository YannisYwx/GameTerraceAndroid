package com.yannis.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
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
public class ItemIconView extends View {
    private static final float TEXT_SIZE_OF_HEIGHT_SCALE = 1 / 3.5f;
    private static final float MARGIN_LEFT_RIGHT_OF_WIDTH = 1 / 20.0f;

    private String label;
    private boolean hasBottomLine;
    private boolean hasTopLine;
    private boolean hasDrawRightIcon;
    private int lineColor;
    private int labelColor;
    private int valueColor;

    private int iconLeftW, iconLeftH;
    private int iconRightW, iconRightH;


    private Drawable iconLeft;
    private Drawable iconRight;

    private Paint paint;

    private float strokeWidth = 4;

    private int width, height;
    private float textSize;
    private float marginLR;
    private String action;
    private String value;

    public ItemIconView(Context context) {
        super(context);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemIconView);
        label = ta.getString(R.styleable.ItemIconView_label);
        hasBottomLine = ta.getBoolean(R.styleable.ItemIconView_hasBottomLine, false);
        hasTopLine = ta.getBoolean(R.styleable.ItemIconView_hasTopLine, false);
        hasDrawRightIcon = ta.getBoolean(R.styleable.ItemIconView_hasDrawRightIcon, true);
        lineColor = ta.getColor(R.styleable.ItemIconView_lineColor, Color.parseColor("#D9D9D9"));
        labelColor = ta.getColor(R.styleable.ItemIconView_labelColor, Color.GRAY);
        valueColor = ta.getColor(R.styleable.ItemIconView_valueColor, Color.BLACK);
        value = ta.getString(R.styleable.ItemIconView_value);
        action = ta.getString(R.styleable.ItemIconView_action);
        iconLeft = ta.getDrawable(R.styleable.ItemIconView_viewIcon);
        iconRight = ta.getDrawable(R.styleable.ItemIconView_arrow);
        ta.recycle();

        label = TextUtils.isEmpty(label) ? "" : label;
        value = TextUtils.isEmpty(value) ? "" : value;
        iconRight = iconRight == null ? context.getResources().getDrawable(R.drawable.icon_arrow) : iconRight;
        init(context);

    }

    public ItemIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(final Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (action != null && !action.equals("")) {
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent();
                        intent.setClassName(context, action);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
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
            canvas.drawLine(0, height, width, height, paint);
        }

        if (hasTopLine) {
            canvas.drawLine(0, 0, width, 0, paint);
        }


        //draw left
        if (iconLeft != null) {
            iconLeftW = iconLeft.getIntrinsicWidth();
            iconLeftH = iconLeft.getIntrinsicHeight();
            iconLeft.setBounds((int) marginLR, (height / 2) - (iconLeftH / 2), (int) marginLR + iconLeftW, height / 2 + iconLeftH / 2);
            iconLeft.draw(canvas);
        }
        paint.setTextSize(textSize);
        paint.setColor(labelColor);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(label, marginLR + iconLeftW * 1.5f, ViewUtils.correctTextY(height / 2.0f, paint), paint);

        //draw right
        if (hasDrawRightIcon) {
            iconRightW = iconRight.getIntrinsicWidth();
            iconRightH = iconRight.getIntrinsicHeight();
            iconRight.setBounds(width - (int) marginLR - iconRightW, (height / 2) - (iconRightH / 2), width - (int) marginLR, height / 2 + iconRightH / 2);
            iconRight.draw(canvas);
        }

        paint.setTextSize(textSize);
        paint.setColor(valueColor);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(value, width - (marginLR + iconRightW * 1.5f), ViewUtils.correctTextY(height / 2.0f, paint), paint);

    }

    public void setValue(String value) {
        this.value = value;
        invalidate();
    }

    public String getValue() {
        return value;
    }

}
