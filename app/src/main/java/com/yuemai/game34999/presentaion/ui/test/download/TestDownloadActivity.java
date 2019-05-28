package com.yuemai.game34999.presentaion.ui.test.download;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.db.DownloadDao;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  17:50
 * @email : 923080261@qq.com
 * @description :
 */
public class TestDownloadActivity extends AbstractMvpLoadPagerActivity<TestDownloadPresenter> implements TestDownloadContract.View {

    @BindView(R.id.rv_downloadList)
    RecyclerView mRecyclerView;
    private ApkDownloadAdapter adapter;
    private LayoutAnimationController controller;
    private List<DownloadTask> values;

    public static final int TYPE_ALL = 0;
    public static final int TYPE_FINISH = 1;
    public static final int TYPE_ING = 2;

    private int type;

    private List<GameInfo> apks = new ArrayList<>();

    public static void start(android.content.Context context) {
        context.startActivity(new Intent(context, TestDownloadActivity.class));
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        mTitleBar.setLeftText("删除所有");
        mTitleBar.setRightText("下载管理");

    }

    @Override
    public void showMsg(String msg) {
    }

    @Override
    public void injectThis(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_test_download;
    }

    @Override
    protected String initTitle() {
        return "下载Apk";
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void onRightTitleBarClick(@NonNull View view) {
        TestDownloadManagerActivity.start(this);
    }

    @Override
    public void onBackClick() {
        DownloadManager.getInstance().removeAll(true);
        adapter = new ApkDownloadAdapter(apks);
        controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getAllTask() {
        updateData(TYPE_ALL);
    }

    public void updateData(int type) {
        //这里是将数据库的数据恢复
        this.type = type;
        if (type == TYPE_ALL) {
            values = DownloadManager.restore(DownloadDao.getInstance().getAll());
        }
        if (type == TYPE_FINISH) {
            values = DownloadManager.restore(DownloadDao.getInstance().getFinished());
        }
        if (type == TYPE_ING) {
            values = DownloadManager.restore(DownloadDao.getInstance().getDownloading());
        }
    }

    @Override
    protected void refreshContentView(View contentView) {
        controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApkDownloadAdapter(apks);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int loadDataFromCache() {
//        initApkData();
        presenter.getDownloadGameInfoList();
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    public void initApkData() {
        apks = new ArrayList<>();
        GameInfo apk1 = new GameInfo();
        apk1.setGname("爱奇艺");
        apk1.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0c10c4c0155c9adf1282af008ed329378d54112ac");
        apk1.setAndroidfileurl("http://121.29.10.1/f5.market.mi-img.com/download/AppStore/0b8b552a1df0a8bc417a5afae3a26b2fb1342a909/com.qiyi.video.apk");
        apks.add(apk1);
        GameInfo apk2 = new GameInfo();
        apk2.setGname("微信");
        apk2.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/00814b5dad9b54cc804466369c8cb18f23e23823f");
        apk2.setAndroidfileurl("http://116.117.158.129/f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk");
        apks.add(apk2);
        GameInfo apk3 = new GameInfo();
        apk3.setGname("新浪微博");
        apk3.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/01db44d7f809430661da4fff4d42e703007430f38");
        apk3.setAndroidfileurl("http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk");
        apks.add(apk3);
        GameInfo apk4 = new GameInfo();
        apk4.setGname("QQ");
        apk4.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/072725ca573700292b92e636ec126f51ba4429a50");
        apk4.setAndroidfileurl("http://121.29.10.1/f3.market.xiaomi.com/download/AppStore/0ff0604fd770f481927d1edfad35675a3568ba656/com.tencent.mobileqq.apk");
        apks.add(apk4);
        GameInfo apk5 = new GameInfo();
        apk5.setGname("陌陌");
        apk5.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/06006948e655c4dd11862d060bd055b4fd2b5c41b");
        apk5.setAndroidfileurl("http://121.18.239.1/f4.market.xiaomi.com/download/AppStore/096f34dec955dbde0597f4e701d1406000d432064/com.immomo.momo.apk");
        apks.add(apk5);
        GameInfo apk6 = new GameInfo();
        apk6.setGname("手机淘宝");
        apk6.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/017a859792d09d7394108e0a618411675ec43f220");
        apk6.setAndroidfileurl("http://121.29.10.1/f3.market.xiaomi.com/download/AppStore/0afc00452eb1a4dc42b20c9351eacacab4692a953/com.taobao.taobao.apk");
        apks.add(apk6);
        GameInfo apk7 = new GameInfo();
        apk7.setGname("酷狗音乐");
        apk7.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0f2f050e21e42f75c7ecca55d01ac4e5e4e40ca8d");
        apk7.setAndroidfileurl("http://121.18.239.1/f5.market.xiaomi.com/download/AppStore/053ed49c1545c6eec3e3e23b31568c731f940934f/com.kugou.android.apk");
        apks.add(apk7);
        GameInfo apk8 = new GameInfo();
        apk8.setGname("网易云音乐");
        apk8.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/02374548ac39f3b7cdbf5bea4b0535b5d1f432f23");
        apk8.setAndroidfileurl("http://121.18.239.1/f4.market.xiaomi.com/download/AppStore/0f458c5661acb492e30b808a2e3e4c8672e6b55e2/com.netease.cloudmusic.apk");
        apks.add(apk8);
        GameInfo apk9 = new GameInfo();
        apk9.setGname("ofo共享单车");
        apk9.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0fe1a5c6092f3d9fa5c4c1e3158e6ff33f6418152");
        apk9.setAndroidfileurl("http://60.28.125.1/f4.market.mi-img.com/download/AppStore/06954949fcd48414c16f726620cf2d52200550f56/so.ofo.labofo.apk");
        apks.add(apk9);
        GameInfo apk10 = new GameInfo();
        apk10.setGname("摩拜单车");
        apk10.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0863a058a811148a5174d9784b7be2f1114191f83");
        apk10.setAndroidfileurl("http://60.28.125.1/f4.market.xiaomi.com/download/AppStore/00cdeb4865c5a4a7d350fe30b9f812908a569cc8a/com.mobike.mobikeapp.apk");
        apks.add(apk10);
        GameInfo apk11 = new GameInfo();
        apk11.setGname("贪吃蛇大作战");
        apk11.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/09f7f5756d9d63bb149b7149b8bdde0769941f09b");
        apk11.setAndroidfileurl("http://60.22.46.1/f3.market.xiaomi.com/download/AppStore/0b02f24ffa8334bd21b16bd70ecacdb42374eb9cb/com.wepie.snake.new.mi.apk");
        apks.add(apk11);
        GameInfo apk12 = new GameInfo();
        apk12.setGname("蘑菇街");
        apk12.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0ab53044735e842c421a57954d86a77aea30cc1da");
        apk12.setAndroidfileurl("http://121.29.10.1/f5.market.xiaomi.com/download/AppStore/07a6ee4955e364c3f013b14055c37b8e4f6668161/com.mogujie.apk");
        apks.add(apk12);
        GameInfo apk13 = new GameInfo();
        apk13.setGname("聚美优品");
        apk13.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/080ed520b76d943e5533017a19bc76d9f554342d0");
        apk13.setAndroidfileurl("http://121.29.10.1/f5.market.mi-img.com/download/AppStore/0e70a572cd5fd6a3718941328238d78d71942aee0/com.jm.android.jumei.apk");
        apks.add(apk13);
        GameInfo apk14 = new GameInfo();
        apk14.setGname("全民K歌");
        apk14.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0f1f653261ff8b3a64324097224e40eface432b99");
        apk14.setAndroidfileurl("http://60.28.123.129/f4.market.xiaomi.com/download/AppStore/04f515e21146022934085454a1121e11ae34396ae/com.tencent.karaoke.apk");
        apks.add(apk14);
        GameInfo apk15 = new GameInfo();
        apk15.setGname("书旗小说");
        apk15.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0c9ce345aa2734b1202ddf32b6545d9407b18ba0b");
        apk15.setAndroidfileurl("http://60.28.125.129/f5.market.mi-img.com/download/AppStore/02d9c4035b248753314f46600cf7347a306426dc1/com.shuqi.controller.apk");
        apks.add(apk15);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadManager.getInstance().removeAllDownloadListener();
    }

    @Override
    public void loadDownloadGameInfoList(List<GameInfo> gameInfoList) {
        apks.clear();
        apks.addAll(gameInfoList);
        adapter.notifyDataSetChanged();

    }
}
