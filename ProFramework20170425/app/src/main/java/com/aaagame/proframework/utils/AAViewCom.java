package com.aaagame.proframework.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AAViewCom {
	/**
	 * 获取EditText
	 * 
	 * @param activity
	 * @param viewId
	 * @return
	 */
	
	public static EditText getEt(Activity activity, int viewId) {
		return (EditText) activity.findViewById(viewId);
	}
	public static EditText getEt(View view, int viewId) {
		return (EditText) view.findViewById(viewId);
	}
	public static EditText getEt(Dialog dialog, int viewId) {
		return (EditText) dialog.findViewById(viewId);
	}

	public static RadioButton getRadioButton(Activity activity, int viewId) {
		return (RadioButton) activity.findViewById(viewId);
	}

	public static RadioGroup getRadioGroup(Activity activity, int viewId) {
		return (RadioGroup) activity.findViewById(viewId);
	}

	public static RadioButton getRadioButton(View view, int viewId) {
		return (RadioButton) view.findViewById(viewId);
	}

	/**
	 * 获取Button
	 * 
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static Button getBtn(Activity activity, int viewId) {
		return (Button) activity.findViewById(viewId);
	}

	public static Button getBtn(Dialog dialog, int viewId) {
		return (Button) dialog.findViewById(viewId);
	}

	public static ImageButton getImgBtn(Activity activity, int viewId) {
		return (ImageButton) activity.findViewById(viewId);
	}

	public static Button getBtn(View view, int viewId) {
		return (Button) view.findViewById(viewId);
	}

	public static View getView(Activity activity, int viewId) {
		return activity.findViewById(viewId);
	}

	public static View getView(View view, int viewId) {
		return view.findViewById(viewId);
	}

	public static TextView getTv(Activity activity, int viewId) {
		return (TextView) activity.findViewById(viewId);
	}

	public static TextView getTv(Dialog dialog, int viewId) {
		return (TextView) dialog.findViewById(viewId);
	}

	public static TextView getTv(View view, int viewId) {
		return (TextView) view.findViewById(viewId);
	}

	public static LinearLayout getLine(Activity activity, int viewId) {
		return (LinearLayout) activity.findViewById(viewId);
	}
	public static RelativeLayout getRela(View view, int viewId) {
		return (RelativeLayout) view.findViewById(viewId);
	}
	public static RelativeLayout getRela(Activity activity, int viewId) {
		return (RelativeLayout) activity.findViewById(viewId);
	}
	public static LinearLayout getLine(Dialog dialog, int viewId) {
		return (LinearLayout) dialog.findViewById(viewId);
	}

	public static LinearLayout getLine(View view, int viewId) {
		return (LinearLayout) view.findViewById(viewId);
	}

	public static ImageView getIv(Activity activity, int viewId) {
		return (ImageView) activity.findViewById(viewId);
	}

	public static ImageView getIv(Dialog dialog, int viewId) {
		return (ImageView) dialog.findViewById(viewId);
	}

	public static CheckBox getCheckBox(Activity activity, int viewId) {
		return (CheckBox) activity.findViewById(viewId);
	}

	public static ImageView getIv(View view, int viewId) {
		return (ImageView) view.findViewById(viewId);
	}

	public static ListView getLv(Activity activity, int viewId) {
		return (ListView) activity.findViewById(viewId);
	}

	public static ListView getLv(Dialog dialog, int viewId) {
		return (ListView) dialog.findViewById(viewId);
	}

	public static ListView getLv(View view, int viewId) {
		return (ListView) view.findViewById(viewId);
	}

	public static WebView getWv(Activity activity, int viewId) {
		return (WebView) activity.findViewById(viewId);
	}
}
