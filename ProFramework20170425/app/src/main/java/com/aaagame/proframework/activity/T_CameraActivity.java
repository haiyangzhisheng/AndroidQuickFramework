package com.aaagame.proframework.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aaagame.proframework.R;
import com.aaagame.proframework.imagebrowser.Photo_Dialog_Fragment;
import com.aaagame.proframework.utils.AAImageUtil;
import com.aaagame.proframework.utils.AAMethod;
import com.aaagame.proframework.utils.AAPath;
import com.aaagame.proframework.utils.Photo_Take_Util;
import com.yalantis.ucrop.UCrop;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import static com.aaagame.proframework.utils.Photo_Take_Util.photomark;
import static com.aaagame.proframework.utils.Photo_Take_Util.selectPhoto;
import static com.aaagame.proframework.utils.Photo_Take_Util.startUCrop;

@ContentView(R.layout.t_activity_camera)
public class T_CameraActivity extends BaseFragmentActivity {
    Photo_Dialog_Fragment photo_dialog_fragment;
    @ViewInject(R.id.photo_value)
    LinearLayout photo_value;
    Photo_Take_Util sL_Photo_Take_Util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sL_Photo_Take_Util = new Photo_Take_Util(null, myActivity, photo_value, 5);
        sL_Photo_Take_Util.setShowDelete();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void reqData() {

    }

    //是否点击Photo_Take_Util请求多张图片
    boolean reqPhotos = true;

    @Event(value = {R.id.btn_tz})
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tz:
                reqPhotos = false;
                photo_dialog_fragment = new Photo_Dialog_Fragment();
                photo_dialog_fragment.setUpdatePath(AAPath.getPathPhoto1());
                photo_dialog_fragment.show(myActivity.getFragmentManager(), "Photo_Dialog_Fragment");
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (photo_dialog_fragment != null) {
            photo_dialog_fragment.setPermissionsResult(myActivity, requestCode, grantResults);
        }
    }

    Uri resultUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (reqPhotos) {
                sL_Photo_Take_Util.setResult(requestCode, resultCode, data);
            } else {
                if (requestCode == photomark) {
                    startUCrop(myActivity, photo_dialog_fragment.getCameraPath(), photo_dialog_fragment.getUpdatePath());
                } else if (requestCode == selectPhoto) {
                    String imgPath = AAImageUtil.getImageAbsolutePath(myActivity, data.getData());
                    if (imgPath == null || !new File(imgPath).exists()) {
                        Toast.makeText(myActivity, "图片在本地不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startUCrop(myActivity, imgPath, photo_dialog_fragment.getUpdatePath());
                } else if (requestCode == UCrop.REQUEST_CROP) {
                    System.out.println(photo_dialog_fragment.getUpdatePath() + "我的图片最终名称--------");
                    AAMethod.updateGallery(myActivity, new String[]{photo_dialog_fragment.getUpdatePath()});
                }

                if (requestCode == UCrop.REQUEST_CROP) {
                    reqPhotos = true;
                    resultUri = UCrop.getOutput(data);
                    if (resultUri != null) {
                        iv_result.setImageResource(0);
                        iv_result.setImageURI(resultUri);
                    } else {
                    }
                }
            }
        }

    }

    @ViewInject(R.id.iv_result)
    ImageView iv_result;


}
