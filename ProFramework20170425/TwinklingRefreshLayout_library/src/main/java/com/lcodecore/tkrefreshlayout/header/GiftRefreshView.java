package com.lcodecore.tkrefreshlayout.header;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lcodecore.tkrefreshlayout.R;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: myName
 * @date: 2017-03-10 16:13
 */
public class GiftRefreshView extends FrameLayout implements IHeaderView {

    private ImageView refreshArrow;
    private TextView refreshTextView;

    public GiftRefreshView(Context context) {
        this(context, null);
    }

    public GiftRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), R.layout.view_giftheader, null);
        refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
        refreshTextView = (TextView) rootView.findViewById(R.id.tv);
        addView(rootView);
    }

    public void setArrowResource(@DrawableRes int resId) {
    }

    public void setTextColor(@ColorInt int color) {
        refreshTextView.setTextColor(color);
    }

    public void setPullDownStr(String pullDownStr1) {
        pullDownStr = pullDownStr1;
    }

    public void setReleaseRefreshStr(String releaseRefreshStr1) {
        releaseRefreshStr = releaseRefreshStr1;
    }

    public void setRefreshingStr(String refreshingStr1) {
        refreshingStr = refreshingStr1;
    }

    private String pullDownStr = "下拉即可刷新···";
    private String releaseRefreshStr = "正在刷新···";
    private String refreshingStr = "正在刷新···";

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) refreshTextView.setText(pullDownStr);
        if (fraction > 1f) refreshTextView.setText(releaseRefreshStr);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) {
            refreshTextView.setText(pullDownStr);

        }

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshTextView.setText(releaseRefreshStr);
        refreshArrow.setVisibility(VISIBLE);
        // 加载动画
        Animation anima = AnimationUtils.loadAnimation(getContext(), R.anim.scal_dialog);
        LinearInterpolator lir = new LinearInterpolator();
        anima.setInterpolator(lir);
        // 使用ImageView显示动画
        refreshArrow.startAnimation(anima);

    }

    @Override
    public void onFinish(OnAnimEndListener listener) {
        listener.onAnimEnd();
    }

    @Override
    public void reset() {
        refreshArrow.setVisibility(VISIBLE);
        refreshTextView.setText(pullDownStr);
    }
}

