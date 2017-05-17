package com.lcodecore.tkrefreshlayout.Footer;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.R;

/**
 * Created by Administrator on 2016/12/19.
 */

public class TvLoadingView extends FrameLayout  implements IBottomView {
    public TvLoadingView(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.tv_loadingview, null);
        iv_load = (ImageView) view.findViewById(R.id.iv_load);
        addView(view);
    }

    public TvLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    ImageView iv_load;

    public TvLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        ((AnimationDrawable) iv_load.getDrawable()).start();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}
