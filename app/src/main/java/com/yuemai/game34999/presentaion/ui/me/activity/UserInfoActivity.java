package com.yuemai.game34999.presentaion.ui.me.activity;

import android.graphics.Color;
import android.view.View;

import com.yannis.common.widget.ItemView;
import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.wheelview.popupwindow.CommonPickerPopWin;
import com.yannis.common.widget.wheelview.popupwindow.DatePickerPopWin;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/27  15:45
 * @email : 923080261@qq.com
 * @description :
 */
public class UserInfoActivity extends AbstractMvpLoadPagerActivity {
    @BindView(R.id.iv_nickName)
    ItemView iv_nickName;
    @BindView(R.id.iv_sex)
    ItemView iv_sex;
    @BindView(R.id.iv_birthday)
    ItemView iv_birthday;
    @BindView(R.id.iv_phoneNum)
    ItemView iv_phoneNum;
    @BindView(R.id.iv_address)
    ItemView iv_address;
    CommonPickerPopWin sexPopWin;
    DatePickerPopWin birthdayPopWin;

    @Override
    protected String initTitle() {
        return getResources().getString(R.string.title_userInfo);
    }

    @Override
    protected void refreshContentView(View contentView) {

    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initData() {
        super.initData();
        List<String> sex = new ArrayList<>();
        sex.add(getResources().getStringArray(R.array.sex)[0]);
        sex.add(getResources().getStringArray(R.array.sex)[1]);
        Arrays.asList(getResources().getStringArray(R.array.sex));
        sexPopWin = new CommonPickerPopWin.Builder(this,
                (pos, value) -> iv_sex.setValue(value))
                .textConfirm(getString(R.string.sure))
                .textCancel(getString(R.string.cancel))
                .btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .loopData(sex)
                .build();
        birthdayPopWin = new DatePickerPopWin.Builder(this,
                (year, month, day, dateDesc) -> iv_birthday.setValue(dateDesc))
                .textConfirm(getString(R.string.sure))
                .textCancel(getString(R.string.cancel))
                .btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .minYear(1950)
                .maxYear(2050)
                .dateChose(com.yannis.common.util.DateUtils.getCurrentDate())
                .build();
    }

    @OnClick({R.id.iv_nickName, R.id.iv_sex, R.id.iv_birthday, R.id.iv_phoneNum, R.id.iv_address})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_nickName:
                break;
            case R.id.iv_sex:
                sexPopWin.showPopWin(this);
                break;
            case R.id.iv_birthday:
                birthdayPopWin.showPopWin(this);
                break;
            case R.id.iv_phoneNum:
                break;
            case R.id.iv_address:
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(ActivityComponent component) {

    }
}
