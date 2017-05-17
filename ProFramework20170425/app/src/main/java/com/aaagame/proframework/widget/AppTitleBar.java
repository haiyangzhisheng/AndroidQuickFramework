package com.aaagame.proframework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaagame.proframework.R;


/**
 * @author chuyh 创建日期: 2016-7-7
 */
public class AppTitleBar extends RelativeLayout {
    private TextView mBack;
    private TextView mTitle;
    private TextView mAction;
    private RelativeLayout rel_top_title;
    private static int DEFAULT_TITLE_COLOR = Color.WHITE;

    public AppTitleBar(Context context) {
        super(context);
        init(context, null);
    }

    public AppTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public AppTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @SuppressWarnings("deprecation")
    private void init(Context context, AttributeSet attr) {
        LayoutInflater.from(context).inflate(R.layout.app_title_bar, this);
        rel_top_title = (RelativeLayout) findViewById(R.id.rel_top_title);
        mBack = (TextView) findViewById(R.id.app_title_back);
        mTitle = (TextView) findViewById(R.id.app_title_text);
        mAction = (TextView) findViewById(R.id.app_title_action);

        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.aa_AppTitleBar);
        if (array != null) {
            Drawable background = array.getDrawable(R.styleable.aa_AppTitleBar_aa_bar_background);
            if (background != null) {
                rel_top_title.setBackgroundDrawable(background);
            } else {
                rel_top_title.setBackgroundColor(getResources().getColor(R.color.top_title_bar_bg));
            }
            String title = array.getString(R.styleable.aa_AppTitleBar_aa_bar_title);
            DEFAULT_TITLE_COLOR = getResources().getColor(R.color.app_white);
            int color = array.getColor(R.styleable.aa_AppTitleBar_aa_bar_title_text_color, DEFAULT_TITLE_COLOR);
            if (!TextUtils.isEmpty(title)) {
                mTitle.setText(title);
                mTitle.setTextColor(color);
            }
        } else {
//            this.setBackgroundColor(this.getResources().getColor(R.color.app_white));
        }

        setTextAndImg(array, mBack, R.styleable.aa_AppTitleBar_aa_back_text, R.styleable.aa_AppTitleBar_aa_back_img);

        setTextAndImg(array, mAction, R.styleable.aa_AppTitleBar_aa_action_text,
                R.styleable.aa_AppTitleBar_aa_action_img);

        if (array != null) {
            array.recycle();
        }
    }

    private void setTextAndImg(TypedArray array, TextView textView, int textId, int imgId) {
        if (array != null) {
            Drawable drawable = array.getDrawable(imgId);
            String backText = array.getString(textId);
            if (!TextUtils.isEmpty(backText)) {
                textView.setText(backText);
            }
            setDrawable(drawable, textView);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.back_arrow);
            setDrawable(drawable, textView);
        }

    }

    private void setDrawable(Drawable drawable, TextView textView) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicWidth());
            textView.setCompoundDrawables(drawable, null, null, null);
        }
    }

    public TextView getAction() {
        mAction.setVisibility(VISIBLE);
        return mAction;
    }

    public TextView getBack() {
        return mBack;
    }
    public RelativeLayout getBarRelativity() {
        return rel_top_title;
    }
    public TextView getTitle() {
        return mTitle;
    }

    public void setTitle(String text) {
        mTitle.setText(text);
    }

    public void setTitle(int text) {
        mTitle.setText(text);
    }
}
