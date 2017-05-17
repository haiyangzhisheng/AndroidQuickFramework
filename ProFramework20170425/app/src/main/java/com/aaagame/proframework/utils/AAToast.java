package com.aaagame.proframework.utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aaagame.proframework.R;


public class AAToast {
    /**
     * 在弹出提示后保存对象，再下次调用时来执行cancel()先关闭之前的提示，避免提示相互覆盖
     */
    private static Toast toast = null;

    public static void toastShow(Activity act, String text) {
        View toastRoot = act.getLayoutInflater().inflate(R.layout.aa_toast, null);
        TextView tv_toast = (TextView) toastRoot.findViewById(R.id.tv_toast);
        tv_toast.setText(text);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(act);
//        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    public static void toastShowLong(Activity act, String text) {
        View toastRoot = act.getLayoutInflater().inflate(R.layout.aa_toast, null);
        TextView tv_toast = (TextView) toastRoot.findViewById(R.id.tv_toast);
        tv_toast.setText(text);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(act);
//        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastRoot);
        toast.show();
    }
}
