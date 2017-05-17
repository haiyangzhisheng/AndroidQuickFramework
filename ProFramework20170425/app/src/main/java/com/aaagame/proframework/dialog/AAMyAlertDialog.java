package com.aaagame.proframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaagame.proframework.R;

import java.util.ArrayList;
import java.util.List;

public class AAMyAlertDialog extends Dialog {
    /**
     * 提示框的三个按钮，自动显示和隐藏
     */
    public Button btn_ok, btn_cancle, btn_midle;
    /**
     * 当前使用的按钮，按照取消中间和确定顺序排
     */
    private List<Button> btn_use = new ArrayList<Button>();
    /**
     * 提示内容
     */
    public TextView tv_title, tv_alert, tv_alertred;
    /**
     * 标题分割线
     */
    private ImageView iv_title_line;
    /**
     * 提示内容变量初始化
     */
    private String title, message = "", messagered;
    /**
     * 按钮显示文本
     */
    private String poText, neText, midText;
    /**
     * 分割线
     */
//    private ImageView iv_midle, iv_ok;
    /**
     * 按钮监听事件
     */
    private View.OnClickListener poLis, neLis, midLis;
    /**
     * 标题容器
     */
    private LinearLayout lin_title;
    /**
     * 弹出框容器
     */
    private LinearLayout line_parent;
    /**
     * 调用的Activity实例
     */
    private Context myActivity;
    /**
     * 弹出框背景颜色，默认白色
     */
    private int dialogBgColor = Color.parseColor("#E6FFFFFF");

    /**
     * 弹出框圆角半径，默认10，单位dip
     */
    private int dialogRadius = 10;
    /**
     * 按钮默认颜色，默认白色
     */
    private int btnDefaultColor = Color.TRANSPARENT;
    /**
     * 按钮点击颜色，默认灰色
     */
    private int btnPressedColor = Color.parseColor("#64BDBDBD");
    /**
     * 标题字体颜色
     */
    private int titleColor = Color.parseColor("#323232");
    /**
     * 标题分割线颜色
     */
    private int titleLineColor = Color.parseColor("#EEEEEE");
    /**
     * 图标
     */
    private ImageView iv_alert;
    private int iv_alert_id = -1;

    /**
     * 构造方法，默认设置点击外部不可消失
     *
     * @param activity
     */
    public AAMyAlertDialog(Context activity) {
        super(activity);
        this.myActivity = activity;
        this.setCanceledOnTouchOutside(false);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置显示内容
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 设置红色提醒内容
     *
     * @param message
     */
    public void setMessageRed(String message) {
        this.messagered = message;
    }

    /**
     * 设置确定按钮内容和监听，当只有一个按钮时，只设置这个方法，否则布局会异常
     *
     * @param text
     * @param l
     */
    public void setPositiveButton(String text, View.OnClickListener l) {
        this.poText = text;
        this.poLis = l;
    }

    /**
     * 设置取消按钮内容和监听
     *
     * @param text
     * @param l
     */
    public void setNegativeButton(String text, View.OnClickListener l) {
        this.neText = text;
        this.neLis = l;
    }

    /**
     * 设置中间按钮内容和监听
     *
     * @param text
     * @param l
     */
    public void setMidleButton(String text, View.OnClickListener l) {
        this.midText = text;
        this.midLis = l;
    }

    /**
     * 设置弹出框背景颜色
     */
    public void setDialogBgColor(int color) {
        this.dialogBgColor = color;
    }

    /**
     * 设置弹出框圆角半径
     *
     * @param radius
     */
    public void setDialogRadius(int radius) {
        this.dialogRadius = radius;
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow(); // 得到对话框
        window.setWindowAnimations(R.style.aa_dialogWindowAnim); // 设置窗口弹出动画
        // 设置对话框背景为透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (lastDialog != null && !mustAlert) {
            lastDialog.dismiss();
        }
        lastDialog = this;
    }

    public boolean mustAlert = false;

    /**
     * 设置不允许被覆盖显示
     */
    public void setMustAlert() {
        mustAlert = true;
    }

    public static AAMyAlertDialog lastDialog;

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

    /**
     * 设置图标，传入资源id值
     *
     * @param icon
     */
    public void setIvAlert(int icon) {
        iv_alert_id = icon;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 初始化变量,将半径的dp转换为pix
        dialogRadius = dip2px(myActivity, dialogRadius);
        setContentView(R.layout.aa_my_alert_dialog);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_midle = (Button) findViewById(R.id.btn_midle);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_alert = (TextView) findViewById(R.id.tv_alert);
        tv_alertred = (TextView) findViewById(R.id.tv_alertred);
        iv_alert = (ImageView) findViewById(R.id.iv_alert);
//        iv_midle = (ImageView) findViewById(R.id.iv_midle);
//        iv_ok = (ImageView) findViewById(R.id.iv_ok);
        lin_title = (LinearLayout) findViewById(R.id.lin_title);
        line_parent = (LinearLayout) findViewById(R.id.line_parent);
        iv_title_line = (ImageView) findViewById(R.id.iv_title_line);
        if (iv_alert_id != -1) {
            iv_alert.setBackgroundResource(iv_alert_id);
            iv_alert.setVisibility(View.VISIBLE);
        }
        // 初始化布局颜色
        tv_title.setTextColor(titleColor);
        iv_title_line.setBackgroundColor(titleLineColor);
        // 设置弹出框背景
        GradientDrawable mbg = new GradientDrawable();// 创建drawable
        mbg.setColor(dialogBgColor);
        mbg.setCornerRadius(dialogRadius);
        line_parent.setBackgroundDrawable(mbg);
        if (message.contains("<html>")) {
            tv_alert.setText(Html.fromHtml(message));
        } else {
            tv_alert.setText(message);
        }
        // 通过按钮数量设置按钮样式
        int buttonCount = 0;
        // 未设置btn_cancle隐藏
        if (neText == null) {
            btn_cancle.setVisibility(View.GONE);
//            iv_ok.setVisibility(View.GONE);
        } else {
            btn_use.add(btn_cancle);
            buttonCount++;
            btn_cancle.setVisibility(View.VISIBLE);
            btn_cancle.setText(neText);
            btn_cancle.setOnClickListener(neLis);
        }
        if (midText == null) {
            btn_midle.setVisibility(View.GONE);
//            iv_midle.setVisibility(View.GONE);
        } else {
            btn_use.add(btn_midle);
            buttonCount++;
            btn_midle.setVisibility(View.VISIBLE);
//            iv_midle.setVisibility(View.VISIBLE);
            btn_midle.setText(midText);
            btn_midle.setOnClickListener(midLis);
        }
        // 未设置btn_ok隐藏
        if (poText == null) {
            btn_ok.setVisibility(View.GONE);
        } else {
            btn_use.add(btn_ok);
            buttonCount++;
            btn_ok.setVisibility(View.VISIBLE);
            btn_ok.setText(poText);
            btn_ok.setOnClickListener(poLis);
//            iv_ok.setVisibility(View.VISIBLE);
        }
        if (title == null) {
            lin_title.setVisibility(View.GONE);
        } else {
            lin_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
        if (messagered == null) {
            tv_alertred.setVisibility(View.GONE);
        } else {
            tv_alertred.setText(messagered);
        }
        // ------------------设置按钮点击效果
//        if (buttonCount == 1) {
//            // 当只有一个按钮时
//            iv_ok.setVisibility(View.GONE);
//            iv_midle.setVisibility(View.GONE);
//            // 默认背景
//            GradientDrawable gd1 = new GradientDrawable();// 创建drawable
//            gd1.setColor(btnDefaultColor);
//            gd1.setCornerRadii(new float[]{0, 0, 0, 0, dialogRadius, dialogRadius, dialogRadius, dialogRadius});
//            // 点击背景
//            GradientDrawable gd2 = new GradientDrawable();// 创建drawable
//            gd2.setColor(btnPressedColor);
//            gd2.setCornerRadii(new float[]{0, 0, 0, 0, dialogRadius, dialogRadius, dialogRadius, dialogRadius});
//            // 定义选择器
//            StateListDrawable selector = new StateListDrawable();
//            selector.addState(new int[]{-android.R.attr.state_pressed}, gd1);
//            selector.addState(new int[]{android.R.attr.state_pressed}, gd2);
//            btn_use.get(View.VISIBLE).setBackgroundDrawable(selector);
//        }
//        if (buttonCount == 2) {
//            if (btn_use.get(View.VISIBLE) == btn_midle) {
//                iv_midle.setVisibility(View.GONE);
//            }
//            try {
//                // 默认背景
//                GradientDrawable gd1 = new GradientDrawable();// 创建drawable
//                gd1.setColor(btnDefaultColor);
//                gd1.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, dialogRadius, dialogRadius});
//                // 点击背景
//                GradientDrawable gd2 = new GradientDrawable();// 创建drawable
//                gd2.setColor(btnPressedColor);
//                gd2.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, dialogRadius, dialogRadius});
//                // 定义选择器
//                StateListDrawable selector = new StateListDrawable();
//                selector.addState(new int[]{-android.R.attr.state_pressed}, gd1);
//                selector.addState(new int[]{android.R.attr.state_pressed}, gd2);
//                btn_use.get(View.VISIBLE).setBackgroundDrawable(selector);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                // 默认背景
//                GradientDrawable gd1 = new GradientDrawable();// 创建drawable
//                gd1.setColor(btnDefaultColor);
//                gd1.setCornerRadii(new float[]{0, 0, 0, 0, dialogRadius, dialogRadius, 0, 0});
//                // 点击背景
//                GradientDrawable gd2 = new GradientDrawable();// 创建drawable
//                gd2.setColor(btnPressedColor);
//                gd2.setCornerRadii(new float[]{0, 0, 0, 0, dialogRadius, dialogRadius, 0, 0});
//                // 定义选择器
//                StateListDrawable selector = new StateListDrawable();
//                selector.addState(new int[]{-android.R.attr.state_pressed}, gd1);
//                selector.addState(new int[]{android.R.attr.state_pressed}, gd2);
//                btn_use.get(1).setBackgroundDrawable(selector);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (buttonCount == 3) {
//
//            try {
//                // 默认背景
//                GradientDrawable gd1 = new GradientDrawable();// 创建drawable
//                gd1.setColor(btnDefaultColor);
//                gd1.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, dialogRadius, dialogRadius});
//                // 点击背景
//                GradientDrawable gd2 = new GradientDrawable();// 创建drawable
//                gd2.setColor(btnPressedColor);
//                gd2.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, dialogRadius, dialogRadius});
//                // 定义选择器
//                StateListDrawable selector = new StateListDrawable();
//                selector.addState(new int[]{-android.R.attr.state_pressed}, gd1);
//                selector.addState(new int[]{android.R.attr.state_pressed}, gd2);
//                btn_use.get(View.VISIBLE).setBackgroundDrawable(selector);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                // 默认背景
//                GradientDrawable gd1 = new GradientDrawable();// 创建drawable
//                gd1.setColor(btnDefaultColor);
//                // 点击背景
//                GradientDrawable gd2 = new GradientDrawable();// 创建drawable
//                gd2.setColor(btnPressedColor);
//                // 定义选择器
//                StateListDrawable selector = new StateListDrawable();
//                selector.addState(new int[]{-android.R.attr.state_pressed}, gd1);
//                selector.addState(new int[]{android.R.attr.state_pressed}, gd2);
//                btn_use.get(1).setBackgroundDrawable(selector);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//                // 默认背景
//                GradientDrawable gd1 = new GradientDrawable();// 创建drawable
//                gd1.setColor(btnDefaultColor);
//                gd1.setCornerRadii(new float[]{0, 0, 0, 0, dialogRadius, dialogRadius, 0, 0});
//                // 点击背景
//                GradientDrawable gd2 = new GradientDrawable();// 创建drawable
//                gd2.setColor(btnPressedColor);
//                gd2.setCornerRadii(new float[]{0, 0, 0, 0, dialogRadius, dialogRadius, 0, 0});
//                // 定义选择器
//                StateListDrawable selector = new StateListDrawable();
//                selector.addState(new int[]{-android.R.attr.state_pressed}, gd1);
//                selector.addState(new int[]{android.R.attr.state_pressed}, gd2);
//                btn_use.get(2).setBackgroundDrawable(selector);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    private int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
