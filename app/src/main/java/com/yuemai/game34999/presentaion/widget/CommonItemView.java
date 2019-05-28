package com.yuemai.game34999.presentaion.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yannis.common.util.ViewUtils;
import com.yuemai.game34999.R;
import com.yuemai.game34999.util.AppFormatUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuemai.game34999.presentaion.widget.CommonItemView.DownloadEvent.clickDownload;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.DownloadEvent.clickFinish;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.DownloadEvent.clickPause;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.DownloadEvent.clickResume;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.DownloadEvent.iconClick;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.Type.copy;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.Type.downloadButton;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.Type.downloadFinished;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.Type.downloading;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.Type.normal;
import static com.yuemai.game34999.presentaion.widget.CommonItemView.Type.remind;


/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/18  15:06
 * @email : 923080261@qq.com
 * @description : 通用的列表item
 */
public class CommonItemView extends RelativeLayout implements DownloadProgressButton.OnDownLoadClickListener {
    @BindView(R.id.tv_num)
    TextView tvIndex;
    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.tv_middle)
    TextView tvMiddle;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_downloadSpeed)
    TextView tvDownloadSpeed;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.btn_normal)
    Button btnNormal;
    @BindView(R.id.btn_download)
    DownloadProgressButton btnDownload;
    @BindView(R.id.pb_download)
    ProgressBar mProgressBar;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;

    private Class<? extends Activity> clazz = null;

    private OnDownloadBtnClickListener mDownloadBtnClickListener;
    private OnCopyInvitationCodeListener mCopyInvitationCodeListener;
    private OnNormalClickListener mNormalClickListener;
    private boolean hasBottomLine = true;
    private boolean hasIndex = false;
    private boolean isShowProgressBar = true;
    private boolean isChecked = false;
    private boolean isShowCheckBox = false;
    private
    @Type
    int type = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int dividerColor = Color.parseColor("#E3E3E3");
    private float dividerHeight = getResources().getDimension(R.dimen.dividerHeight);
    private Paint mPaint;

    public CommonItemView(@NonNull Context context) {
        this(context, Type.normal);
    }

    public CommonItemView(@NonNull Context context, @Type int type) {
        this(context, null, type);
    }

    public CommonItemView(@NonNull Context context, @Nullable AttributeSet attrs, @Type int type) {
        this(context, attrs, 0, type);
    }

    public CommonItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @Type int type) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_common_item, this);
        ButterKnife.bind(this);
        init(context, attrs, type);
    }

    public void setForwardActivity(@NonNull Class<? extends Activity> cls) {
        this.clazz = cls;
    }

    public Class<? extends Activity> getForwardActivity() {
        return this.clazz;
    }

    @SuppressLint("WrongConstant")
    private void init(Context context, AttributeSet attrs, @Type int type) {
        this.type = type;
        dividerHeight = ViewUtils.dip2px(context, 0.88f);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonItemView);
            this.hasBottomLine = ta.getBoolean(R.styleable.CommonItemView_civ_hasBottomLine, true);
            this.hasIndex = ta.getBoolean(R.styleable.CommonItemView_civ_hasIndex, false);
            this.type = getItemType(ta.getInt(R.styleable.CommonItemView_civ_type, 0));
            this.dividerColor = ta.getColor(R.styleable.CommonItemView_civ_dividerColor, Color.parseColor("#E3E3E3"));
            this.dividerHeight = ta.getDimension(R.styleable.CommonItemView_civ_dividerHeight, getResources().getDimension(R.dimen.dividerHeight));
            this.paddingLeft = (int) ta.getDimension(R.styleable.CommonItemView_civ_dividerPaddingLeft, 0);
            this.paddingRight = (int) ta.getDimension(R.styleable.CommonItemView_civ_dividerPaddingRight, 0);
            ta.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStrokeWidth(dividerHeight);
        setupViewVisibility();
    }


    private void setupViewVisibility() {
        tvIndex.setVisibility(hasIndex ? VISIBLE : GONE);
        tvRight.setVisibility(type == Type.downloading || type == Type.downloadFinished ? VISIBLE : INVISIBLE);
        mProgressBar.setVisibility(type == Type.downloading && isShowProgressBar ? VISIBLE : INVISIBLE);
        tvBottom.setVisibility(type == Type.downloading && isShowProgressBar ? INVISIBLE : VISIBLE);
        btnNormal.setVisibility(type != Type.downloadButton ? VISIBLE : INVISIBLE);
        btnDownload.setVisibility(type == Type.downloadButton ? VISIBLE : INVISIBLE);
        mCheckBox.setVisibility(isShowCheckBox ? VISIBLE : GONE);
        setBtnStyle(type);
    }

    public void setRightButtonText(@StringRes int strRes) {
        if (btnNormal != null) {
            btnNormal.setText(strRes);
        }
    }

    public void setRightButtonText(@NonNull String text) {
        if (btnNormal != null) {
            btnNormal.setText(text);
        }
    }

    private void setBtnStyle(int type) {
        switch (type) {
            case remind:
                ColorStateList colorStateList = getResources().getColorStateList(R.color.btn_text_color_orange);
                btnNormal.setTextColor(colorStateList);
                btnNormal.setBackgroundResource(R.drawable.btn_orange);
                btnNormal.setText(R.string.remind);
                break;
            case Type.normal:
            case Type.copy:
                btnNormal.setText(R.string.gift_copy);
            case Type.downloadFinished:
            case Type.downloading:
            default:
                btnNormal.setTextColor(Color.WHITE);
                btnNormal.setBackgroundResource(R.drawable.normal_button_selector);
                break;
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        setupViewVisibility();
        if (hasBottomLine) {
            canvas.drawLine(paddingLeft, getMeasuredHeight(), getMeasuredWidth() - paddingRight, getMeasuredHeight(), mPaint);
        }
    }

    @OnClick({R.id.btn_normal, R.id.tv_right})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_normal:
                break;
            case R.id.tv_right:
                break;
            default:
                break;
        }
    }

    public void setProgressBarVisibility(boolean isShowProgressBar) {
        this.isShowProgressBar = isShowProgressBar;
        invalidate();
    }

    public void setItemType(@Type int type) {
        this.type = type;
        invalidate();
    }

    @SuppressLint("WrongConstant")
    public
    @Type
    int getItemType() {
        return type;
    }

    public void setDividerHeight(float height) {
        this.dividerHeight = height;
        invalidate();
    }

    public void setDividerPaddingDp(int paddingLeft, int paddingRight) {
        this.paddingLeft = ViewUtils.dip2px(getContext(), paddingLeft);
        this.paddingRight = ViewUtils.dip2px(getContext(), paddingRight);
        invalidate();
    }

    public void setHasBottomLine(boolean hasBottomLine) {
        this.hasBottomLine = hasBottomLine;
        invalidate();
    }

    public void setHasIndex(boolean hasIndex) {
        this.hasIndex = hasIndex;
        this.isShowCheckBox = false;
        invalidate();
    }

    public void setBackground(@DrawableRes int res) {
        setBackgroundResource(res);
        invalidate();
    }

    @Override
    public void clickDownload() {

    }

    @Override
    public void clickPause() {

    }

    @Override
    public void clickResume() {

    }

    @Override
    public void clickFinish() {

    }

    @Override
    public void clickDisenable() {

    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public TextView getTvIndex() {
        return tvIndex;
    }

    public TextView getTvTop() {
        return tvTop;
    }

    public TextView getTvMiddle() {
        return tvMiddle;
    }

    public TextView getTvBottom() {
        return tvBottom;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }

    public Button getNormalBtn() {
        return btnNormal;
    }

    public TextView getTvDownloadSpeed() {
        tvDownloadSpeed.setVisibility(VISIBLE);
        return tvDownloadSpeed;
    }

    public DownloadProgressButton getBtnDownload() {
        return btnDownload;
    }

    @SuppressLint("WrongConstant")
    public @Type
    int getType() {
        return type;
    }

    public interface OnDownloadBtnClickListener {

        void onIconClick(View view);

        void onDownLoadButtonClick(@DownloadEvent int event, View view, DownloadProgressButton button);
    }

    public void setDownLoadListener(OnDownloadBtnClickListener listener) {
        this.mDownloadBtnClickListener = listener;
    }

    public interface OnCopyInvitationCodeListener {
        void onCopyInvitationCode(String invitationCode);
    }

    public void setCopyInvitationCodeListener(OnCopyInvitationCodeListener listener) {
        this.mCopyInvitationCodeListener = listener;
    }

    public interface OnNormalClickListener {
        void OnNormalClick(Object obj);
    }

    @IntDef({iconClick, clickDownload, clickPause, clickResume, clickFinish})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadEvent {
        /**
         * 图标点击
         */
        int iconClick = 0;
        /**
         * 点击下载
         */
        int clickDownload = 1;
        /**
         * 点击暂停
         */
        int clickPause = 2;
        /**
         * 点击继续
         */
        int clickResume = 3;
        /**
         * 点击完成
         */
        int clickFinish = 4;
    }

    @IntDef({normal, copy, remind, downloadButton, downloading, downloadFinished})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        /**
         * 正常的点击
         */
        int normal = 0;

        /**
         * 点击复制
         */
        int copy = 1;

        /**
         * 提醒
         */
        int remind = 2;

        /**
         * 下载 按钮
         */
        int downloadButton = 3;

        /**
         * progressBar下载显示
         */
        int downloading = 4;

        /**
         * 下载完成
         */
        int downloadFinished = 5;
    }

    public
    @Type
    int getItemType(int type) {
        switch (type) {
            case 0:
                return Type.normal;
            case 1:
                return Type.copy;
            case 2:
                return Type.remind;
            case 3:
                return Type.downloadButton;
            case 4:
                return Type.downloading;
            case 5:
                return Type.downloadFinished;
            default:
                return Type.normal;
        }
    }

    public void setCheckBoxVisibility(boolean visibility) {
        isShowCheckBox = visibility;
        mCheckBox.setVisibility(isShowCheckBox ? VISIBLE : GONE);
        invalidate();
    }

    public CheckBox getCheckBox() {
        return mCheckBox;
    }

    public boolean isChecked() {
        return mCheckBox.isChecked();
    }

    /**
     * 设置提示 默认红色
     *
     * @param msg msg
     */
    public void setNoticeMsg(String msg) {
        setNoticeMsg(msg, true);
    }

    /**
     * 设置提示 默认红色
     *
     * @param msg       msg
     * @param isWarning true 警告 红色  false 绿色
     */
    public void setNoticeMsg(String msg, boolean isWarning) {
        tvDownloadSpeed.setEnabled(!isWarning);
        tvDownloadSpeed.setVisibility(VISIBLE);
        tvDownloadSpeed.setText(msg);
    }

    /**
     * 设置顶部文字
     *
     * @param text 文字内容
     */
    public void setTopText(String text) {
        tvTop.setText(text);
    }

    /**
     * 设置中间文字
     *
     * @param text 文字内容
     */
    public void setMiddleText(String text) {
        tvMiddle.setText(text);
    }

    /**
     * 设置底部文字
     *
     * @param text 文字内容
     */
    public void setBottomText(String text) {
        tvBottom.setVisibility(VISIBLE);
        tvBottom.setText(text);
    }


    /**
     * 设置下载按钮状态
     *
     * @param status 状态
     */
    public void setDownloadButtonStatus(DownloadProgressButton.ButtonStatus status) {
        switch (status) {
            case NORMAL:
                btnDownload.reset();
                break;
            case WAITING:
                btnDownload.waiting();
                break;
            case DOWNLOADING:
                btnDownload.downloading();
                break;
            case PAUSE:
                btnDownload.pause();
                break;
            case FINISH:
                btnDownload.finish();
                break;
            case DISENABLE:
                btnDownload.disenable();
                break;
            default:
                break;
        }
    }

    /**
     * 设置下载进度
     *
     * @param progress 当前进度
     */
    public void setProgress(int progress) {
        mProgressBar.setVisibility(VISIBLE);
        mProgressBar.setProgress(progress);
    }

    /**
     * 设置下载进度
     *
     * @param progress 当前进度
     */
    public void setBtnProgress(int progress) {
        btnDownload.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
        btnDownload.setProgress(progress);
    }


    /**
     * 设置下载速度
     *
     * @param speed 当前速度
     */
    public void setSpeed(long speed) {
        tvDownloadSpeed.setEnabled(true);
        tvDownloadSpeed.setVisibility(VISIBLE);
        tvDownloadSpeed.setText(AppFormatUtils.formatApkDownloadSpeed(speed));
    }

    /**
     * 设置apk下载占比
     *
     * @param currentSize 当前下载的大小
     * @param totalSize   当前的总大小
     */
    public void setApkDownloadPercent(long currentSize, long totalSize) {
        tvMiddle.setText(AppFormatUtils.formatApkDownloadPercent(currentSize, totalSize));
    }

    /**
     * 设置下载完成提示
     */
    public void setFinishedNotice() {
        tvDownloadSpeed.setEnabled(true);
        tvDownloadSpeed.setVisibility(VISIBLE);
        tvDownloadSpeed.setText(Notice.FINISHED.msg);
    }

    /**
     * 设置暂停提示
     */
    public void setPauseNotice() {
        tvDownloadSpeed.setEnabled(true);
        tvDownloadSpeed.setVisibility(VISIBLE);
        tvDownloadSpeed.setText(Notice.PAUSED.msg);
    }

    /**
     * 设置出错提示
     */
    public void seErrorNotice() {
        tvDownloadSpeed.setEnabled(false);
        tvDownloadSpeed.setVisibility(VISIBLE);
        tvDownloadSpeed.setText(Notice.DOWNLOAD_ERROR.msg);
    }

    /**
     * 设置出错提示
     *
     * @param msg msg
     */
    public void seErrorNotice(@NonNull String msg) {
        tvDownloadSpeed.setEnabled(false);
        tvDownloadSpeed.setVisibility(VISIBLE);
        tvDownloadSpeed.setText(msg);
    }

    public enum Notice {
        DOWNLOAD("下载"),
        FINISHED("已完成"),
        WAITING("等待中"),
        PAUSED("已暂停"),
        PAUSE("暂停"),
        CONTINUE("继续"),
        DOWNLOAD_ERROR("下载出错"),
        ERROR("出错"),
        DOWNLOAD_FINISHED("下载完成"),
        INSTALL("安装"),
        NO_RESOURCE("暂无资源");

        private String msg;

        Notice(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 设置正常按钮文字
     *
     * @param msg text
     */
    public void setNormalButtonText(String msg) {
        btnNormal.setVisibility(VISIBLE);
        btnNormal.setText(msg);
    }

    /**
     * 设置正常按钮文字
     *
     * @param notice notice
     */
    public void setNormalButtonText(Notice notice) {
        btnNormal.setVisibility(VISIBLE);
        btnNormal.setText(notice.msg);
    }

    public static CommonItemView createNormalItemView(@NonNull Context context, @Type int type) {
        return new CommonItemView(context, type);
    }

    public static CommonItemView createNormalItemView(@NonNull Context context, @Type int type, String btnText) {
        CommonItemView commonItemView = new CommonItemView(context, type);
        commonItemView.getNormalBtn().setText(btnText);
        return commonItemView;
    }

    public static CommonItemView createDownloadButtonItemView(@NonNull Context context) {
        return new CommonItemView(context, Type.downloadButton);
    }
}