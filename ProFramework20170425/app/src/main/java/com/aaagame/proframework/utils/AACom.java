package com.aaagame.proframework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaagame.proframework.imagebrowser.ImagePagerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.aaagame.proframework.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

public class AACom {

    public static String getStrFrEdit(EditText et) {
        return et.getText().toString().trim();
    }

    public static String getStrFrEdit(Activity activity, int etid) {
        return ((EditText) activity.findViewById(etid)).getText().toString().trim();
    }

    public static String getStrFrTv(Activity activity, int tvid) {
        return ((TextView) activity.findViewById(tvid)).getText().toString().trim();
    }

    public static boolean isEditEmpty(Activity activity, int etid) {
        EditText ee = ((EditText) activity.findViewById(etid));
        return ((EditText) activity.findViewById(etid)).getText().toString().trim().equals("");
    }

    public static boolean isTvEmpty(Activity activity, int etid) {
        return ((TextView) activity.findViewById(etid)).getText().toString().trim().equals("");
    }

    public static String getStrFrTv(TextView tv) {
        return tv.getText().toString().trim();
    }

    public static String getMsgName(Object object) {
        String v = object.getClass().getName();
        return v.substring(v.length() - 4);
    }

    public static Type getStringListType() {
        return new TypeToken<List<String>>() {
        }.getType();
    }

    /**
     * 通过获取指定颜色的文本
     *
     * @param context
     * @param colorResource
     * @param text
     * @return
     */
    public static String getColorHtmlText(Context context, int colorResource, String text) {
        return "<font color='" + "#" + context.getResources().getString(colorResource).substring(3) + "'>" + text
                + "</font>";
    }

    public static String getColorHtmlTextArrow(Context context, int colorResource, String text) {
        return "<b><strong><font color='" + "#" + context.getResources().getString(colorResource).substring(3) + "'>"
                + text + "</font></strong></b>";
    }

    public static Gson gson;

    /**
     * 获取gson
     *
     * @return
     */
    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static boolean isEditEmpty(EditText et) {
        if (et.getText().toString().trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(String value) {
        if (value == null || value.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打开分享面板
     *
     * @param activity
     * @param title
     * @param content
     * @param url
     * @param image
     */
    public static void showShare(final Activity activity, String title, String content, String url, String image) {
        // 请求存储权限
        AACamera.checkStoragePer(activity);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setShareboardBackgroundColor(activity.getResources().getColor(R.color.app_white));
//        config.setCancelButtonBackground(getR)
        ShareAction shareAction = new ShareAction(activity);

        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        if (!TextUtils.isEmpty(image)) {
            UMImage umImage = new UMImage(activity, image);
            web.setThumb(umImage);  //缩略图
        }
        web.setDescription(content);//描述
        shareAction.withMedia(web);
        shareAction.setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {
                        try {
                            if (t.getMessage().contains("没有安装应用")) {
                                AAToast.toastShow(activity, "没有安装应用");
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (t != null) {
                            Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(activity, " 取消分享", Toast.LENGTH_SHORT).show();
                    }
                }).open(config);
    }

    /**
     * 加载图片为圆形
     *
     * @param imageView
     * @param path
     */
    public static void displayCircleImage(ImageView imageView, String path) {
        ImageOptions imageOptions = new ImageOptions.Builder().setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                // 加载中或错误图片的ScaleType
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true)
                .setLoadingDrawableId(R.drawable.default_head)
                .setFailureDrawableId(R.drawable.default_head)
                .build();
        x.image().bind(imageView, path, imageOptions);
    }

    /**
     * 加载图片为圆形
     *
     * @param imageView
     * @param path
     */
    public static void displayCircleImage(ImageView imageView, String path, Callback.CommonCallback<Drawable> callback) {
        ImageOptions imageOptions = new ImageOptions.Builder().setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true)
                .setLoadingDrawableId(R.drawable.default_head)
                .setFailureDrawableId(R.drawable.default_head)
                .build();
        x.image().bind(imageView, path, imageOptions, callback);
    }

    /**
     * 加载图片FIT_XY
     *
     * @param imageView
     * @param path
     */
    public static void displayFitImage(ImageView imageView, String path) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.drawable.default_image)
                .setFailureDrawableId(R.drawable.default_image)
                .build();
        x.image().bind(imageView, path, imageOptions);
    }

    /**
     * 加载图片Gif FIT_XY
     *
     * @param imageView
     * @param path
     */
    public static void displayGifFitImage(ImageView imageView, String path) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.drawable.default_image)
                .setFailureDrawableId(R.drawable.default_image)
                .setIgnoreGif(false)
                .build();
        x.image().bind(imageView, path, imageOptions);
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    public static void imageBrower(Activity myActivity, int position, String[] urls2, int photo_delete_mark) {
        Intent intent = new Intent(myActivity, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra("showDelete", true);
        myActivity.startActivityForResult(intent, photo_delete_mark);
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    public static void imageBrower(Activity myActivity, int position, String[] urls2) {
        Intent intent = new Intent(myActivity, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        myActivity.startActivity(intent);
    }

    /**
     * 格式化保留两位小数
     *
     * @param price
     * @return
     */
    public static String get2price(String price) {
        try {
            return new java.text.DecimalFormat("######0.00").format(Double.valueOf(price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化无小数点价格
     *
     * @param price
     * @return
     */
    public static String get2nopointprice(String price) {
        try {
            return new java.text.DecimalFormat("#0").format(Double.valueOf(price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
