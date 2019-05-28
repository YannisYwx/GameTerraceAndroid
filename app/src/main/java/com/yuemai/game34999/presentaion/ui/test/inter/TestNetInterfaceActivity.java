package com.yuemai.game34999.presentaion.ui.test.inter;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  17:50
 * @email : 923080261@qq.com
 * @description :
 */
public class TestNetInterfaceActivity extends AbstractMvpLoadPagerActivity<TestNetInterfacePresenter> implements TestNetInterfaceContract.View{
    @BindView(R.id.sp)
    Spinner sp;
    @BindView(R.id.btn_test)
    Button btn;
    @BindView(R.id.tv_info)
    TextView mTextView;

    private ArrayAdapter adapter;


    public static void start(android.content.Context context) {
        context.startActivity(new Intent(context, TestNetInterfaceActivity.class));
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {
        showResult(msg);
    }

    @Override
    public void injectThis(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_test_net_interface;
    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    public void initEvent() {
        super.initEvent();

    }

    @Override
    protected void refreshContentView(View contentView) {
        adapter = new ArrayAdapter<>(TestNetInterfaceActivity.this, android.R.layout.simple_list_item_1, TestNetInterfacePresenter.INTER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @OnClick(R.id.btn_test)
    void onBtnClick() {
        int id = (int) sp.getSelectedItemId();
        showToast("id = " + id);
        presenter.request(id);
    }

    @Override
    public void showResult(String result) {
        mTextView.setText("");
        mTextView.setText(result);
    }
}
