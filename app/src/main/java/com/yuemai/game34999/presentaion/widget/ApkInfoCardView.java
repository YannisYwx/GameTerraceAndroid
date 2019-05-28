package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuemai.game34999.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.yuemai.game34999.presentaion.widget.ApkInfoCardView.ApkCardEvent.ClickDownload;
import static com.yuemai.game34999.presentaion.widget.ApkInfoCardView.ApkCardEvent.ClickFinish;
import static com.yuemai.game34999.presentaion.widget.ApkInfoCardView.ApkCardEvent.ClickPause;
import static com.yuemai.game34999.presentaion.widget.ApkInfoCardView.ApkCardEvent.ClickResume;
import static com.yuemai.game34999.presentaion.widget.ApkInfoCardView.ApkCardEvent.IconClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/18  15:06
 * @email : 923080261@qq.com
 * @description :
 */
public class ApkInfoCardView extends FrameLayout {
    TextView tvApkName;
    TextView tvApkCapacity;
    ImageView ivApkIcon;
    DownloadProgressButton mButton;

    private Drawable apkIcon;
    private String apkName;
    private String apkCapacity;
    OnApkCardClickListener mListener;

    public ApkInfoCardView(@NonNull Context context) {
        this(context, null);
    }

    public ApkInfoCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ApkInfoCardView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ApkInfoCardView);
            apkIcon = ta.getDrawable(R.styleable.ApkInfoCardView_apkIcon);
            apkName = ta.getString(R.styleable.ApkInfoCardView_apkName);
            apkCapacity = ta.getString(R.styleable.ApkInfoCardView_apkCapacity);
            ta.recycle();
        }
        LayoutInflater.from(context).inflate(R.layout.view_apk_info, this, true);
    }

    public void setApkInfo(@NonNull Bitmap bitmap, @NonNull String apkName, @NonNull String apkCapacity) {
        tvApkName.setText(apkName);
        tvApkCapacity.setText(apkCapacity);
        ivApkIcon.setImageBitmap(bitmap);
        invalidate();
    }

    public void setApkInfo(@DrawableRes int res, @NonNull String apkName, @NonNull String apkCapacity) {
        tvApkName.setText(apkName);
        tvApkCapacity.setText(apkCapacity);
        ivApkIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), res));
        invalidate();
    }

    public void setApkInfo(@NonNull String apkName, @NonNull String apkCapacity) {
        tvApkName.setText(apkName);
        tvApkCapacity.setText(apkCapacity);
        invalidate();
    }

    public void loadApkIcon(String url, @NonNull String apkName, @NonNull String apkCapacity) {
        tvApkName.setText(apkName);
        tvApkCapacity.setText(apkCapacity);
        invalidate();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvApkName = findViewById(R.id.tv_apkName);
        tvApkCapacity = findViewById(R.id.tv_apkCapacity);
        ivApkIcon = findViewById(R.id.iv_apkIcon);
        mButton = findViewById(R.id.btn_download);
        tvApkName.setText(TextUtils.isEmpty(apkName) ? "" : apkName);
        tvApkCapacity.setText(TextUtils.isEmpty(apkCapacity) ? "" : apkCapacity);
        ivApkIcon.setImageDrawable(apkIcon);


        ivApkIcon.setOnClickListener(view -> {
            if (mListener == null) {
                return;
            }
            mListener.onIconClick(this);
        });

        mButton.setOnDownLoadClickListener(new DownloadProgressButton.OnDownLoadClickListener() {
            @Override
            public void clickDownload() {
                if (mListener == null) {
                    return;
                }
                mListener.onDownLoadButtonClick(ApkCardEvent.ClickDownload, ApkInfoCardView.this, mButton);
            }

            @Override
            public void clickPause() {
                if (mListener == null) {
                    return;
                }
                mListener.onDownLoadButtonClick(ApkCardEvent.ClickPause, ApkInfoCardView.this, mButton);
            }

            @Override
            public void clickResume() {
                if (mListener == null) {
                    return;
                }
                mListener.onDownLoadButtonClick(ApkCardEvent.ClickResume, ApkInfoCardView.this, mButton);
            }

            @Override
            public void clickFinish() {
                if (mListener == null) {
                    return;
                }
                mListener.onDownLoadButtonClick(ApkCardEvent.ClickFinish, ApkInfoCardView.this, mButton);
            }

            @Override
            public void clickDisenable() {

            }
        });
    }

    public TextView getTvApkName() {
        return tvApkName;
    }

    public TextView getTvApkCapacity() {
        return tvApkCapacity;
    }

    public ImageView getIvApkIcon() {
        return ivApkIcon;
    }

    public interface OnApkCardClickListener {

        void onIconClick(View view);

        void onDownLoadButtonClick(@ApkCardEvent int event, View view, DownloadProgressButton button);
    }

    public void setApkCardListener(OnApkCardClickListener listener) {
        this.mListener = listener;
    }

    @IntDef({IconClick, ClickDownload, ClickPause, ClickResume, ClickFinish})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApkCardEvent {
        int IconClick = 0;     //图标点击
        int ClickDownload = 1; //点击下载
        int ClickPause = 2;    //点击暂停
        int ClickResume = 3;   //点击继续
        int ClickFinish = 4;   //点击完成
    }
}