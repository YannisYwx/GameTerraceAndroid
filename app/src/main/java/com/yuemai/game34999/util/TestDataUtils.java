package com.yuemai.game34999.util;

import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.data.bean.InformationBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/12  15:02
 * @email : 923080261@qq.com
 * @description :
 */
public class TestDataUtils {

    public static List getTestDatas() {
        List datas = new ArrayList();
        for (int i = 0; i < 50; i++) {
            datas.add(1);
        }
        return datas;
    }

    public static List getTestDatas(int count) {
        count = Math.max(0, count);
        List datas = new ArrayList();
        for (int i = 0; i < count; i++) {
            datas.add(1);
        }
        return datas;
    }


    public static ArrayList<GameInfo> initApkData() {
        ArrayList<GameInfo> apks = new ArrayList<>();
        GameInfo apk1 = new GameInfo();
        apk1.setGname("爱奇艺");
        apk1.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0c10c4c0155c9adf1282af008ed329378d54112ac");
        apk1.setAndroidfileurl("http://121.29.10.1/f5.market.mi-img.com/download/AppStore/0b8b552a1df0a8bc417a5afae3a26b2fb1342a909/com.qiyi.video.apk");

        apk1.setGtitle("我只是一个测试title");
        apk1.setAndrioddownload(1_00_00000);
        apk1.setAndroidfilesize(1024 * 1024 * 256);
        apk1.setAndrioddownload(128_0000);

        apks.add(apk1);
        GameInfo apk2 = new GameInfo();
        apk2.setGname("微信");
        apk2.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/00814b5dad9b54cc804466369c8cb18f23e23823f");
        apk2.setAndroidfileurl("http://116.117.158.129/f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk");

        apk2.setGtitle("我只是一个测试title");
        apk2.setAndrioddownload(1_00_00000);
        apk2.setAndroidfilesize(1024 * 1024 * 256);
        apk2.setAndrioddownload(128_0000);

        apks.add(apk2);
        GameInfo apk3 = new GameInfo();
        apk3.setGname("新浪微博");
        apk3.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/01db44d7f809430661da4fff4d42e703007430f38");
        apk3.setAndroidfileurl("http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk");

        apk3.setGtitle("我只是一个测试title");
        apk3.setAndrioddownload(1_00_00000);
        apk3.setAndroidfilesize(1024 * 1024 * 256);
        apk3.setAndrioddownload(128_0000);


        apks.add(apk3);
        GameInfo apk4 = new GameInfo();
        apk4.setGname("QQ");
        apk4.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/072725ca573700292b92e636ec126f51ba4429a50");
        apk4.setAndroidfileurl("http://121.29.10.1/f3.market.xiaomi.com/download/AppStore/0ff0604fd770f481927d1edfad35675a3568ba656/com.tencent.mobileqq.apk");

        apk4.setGtitle("我只是一个测试title");
        apk4.setAndrioddownload(1_00_00000);
        apk4.setAndroidfilesize(1024 * 1024 * 256);
        apk4.setAndrioddownload(128_0000);

        apks.add(apk4);
        GameInfo apk5 = new GameInfo();
        apk5.setGname("陌陌");
        apk5.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/06006948e655c4dd11862d060bd055b4fd2b5c41b");
        apk5.setAndroidfileurl("http://121.18.239.1/f4.market.xiaomi.com/download/AppStore/096f34dec955dbde0597f4e701d1406000d432064/com.immomo.momo.apk");

        apk5.setGtitle("我只是一个测试title");
        apk5.setAndrioddownload(1_00_00000);
        apk5.setAndroidfilesize(1024 * 1024 * 256);
        apk5.setAndrioddownload(128_0000);

        apks.add(apk5);
        GameInfo apk6 = new GameInfo();
        apk6.setGname("手机淘宝");
        apk6.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/017a859792d09d7394108e0a618411675ec43f220");
        apk6.setAndroidfileurl("http://121.29.10.1/f3.market.xiaomi.com/download/AppStore/0afc00452eb1a4dc42b20c9351eacacab4692a953/com.taobao.taobao.apk");

        apk6.setGtitle("我只是一个测试title");
        apk6.setAndrioddownload(1_00_00000);
        apk6.setAndroidfilesize(1024 * 1024 * 256);
        apk6.setAndrioddownload(128_0000);

        apks.add(apk6);
        GameInfo apk7 = new GameInfo();
        apk7.setGname("酷狗音乐");
        apk7.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0f2f050e21e42f75c7ecca55d01ac4e5e4e40ca8d");
        apk7.setAndroidfileurl("http://121.18.239.1/f5.market.xiaomi.com/download/AppStore/053ed49c1545c6eec3e3e23b31568c731f940934f/com.kugou.android.apk");

        apk7.setGtitle("我只是一个测试title");
        apk7.setAndrioddownload(1_00_00000);
        apk7.setAndroidfilesize(1024 * 1024 * 256);
        apk7.setAndrioddownload(128_0000);

        apks.add(apk7);
        GameInfo apk8 = new GameInfo();
        apk8.setGname("网易云音乐");
        apk8.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/02374548ac39f3b7cdbf5bea4b0535b5d1f432f23");
        apk8.setAndroidfileurl("http://121.18.239.1/f4.market.xiaomi.com/download/AppStore/0f458c5661acb492e30b808a2e3e4c8672e6b55e2/com.netease.cloudmusic.apk");

        apk8.setGtitle("我只是一个测试title");
        apk8.setAndrioddownload(1_00_00000);
        apk8.setAndroidfilesize(1024 * 1024 * 256);
        apk8.setAndrioddownload(128_0000);

        apks.add(apk8);
        GameInfo apk9 = new GameInfo();
        apk9.setGname("ofo共享单车");
        apk9.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0fe1a5c6092f3d9fa5c4c1e3158e6ff33f6418152");
        apk9.setAndroidfileurl("http://60.28.125.1/f4.market.mi-img.com/download/AppStore/06954949fcd48414c16f726620cf2d52200550f56/so.ofo.labofo.apk");

        apk9.setGtitle("我只是一个测试title");
        apk9.setAndrioddownload(1_00_00000);
        apk9.setAndroidfilesize(1024 * 1024 * 256);
        apk9.setAndrioddownload(128_0000);

        apks.add(apk9);
        GameInfo apk10 = new GameInfo();
        apk10.setGname("摩拜单车");
        apk10.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0863a058a811148a5174d9784b7be2f1114191f83");
        apk10.setAndroidfileurl("http://60.28.125.1/f4.market.xiaomi.com/download/AppStore/00cdeb4865c5a4a7d350fe30b9f812908a569cc8a/com.mobike.mobikeapp.apk");

        apk10.setGtitle("我只是一个测试title");
        apk10.setAndrioddownload(1_00_00000);
        apk10.setAndroidfilesize(1024 * 1024 * 256);
        apk10.setAndrioddownload(128_0000);

        apks.add(apk10);
        GameInfo apk11 = new GameInfo();
        apk11.setGname("贪吃蛇大作战");
        apk11.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/09f7f5756d9d63bb149b7149b8bdde0769941f09b");
        apk11.setAndroidfileurl("http://60.22.46.1/f3.market.xiaomi.com/download/AppStore/0b02f24ffa8334bd21b16bd70ecacdb42374eb9cb/com.wepie.snake.new.mi.apk");

        apk11.setGtitle("我只是一个测试title");
        apk11.setAndrioddownload(1_00_00000);
        apk11.setAndroidfilesize(1024 * 1024 * 256);
        apk11.setAndrioddownload(128_0000);

        apks.add(apk11);
        GameInfo apk12 = new GameInfo();
        apk12.setGname("蘑菇街");
        apk12.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0ab53044735e842c421a57954d86a77aea30cc1da");
        apk12.setAndroidfileurl("http://121.29.10.1/f5.market.xiaomi.com/download/AppStore/07a6ee4955e364c3f013b14055c37b8e4f6668161/com.mogujie.apk");

        apk12.setGtitle("我只是一个测试title");
        apk12.setAndrioddownload(1_00_00000);
        apk12.setAndroidfilesize(1024 * 1024 * 256);
        apk12.setAndrioddownload(128_0000);

        apks.add(apk12);
        GameInfo apk13 = new GameInfo();
        apk13.setGname("聚美优品");
        apk13.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/080ed520b76d943e5533017a19bc76d9f554342d0");
        apk13.setAndroidfileurl("http://121.29.10.1/f5.market.mi-img.com/download/AppStore/0e70a572cd5fd6a3718941328238d78d71942aee0/com.jm.android.jumei.apk");

        apk13.setGtitle("我只是一个测试title");
        apk13.setAndrioddownload(1_00_00000);
        apk13.setAndroidfilesize(1024 * 1024 * 256);
        apk13.setAndrioddownload(128_0000);

        apks.add(apk13);
        GameInfo apk14 = new GameInfo();
        apk14.setGname("全民K歌");
        apk14.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0f1f653261ff8b3a64324097224e40eface432b99");
        apk14.setAndroidfileurl("http://60.28.123.129/f4.market.xiaomi.com/download/AppStore/04f515e21146022934085454a1121e11ae34396ae/com.tencent.karaoke.apk");

        apk14.setGtitle("我只是一个测试title");
        apk14.setAndrioddownload(1_00_00000);
        apk14.setAndroidfilesize(1024 * 1024 * 256);
        apk14.setAndrioddownload(128_0000);

        apks.add(apk14);
        GameInfo apk15 = new GameInfo();
        apk15.setGname("书旗小说");
        apk15.setGicon("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0c9ce345aa2734b1202ddf32b6545d9407b18ba0b");
        apk15.setAndroidfileurl("http://60.28.125.129/f5.market.mi-img.com/download/AppStore/02d9c4035b248753314f46600cf7347a306426dc1/com.shuqi.controller.apk");

        apk15.setGtitle("我只是一个测试title");
        apk15.setAndrioddownload(1_00_00000);
        apk15.setAndroidfilesize(1024 * 1024 * 256);
        apk15.setAndrioddownload(128_0000);

        apks.add(apk15);
        return apks;
    }


    public static List<InformationBean> getInformationData(int type, int count) {
        List<InformationBean> datas = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            InformationBean informationBean = new InformationBean();
            informationBean.setDate("08-08 08:08");
            informationBean.setInfo("看老纸不大喜你!");
            informationBean.setGameType("公测|3D|角色扮演");
            informationBean.setInformationType((type == 0) ? (random.nextInt(7) + 1) : type);
            informationBean.setTitle("好好学习  天天向上 去你妈的狗屁哦");
            informationBean.setPreviewNum(random.nextInt(10_000) + "");
            datas.add(informationBean);
        }
        return datas;
    }
}
