package com.aaagame.proframework.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.aaagame.proframework.R;
import com.aaagame.proframework.activity.test.T_CameraActivity;
import com.aaagame.proframework.bean.VersionBean;
import com.aaagame.proframework.dialog.AAMyAlertDialog;
import com.aaagame.proframework.imagebrowser.Photo_Dialog_Fragment;
import com.aaagame.proframework.utils.AAToast;
import com.aaagame.proframework.utils.AAViewCom;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity {
    Photo_Dialog_Fragment photo_dialog_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_tz = AAViewCom.getBtn(myActivity, R.id.btn_tz);
        btn_tz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animStartActivity(T_CameraActivity.class);
//                photo_dialog_fragment = new Photo_Dialog_Fragment();
//                photo_dialog_fragment.setUpdatePath(AAPath.getPathPhoto1());
//                photo_dialog_fragment.show(myActivity.getFragmentManager(), "Photo_Dialog_Fragment");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        photo_dialog_fragment.setPermissionsResult(myActivity, requestCode, grantResults);
    }

    Button btn_tz;

    private void checkAppState() {
        try {// 注册后台版本检测通知
            MyVersionBroadcastReciver myVersionReceiver = new MyVersionBroadcastReciver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("CheckVersion");
            getApplicationContext().registerReceiver(myVersionReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent myIntent = new Intent(this, AppUpdateService.class);
        myIntent.putExtra("checkversion", "checkversion");
        startService(myIntent);
//        try {// 注册后台礼包检测通知
//            MyBootPackBroadcastReciver myVersionReceiver = new MyBootPackBroadcastReciver();
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction("CheckBootPack");
//            getApplicationContext().registerReceiver(myVersionReceiver, intentFilter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Intent service = new Intent(this, AppUpdateService.class);
//        service.putExtra("BootPack", "BootPack");
//        startService(service);
    }

    // 后台礼包检测结果通知
    private class MyBootPackBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
//            List<Voucher_Dialog_Bean> mylist = getGson().fromJson(intent.getStringExtra("vouchers"), new TypeToken<List<Voucher_Dialog_Bean>>() {
//            }.getType());
//            try {
//                BootPackDialog d = new BootPackDialog(MainActivity.this, R.style.MyDialog,
//                        mylist);
//                d.show();
//                d.getWindow().setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    // 后台版本检测结果通知
    private class MyVersionBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final VersionBean versionBean = getGson().fromJson(intent.getStringExtra("versionBean"), VersionBean.class);
            try {
                final AAMyAlertDialog alertDialog = new AAMyAlertDialog(myActivity);
                alertDialog.setTitle("更新提示");
                alertDialog.setMessage(versionBean.getRemark());
                alertDialog.setMustAlert();
                alertDialog.setPositiveButton("更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(myActivity, AppUpdateService.class);
                        myIntent.putExtra("updateurl", versionBean.getUrl());
                        myActivity.startService(myIntent);
                        AAToast.toastShow(myActivity, "正在下载，请稍后");
                        alertDialog.dismiss();
                    }
                });
                if (versionBean.isCompel()) {
                    alertDialog.setOnKeyListener(keylistener);
                } else {
                    alertDialog.setNegativeButton("忽略", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
                alertDialog.show();
                alertDialog.btn_ok.setBackgroundResource(R.drawable.shape_rect_yellow_btn_pressed);
                alertDialog.btn_ok.setTextColor(getResources().getColor(R.color.app_white));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
