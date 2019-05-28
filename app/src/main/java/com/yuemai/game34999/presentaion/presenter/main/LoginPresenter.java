package com.yuemai.game34999.presentaion.presenter.main;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yannis.common.rx.RxUtils;
import com.yannis.common.util.LocationUtils;
import com.yuemai.game34999.GameApp;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.AccountLoginBean;
import com.yuemai.game34999.data.bean.LoginBean;
import com.yuemai.game34999.data.bean.LoginResult;
import com.yuemai.game34999.data.bean.UserInfo;
import com.yuemai.game34999.presentaion.contract.LoginContract;
import com.yuemai.game34999.presentaion.ui.main.activity.LoginActivity;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;

import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;
import static com.umeng.socialize.bean.SHARE_MEDIA.SINA;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;


/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/21  11:54
 * @email : 923080261@qq.com
 * @description :
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter, UMAuthListener {

    private DataManager mDataManager;


    @Inject
    public LoginPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void doLogin(final AccountLoginBean accountLoginBean) {
        Disposable disposable = Flowable.create((FlowableOnSubscribe<String>) e -> {
            String address = LocationUtils.getInstance().getDetailAddress(GameApp.getInstance());
            e.onNext(address);
            e.onComplete();
        }, BackpressureStrategy.BUFFER).subscribe(s -> {
            accountLoginBean.setAddress(s);
            addSubscribe(mDataManager.doLogin(accountLoginBean)
                    .compose(RxUtils.getDefaultSchedulers())
                    .compose(RxUtils.handleResult())
                    .subscribeWith(new CommonSubscriber<LoginResult>(mView) {
                        @Override
                        public void onNext(LoginResult loginResult) {
                            Logger.e(loginResult.toString());
                            if (!TextUtils.isEmpty(loginResult.getUsertoken())) {
                                //保存用户token
                                mDataManager.saveToken(loginResult.getUsertoken());
                                mView.loginSuccess();
                            } else {
                                mView.loginFailed();
                            }
                        }
                    })
            );
        });
        addSubscribe(disposable);
    }

    @Override
    public void loginByQQ() {
        UMShareAPI.get(GameApp.instance).getPlatformInfo((LoginActivity) mView, QQ, this);
    }

    @Override
    public void loginByWeChat() {
        UMShareAPI.get(GameApp.instance).getPlatformInfo((LoginActivity) mView, WEIXIN, this);
    }

    @Override
    public void loginByWeiBo() {
        UMShareAPI.get(GameApp.instance).getPlatformInfo((LoginActivity) mView, SINA, this);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        switch (share_media) {
            case QQ:
                break;
            case WEIXIN:
                break;
            case SINA:
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        loginByThirdParty(share_media, i, map);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        switch (share_media) {
            case QQ:
                break;
            case WEIXIN:
                break;
            case SINA:
                break;
            default:
                break;
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        switch (share_media) {
            case QQ:
                break;
            case WEIXIN:
                break;
            case SINA:
                break;
            default:
                break;
        }
    }

    /**
     * 第三方登录
     *
     * @param share_media 类型
     * @param i           i
     * @param map         数据
     */
    private void loginByThirdParty(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        LoginBean loginBean = new LoginBean();
        switch (share_media) {
            case QQ:
                break;
            case WEIXIN:
                break;
            case SINA:
                break;
            default:
                break;
        }

        addSubscribe(mDataManager.doLoginThirdParty(loginBean)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<UserInfo>(mView) {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        Logger.e("CommonSubscriber ---------------------- onNext");
                        Logger.e(userInfo.toString());
                    }
                }));
    }
}
