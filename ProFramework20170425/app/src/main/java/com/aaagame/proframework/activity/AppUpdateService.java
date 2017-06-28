package com.aaagame.proframework.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import com.aaagame.proframework.R;
import com.aaagame.proframework.bean.VersionBean;
import com.aaagame.proframework.utils.AACom;
import com.aaagame.proframework.utils.AADate;
import com.aaagame.proframework.utils.AAPath;
import com.aaagame.proframework.utils.Ahttp;
import com.aaagame.proframework.utils.ArequestCallBack;
import com.aaagame.proframework.utils.ConInterface;
import com.aaagame.proframework.utils.SpUtils;

import org.json.JSONArray;
import org.xutils.common.Callback.ProgressCallback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


/**
 * Created by LiBin on 15/11/26.
 */

@SuppressLint("NewApi")
public class AppUpdateService extends Service {

    private NotificationManager mNotificationManager;
    private Notification.Builder mNotificationBuilder;

    private static final int NOTIFY_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (intent.getStringExtra("BootPack") != null) {
                checkBootPack();
                return START_NOT_STICKY;
            }
            if (intent.getStringExtra("checkversion") == null) {
                if (intent.getStringExtra("updateurl") != null) {
                    goDownLoad(intent.getStringExtra("updateurl"));
                }
            } else {
                System.out.println("service检查版本=============");
                checkVersion(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }

    private void checkBootPack() {
        if (!SpUtils.isLogin(AppUpdateService.this.getApplicationContext())) {
            return;
        }
        System.out.println("礼包检测================");
        Ahttp ahttp = new Ahttp("", "");
        ahttp.send(new ArequestCallBack<String>(ahttp) {
            @Override
            public void onSuccess(String responseInfo) {
                super.onSuccess(responseInfo);
                if (aresult.ret.equals("0")) {
                    return;
                }
//                final SL_Version version = AACom.getGson().fromJson(data, SL_Version.class);

                try {
                    JSONArray array = objectData.getJSONArray("vouchers");
                    Intent broadcase = new Intent("CheckBootPack");
                    broadcase.putExtra("vouchers", array.toString());
                    AppUpdateService.this.sendBroadcast(broadcase);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });
    }

    private void checkVersion(final Intent intent) {
        Ahttp ahttp = new Ahttp(ConInterface.Sample, "");
        ahttp.send(new ArequestCallBack<String>(ahttp) {
            @Override
            public void onSuccess(String responseInfo) {
                super.onSuccess(responseInfo);
                if (aresult.ret.equals("0")) {
                    return;
                }
                final VersionBean version = AACom.getGson().fromJson(data, VersionBean.class);
                Intent broadcase = new Intent("CheckVersion");
                broadcase.putExtra("versionBean", AACom.getGson().toJson(version));
                AppUpdateService.this.sendBroadcast(broadcase);
            }

        });
    }

    private void goDownLoad(String updateurl) {
        setUpNotification();
        updateApp(updateurl);
    }

    @SuppressLint("NewApi")
    private void setUpNotification() {
        mNotificationBuilder = new Notification.Builder(getApplicationContext());

        Notification mNotification = mNotificationBuilder.setContentTitle("正在下载").setContentText("软件下载中请耐心等待")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setTicker("正在下载" + getResources().getString(R.string.app_name)).setWhen(System.currentTimeMillis()).setProgress(100, 0, false).build();

        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    public static final int connTimeout = 1000 * 500;

    /**
     * 下载应用程序
     *
     * @param updateUrl 应用程序地址
     */
    private void updateApp(String updateUrl) {

        RequestParams params = new RequestParams(ConInterface.IMAGE_HOST + updateUrl);
        params.setConnectTimeout(connTimeout);
        params.setAutoResume(true);
        params.setAutoRename(true);
        params.setSaveFilePath(AAPath.getApkPath() + File.separator + AADate.getCurrentTime(AADate.ymdhms_name) + ".apk");
        params.setExecutor(new PriorityExecutor(2, true));
        params.setCancelFast(true);
        x.http().get(params, new ProgressCallback<File>() {

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Notification mNotification = mNotificationBuilder.setContentTitle("下载失败").setContentText("下载失败")
                        .setTicker("下载失败").build();
                Toast.makeText(AppUpdateService.this, "下载失败", Toast.LENGTH_SHORT).show();
                mNotification.flags = Notification.FLAG_AUTO_CANCEL;

                mNotificationManager.notify(NOTIFY_ID, mNotification);

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(File result) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Notification mNotification = mNotificationBuilder.setContentTitle("下载完成").setContentText("下载完成")
                        .setContentIntent(pendingIntent).build();

                mNotification.flags = Notification.FLAG_AUTO_CANCEL;

                mNotificationManager.notify(NOTIFY_ID, mNotification);

                startActivity(intent);

                stopSelf();

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int tmp = (int) ((current * 100l) / total);
                Notification mNotification = mNotificationBuilder.setProgress(100, (int) tmp, false).build();
                mNotification.flags = Notification.FLAG_ONGOING_EVENT;
                mNotificationBuilder.setContentText("进度" + tmp + "%");
                mNotificationManager.notify(NOTIFY_ID, mNotification);
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onWaiting() {
            }
        });
    }
}
