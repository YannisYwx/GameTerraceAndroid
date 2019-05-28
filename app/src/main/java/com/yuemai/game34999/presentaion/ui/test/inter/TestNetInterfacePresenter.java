package com.yuemai.game34999.presentaion.ui.test.inter;

import com.yannis.common.rx.RxUtils;
import com.yannis.common.util.LocationUtils;
import com.yuemai.game34999.GameApp;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.AccountLoginBean;
import com.yuemai.game34999.data.bean.AppConfiguration;
import com.yuemai.game34999.data.bean.BannerInfo;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.data.bean.GameType;
import com.yuemai.game34999.data.bean.LoginResult;
import com.yuemai.game34999.data.bean.ReadStatus;
import com.yuemai.game34999.data.bean.RegisterBean;
import com.yuemai.game34999.data.bean.RegisterResult;
import com.yuemai.game34999.data.bean.UserMessage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  17:42
 * @email : 923080261@qq.com
 * @description :
 */
public class TestNetInterfacePresenter extends RxPresenter<TestNetInterfaceContract.View> implements TestNetInterfaceContract.Presenter {
    public static final String[] INTER = {
            "登录",
            "注册",
            "获取广告数据",
            "获取行业配置",
            "获取婚姻配置",
            "获取资产配置",
            "获取学历配置",
            "获取用户信息",
            "设置消息已读",
            "获取游戏类型",
            "根据类型获取标签",
            "获取游戏详情",

    };

    DataManager mDataManager;
    StringBuilder sb = new StringBuilder();

    @Inject
    public TestNetInterfacePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


    @Override
    public void request(long position) {
        switch ((int) position) {
            case 0:
                doLogin();
                break;
            case 1:
                doRegister();
                break;
            case 2:
                getBannerInfo();
                break;
            case 3:
                getAppConfiguration(AppConfiguration.Configuration.USER_INDUSTRY);
                break;
            case 4:
                getAppConfiguration(AppConfiguration.Configuration.MARITAL_STATUS);
                break;
            case 5:
                getAppConfiguration(AppConfiguration.Configuration.CAPITALPOSITION);
                break;
            case 6:
                getAppConfiguration(AppConfiguration.Configuration.EDUCATION);
                break;
            case 7:
                getUserMessage();
                break;
            case 8:
                setUserMessageIsRead();
                break;
            case 9:
                getAllGameType();
                break;
            case 10:
                getGameTypeById();
                break;
            case 11:
                getGameList();
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     */
    private void doLogin() {
        AccountLoginBean loginBean = new AccountLoginBean();
        //{"account":"13600169041","address":"","pwd":"123456","terminal":"1"}
        loginBean.setAccount("13600169041");
        loginBean.setAddress("");
        loginBean.setPwd("123456");
        loginBean.setTerminal(String.valueOf(Constants.PLATFORM_CODE));
        Disposable disposable = Flowable.create((FlowableOnSubscribe<String>) e -> {
            String address = LocationUtils.getInstance().getDetailAddress(GameApp.getInstance());
            e.onNext(address);
            e.onComplete();
        }, BackpressureStrategy.BUFFER).subscribe(s -> {
            loginBean.setAddress(s);
            addSubscribe(mDataManager.doLogin(loginBean)
                    .compose(RxUtils.getDefaultSchedulers())
                    .compose(RxUtils.handleResult())
                    .subscribeWith(new CommonSubscriber<LoginResult>(mView) {
                        @Override
                        public void onNext(LoginResult loginResult) {
                            mView.showResult(loginResult.toString());
                        }
                    })
            );
        });
        addSubscribe(disposable);
    }

    private void doRegister() {
        RegisterBean registerBean = new RegisterBean();
        registerBean.setAuthcode("fds");
        registerBean.setInvitationCode("fds");
        registerBean.setIp("fds");
        registerBean.setNapwdcom(1);
        registerBean.setSalt("fdsa");
        registerBean.setUaccount("fdfds");
        registerBean.setPwd("fdsafds");
        registerBean.setAddress("qwer");
        addSubscribe(mDataManager.doRegister(registerBean)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<RegisterResult>(mView) {
                    @Override
                    public void onNext(RegisterResult result) {
                        mView.showResult(result.toString());
                    }
                }));
    }


    /**
     * 获取广告头部数据
     */
    private void getBannerInfo() {
        addSubscribe(mDataManager.getBannerInfo()
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<BannerInfo>>(mView) {
                    @Override
                    public void onNext(List<BannerInfo> bannerInfos) {
                        showInfo(bannerInfos);
                    }
                }));
    }

    /**
     * 获取配置信息
     *
     * @param configuration
     */
    private void getAppConfiguration(String configuration) {
        addSubscribe(mDataManager.getAppConfiguration(configuration)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<AppConfiguration>>(mView) {
                    @Override
                    public void onNext(List<AppConfiguration> appConfigurations) {
                        showInfo(configuration, appConfigurations);
                    }
                }));
    }

    /**
     * 获取用户信息
     */
    private void getUserMessage() {
        addSubscribe(mDataManager.getUserMessage("1", 1)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<UserMessage>>(mView) {
                    @Override
                    public void onNext(List<UserMessage> userMessages) {
                        showInfo(userMessages);
                    }
                }));
    }


    /**
     * 设置消息已读
     */
    private void setUserMessageIsRead() {
        addSubscribe(mDataManager.setUserMessageIsRead("1")
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<ReadStatus>(mView) {
                    @Override
                    public void onNext(ReadStatus readStatus) {
                        mView.showResult(readStatus.toString());
                    }
                }));
    }


    private void getAllGameType() {
        addSubscribe(mDataManager.getAllGameType()
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult()).subscribeWith(new CommonSubscriber<List<GameType>>(mView) {
                    @Override
                    public void onNext(List<GameType> gameTypes) {
                        showInfo(gameTypes);
                    }
                }));
    }


    private void getGameTypeById() {
        addSubscribe(mDataManager.getGameTypeByParentId(8)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult()).subscribeWith(new CommonSubscriber<List<GameType>>(mView) {
                    @Override
                    public void onNext(List<GameType> gameTypes) {
                        showInfo(gameTypes);
                    }
                }));
    }


    private void getGameList() {
        addSubscribe(mDataManager.getGameList("飞行", 1)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult()).subscribeWith(new CommonSubscriber<List<GameInfo>>(mView) {
                    @Override
                    public void onNext(List<GameInfo> gameInfos) {
                        showInfo(gameInfos);
                    }
                }));
    }


    private <T> void showInfo(List<T> objects) {
        sb.delete(0, sb.length());
        assert objects != null;
        for (T obj : objects) {
            sb.append(obj.toString()).append("\n");
        }
        mView.showResult(sb.toString());
    }

    private <T> void showInfo(String title, List<T> objects) {
        sb.delete(0, sb.length());
        assert objects != null;
        sb.append(title);
        for (T obj : objects) {
            sb.append(obj.toString()).append("\n");
        }
        mView.showResult(sb.toString());
    }

    private void getDownloadGameList() {
        addSubscribe(mDataManager.getGameList("3D", 1)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult()).subscribeWith(new CommonSubscriber<List<GameInfo>>(mView) {
                    @Override
                    public void onNext(List<GameInfo> gameInfos) {
                        showInfo(gameInfos);
                    }
                }));
    }
}
