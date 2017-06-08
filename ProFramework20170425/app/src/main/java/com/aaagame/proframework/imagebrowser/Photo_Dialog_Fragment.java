package com.aaagame.proframework.imagebrowser;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.utils.AACamera;
import com.aaagame.proframework.utils.AAComAdapter;
import com.aaagame.proframework.utils.AADate;
import com.aaagame.proframework.utils.AAPath;
import com.aaagame.proframework.utils.AAToast;
import com.aaagame.proframework.utils.AAViewCom;
import com.aaagame.proframework.utils.Photo_Take_Util;

import java.io.File;
import java.util.Random;

import static com.aaagame.proframework.utils.Photo_Take_Util.selectPhoto;


public class Photo_Dialog_Fragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_photo_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        initView(dialog.getWindow().getDecorView());
        AACamera.checkStoragePer(getActivity());
        return dialog;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //拍照保存的位置
    private String cameraPath;
    //要上传的位置
    private String updatePath;

    /**
     * 获取要上传的位置
     *
     * @return
     */
    public String getUpdatePath() {
        return updatePath;
    }

    /**
     * 设置要上传的位置
     *
     * @param updatePath
     */
    public void setUpdatePath(String updatePath) {
        this.updatePath = updatePath;
    }

    /**
     * 获取拍照的图片路径
     *
     * @return
     */
    public String getCameraPath() {
        return cameraPath;
    }

    private AAComAdapter adapter;

    /**
     * 设置List中用获取图片时的Adapter
     *
     * @param adapter
     */
    public void setAdapter(AAComAdapter adapter) {
        this.adapter = adapter;
    }

    private Photo_Take_Util photo_take_util;

    /**
     * 设置图片管理工具对象
     *
     * @param photo_take_util
     */
    public void setPhoto_Take_Util(Photo_Take_Util photo_take_util) {
        this.photo_take_util = photo_take_util;
    }

    TextView tv_cancel, tv_camera, tv_gallery;

    private void initView(View view) {
        tv_cancel = AAViewCom.getTv(view, R.id.tv_cancel);
        tv_camera = AAViewCom.getTv(view, R.id.tv_camera);
        tv_gallery = AAViewCom.getTv(view, R.id.tv_gallery);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo_Dialog_Fragment.this.dismiss();
            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null && photo_take_util != null) {
                    adapter.currentUtil = photo_take_util;
                }
                cameraPath = AAPath.getCacheFilesPath() + File.separator + AADate.getCurrentTime(AADate.ymdhms_name) + new Random().nextInt() + ".jpg";
                takePhoto(getActivity());
                Photo_Dialog_Fragment.this.dismiss();
            }
        });
        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null && photo_take_util != null) {
                    adapter.currentUtil = photo_take_util;
                }
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                getActivity().startActivityForResult(intent, selectPhoto);
                Photo_Dialog_Fragment.this.dismiss();
            }
        });
    }

    /**
     * 执行拍照
     */
    public void takePhoto(Activity activity) {
        AACamera.cameraMethod(activity, Photo_Take_Util.photomark, cameraPath);
    }

    public void setPermissionsResult(Activity activity, int requestCode, int[] grantResults) {
        try {
            switch (requestCode) {
                case AACamera.Req_Camera:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        takePhoto(activity);
                    } else {
                        AAToast.toastShow(getActivity(), "获取相机授权失败");
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        hideSoftKeyboard(getActivity());
        super.onStop();
    }

    /**
     * 隐藏虚拟键盘
     */
    protected void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getWindow()
                    .getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (activity.getCurrentFocus() != null)
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
