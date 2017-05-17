package com.lcodecore.tkrefreshlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ScaleView extends View {

    private Bitmap initialBitmap;
    private int measuredWidth;
    private int measuredHeight;
    private float mCurrentProgress;
    private Bitmap scaledBitmap;

    public ScaleView(Context context, AttributeSet attrs,
                     int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        initialBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gift_loading));
    }

    /**
     * 重写onMeasure方法主要是设置wrap_content时 View的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //根据设置的宽度来计算高度
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureWidth(heightMeasureSpec));
    }

    /**
     * 当wrap_content的时候，宽度即为图片的宽度
     *
     * @param widMeasureSpec
     * @return
     */
    private int measureWidth(int widMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(widMeasureSpec);
        int mode = MeasureSpec.getMode(widMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    /**
     * 在onLayout里面获得测量后View的宽高
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
        //  给图片进行等比例的缩放
        scaledBitmap = Bitmap.createScaledBitmap(initialBitmap, measuredWidth, measuredHeight, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这个方法是对画布进行缩放，从而达到图片的缩放，第一个参数为宽度缩放比例，第二个参数为高度缩放比例，
        canvas.scale(mCurrentProgress, mCurrentProgress, measuredWidth / 2, measuredHeight / 2);
        //将等比例缩放后的图形画在画布上面
        canvas.drawBitmap(scaledBitmap, 0, 0, null);

    }

    /**
     * 设置缩放比例，从0到1  0为最小 1为最大
     *
     * @param currentProgress
     */
    public void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;
    }

}