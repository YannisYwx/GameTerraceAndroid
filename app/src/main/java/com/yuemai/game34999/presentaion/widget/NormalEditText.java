package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuemai.game34999.R;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/1  17:40
 * @email : 923080261@qq.com
 * @description :
 */
public class NormalEditText extends RelativeLayout implements View.OnClickListener, TextWatcher {

    private DeletableEditText mEditText;
    private ImageView mEdit_psw;
    private boolean mIsShow;
    private TextChangedListener mListener;

    public NormalEditText(Context context) {
        super(context, null);
    }

    public NormalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_normal_edittext, this);

        //读取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NormalEditText);
        String text = typedArray.getString(R.styleable.NormalEditText_text);
        String hint = typedArray.getString(R.styleable.NormalEditText_hint);
        boolean isShowEye = typedArray.getBoolean(R.styleable.NormalEditText_isPswIcon, false);
        boolean deletable = typedArray.getBoolean(R.styleable.NormalEditText_hint, true);
        int inputType = typedArray.getInt(R.styleable.NormalEditText_inputType, 0);

        //查找控件
        TextView edit_tv = findViewById(R.id.tv_label);
        mEdit_psw = findViewById(R.id.iv_psw_icon);
        mEditText = findViewById(R.id.det);
        mEdit_psw.setOnClickListener(this);

        //赋值
        edit_tv.setText(text);
        mEditText.setHint(hint);
        mEditText.setEditInputType(inputType);
        mEdit_psw.setVisibility(isShowEye ? VISIBLE : GONE);
        mEditText.addTextChangedListener(this);
        typedArray.recycle();
    }

    public String getTextContent() {
        return mEditText.getText().toString().trim();
    }

    public void setTextContent(String textContent) {
        mEditText.setText(textContent);
    }

    public void setEditSelection(int selection) {
        mEditText.setSelection(selection);
    }

    @Override
    public void onClick(View v) {
        processPswHideOrShow();
    }

    /**
     * 处理显示或隐藏
     */
    private void processPswHideOrShow() {
        mEdit_psw.setSelected(!mEdit_psw.isSelected());
        mIsShow = !mIsShow;
        String etString = mEditText.getText().toString().trim();
        if (!mIsShow) {
            mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {//显示
            mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        mEditText.setSelection(etString.length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mListener != null) {
            mListener.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setTextChangedListener(TextChangedListener listener) {
        mListener = listener;
    }

    public interface TextChangedListener {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }
}
