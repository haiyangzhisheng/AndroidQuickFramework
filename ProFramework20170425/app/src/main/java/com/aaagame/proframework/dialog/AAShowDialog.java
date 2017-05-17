package com.aaagame.proframework.dialog;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.aaagame.proframework.activity.BaseFragmentActivity;
import com.aaagame.proframework.utils.AACom;


public class AAShowDialog {
    public static void showAlert(Activity context, String msg) {
        showAlert(false, context, null, null, msg);
    }

    public static void showAlert(boolean noFinish, Activity context, String msg) {
        showAlert(noFinish, context, null, null, msg);
    }

    public static void showAlert(final boolean noFinish, final Activity context, String btn_text, String title,
                                 String msg) {
        final AAMyAlertDialog md = new AAMyAlertDialog(context);
        if (AACom.isEmpty(btn_text)) {
            btn_text = "确 定";
        }
        md.setPositiveButton(btn_text, new OnClickListener() {

            @Override
            public void onClick(View v) {
                md.dismiss();
                if (!noFinish) {
                    try {
                        ((BaseFragmentActivity) context).animFinish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        md.setMessage(msg);
        if (!AACom.isEmpty(title)) {
            md.setTitle(title);
        }
        md.show();
    }

    public static void showAlert(final Activity context, String btn_text, String title, String msg) {
        final AAMyAlertDialog md = new AAMyAlertDialog(context);
        if (AACom.isEmpty(btn_text)) {
            btn_text = "确 定";
        }
        md.setPositiveButton(btn_text, new OnClickListener() {

            @Override
            public void onClick(View v) {
                md.dismiss();
                context.finish();
            }
        });
        md.setMessage(msg);
        if (!AACom.isEmpty(title)) {
            md.setTitle(title);
        }
        md.show();
    }

    public static void showAlert(final boolean noFinish, Activity context, String title, String msg) {
        showAlert(noFinish, context, null, title, msg);
    }

    public static void showAlert(Activity context, String title, String msg) {
        showAlert(false, context, null, title, msg);
    }
}
