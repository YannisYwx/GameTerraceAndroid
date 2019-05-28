package com.yuemai.game34999.presentaion.ui.main.activity;

import android.text.TextUtils;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.yannis.common.util.LocationUtils;
import com.yannis.common.util.RegexUtils;
import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.data.bean.AccountLoginBean;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.contract.LoginContract;
import com.yuemai.game34999.presentaion.presenter.main.LoginPresenter;
import com.yuemai.game34999.presentaion.widget.NormalEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/1  17:40
 * @email : 923080261@qq.com
 * @description : 登录页面 账号登录 第三方登录
 */
public class LoginActivity extends AbstractMvpLoadPagerActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.net_account)
    NormalEditText net_account;
    @BindView(R.id.net_pwd)
    NormalEditText net_pwd;

    @Override
    public int setContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    protected void refreshContentView(View contentView) {
        LocationUtils.getInstance().getLocation(this);
        net_pwd.setTextContent("123456");
        net_account.setTextContent("13600169041");
    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        doSomeWork();
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public boolean isShowToolbar() {
        return false;
    }

    @OnClick({R.id.tv_register, R.id.tv_forget_pwd, R.id.btn_login, R.id.iv_qq, R.id.iv_wechat, R.id.iv_weibo})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                forward(RegisterActivity.class);
                break;
            case R.id.tv_forget_pwd:
                forward(ForgetPwdActivity.class);
                break;
            case R.id.btn_login:
                String checkMsg = checkLoginInfo();
                if (TextUtils.isEmpty(checkMsg)) {
                    AccountLoginBean loginBean = new AccountLoginBean();
                    //{"account":"13600169041","address":"","pwd":"123456","terminal":"1"}
                    loginBean.setAccount(net_account.getTextContent());
                    loginBean.setAddress("");
                    loginBean.setPwd(net_pwd.getTextContent());
                    loginBean.setTerminal(String.valueOf(Constants.PLATFORM_CODE));
                    presenter.doLogin(loginBean);
                } else {
                    showToast(checkMsg);
                }
                break;
            case R.id.iv_qq:
                presenter.loginByQQ();
                break;
            case R.id.iv_wechat:
                presenter.loginByWeChat();
                break;
            case R.id.iv_weibo:
                presenter.loginByWeiBo();
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
        component.inject(this);
    }

    @Override
    public void loginSuccess() {
        showMsg("登录成功");
    }

    @Override
    public void loginFailed() {
        showMsg("登录失败");
    }

    private String checkLoginInfo() {

        String account = net_account.getTextContent();
        String pwd = net_pwd.getTextContent();

        if (TextUtils.isEmpty(account)) {
            return "请输入账号信息";
        } else if (TextUtils.isEmpty(pwd)) {
            return "请输入密码";
        } else if (RegexUtils.checkChinese(account)) {
            return "请不要输入汉字";
        } else if (account.length() > 18) {
            return "请输入长度范围在18以内的非汉字账号";
        } else if (!(pwd.length() >= 6 && pwd.length() <= 18)) {
            return "请输入6-18位长度密码";
        }
        return null;
    }

    private void doSomeWork() {
        Logger.e("doSomeWork--------" + Thread.currentThread().getName());
        getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .map(integers -> {
                    Logger.e("map--------" + Thread.currentThread().getName());
                    List<String> strings = new ArrayList<>();
                    for (int i : integers) {
                        strings.add(i + ":s");
                    }

                    return strings;
                })
                .subscribe(getObserver());
    }

    private Observable<List<Integer>> getObservable() {
        return Observable.create(e -> {
            if (!e.isDisposed()) {
                List<Integer> integers = new ArrayList<>();
                Logger.e("create--------" + Thread.currentThread().getName());
                integers.add(1);
                integers.add(1);
                integers.add(1);
                e.onNext(integers);
                e.onComplete();
            }
        });
    }

    private Observer<List<String>> getObserver() {
        return new Observer<List<String>>() {

            @Override
            public void onSubscribe(Disposable d) {
                Logger.e("onSubscribe--------" + Thread.currentThread().getName());
                Logger.d(" onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(List<String> strings) {
                Logger.e("onNext--------" + Thread.currentThread().getName());

                for (String s : strings) {
                    Logger.e(" onNext : " + s);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                Logger.e("onComplete--------" + Thread.currentThread().getName());
            }
        };
    }

}
