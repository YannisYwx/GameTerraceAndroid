package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.yuemai.game34999.R;

import static com.yuemai.game34999.presentaion.widget.DownloadProgressButton.ButtonStatus.DISENABLE;
import static com.yuemai.game34999.presentaion.widget.DownloadProgressButton.ButtonStatus.DOWNLOADING;
import static com.yuemai.game34999.presentaion.widget.DownloadProgressButton.ButtonStatus.FINISH;


/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/27  9:52
 * @email : 923080261@qq.com
 * @description :显示进度的下载按钮
 */
public class DownloadProgressButton extends android.support.v7.widget.AppCompatTextView {

    public interface OnDownLoadClickListener {
        /**
         * 点击开始下载
         */
        void clickDownload();

        /**
         * 点击暂停
         */
        void clickPause();

        /**
         * 点击重新开始
         */
        void clickResume();

        /**
         * 点击完成
         */
        void clickFinish();

        /**
         * 点击不可用下载
         */
        void clickDisenable();
    }

    /**
     * 背景画笔
     */
    private Paint mBackgroundPaint;
    /**
     * 背景边框画笔
     */
    private Paint mBackgroundBorderPaint;
    /**
     * 按钮文字画笔
     */
    private volatile Paint mTextPaint;
    /**
     * 背景颜色
     */
    private int mBackgroundColor;
    /**
     * 下载中后半部分后面背景颜色
     */
    private int mBackgroundSecondColor;
    /**
     * 文字颜色
     */
    private int mTextColor;
    /**
     * 覆盖后颜色
     */
    private int mTextCoverColor;


    private float mProgress = -1;
    private float mToProgress;
    private int mMaxProgress;
    private int mMinProgress;
    private float mProgressPercent;

    private float mButtonRadius;

    /**
     * 控件背景范围
     */
    private RectF mBackgroundBounds;
    /**
     * 背景颜色渲染 进度变化
     */
    private LinearGradient mProgressBgGradient;
    /**
     * 字体颜色渲染变化
     */
    private LinearGradient mProgressTextGradient;

    private CharSequence mCurrentText;

    private ButtonStatus mButtonState = ButtonStatus.NORMAL;

    /**
     * 按钮是否可用
     */
    private boolean isDisenable = false;

    private static final String PROGRESS_FORMAT = "%d%%";

    public enum ButtonStatus {
        /**
         * 正常状态
         */
        NORMAL("下载"),
        /**
         * 等待状态
         */
        WAITING("等待"),
        /**
         * 下载状态
         */
        DOWNLOADING(PROGRESS_FORMAT),
        /**
         * 暂停状态
         */
        PAUSE("继续"),
        /**
         * 完成状态
         */
        FINISH("安装"),
        /**
         * 按钮不可使用
         */
        DISENABLE("下载");
        private String text;


        ButtonStatus(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    private int mState = 0;
    /**
     * 线条的宽度
     */
    private float backgroundStrokeWidth;
    private String mNormalText, mDowningText, mFinishText, mPauseText, mWaitingText;
    private OnDownLoadClickListener mOnDownLoadClickListener;
    private boolean mEnablePause = true;


    public DownloadProgressButton(Context context) {
        this(context, null);
    }

    public DownloadProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initAttrs(context, attrs);
            init();
        }
    }

    public DownloadProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DownloadProgressButton);
        mBackgroundColor = a.getColor(R.styleable.DownloadProgressButton_background_color, Color.parseColor("#6699ff"));
        mBackgroundSecondColor = a.getColor(R.styleable.DownloadProgressButton_background_second_color, Color.LTGRAY);
        mButtonRadius = a.getDimension(R.styleable.DownloadProgressButton_radius, getMeasuredHeight() / 2);
        mTextColor = a.getColor(R.styleable.DownloadProgressButton_text_color, mBackgroundColor);
        mTextCoverColor = a.getColor(R.styleable.DownloadProgressButton_text_cover_color, Color.WHITE);
        backgroundStrokeWidth = a.getDimension(R.styleable.DownloadProgressButton_background_strokeWidth, 3F);
        mNormalText = a.getString(R.styleable.DownloadProgressButton_text_normal);
        mDowningText = a.getString(R.styleable.DownloadProgressButton_text_downing);
        mFinishText = a.getString(R.styleable.DownloadProgressButton_text_finish);
        mPauseText = a.getString(R.styleable.DownloadProgressButton_text_pause);
        a.recycle();
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        mTextPaint.setTextSize(getTextSize());
        invalidate();
    }

    private void init() {
        mMaxProgress = 100;
        mMinProgress = 0;
        mProgress = 0;
        if (!TextUtils.isEmpty(mNormalText)) {
            ButtonStatus.NORMAL.setText(mNormalText);
        }
        if (!TextUtils.isEmpty(mDowningText)) {
            ButtonStatus.DOWNLOADING.setText(mDowningText + PROGRESS_FORMAT);
        } else {
            mDowningText = "";
        }
        if (!TextUtils.isEmpty(mFinishText)) {
            ButtonStatus.FINISH.setText(mFinishText);
        }
        if (!TextUtils.isEmpty(mPauseText)) {
            ButtonStatus.PAUSE.setText(mPauseText);
        }
        if (!TextUtils.isEmpty(mWaitingText)) {
            ButtonStatus.WAITING.setText(mWaitingText);
        }
        //设置背景画笔
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundBorderPaint = new Paint();
        mBackgroundBorderPaint.setAntiAlias(true);
        mBackgroundBorderPaint.setStyle(Paint.Style.STROKE);
        mBackgroundBorderPaint.setStrokeWidth(backgroundStrokeWidth);
        mBackgroundBorderPaint.setColor(mBackgroundColor);
        //设置文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        //解决文字有时候画不出问题
        setLayerType(LAYER_TYPE_SOFTWARE, mTextPaint);

        //初始化状态设为NORMAL
        setState(ButtonStatus.NORMAL);
        setOnClickListener(v -> {
            if (mOnDownLoadClickListener == null) {
                return;
            }
            if (getState() == ButtonStatus.NORMAL) {
                mOnDownLoadClickListener.clickDownload();
                setState(DOWNLOADING);
                setProgressText(0);
            } else if (getState() == DOWNLOADING) {
                if (mEnablePause) {
                    mOnDownLoadClickListener.clickPause();
                    setState(ButtonStatus.PAUSE);
                }
            } else if (getState() == ButtonStatus.PAUSE) {
                mOnDownLoadClickListener.clickResume();
                setState(DOWNLOADING);
                setProgressText((int) mProgress);
            } else if (getState() == FINISH) {
                mOnDownLoadClickListener.clickFinish();
            } else if (getState() == DISENABLE) {
                mOnDownLoadClickListener.clickDisenable();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            drawing(canvas);
        }
    }

    private void drawing(Canvas canvas) {
        drawBackground(canvas);
        drawTextAbove(canvas);
    }

    /**
     * 画背景 进度
     *
     * @param canvas 画布
     */
    private void drawBackground(Canvas canvas) {
        if (mBackgroundBounds == null) {
            mBackgroundBounds = new RectF();
            if (mButtonRadius == 0) {
                mButtonRadius = getMeasuredHeight() / 2;
            }
            mBackgroundBounds.left = backgroundStrokeWidth;
            mBackgroundBounds.top = backgroundStrokeWidth;
            mBackgroundBounds.right = getMeasuredWidth() - backgroundStrokeWidth;
            mBackgroundBounds.bottom = getMeasuredHeight() - backgroundStrokeWidth;
        }
        mBackgroundBorderPaint.setAlpha(255);
        switch (mButtonState) {
            case NORMAL:
                break;
            case DOWNLOADING:
            case PAUSE:
                mProgressPercent = mProgress / (mMaxProgress + 0f);
                /*
                  mBackgroundColor 进度的颜色 显示下载部分
                  mBackgroundSecondColor 背景颜色  显示未完成部分
                 */
                mProgressBgGradient = new LinearGradient(backgroundStrokeWidth,
                        0, getMeasuredWidth() - backgroundStrokeWidth, 0,
                        new int[]{mBackgroundColor, mBackgroundSecondColor},
                        new float[]{mProgressPercent, mProgressPercent + 0.001f},
                        Shader.TileMode.CLAMP
                );
                mBackgroundPaint.setColor(mBackgroundColor);
                mBackgroundPaint.setShader(mProgressBgGradient);
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                break;
            case FINISH:
                mBackgroundPaint.setShader(null);
                mBackgroundPaint.setColor(mBackgroundColor);
                canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundPaint);
                break;
            case DISENABLE:
                mBackgroundBorderPaint.setAlpha(100);
                break;
            default:
                break;
        }
        //绘制边框
        canvas.drawRoundRect(mBackgroundBounds, mButtonRadius, mButtonRadius, mBackgroundBorderPaint);
    }

    /**
     * 画文字
     *
     * @param canvas 画布
     */
    private void drawTextAbove(Canvas canvas) {
        mTextPaint.setTextSize(getTextSize());
        final float y = (canvas.getHeight() / 2 - (mTextPaint.descent() / 2 + mTextPaint.ascent() / 2)) * 1.01f;
        if (mCurrentText == null) {
            mCurrentText = "";
        }
        final float textWidth = mTextPaint.measureText(mCurrentText.toString());
        switch (mButtonState) {
            case NORMAL:
            case WAITING:
                mTextPaint.setShader(null);
                mTextPaint.setColor(mTextColor);
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;
            case DOWNLOADING:
            case PAUSE:
                float w = getMeasuredWidth() - 2 * backgroundStrokeWidth;
                //进度条压过距离 当前绘制了的进度长度
                float coverLength = w * mProgressPercent;
                //文字左侧
                float textLeft = w / 2 - textWidth / 2;
                //文字右侧
                float textRight = w / 2 + textWidth / 2;
                //文字变色部分的距离 被覆盖的文字部分
                float coverTextLength = coverLength - textLeft;
                float textProgress = coverTextLength / textWidth;
                if (coverLength <= textLeft) {
                    //当前进度还未达到文字位置
                    mTextPaint.setShader(null);
                    mTextPaint.setColor(mTextColor);
                } else if (textLeft < coverLength && coverLength <= textRight) {
                    //当前进度在文字长度范围内
                    mProgressTextGradient = new LinearGradient((w - textWidth) / 2 + backgroundStrokeWidth, 0,
                            (w + textWidth) / 2 + backgroundStrokeWidth, 0,
                            new int[]{mTextCoverColor, mTextColor},
                            new float[]{textProgress, textProgress + 0.001f},
                            Shader.TileMode.CLAMP);
                    mTextPaint.setColor(mTextColor);
                    mTextPaint.setShader(mProgressTextGradient);
                } else {
                    //完全覆盖
                    mTextPaint.setShader(null);
                    mTextPaint.setColor(mTextCoverColor);
                }
                canvas.drawText(mCurrentText.toString(), (w - textWidth) / 2 + backgroundStrokeWidth, y, mTextPaint);
                break;
            case FINISH:
                mTextPaint.setColor(mTextCoverColor);
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;
            case DISENABLE:
                mTextPaint.setColor(mTextColor);
                mTextPaint.setAlpha(100);
                canvas.drawText(mCurrentText.toString(), (getMeasuredWidth() - textWidth) / 2, y, mTextPaint);
                break;
            default:
                break;
        }
    }

    /**
     * 获取状态
     *
     * @return ButtonState
     */
    public ButtonStatus getState() {
        return mButtonState;
    }

    /**
     * 重置
     */
    public void reset() {
        setState(ButtonStatus.NORMAL);
    }

    /**
     * 完成
     */
    public void finish() {
        setState(FINISH);
    }

    /**
     * 暂停
     */
    public void pause() {
        setState(ButtonStatus.PAUSE);
    }

    /**
     * 下载
     */
    public void downloading() {
        setState(ButtonStatus.DOWNLOADING);
    }

    /**
     * 等待
     */
    public void waiting() {
        setState(ButtonStatus.WAITING);
    }

    /**
     * 按钮失效
     */
    public void disenable() {
        setState(ButtonStatus.DISENABLE);
    }

    /**
     * 设置按钮状态
     *
     * @param state 正常、下载中、完成、暂停、等待
     */
    private void setState(ButtonStatus state) {
        this.mButtonState = state;
        mState = state.ordinal();
        mCurrentText = mButtonState.getText();
        switch (mButtonState) {
            case NORMAL:
                //正常状态下
                mProgress = mToProgress = mMinProgress;
                break;
            case WAITING:
                //等待中
                break;
            case DOWNLOADING:
                //正在下载
                mCurrentText = mDowningText + String.format(mCurrentText.toString(), (int) mProgress);
                break;
            case PAUSE:
                //按下去的效果
                break;
            case FINISH:
                //下载完成
                mProgress = mMaxProgress;
                break;
            case DISENABLE:
                //设置按钮不可用
                isDisenable = true;
                break;
            default:
                break;
        }
        setCurrentText(mCurrentText);
        invalidate();
    }

    public void setCurrentText(CharSequence charSequence) {
        mCurrentText = charSequence;
        invalidate();
    }

    public CharSequence getCurrentText() {
        return mCurrentText;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        if (progress < mMinProgress || progress > mMaxProgress) {
            return;
        }
        mProgress = progress;
        setState(DOWNLOADING);
    }

    private void setProgressText(int progress) {
        if (getState() == DOWNLOADING) {
            setCurrentText(mDowningText + progress + "%");
        }
    }

    public float getButtonRadius() {
        return mButtonRadius;
    }

    public void setButtonRadius(float buttonRadius) {
        mButtonRadius = buttonRadius;
    }

    public int getTextColor() {
        return mTextColor;
    }

    @Override
    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    public int getTextCoverColor() {
        return mTextCoverColor;
    }

    public void setTextCoverColor(int textCoverColor) {
        mTextCoverColor = textCoverColor;
    }

    public int getMinProgress() {
        return mMinProgress;
    }

    public void setMinProgress(int minProgress) {
        mMinProgress = minProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    public OnDownLoadClickListener getOnDownLoadClickListener() {
        return mOnDownLoadClickListener;
    }

    public void setOnDownLoadClickListener(OnDownLoadClickListener onDownLoadClickListener) {
        mOnDownLoadClickListener = onDownLoadClickListener;
    }

    public boolean isEnablePause() {
        return mEnablePause;
    }

    public void setEnablePause(boolean enablePause) {
        mEnablePause = enablePause;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mState = ss.state;
        mProgress = ss.progress;
        mCurrentText = ss.currentText;
        mButtonState = ButtonStatus.values()[mState];
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, (int) mProgress, mState, mCurrentText.toString());
    }

    public static class SavedState extends BaseSavedState {

        private int progress;
        private int state;
        private String currentText;

        public SavedState(Parcelable parcel, int progress, int state, String currentText) {
            super(parcel);
            this.progress = progress;
            this.state = state;
            this.currentText = currentText;
        }

        private SavedState(Parcel in) {
            super(in);
            progress = in.readInt();
            state = in.readInt();
            currentText = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(progress);
            out.writeInt(state);
            out.writeString(currentText);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
