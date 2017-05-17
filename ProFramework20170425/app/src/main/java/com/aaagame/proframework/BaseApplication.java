package com.aaagame.proframework;

import android.app.Application;
import android.app.Notification;

import com.aaagame.proframework.utils.AAMethod;

import org.xutils.x;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2016/12/15.
 */

public class BaseApplication extends Application {
    public static String MyVersionName = "0";

    @Override
    public void onCreate() {
        super.onCreate();
        MyVersionName = AAMethod.getVersionName(this);
        //xutils初始化
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
        //分享=================================
//        UMShareAPI.get(this);
//        //友盟分享
//        PlatformConfig.setWeixin("wxd2287aa9b3d7b19c",
//                "71aa1564cfbfdeff815aaa1b13fed394");
//        // 微信 appid appsecret
//        PlatformConfig.setSinaWeibo("24322517",
//                "b4cb9da15bebb52ea13a73956dd098ae", "http://sns.whalecloud.com");
//        // 新浪微博 appkey appsecret 1105468963
//        PlatformConfig.setQQZone("101387962", "eef677a276c72dc46f914e2e3bfaccc4");
//        // QQ和Qzone appid appkey
//        Config.DEBUG = true;
//        initJpush();
    }

    // 初始化激光推送
    public void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());
        JPushInterface.resumePush(getApplicationContext());
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(getApplicationContext());
//        builder.statusBarDrawable = R.drawable.empty_default;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }
}
