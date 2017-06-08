package com.aaagame.proframework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aaagame.proframework.R;
import com.aaagame.proframework.imagebrowser.Photo_Dialog_Fragment;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;
import static com.aaagame.proframework.utils.AACamera.photoWidth;


public class Photo_Take_Util {
    private Activity myActivity;
    private int photoCount = 10;
    private AAComAdapter ada;

    public Photo_Take_Util(AAComAdapter ada, Activity activity, LinearLayout linearLayout, int photoCount) {
        // 请求存储权限
        AACamera.checkStoragePer(activity);
        this.photo_value = linearLayout;
        this.myActivity = activity;
        this.photoCount = photoCount;
        this.ada = ada;
        init();
    }

    public Photo_Take_Util(AAComAdapter ada, Activity activity, LinearLayout linearLayout, int photoCount,
                           ArrayList<String> httpImgList) {
        this.photo_value = linearLayout;
        this.myActivity = activity;
        this.photoCount = photoCount;
        this.httpImgList = httpImgList;
        this.ada = ada;
        init();
    }

    public void init() {
        initVariable();
        initLayout();
        initListener();
        initData();
        initPhoto();

    }

    private void initVariable() {

    }

    private void initListener() {
    }

    private void initData() {

    }

    // -------------------------拍照开始
    private List<ImageView> lsimg = new ArrayList<ImageView>();
    public List<String> imgpathlist = new ArrayList<String>();
    private Map<String, String> showImgMapPath = new HashMap<String, String>();
    private Drawable imgdraw;
    LinearLayout photo_value;

    private void initPhoto() {
        imgdraw = myActivity.getResources().getDrawable(R.drawable.photo_add);
        addImgv();
        imgpathlist = new ArrayList<String>();
        if (httpImgList != null) {
            initImgview();
        }
    }

    boolean isOnlyShow = false;
    ArrayList<String> httpImgList;
    boolean showDelete = false;

    public void setShowDelete() {
        showDelete = true;
    }

    private void initLayout() {
        imgdraw = myActivity.getResources().getDrawable(R.drawable.photo_add);
    }

    private void addImgv() {
        // 初始化第一个 img container 添加一个图片
        ImageView imageView = new ImageView(myActivity);
        lsimg.add(imageView);
        imageView.setOnClickListener(imgclk);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(DensityUtil.dp2px(myActivity, 40),
                DensityUtil.dp2px(myActivity, 40));
        param.setMargins(DensityUtil.dp2px(myActivity, 5), 0, DensityUtil.dp2px(myActivity, 5), 0);
        imageView.setLayoutParams(param);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setMaxHeight(DensityUtil.dp2px(myActivity, 40));
        imageView.setMaxWidth(DensityUtil.dp2px(myActivity, 40));
        // v.setImageDrawable(imgdraw);
        photo_value.addView(imageView);
        imageView.setImageDrawable(imgdraw);
    }

    /*
     * 刷新照片
     */
    private void updateImgs(String path) {
        for (int i = 0; i < imgpathlist.size(); i++) {
            if (imgpathlist.get(i).equals(path)) {
                photo_value.removeView(lsimg.get(i));
                lsimg.remove(i);
                imgpathlist.remove(i);
                if (lsimg.size() == 0 || lsimg.size() == photoCount - 1) {
                    addImgv();
                }
            }
        }
    }

    public static int getFileSize(String path) {
        int size = 0;
        File f = new File(path);
        size = (int) (f.length() / 1024);
        f = null;
        return size;
    }

    private ImageView curimgv;

    /*
     * 压缩照片
     */
    public static void compressImg(String path, int frate) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 是否只加载 照片的边框 信息
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        int rate = 2;
        if (frate != 0) {
            rate = frate;
        }
        opts.inSampleSize = rate;
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, opts);

        File f = new File(path);
        try {
            FileOutputStream outstm = new FileOutputStream(f);
            boolean b = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstm);
            if (b) {
                outstm.flush();
                outstm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 设置图片
     */
    GradientDrawable gd2, gd1;
    int strokeWidth = 1; // 3dp 边框宽度
    int roundRadius = 5; // 8dp 圆角半径

    /*
     * 初始化图片
     */
    private void initImgview() {
        // 加载网络图片
        for (int j = 0; j < httpImgList.size(); j++) {
            String path = httpImgList.get(j);
            curimgv = lsimg.get(lsimg.size() - 1);
            ImageView myImageView = lsimg.get(lsimg.size() - 1);
            try {
                myImageView.setImageResource(R.drawable.default_image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String curimgpath = AAPath.getPathPhoto(getImgname(curimgv));
            imgpathlist.add(curimgpath);
            final String url = "/" + path;
            x.image().bind(myImageView, url, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
                    ImageOptions imageOptions = new ImageOptions.Builder()
                            .build();
                    x.image().loadFile(url, imageOptions, new Callback.CacheCallback<File>() {
                        @Override
                        public boolean onCache(File result) {
                            //在这里可以做图片另存为等操作
                            Log.i("JAVA", "file：" + result.getPath() + result.getName());

                            try {
                                String curimgpath = result.getPath();
                                ImageView myImg = lsimg.get(0);
                                String mypath = imgpathlist.get(0);
                                for (int i = 0; i < httpImgList.size(); i++) {
                                    if (url.contains(httpImgList.get(i))) {
                                        mypath = imgpathlist.get(i);
                                        myImg = lsimg.get(i);
                                    }
                                }
                                try {
                                    FileInputStream fins;
                                    fins = new FileInputStream(curimgpath);
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inJustDecodeBounds = true;
                                    BitmapFactory.decodeFile(curimgpath, options);
                                    float iss = 1;
                                    try {
                                        iss = options.outHeight < options.outWidth
                                                ? (float) options.outHeight / (float) 100
                                                : (float) options.outWidth / (float) 100;
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                    options.inSampleSize = (int) iss;
                                    BufferedInputStream ins = new BufferedInputStream(fins);
                                    Bitmap bm = null;
                                    options.inJustDecodeBounds = false;
                                    bm = BitmapFactory.decodeStream(ins, null, options);
                                    bm = Bitmap.createScaledBitmap(bm, 150, 150, true);
                                    myImg.setImageBitmap(bm);
                                    // 在此添加代码对照片进行压缩处理使照片达到100k左右
                                    // compressImg(curimgpath, 10);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                AAMethod.fileChannelCopy(result, new File(mypath));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return true; //相信本地缓存返回true
                        }

                        @Override
                        public void onSuccess(File result) {
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {
                        }
                    });
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
            if (lsimg.size() < photoCount) {
                addImgv();
            }
        }
        if (isOnlyShow) {
            photo_value.removeView(lsimg.get(lsimg.size() - 1));
        }
    }

    /*
     * 设置图片
     */
    private void setImgview(String curimgpath) {
        imgpathlist.add(curimgpath);
        try {
            FileInputStream fins;
            fins = new FileInputStream(curimgpath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(curimgpath, options);
            float iss = 1;
            try {
                iss = options.outHeight < options.outWidth ? (float) options.outHeight / (float) 100
                        : (float) options.outWidth / (float) 100;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            options.inSampleSize = (int) iss;
            BufferedInputStream ins = new BufferedInputStream(fins);
            Bitmap bm = null;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeStream(ins, null, options);
            bm = Bitmap.createScaledBitmap(bm, 150, 150, true);
            curimgv.setImageBitmap(bm);
            // 在此添加代码对照片进行压缩处理使照片达到100k左右
            // compressImg(curimgpath, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lsimg.size() < photoCount) {
            addImgv();
        }
    }

    public static final int selectPhoto = 52;
    public static final int photomark = 51;
    public int photo_delete_mark = 54;
    Photo_Dialog_Fragment photo_dialog_fragment;
    OnClickListener imgclk = new OnClickListener() {
        @Override
        public void onClick(View v) {
            curimgv = (ImageView) v;
            if (((ImageView) v).getDrawable().equals(imgdraw)) {
                // takePhoto(getImgname(v));
                String curimgpath = AAPath.getPathPhoto(getImgname(v));
                photo_dialog_fragment = new Photo_Dialog_Fragment();
                if (ada != null) {
                    photo_dialog_fragment.setAdapter(ada);
                }
                photo_dialog_fragment.setUpdatePath(curimgpath);
                photo_dialog_fragment.setPhoto_Take_Util(Photo_Take_Util.this);
                photo_dialog_fragment.show(myActivity.getFragmentManager(), "Photo_Dialog_Fragment");
                return;
            }
            int k = 0;
            for (int i = 0; i < lsimg.size(); i++) {
                if (lsimg.get(i) == v) {
                    k = i;
                }
            }
            if (httpImgList != null && httpImgList.size() > 0) {
                try {
                    String[] myimgs = new String[httpImgList.size()];
                    for (int i = 0; i < httpImgList.size(); i++) {
                        myimgs[i] = httpImgList.get(i);
                    }
                    if (showDelete) {
                        AACom.imageBrower(myActivity, k, myimgs, photo_delete_mark);
                    } else {
                        AACom.imageBrower(myActivity, k, myimgs);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            try {
                String[] myimgs = new String[imgpathlist.size()];
                for (int i = 0; i < imgpathlist.size(); i++) {
                    myimgs[i] = imgpathlist.get(i);
                }
                if (showDelete) {
                    AACom.imageBrower(myActivity, k, myimgs, photo_delete_mark);
                } else {
                    AACom.imageBrower(myActivity, k, myimgs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public void setPermissionsResult(Activity activity, int requestCode, int[] grantResults) {
        try {
            switch (requestCode) {
                case AACamera.Req_Camera:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        photo_dialog_fragment.takePhoto(activity);
                    } else {
                        AAToast.toastShow(myActivity, "获取相机授权失败");
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == photomark) {
                startUCrop(myActivity, photo_dialog_fragment.getCameraPath(), photo_dialog_fragment.getUpdatePath());
            } else if (requestCode == photo_delete_mark) {
                updateImgs(data.getStringExtra("path"));
                if (ada != null) {
                    ada.notifyDataSetChanged();
                }
            } else if (requestCode == selectPhoto) {
                String imgPath = AAImageUtil.getImageAbsolutePath(myActivity, data.getData());
                if (imgPath == null || !new File(imgPath).exists()) {
                    Toast.makeText(myActivity, "图片在本地不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                startUCrop(myActivity, imgPath, photo_dialog_fragment.getUpdatePath());
            } else if (requestCode == UCrop.REQUEST_CROP) {
                try {
                    setImgview(photo_dialog_fragment.getUpdatePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(photo_dialog_fragment.getUpdatePath() + "我的图片最终名称--------");
                AAMethod.updateGallery(myActivity, new String[]{photo_dialog_fragment.getUpdatePath()});
            }
        }

    }

    // 进行剪切
    public static void startPhotoZoom(Activity activity, String sourcePath, String uploadPath) {
        //剪切原图片路径和输出路径不能相同否则图片大小信息不能在MIUI8上更新，图片应该在剪切成功后再保存到要上传的路径
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(sourcePath)), "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", photoWidth);
        intent.putExtra("outputY", photoWidth);
        intent.putExtra("return-data", false);
        //剪切后的图片直接保存到要上传的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(uploadPath)));
        // intent.putExtra("outputFormat",
        // Bitmap.CompressFormat.JPEG.toString());

//        activity.startActivityForResult(intent, cropPhoto);
    }

    // 进行剪切
    public static void startUCrop(Activity myActivity, String sourcePath, String uploadPath) {
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
        UCrop.of(Uri.fromFile(new File(sourcePath)), Uri.fromFile(new File(uploadPath)))
                .withAspectRatio(1, 1)
                .withMaxResultSize(maxWidth, maxHeight).withOptions(options)
                .start(myActivity);
    }

    /**
     * 删除已提交的图片
     */
    public void deleteSubmitedImg() {
        try {
            for (String path : imgpathlist) {
                if (new File(path).exists()) {
                    new File(path).delete();
                    AAMethod.updateGallery(myActivity, new String[]{path});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取当前点击事件的照片的名字
     */
    private String getImgname(View v) {
        return AADate.getCurrentTime(AADate.ymdhms_name) + new Random().nextInt(1000) + ".jpg";
    }
    /*
     * 获取当前点击事件的照片的名字
	 */
    // private String getImgname(View v) {
    // String name = "";
    // int k = 0;
    // for (int i = 1; i <= lsimg.size(); i++) {
    // if (lsimg.get(i - 1) == v) {
    // k = i;
    // }
    // }
    // switch (k) {
    // case 1:
    // name = "img1.jpg";
    // break;
    // case 2:
    // name = "img2.jpg";
    // break;
    // case 3:
    // name = "img3.jpg";
    // break;
    // case 4:
    // name = "img4.jpg";
    // break;
    // case 5:
    // name = "img5.jpg";
    // break;
    // case 6:
    // name = "img6.jpg";
    // break;
    // case 7:
    // name = "img7.jpg";
    // break;
    // case 8:
    // name = "img8.jpg";
    // break;
    // case 9:
    // name = "img9.jpg";
    // break;
    // case 10:
    // name = "img10.jpg";
    // break;
    // }
    // return name;
    // }

    // ---------------------------------------拍照结束

    // --------------------------------------------------------------线程分割线

    private String nowImagePath = "";

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
