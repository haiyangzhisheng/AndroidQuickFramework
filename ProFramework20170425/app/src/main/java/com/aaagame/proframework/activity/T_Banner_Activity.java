package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.aaagame.proframework.R;
import com.aaagame.proframework.utils.AACom;
import com.aaagame.proframework.utils.AAMethod;
import com.aaagame.proframework.utils.BannerImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;

@ContentView(R.layout.t_activity_banner)
public class T_Banner_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBanner();
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

    @ViewInject(R.id.banner)
    Banner banner;
    String[] urls = {"http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg"};

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(AAMethod.getScreenWidth(myActivity), AAMethod.getScreenWidth(myActivity));
        banner.setLayoutParams(lp);
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                AACom.imageBrower(myActivity, position - 1, urls);
            }
        });
        banner.setImages(Arrays.asList(urls)).setImageLoader(new BannerImageLoader()).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            banner.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

}
