package com.aaagame.proframework.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.aaagame.proframework.R;
import com.aaagame.proframework.bean.VersionBean;
import com.aaagame.proframework.dialog.AAMyAlertDialog;
import com.aaagame.proframework.imagebrowser.Photo_Dialog_Fragment;
import com.aaagame.proframework.utils.AAImageUtil;
import com.aaagame.proframework.utils.AAMethod;
import com.aaagame.proframework.utils.AAPath;
import com.aaagame.proframework.utils.AAToast;
import com.aaagame.proframework.utils.AAViewCom;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;
import static com.aaagame.proframework.utils.Photo_Take_Util.cropPhoto;
import static com.aaagame.proframework.utils.Photo_Take_Util.photomark;
import static com.aaagame.proframework.utils.Photo_Take_Util.selectPhoto;
import static com.aaagame.proframework.utils.Photo_Take_Util.startPhotoZoom;

@ContentView(R.layout.t_activity_camera)
public class T_CameraActivity extends BaseFragmentActivity {
    Photo_Dialog_Fragment photo_dialog_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_tz = AAViewCom.getBtn(myActivity, R.id.btn_tz);
        btn_tz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo_dialog_fragment = new Photo_Dialog_Fragment();
                photo_dialog_fragment.setUpdatePath(AAPath.getPathPhoto1());
                photo_dialog_fragment.show(myActivity.getFragmentManager(), "Photo_Dialog_Fragment");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        photo_dialog_fragment.setPermissionsResult(myActivity, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == photomark) {
                startPhotoZoom(myActivity, photo_dialog_fragment.getCameraPath(), photo_dialog_fragment.getUpdatePath());
            } else if (requestCode == selectPhoto) {
                String imgPath = AAImageUtil.getImageAbsolutePath(myActivity, data.getData());
                if (imgPath == null || !new File(imgPath).exists()) {
                    Toast.makeText(myActivity, "图片在本地不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
//                startPhotoZoom(myActivity, imgPath, photo_dialog_fragment.getUpdatePath());
                UCrop.Options options = new UCrop.Options();
                //开始设置
                //一共三个参数，分别对应裁剪功能页面的“缩放”，“旋转”，“裁剪”界面，对应的传入NONE，就表示关闭了其手势操作，比如这里我关闭了缩放和旋转界面的手势，只留了裁剪页面的手势操作
                options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
                options.setHideBottomControls(true);
                options.setShowCropGrid(false);
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                options.withMaxResultSize(500, 500);
                options.setCompressionQuality(70);
                options.setToolbarColor(ContextCompat.getColor(myActivity, R.color.blue500));
                options.setStatusBarColor(ContextCompat.getColor(myActivity, R.color.blue500));
                UCrop.of(Uri.fromFile(new File(imgPath)), Uri.fromFile(new File(photo_dialog_fragment.getUpdatePath())))
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(maxWidth, maxHeight).withOptions(options)
                        .start(myActivity);
            } else if (requestCode == cropPhoto) {
                System.out.println(photo_dialog_fragment.getUpdatePath() + "我的图片最终名称--------");
                AAMethod.updateGallery(myActivity, new String[]{photo_dialog_fragment.getUpdatePath()});
            }

            if (requestCode == UCrop.REQUEST_CROP) {
                final Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null) {
                    iv_result.setImageURI(resultUri);
                } else {
                }
            }
        }

    }

    @ViewInject(R.id.iv_result)
    ImageView iv_result;

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
