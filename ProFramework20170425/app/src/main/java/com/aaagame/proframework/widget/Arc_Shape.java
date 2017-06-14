package com.aaagame.proframework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.aaagame.proframework.R;

import org.xutils.common.util.DensityUtil;

/**
 * Created by Administrator on 2017/6/6.
 */
public class Arc_Shape extends View {
    private int bgColor;
    private float cutWidth;

    public Arc_Shape(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.aa_bottom_circle);
        if (array != null) {
            bgColor = array.getColor(R.styleable.aa_bottom_circle_aa_bg_color, getResources().getColor(R.color.app_blue));
            cutWidth = array.getDimension(R.styleable.aa_bottom_circle_aa_cut_width, 0);
        } else {
            bgColor = getResources().getColor(R.color.app_blue);
            cutWidth = 0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas2) {
        super.onDraw(canvas2);
        Bitmap whiteBgBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(whiteBgBitmap);
        /***********配置画笔*************/
        Paint paint = new Paint();    //采用默认设置创建一个画笔
        paint.setAntiAlias(true);//使用抗锯齿功能
        paint.setColor(bgColor);    //设置画笔的颜色为绿色

        paint.setStyle(Paint.Style.FILL);//设置画笔类型为填充
        /***********绘制圆弧*************/ /**
         * 这是一个居中的圆
         */
        RectF oval = new RectF(0, 0, getWidth() + DensityUtil.dip2px(cutWidth) * 2, getHeight());
        oval.offset(-DensityUtil.dip2px(cutWidth), 0);//使rectf_head所确定的矩形向右偏移100像素，向下偏移20像素
        canvas.drawArc(oval, 0, 180, false, paint);//绘制圆弧，不含圆心
        canvas2.drawBitmap(whiteBgBitmap, new Rect(0, getHeight() / 2, getWidth(), getHeight()), oval, paint);
        whiteBgBitmap.recycle();
    }
}
