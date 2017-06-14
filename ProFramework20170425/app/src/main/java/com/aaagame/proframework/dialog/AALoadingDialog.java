package com.aaagame.proframework.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaagame.proframework.R;


public class AALoadingDialog extends Dialog {

    private String initstr, strtitle;

    public AALoadingDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        this.setCanceledOnTouchOutside(false);
    }

    public void setTitle(String strtitle) {
        this.strtitle = strtitle;
    }

    public void setMsgNoShowCancel(String initstr) {
        this.initstr = initstr;
        if (tv_show != null) {
            tv_show.setText(initstr);
        }
    }

    public void setMsgNoShow(final AsyncTask asyncTask, String initstr) {
        this.initstr = initstr;
        if (tv_show != null) {
            tv_show.setText(initstr);
        }
        this.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                asyncTask.cancel(true);
            }
        });
    }

    public void setMsg(String initstr) {
        this.initstr = initstr;
        if (tv_show != null) {
            tv_show.setText(initstr);
        }
        this.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                try {
                    // asyncTask.cancel(true);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    public void setMsg(final AsyncTask asyncTask, String initstr) {
        this.initstr = initstr;
        if (tv_show != null) {
            tv_show.setText(initstr);
        }
        this.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                try {
                    asyncTask.cancel(true);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    private TextView tv_show, tv_title;
    private ImageView iv_loading;
    private Activity activity;
    private LinearLayout lin_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置对话框背景为透明
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.loading_dialog);
        tv_show = (TextView) findViewById(R.id.tv_show);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_loading = (ImageView) findViewById(R.id.iv_loading);
        lin_title = (LinearLayout) findViewById(R.id.lin_title);
        load();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        // 获取屏幕宽、高用
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        lp.width = (int) (width * 0.65); // 宽度设置为屏幕的0.95
        dialogWindow.setAttributes(lp);
    }

    private void load() {
        if (strtitle == null) {
            lin_title.setVisibility(View.GONE);
        } else {
            lin_title.setVisibility(View.VISIBLE);
            tv_title.setText(strtitle);
        }
        tv_show.setText(initstr);
        strtitle = null;
        initstr = null;
        // 加载动画
        Animation anima = AnimationUtils.loadAnimation(activity, R.anim.loading_anim);
        LinearInterpolator lir = new LinearInterpolator();
        anima.setInterpolator(lir);
        // 使用ImageView显示动画
        iv_loading.startAnimation(anima);
//        initAnim();
    }

//    private void initAnim() {
//        //摇摆
//        TranslateAnimation translateAnimation = new TranslateAnimation(150f, 350f, 50, 50);
//        RotateAnimation rotateAnimation=new Ro
//        translateAnimation.setDuration(1000);
//        translateAnimation.setRepeatCount(Animation.INFINITE);
//        translateAnimation.setRepeatMode(Animation.REVERSE);
//        iv_loading.setAnimation(translateAnimation); //这里iv就是我们要执行动画的item，例如一个imageView
//        translateAnimation.start();
//    }
}
