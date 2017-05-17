package com.aaagame.proframework.utils;

import android.content.Context;
import android.widget.ImageView;

import com.aaagame.proframework.R;
import com.youth.banner.loader.ImageLoader;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Administrator on 2016/12/30.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageOptions imageOptions = new ImageOptions.Builder()
//                .setSize(0, DensityUtil.dip2px(120))
//            .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
//                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.drawable.default_image)
                .setFailureDrawableId(R.drawable.default_image)
                .build();
        x.image().bind(imageView, ((String) path), imageOptions);
    }
}
